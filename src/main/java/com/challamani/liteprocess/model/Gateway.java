package com.challamani.liteprocess.model;

import lombok.Data;

@Data
public class Gateway {

    private String name;
    private String expression;
    private String onTrue;
    private String onFalse;

}
