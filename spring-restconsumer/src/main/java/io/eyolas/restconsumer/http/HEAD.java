package io.eyolas.restconsumer.http;

import io.eyolas.restconsumer.rest.RestMethod;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.springframework.http.HttpMethod;

/**
 *
 * @author eyolas
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
@RestMethod(HttpMethod.HEAD)
public @interface HEAD {
  String value();
}