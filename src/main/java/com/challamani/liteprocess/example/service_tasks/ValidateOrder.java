package com.challamani.liteprocess.example.service_tasks;

import com.challamani.liteprocess.example.model.Item;
import com.challamani.liteprocess.model.ServiceTask;
import com.challamani.liteprocess.runtime.LiteTaskExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ValidateOrder implements LiteTaskExecutor<ServiceTask> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public void execute(ServiceTask serviceTask) {

        String orderId = (String) serviceTask.getInboundVariables().get("orderId");
        String orderStatus = (String) serviceTask.getInboundVariables().get("orderStatus");

        List<Item> orderItems = null;
        try {
            String orderItemsStr = OBJECT_MAPPER.writeValueAsString(serviceTask.getInboundVariables().get("orderItems"));
            log.info("order items {}", orderItemsStr);
            orderItems = OBJECT_MAPPER.readValue(orderItemsStr, new TypeReference<ArrayList<Item>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("<<< Validate order: {} status: {}", orderId, orderStatus);
        boolean isItemQuantityExceeding = orderItems.stream().anyMatch(item -> item.getQuantity() > 5);
        boolean isOrderItemsExceeding = orderItems.size() > 10;

        if (isItemQuantityExceeding || isOrderItemsExceeding) {
            orderStatus = "PENDING";
        } else {
            orderStatus = "CONFIRMED";
        }

        log.info("<<< Validate order: {} status: {}", orderId, orderStatus);

        Map<String, Object> outboundVariables = new HashMap<>();
        outboundVariables.put("orderStatus", orderStatus);
        outboundVariables.put("approved", orderStatus.equalsIgnoreCase("CONFIRMED"));
        serviceTask.setOutboundVariables(outboundVariables);
    }
}
