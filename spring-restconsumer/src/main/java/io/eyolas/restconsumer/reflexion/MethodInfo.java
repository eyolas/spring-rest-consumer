package io.eyolas.restconsumer.reflexion;

import com.fasterxml.jackson.databind.JavaType;
import io.eyolas.restconsumer.query.QueryType;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 *
 * @author dtouzet
 */
@Data
public class MethodInfo {
    private HttpMethod httpMethod = HttpMethod.GET;
    private String uri = "";
    private List<Map.Entry<String, QueryType>> paramsMatch;
    private JavaType returnType;
    
}
