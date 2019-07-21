package cn.chenzw.swagger.ext.samples.controllers;

import io.swagger.annotations.ext.ApiGroup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiGroup(name = "homeGroup")
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
