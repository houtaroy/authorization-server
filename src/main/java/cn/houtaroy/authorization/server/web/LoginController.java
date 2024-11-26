package cn.houtaroy.authorization.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 自定义登录页Controller
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
