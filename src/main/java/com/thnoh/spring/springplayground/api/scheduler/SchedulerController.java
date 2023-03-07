package com.thnoh.spring.springplayground.api.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @GetMapping("/start1")
    public String start1(){

        schedulerService.register1();

        return "start1";
    }

    @GetMapping("/start2")
    public String start(){

        schedulerService.register2();

        return "start2";
    }

    @GetMapping("/stop1")
    public String stop(){

        schedulerService.remove1();

        return "stop1";
    }

    @GetMapping("/stop2")
    public String stop2(){

        schedulerService.remove2();

        return "stop2";
    }
}
