package com.challamani.liteprocess.model;


import java.util.*;

public class LiteProcessInstanceBuilder {

    private String id;
    private String processDefinitionKey;
    private String processDefinitionId;
    private String initiator;
    private String version;
    private LiteProcessStatus status;
    private Date startDate;
    private String name;
    private String instanceKey;
    private Map<String, Object> processVariables = new HashMap();

    public LiteProcessInstanceBuilder() {
    }

    public LiteProcessInstanceBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public LiteProcessInstanceBuilder withProcessDefinitionId(String processDefinitionId){
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public LiteProcessInstanceBuilder withInitiator(String initiator){
        this.initiator = initiator;
        return this;
    }

    public LiteProcessInstanceBuilder withStatus(LiteProcessStatus status){
        this.status = status;
        return this;
    }

    public LiteProcessInstanceBuilder withStartDate(Date startDate){
        this.startDate = startDate;
        return this;
    }

    public LiteProcessInstanceBuilder withVersion(String version){
        this.version = version;
        return this;
    }

    public LiteProcessInstanceBuilder withProcessVariables(Map<String, Object> variables) {
        this.processVariables = variables;
        return this;
    }

    public LiteProcessInstanceBuilder withProcessVariable(String name, Object value) {
        if (this.processVariables == null) {
            this.processVariables = new HashMap();
        }

        this.processVariables.put(name, value);
        return this;
    }

    public LiteProcessInstanceBuilder withInstanceKey(String instanceKey) {
        this.instanceKey = instanceKey;
        return this;
    }

    public LiteProcessInstanceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LiteProcessInstanceBuilder withProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public LiteProcessInstance build() {
        LiteProcessInstance liteProcessInstance = new LiteProcessInstance();
        liteProcessInstance.setId(UUID.randomUUID().toString());
        liteProcessInstance.setLiteProcessDefinitionId(processDefinitionId);
        liteProcessInstance.setLiteProcessDefinitionKey(processDefinitionKey);
        liteProcessInstance.setInitiator(initiator);
        liteProcessInstance.setLiteProcessDefinitionVersion(version);
        liteProcessInstance.setStatus(status);
        liteProcessInstance.setName(name);
        liteProcessInstance.setStartDate(Calendar.getInstance().getTime());
        liteProcessInstance.setInstanceKey(instanceKey);
        liteProcessInstance.setProcessVariables(processVariables);
        return liteProcessInstance;
    }
}
