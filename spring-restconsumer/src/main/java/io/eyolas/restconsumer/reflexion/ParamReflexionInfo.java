package io.eyolas.restconsumer.reflexion;

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
public class ParamReflexionInfo {
    private Class parameterType;
    
    private List<Annotation> annotations;
}
