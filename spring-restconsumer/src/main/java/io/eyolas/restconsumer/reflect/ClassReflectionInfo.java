package io.eyolas.restconsumer.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Builder;

/**
 *
 * @author eyolas
 */
@Builder
@Data
public class ClassReflectionInfo {

    private final Class clazz;

    private Map<Method, MethodReflectionInfo> methodReflexionInfos = new HashMap<>();
}
