package com.example.Food_Order_Service.Order;


import com.example.Food_Order_Service.Common.Payment;
import com.example.Food_Order_Service.Common.TransactionRequest;
import com.example.Food_Order_Service.Common.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@RefreshScope
public class OrderService {
    private final OrderRepository repo;


    @Lazy
    private final RestTemplate template;

    @Value("${microservice.food-payment-service.endpoints.endpoint.uri}")
    private String END_POINT_URL;

    private Logger log= (Logger) LoggerFactory.getLogger(OrderService.class);
    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String  response="";
        Order order=request.getOrder();
        Payment payment=request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        log.info("Order service request : { }"+new ObjectMapper().writeValueAsString(request));
//rest call to save payment
//        Payment paymentResponse=template.postForObject("http://localhost:8081/payment/doPayment",payment, Payment.class);
//        Payment paymentResponse=template.postForObject("http://FOOD-PAYMENT-SERVICE/payment/doPayment",payment, Payment.class);

        Payment paymentResponse=template.postForObject(END_POINT_URL,payment, Payment.class);

        log.info("payment service response : { }"+new ObjectMapper().writeValueAsString(paymentResponse));

        response=paymentResponse.getPaymentStatus().equals("success")?"Payment processing successful and order placed":"there is a failure in payment api,order added to cart";


        repo.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
    }
}
