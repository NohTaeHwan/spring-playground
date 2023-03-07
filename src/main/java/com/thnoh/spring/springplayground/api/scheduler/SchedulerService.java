package com.thnoh.spring.springplayground.api.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final TaskScheduler taskScheduler;

    public void register1() {
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(()->log.info("[1] ={}",Thread.currentThread().getName()), 1000);
        scheduledTasks.put("mySchedulerId1", task);
    }

    public void register2() {
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(new ScheduleThread(),1000);
        scheduledTasks.put("mySchedulerId2", task);
    }

    public static class ScheduleThread implements Runnable{
        @Override
        public void run(){
            log.info("[2] ={}",Thread.currentThread().getName());
        }
    }

    public void remove1() {
        scheduledTasks.get("mySchedulerId1").cancel(true);
    }

    public void remove2() {
        scheduledTasks.get("mySchedulerId2").cancel(true);
    }

/*
    @Scheduled(fixedRateString = "${myscheduler.period}", initialDelay = 2000)
    private void scheduleTest() {
        log.error("fix");
    }
*/



}
