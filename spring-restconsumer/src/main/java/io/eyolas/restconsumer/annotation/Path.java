package io.eyolas.restconsumer.annotation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author eyolas
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Path {
  String value();
}
