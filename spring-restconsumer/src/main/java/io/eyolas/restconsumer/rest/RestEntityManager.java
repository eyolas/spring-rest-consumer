package io.eyolas.restconsumer.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eyolas.http.query.UriQueryBuilder;
import java.io.IOException;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author eyolas
 */
@Slf4j
@Data
public class RestEntityManager {

    private final RestTemplate restTemplate;

    private final String endPoint;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Builder
    public RestEntityManager(RestTemplate restTemplate, String endPoint) {
        Assert.notNull(restTemplate, "restTemplate is mandatory");
        Assert.notNull(endPoint, "endPoint is mandatory");
        this.restTemplate = restTemplate;
        this.endPoint = endPoint;
    }

    /**
     * <p>
     * Create url
     * </p>
     *
     * @param uri
     * @param params
     * @return l'url complete
     */
    private String createUrl(String uri, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(endPoint);
        sb.append(uri);
        if (!CollectionUtils.isEmpty(params)) {
            sb.append(new UriQueryBuilder().params(params).build());
        }
        return sb.toString();
    }

    /**
     *
     * @param <T>
     * @param method
     * @param uri
     * @param params
     * @param pathVariables
     * @param typeParameterClass
     * @return
     */
    public <T> T call(HttpMethod method, String uri, Map<String, Object> params, Map<String, Object> pathVariables, JavaType typeParameterClass) {

        String url = createUrl(uri, params);
        ResponseEntity<String> response;
        log.debug("Call {}", uri);
        if (!CollectionUtils.isEmpty(pathVariables)) {
            log.debug("With pathVariables {}", pathVariables.toString());
            response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    String.class,
                    pathVariables
            );
        } else {
            response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    String.class
            );
        }
        if (null != response && null != response.getBody()) {
            try {
                return objectMapper.readValue(response.getBody(), typeParameterClass);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }

        return null;
    }
    
    
//    private JavaType getType() {
//        
//    }
}
