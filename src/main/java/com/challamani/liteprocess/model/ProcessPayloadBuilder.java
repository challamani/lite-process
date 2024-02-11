package com.challamani.liteprocess.model;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProcessPayloadBuilder {
    private String processDefinitionKey;
    private String name;
    private String instanceKey;
    private Map<String, Object> variables = new HashMap();

    public ProcessPayloadBuilder() {
    }

    public ProcessPayloadBuilder withVariables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public ProcessPayloadBuilder withVariable(String name, Object value) {
        if (this.variables == null) {
            this.variables = new HashMap();
        }

        this.variables.put(name, value);
        return this;
    }

    public ProcessPayloadBuilder withInstanceKey(String instanceKey) {
        this.instanceKey = instanceKey;
        return this;
    }

    public ProcessPayloadBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProcessPayloadBuilder withProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public ProcessPayload build() {
        return new ProcessPayload(UUID.randomUUID().toString(), this.processDefinitionKey, this.name, this.instanceKey, this.variables);
    }
}
