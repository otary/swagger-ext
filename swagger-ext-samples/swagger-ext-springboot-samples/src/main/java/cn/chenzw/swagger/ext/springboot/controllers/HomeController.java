package cn.chenzw.swagger.ext.springboot.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ext.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ApiGroup(name = "homeGroup")
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ApiMapResponses({
            @ApiMapResponse(key = "resp1", description = "响应值1"),
            @ApiMapResponse(key = "resp2", description = "响应值2")
    })
    @PostMapping("/map")
    public Map<String, Object> testMapWithArgsAnnotation(@ApiMapParams(name = "map2", value = {
            @ApiMapParam(key = "test", description = "测试"),
            @ApiMapParam(key = "test2", description = "测试2")
    }) @RequestBody Map<String, Object> map2) {
        return map2;
    }


    @ApiMapParams(name = "map2", value = {
            @ApiMapParam(key = "test", description = "测试"),
            @ApiMapParam(key = "test2", description = "测试2")
    })
    @PostMapping("/map2")
    public Map<String, Object> testMapWidthMethodAnnotation(@RequestBody Map<String, Object> map2) {
        return map2;
    }

}
