package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    String message;

    public WelcomeController(
        // Refer to configuration property app.message.text to set value for
        // constructor argument text.
        @Value("${WELCOME_MESSAGE}")String str){
        this.message = str;
    }

    @GetMapping("/")
    public String sayHello(){
        return message;
    }
}
