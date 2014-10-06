package io.eyolas.restconsumer.rest;

import io.eyolas.restconsumer.exception.ParameterAnnotationException;
import io.eyolas.restconsumer.annotation.Path;
import io.eyolas.restconsumer.annotation.Query;
import io.eyolas.restconsumer.annotation.QueryMap;
import io.eyolas.restconsumer.reflect.ClassReflectionInfo;
import io.eyolas.restconsumer.reflect.MethodReflectionInfo;
import io.eyolas.restconsumer.reflect.ParamReflectionInfo;
import io.eyolas.restconsumer.reflect.utils.ReflectionUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eyolas.restconsumer.annotation.QueryType;
import io.eyolas.restconsumer.reflect.MethodInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

/**
 *
 * @author eyolas
 */
public class RestBuilder {

    /**
     * Create the service
     *
     * @param <T> auto casting
     * @param service
     * @param restEntityManager
     * @return
     * @throws io.eyolas.restconsumer.exception.ParameterAnnotationException
     */
    public <T> T create(Class<T> service, RestEntityManager restEntityManager) throws ParameterAnnotationException {
        Assert.notNull(service, "service is mandatory");
        Assert.notNull(restEntityManager, "RestEntityManager is mandatory");
        Assert.isTrue(service.isInterface(), "service must be an interface");

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new RestHandler(ReflectionUtils.extractAnnotation(service), restEntityManager));
    }

    private class RestHandler implements InvocationHandler {

        private final ClassReflectionInfo classReflexionInfo;
        private final RestEntityManager restEntityManager;
        private final Map<Method, MethodInfo> cache = new HashMap<>();

        public RestHandler(ClassReflectionInfo classReflexionInfo, RestEntityManager restEntityManager) {
            this.classReflexionInfo = classReflexionInfo;
            this.restEntityManager = restEntityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }

            Map<String, Object> params = new HashMap<>();
            Map<String, Object> pathVariables = new HashMap<>();
            MethodInfo methodInfo = cache.get(method);

            if (null == methodInfo) {
                methodInfo = new MethodInfo();
                MethodReflectionInfo methodReflexionInfo = classReflexionInfo.getMethodReflexionInfos().get(method);

                Entry<RestMethod, String> infoMethodHttp = getInfoMethod(methodReflexionInfo);

                HttpMethod httpMethod = HttpMethod.GET;
                String uri = "";
                if (null != infoMethodHttp) {
                    httpMethod = infoMethodHttp.getKey().value();
                    uri = infoMethodHttp.getValue();
                }
                methodInfo.setUri(uri);
                methodInfo.setHttpMethod(httpMethod);

                List<Entry<String, QueryType>> paramsMatch = new ArrayList<>();
                int i = 0;
                for (ParamReflectionInfo parameter : methodReflexionInfo.getParams()) {
                    Entry<String, QueryType> ent = null;
                    for (Annotation annotation : parameter.getAnnotations()) {
                        if (ReflectionUtils.isAssignableFrom(annotation, Path.class, Query.class, QueryMap.class)) {
                            String key = (String) AnnotationUtils.getValue(annotation);
                            QueryType value;
                            if (ReflectionUtils.isAssignableFrom(annotation, Path.class)) {
                                value = QueryType.PATH;
                            } else {
                                value = QueryType.PARAM;
                            }
                            ent = new AbstractMap.SimpleEntry(key, value);
                            break;
                        }

                    }
                    paramsMatch.add(ent);
                    i++;
                }

                JavaType returnType = getType(method, restEntityManager.getObjectMapper());
                methodInfo.setParamsMatch(paramsMatch);
                methodInfo.setReturnType(returnType);
                cache.put(method, methodInfo);
            }
            for (int i = 0; i < args.length; i++) {
                Entry<String, QueryType> paramInfo = methodInfo.getParamsMatch().get(i);
                if (null != paramInfo) {
                    Object argument = args[i];
                    switch (paramInfo.getValue()) {
                        case PARAM:
                            if (argument instanceof Map) {
                                params.putAll((Map<? extends String, ? extends Object>) argument);
                            } else {
                                params.put(paramInfo.getKey(), argument);
                            }
                        case PATH:
                            pathVariables.put(paramInfo.getKey(), argument);
                    }
                }

            }

            return restEntityManager.call(methodInfo.getHttpMethod(), methodInfo.getUri(), params, pathVariables, methodInfo.getReturnType());
        }

        /**
         * Return jackson javaType
         *
         * @param method
         * @param objectMapper
         * @return
         */
        private JavaType getType(Method method, ObjectMapper objectMapper) {
            Class returnClass = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnClass)) {
                Type returnType = method.getGenericReturnType();
                if (returnType instanceof ParameterizedType) {
                    ParameterizedType paramType = (ParameterizedType) returnType;
                    Type[] argTypes = paramType.getActualTypeArguments();
                    if (argTypes.length > 0) {
                        return objectMapper.getTypeFactory().constructCollectionType(returnClass, (Class) argTypes[0]);
                    }
                }
                throw new IllegalArgumentException("Method " + method.getName() + " has unknown return type");
            } else {
                return objectMapper.getTypeFactory().constructType(method.getGenericReturnType());
            }
        }

        /**
         * Get information of method
         *
         * @param methodReflexionInfo
         * @return entry
         */
        private Entry<RestMethod, String> getInfoMethod(MethodReflectionInfo methodReflexionInfo) {
            List<Annotation> annotations = methodReflexionInfo.getAnnotations();
            for (Annotation annotation : annotations) {
                RestMethod restMethod = AnnotationUtils.findAnnotation(annotation.getClass(), RestMethod.class);
                if (null != restMethod && null != restMethod.value()) {
                    return new AbstractMap.SimpleEntry(restMethod, (String) AnnotationUtils.getValue(annotation));
                }
            }

            return null;
        }
    }
}
