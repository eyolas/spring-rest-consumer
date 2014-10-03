package io.eyolas.restconsumer.rest;

import io.eyolas.restconsumer.exception.ParameterAnnotationException;
import io.eyolas.restconsumer.query.Path;
import io.eyolas.restconsumer.query.Query;
import io.eyolas.restconsumer.query.QueryMap;
import io.eyolas.restconsumer.reflexion.ClassReflexionInfo;
import io.eyolas.restconsumer.reflexion.MethodReflexionInfo;
import io.eyolas.restconsumer.reflexion.ParamReflexionInfo;
import io.eyolas.restconsumer.utils.ReflexionUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.AbstractMap;
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
                new RestHandler(ReflexionUtils.extractAnnotation(service), restEntityManager));
    }

    private class RestHandler implements InvocationHandler {

        private final ClassReflexionInfo classReflexionInfo;
        private final RestEntityManager restEntityManager;

        public RestHandler(ClassReflexionInfo classReflexionInfo, RestEntityManager restEntityManager) {
            this.classReflexionInfo = classReflexionInfo;
            this.restEntityManager = restEntityManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            
            
            MethodReflexionInfo methodReflexionInfo = classReflexionInfo.getMethodReflexionInfos().get(method);

            Entry<RestMethod, String> infoMethodHttp = getInfoMethod(methodReflexionInfo);

            HttpMethod httpMethod = HttpMethod.GET;
            String uri = "";
            if (null != infoMethodHttp) {
                httpMethod = infoMethodHttp.getKey().value();
                uri = infoMethodHttp.getValue();
            }

            Map<String, Object> params = new HashMap<>();
            Map<String, Object> pathVariables = new HashMap<>();

            int i = 0;
            for (ParamReflexionInfo parameter : methodReflexionInfo.getParams()) {

                for (Annotation annotation : parameter.getAnnotations()) {
                    String key = (String) AnnotationUtils.getValue(annotation);
                    if (ReflexionUtils.isAssignableFrom(annotation, Path.class)) {
                        pathVariables.put(key, args[i]);
                    } else if (ReflexionUtils.isAssignableFrom(annotation, Query.class)) {
                        params.put(key, args[i]);
                    } else if (ReflexionUtils.isAssignableFrom(annotation, QueryMap.class)) {
                        params.putAll((Map<? extends String, ? extends Object>) args[i]);
                    }
                }

                i++;
            }
            method.getReturnType().getDeclaredFields();
            Class returnClass = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnClass)) {
                Type returnType = method.getGenericReturnType();
                if (returnType instanceof ParameterizedType) {
                    ParameterizedType paramType = (ParameterizedType) returnType;
                    Type[] argTypes = paramType.getActualTypeArguments();
                    if (argTypes.length > 0) {
                        System.out.println("Generic type is " + argTypes[0]);
                    }
                }
            }
            
            return restEntityManager.call(httpMethod, uri, params, pathVariables, getType(method, restEntityManager.getObjectMapper()));
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
                return null;
            } else {
                return objectMapper.getTypeFactory().constructType(method.getGenericReturnType());
            }
        }

        /**
         * Get information of method
         * @param methodReflexionInfo
         * @return entry
         */
        private Entry<RestMethod, String> getInfoMethod(MethodReflexionInfo methodReflexionInfo) {
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
