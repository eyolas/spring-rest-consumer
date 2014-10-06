package io.eyolas.example.server.config;

import io.eyolas.example.server.domain.DatabaseService;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author dtouzet
 */
@Configuration
public class Init {
    
    @Resource
    private DatabaseService initService;

    @PostConstruct
    public void initialize() {
        initService.initDatabase();
    }
}
