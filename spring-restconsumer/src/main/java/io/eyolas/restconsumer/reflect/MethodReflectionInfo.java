package io.eyolas.restconsumer.reflect;

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
public class MethodReflectionInfo {
    private Method method;
    
    private List<Annotation> annotations = new ArrayList<>();
    
    private List<ParamReflectionInfo> params = new ArrayList<>();

    public MethodReflectionInfo(Method method) {
        this.method = method;
    }
    
    
}
