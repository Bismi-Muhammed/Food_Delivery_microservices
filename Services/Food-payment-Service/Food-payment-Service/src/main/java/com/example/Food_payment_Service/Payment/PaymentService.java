package com.example.Food_payment_Service.Payment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;
    private Logger log= (Logger) LoggerFactory.getLogger(PaymentService.class);
    //changes

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        log.info("Payment service request : { }"+new ObjectMapper().writeValueAsString(payment));

        return repo.save(payment);
    }
    public String  paymentProcessing(){
        //3rd party payment gateway paypal,credit cardss
        return new Random().nextBoolean()?"success":"failure";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
        Payment payment=repo.findByOrderId(orderId);
        log.info("Payment service response : { }"+new ObjectMapper().writeValueAsString(payment));

        return payment;

    }
}
