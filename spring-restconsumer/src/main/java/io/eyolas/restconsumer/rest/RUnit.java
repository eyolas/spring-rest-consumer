package io.eyolas.restconsumer.rest;

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