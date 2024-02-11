package com.challamani.liteprocess.runtime;

import com.challamani.liteprocess.model.LiteProcessInstance;
import com.challamani.liteprocess.model.ProcessPayload;

public interface LiteProcessRuntime {

    LiteProcessInstance start(ProcessPayload processPayload);

    LiteProcessInstance update(ProcessPayload processPayload);

    LiteProcessInstance delete(ProcessPayload processPayload);

    LiteProcessInstance resume(ProcessPayload processPayload);

    LiteProcessInstance suspend(ProcessPayload processPayload);
}
