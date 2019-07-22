package cn.chenzw.swagger.ext.spring.controllers;

import io.swagger.annotations.ext.ApiGroup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiGroup(name = "helloGroup")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/say")
    public String say(String name) {
        return "hello, " + name;
    }
}
