package com.challamani.liteprocess.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ProcessPayload {

    private String id;
    private String liteProcessDefinitionKey;
    private String name;
    private String instanceKey;
    private Map<String, Object> variables;

}
