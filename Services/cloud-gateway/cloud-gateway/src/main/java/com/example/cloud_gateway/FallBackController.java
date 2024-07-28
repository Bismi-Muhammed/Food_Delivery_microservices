//package com.example.cloud_gateway;
//
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//@RestController
//public class FallBackController {
//
//    @RequestMapping("/orderFallBack")
//    public Mono<String> orderServiceFallBAck(){
//      return Mono.just("Order service is taking too much time too respond or is down,please try again later");
//    }
//
//    @RequestMapping("/paymentFallBack")
//    public Mono<String> paymentServiceFallBAck(){
//        return Mono.just("Payment service is taking too much time too respond or is down,please try again later");
//    }
//}
