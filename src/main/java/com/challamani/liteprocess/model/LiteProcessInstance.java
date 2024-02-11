package com.challamani.liteprocess.model;


import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Data
@ToString
public class LiteProcessInstance {

    private String id;
    private String name;
    private String liteProcessDefinitionId;
    private String liteProcessDefinitionKey;
    private String initiator;
    private Date startDate;
    private Date completedDate;
    private String instanceKey;
    private LiteProcessStatus status;
    private Integer liteProcessDefinitionVersion;
    private Map<String, Object> processVariables;

}
