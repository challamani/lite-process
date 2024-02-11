package com.challamani.liteprocess.example.service_tasks;

import com.challamani.liteprocess.model.ServiceTask;
import com.challamani.liteprocess.runtime.LiteTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PackingService implements LiteTaskExecutor<ServiceTask> {
    @Override
    public void execute(ServiceTask serviceTask) {

        Map<String, Object> inBoundVariables = serviceTask.getInboundVariables();
        String orderId = (String) inBoundVariables.get("orderId");
        String orderStatus = (String) inBoundVariables.get("orderStatus");
        log.info("<<< PackingService order {} status {}", orderId, orderStatus);
        if(orderStatus.equalsIgnoreCase("CONFIRMED")){
            log.info("<<< packing is completed for the order {}", orderId);
            orderStatus = "ReadyToShip";
        }else{
            log.info("<<< unexpected order {} has arrived to packing section", orderId);
            orderStatus = "PENDING";
        }

        Map<String, Object> outboundVariables = new HashMap<>();
        outboundVariables.put("orderStatus", orderStatus);
        serviceTask.setOutboundVariables(outboundVariables);
        log.info("<<< PackingService order {} status {}", orderId, orderStatus);
    }
}
