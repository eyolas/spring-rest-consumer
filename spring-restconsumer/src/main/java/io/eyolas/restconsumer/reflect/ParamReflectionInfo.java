package io.eyolas.restconsumer.reflect;

import java.lang.annotation.Annotation;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;

/**
 *
 * @author dtouzet
 */
@Builder
@Data
public class ParamReflectionInfo {
    private Class parameterType;
    
    private List<Annotation> annotations;
}
