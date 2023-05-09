package it.paleocapa.mastroiannim;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @GetMapping("/api")
    public String home() {
        return "Hello from Docker!";
    }
}
