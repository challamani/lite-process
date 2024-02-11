package com.challamani.liteprocess.example.controller;

import com.challamani.liteprocess.example.model.OrderProcessRequest;

import com.challamani.liteprocess.model.LiteProcessInstance;
import com.challamani.liteprocess.model.ProcessPayloadBuilder;
import com.challamani.liteprocess.runtime.LiteProcessRuntime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LiteProcessController {
    private final LiteProcessRuntime liteProcessRuntime;
    @PostMapping(value = "/start/{processDefinitionKey}")
    public LiteProcessInstance startProcess(@RequestBody OrderProcessRequest orderProcessRequest,
                                        @PathVariable String processDefinitionKey) {

        log.info("request processDefinitionKey {}, status variable value {} businessKey {}",
                processDefinitionKey,
                orderProcessRequest.getOrder().getStatus(),
                orderProcessRequest.getBusinessKey());

        LiteProcessInstance liteProcessInstance =  liteProcessRuntime.start(new ProcessPayloadBuilder()
                .withInstanceKey(orderProcessRequest.getBusinessKey())
                .withVariable("orderStatus", orderProcessRequest.getOrder().getStatus())
                .withVariable("orderId", orderProcessRequest.getOrder().getId())
                .withVariable("orderItems", orderProcessRequest.getOrder().getOrderItems())
                .withProcessDefinitionKey(processDefinitionKey)
                .withName("Process starting for the order# : " + orderProcessRequest.getOrder().getId())
                .build());

        log.info(">>> Created Process Instance: {}", liteProcessInstance);
        return liteProcessInstance;
    }

}
