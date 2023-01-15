package com.thnoh.spring.springplayground.api.async.controller;

import com.thnoh.spring.springplayground.api.async.service.CallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {

    private final CallerService callerService;

    @GetMapping("/{type}")
    public String callAsync(@PathVariable("type") Integer type) throws Exception{

        if(type == 0){
            callerService.callAsync();
        } else if (type == 1) {
            callerService.callAsyncWithConstructor();
        } else if(type == 2){
            callerService.callInnerAsync();
        }

        return "ok";
    }

}
