package io.eyolas.restconsumer.utils;

import io.eyolas.restconsumer.exception.ParameterAnnotationException;
import io.eyolas.restconsumer.query.Path;
import io.eyolas.restconsumer.query.Query;
import io.eyolas.restconsumer.query.QueryMap;
import io.eyolas.restconsumer.reflexion.ClassReflexionInfo;
import io.eyolas.restconsumer.reflexion.MethodReflexionInfo;
import io.eyolas.restconsumer.reflexion.ParamReflexionInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eyolas
 */
public class ReflexionUtils {

    /**
     * Extract annotation
     *
     * @param clazz
     * @return ClassReflexionInfo
     */
    public static ClassReflexionInfo extractAnnotation(Class clazz) throws ParameterAnnotationException {
        Map<Method, MethodReflexionInfo> methodReflexionInfos = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            MethodReflexionInfo info = new MethodReflexionInfo(method);

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

                info.getParams().add(ParamReflexionInfo.builder()
                        .annotations(anno)
                        .parameterType(parameterType)
                        .build());

                i++;
            }

            methodReflexionInfos.put(method, info);
        }

        return ClassReflexionInfo.builder()
                .clazz(clazz)
                .methodReflexionInfos(methodReflexionInfos)
                .build();
    }

    public static boolean hasQueryAnnotation(List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (isAssignableFrom(annotation, Path.class) || isAssignableFrom(annotation, Query.class) || isAssignableFrom(annotation, QueryMap.class)) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean isAssignableFrom(Annotation annotation, Class annotationType) {
        return annotation.annotationType().isAssignableFrom(annotationType);
    }
}
