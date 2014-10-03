package io.eyolas.restconsumer.rest;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.springframework.http.HttpMethod;

/**
 *
 * @author eyolas
 */
@Documented
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface RestMethod {

    HttpMethod value();

    boolean hasBody() default false;
}
