package io.eyolas.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import io.eyolas.example.server.aop.logging.LoggingAspect;

/**
 *
 * @author eyolas
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
