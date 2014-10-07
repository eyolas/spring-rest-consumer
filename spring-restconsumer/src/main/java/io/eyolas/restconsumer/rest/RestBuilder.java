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
import io.eyolas.restconsumer.annotation.QueryType;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap;
import java.util.ArrayList;
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

                Entry<RestMethod, String> httpMethodInfo = ReflectionUtils.getHttpMethodInfo(methodReflexionInfo);

                HttpMethod httpMethod = HttpMethod.GET;
                String uri = "";
                if (null != httpMethodInfo) {
                    httpMethod = httpMethodInfo.getKey().value();
                    uri = httpMethodInfo.getValue();
                }
                methodInfo.setUri(uri);
                methodInfo.setHttpMethod(httpMethod);

                List<Entry<String, QueryType>> paramsMatch = new ArrayList<>();

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
                }

                JavaType returnType = ReflectionUtils.getType(method, restEntityManager.getObjectMapper());
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

    }
}
