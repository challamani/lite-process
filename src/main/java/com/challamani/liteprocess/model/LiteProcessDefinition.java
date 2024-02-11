package com.challamani.liteprocess.model;

import lombok.Data;

import java.util.List;

@Data
public class LiteProcessDefinition {

    private String id;
    private String name;
    private String description;
    private int version;
    private String key;
    private boolean async;
    private List<String> initiatorRoles;
    private List<ServiceTask> serviceTasks;
}
