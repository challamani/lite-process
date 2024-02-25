package com.challamani.liteprocess.runtime;

import com.challamani.liteprocess.model.*;
import com.challamani.liteprocess.service.ProcessDefinitionService;
import com.challamani.liteprocess.handler.ProcessRuntimeTaskHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiteProcessRuntimeImpl implements LiteProcessRuntime {

    private final ProcessRuntimeTaskHandler processRuntimeTaskHandler;
    private final ProcessDefinitionService processDefinitionService;

    @Override
    public LiteProcessInstance start(ProcessPayload processPayload) {

        LiteProcessDefinition liteProcessDefinition = processDefinitionService
                .getProcessDefinitionByKey(processPayload.getLiteProcessDefinitionKey());

        //Create process-instance
        LiteProcessInstance liteProcessInstance = new LiteProcessInstanceBuilder()
                .withId(UUID.randomUUID().toString())
                .withInstanceKey(processPayload.getInstanceKey())
                .withProcessDefinitionKey(liteProcessDefinition.getKey())
                .withProcessDefinitionId(liteProcessDefinition.getId())
                .withName(processPayload.getName())
                .withInitiator("system")
                .withProcessVariables(processPayload.getVariables())
                .withStartDate(Calendar.getInstance().getTime())
                .withStatus(LiteProcessStatus.RUNNING)
                .withVersion("1.0")
                .build();

        processRuntimeTaskHandler.executeAsyncProcess(liteProcessInstance, liteProcessDefinition)
                .exceptionallyAsync(throwable -> {
                    //failure status need to update in DB
                    log.info("exception while executing the service tasks {}", throwable);
                    return liteProcessInstance;
                });
        return liteProcessInstance;
    }

    @Override
    public LiteProcessInstance update(ProcessPayload processPayload) {
        return null;
    }

    @Override
    public LiteProcessInstance delete(ProcessPayload processPayload) {
        return null;
    }

    @Override
    public LiteProcessInstance resume(ProcessPayload processPayload) {
        return null;
    }

    @Override
    public LiteProcessInstance suspend(ProcessPayload processPayload) {
        return null;
    }
}
