package io.eyolas.restconsumer.exception;

import org.springframework.beans.BeansException;

/**
 *
 * @author eyolas
 */
public class ParameterAnnotationException extends BeansException {

    public ParameterAnnotationException(String msg) {
        super(msg);
    }

    public ParameterAnnotationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    
    
}
