package com.thnoh.spring.springplayground.api.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallerService {

    private final AsyncService asyncService;

    /* 정상적으로 Async 호출 */
    public void callAsync(){
        log.info("[callAsync()]");
        log.info("::::::Thread Name : " + Thread.currentThread().getName());

        asyncService.asyncReceiver1();
        asyncService.asyncReceiver2();
    }

    /* Async 메소드를 담은 Bean 생성해서 호출 */
    public void callAsyncWithConstructor(){
        AsyncService asyncService1 = new AsyncService();
        log.info("[callAsyncWithConstructor()]");
        log.info("::::::Thread Name : " + Thread.currentThread().getName());
        asyncService1.asyncReceiver1();
        asyncService1.asyncReceiver2();
    }

    /* 같은 클래스에 있는 async 메소드 호출 */
    public void callInnerAsync(){
        log.info("[callInnerAsync()]");
        log.info("::::::Thread Name : " + Thread.currentThread().getName());
        this.innerAsyncReceiver1();
        this.innerAsyncReceiver2();
    }

    @Async("taskExecutor1")
    public void innerAsyncReceiver1(){
        log.info("[innerAsyncReceiver1()]");
        for(int i=0;i<5;i++){
            log.info("::::::Thread Name : " + Thread.currentThread().getName());
        }
    }

    @Async("taskExecutor2")
    public void innerAsyncReceiver2(){
        log.info("[innerAsyncReceiver2()]");
        for(int i=0;i<5;i++){
            log.info("::::::Thread Name : " + Thread.currentThread().getName());
        }
    }

}
