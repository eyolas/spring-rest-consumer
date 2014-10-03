package io.eyolas.restconsumer.reflexion;

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
public class ClassReflexionInfo {

    private final Class clazz;

    private Map<Method, MethodReflexionInfo> methodReflexionInfos = new HashMap<>();
}
