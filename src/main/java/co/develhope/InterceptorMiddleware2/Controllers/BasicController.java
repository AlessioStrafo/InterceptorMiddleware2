package co.develhope.InterceptorMiddleware2.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class BasicController {
    @GetMapping("")
    public String sayWelcome() {
        return "Welcome User";
    }
}
