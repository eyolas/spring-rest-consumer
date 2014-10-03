package io.eyolas.restconsumer.rest;

import javax.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Bean processor for proxify interface
 * @author eyolas
 */
public class RestConsumerPostProcessor implements BeanPostProcessor{
    @Resource
    private RestBuilder restBuilder;

    @Override
    public Object postProcessBeforeInitialization(Object object, String string) throws BeansException {
        if (object.getClass().isInterface()) {
            RestResource resource = AnnotationUtils.findAnnotation(object.getClass(), RestResource.class);
            if (null != resource) {
                return restBuilder.create(object.getClass(), null);
            }
        }
        return object;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String string) throws BeansException {
        return o;
    }
}
