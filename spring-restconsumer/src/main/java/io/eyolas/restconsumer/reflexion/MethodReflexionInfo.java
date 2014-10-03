package io.eyolas.restconsumer.reflexion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author eyolas
 */
@Data
public class MethodReflexionInfo {
    private Method method;
    
    private List<Annotation> annotations = new ArrayList<>();
    
    private List<ParamReflexionInfo> params = new ArrayList<>();

    public MethodReflexionInfo(Method method) {
        this.method = method;
    }
    
    
}
