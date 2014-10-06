package io.eyolas.restconsumer.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author eyolas
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface RUnit {
    public String value();
}