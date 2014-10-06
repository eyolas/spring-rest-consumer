package io.eyolas.restconsumer.reflect.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eyolas.restconsumer.exception.ParameterAnnotationException;
import io.eyolas.restconsumer.annotation.Path;
import io.eyolas.restconsumer.annotation.Query;
import io.eyolas.restconsumer.annotation.QueryMap;
import io.eyolas.restconsumer.reflect.ClassReflectionInfo;
import io.eyolas.restconsumer.reflect.MethodReflectionInfo;
import io.eyolas.restconsumer.reflect.ParamReflectionInfo;
import io.eyolas.restconsumer.rest.RestMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author eyolas
 */
public class ReflectionUtils {

    /**
     * Extract annotation
     *
     * @param clazz
     * @return ClassReflexionInfo
     */
    public static ClassReflectionInfo extractAnnotation(Class clazz) throws ParameterAnnotationException {
        Map<Method, MethodReflectionInfo> methodReflexionInfos = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            MethodReflectionInfo info = new MethodReflectionInfo(method);

            //extract method annotations
            Annotation[] mAnnotations = method.getAnnotations();
            if (null != mAnnotations && mAnnotations.length > 0) {
                info.getAnnotations().addAll(Arrays.asList(mAnnotations));
            }

            //extract parameters annotations
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Class[] parameterTypes = method.getParameterTypes();

            int i = 0;
            for (Annotation[] pAnnotations : parameterAnnotations) {
                Class parameterType = parameterTypes[i];

                if (null == pAnnotations || pAnnotations.length == 0) {
                    throw new ParameterAnnotationException("Parameter must have query annotation");
                }

                List<Annotation> anno = Arrays.asList(pAnnotations);
                if (!hasQueryAnnotation(anno)) {
                    throw new ParameterAnnotationException("Parameter must have query annotation");
                }

                for (Annotation annotation : anno) {
                    if (isAssignableFrom(annotation, QueryMap.class) && !Map.class.isAssignableFrom(parameterType)) {
                        throw new ParameterAnnotationException("QueryMap must be an map");
                    }
                }

                info.getParams().add(ParamReflectionInfo.builder()
                        .annotations(anno)
                        .parameterType(parameterType)
                        .build());

                i++;
            }

            methodReflexionInfos.put(method, info);
        }

        return ClassReflectionInfo.builder()
                .clazz(clazz)
                .methodReflexionInfos(methodReflexionInfos)
                .build();
    }

    public static boolean hasQueryAnnotation(List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (isAssignableFrom(annotation, Path.class, Query.class, QueryMap.class)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAssignableFrom(Annotation annotation, Class... annotationTypes) {
        for (Class annotationType : annotationTypes) {
            if (annotation.annotationType().isAssignableFrom(annotationType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return jackson javaType
     *
     * @param method
     * @param objectMapper
     * @return
     */
    public static JavaType getType(Method method, ObjectMapper objectMapper) {
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
    public static Map.Entry getHttpMethodInfo(MethodReflectionInfo methodReflexionInfo) {
        List<Annotation> annotations = methodReflexionInfo.getAnnotations();
        if (!CollectionUtils.isEmpty(annotations)) {
            for (Annotation annotation : annotations) {
                RestMethod restMethod = AnnotationUtils.findAnnotation(annotation.getClass(), RestMethod.class);
                if (null != restMethod && null != restMethod.value()) {
                    return new AbstractMap.SimpleEntry(restMethod, (String) AnnotationUtils.getValue(annotation));
                }
            }
        }
        return null;
    }
}
