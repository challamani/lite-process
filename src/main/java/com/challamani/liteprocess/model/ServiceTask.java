package com.challamani.liteprocess.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ServiceTask {

    private Integer executionOrder;
    private String name;
    private boolean async;
    private String handler;
    private List<String> mandatoryVariables;
    private Gateway exclusiveGateway;
    private LiteProcessStatus status;
    private Integer noOfRetries;
    private Map<String, Object> localVariables;
    private Map<String, Object> inboundVariables;
    private Map<String, Object> outboundVariables;
    private String liteProcessInstanceId;
    private String nextTask;
}
