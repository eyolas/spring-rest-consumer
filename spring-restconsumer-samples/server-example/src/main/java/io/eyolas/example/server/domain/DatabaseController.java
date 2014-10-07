package io.eyolas.example.server.domain;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dtouzet
 */
@RestController
@RequestMapping("/database")
public class DatabaseController {
    @Resource
    private DatabaseService databaseService;
    
    @RequestMapping("/init")
    public ResponseEntity<Void> initDatabase() {
        databaseService.initDatabase();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
