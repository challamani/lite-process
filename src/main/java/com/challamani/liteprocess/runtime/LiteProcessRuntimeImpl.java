package com.challamani.liteprocess.runtime;

import com.challamani.liteprocess.model.LiteProcessDefinition;
import com.challamani.liteprocess.model.LiteProcessInstance;
import com.challamani.liteprocess.model.LiteProcessStatus;
import com.challamani.liteprocess.model.ProcessPayload;
import com.challamani.liteprocess.service.ProcessDefinitionService;
import com.challamani.liteprocess.service.ProcessRuntimeTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiteProcessRuntimeImpl implements LiteProcessRuntime {

    private final ProcessRuntimeTaskService processRuntimeTaskService;
    private final ProcessDefinitionService processDefinitionService;

    @Override
    public LiteProcessInstance start(ProcessPayload processPayload) {

        LiteProcessDefinition liteProcessDefinition = processDefinitionService
                .getProcessDefinitionByKey(processPayload.getLiteProcessDefinitionKey());

        //Create process-instance,
        LiteProcessInstance liteProcessInstance = new LiteProcessInstance();
        liteProcessInstance.setId(UUID.randomUUID().toString());
        liteProcessInstance.setLiteProcessDefinitionId(liteProcessDefinition.getId());
        liteProcessInstance.setLiteProcessDefinitionKey(liteProcessInstance.getLiteProcessDefinitionKey());
        liteProcessInstance.setInitiator("system");
        liteProcessInstance.setLiteProcessDefinitionVersion(1);
        liteProcessInstance.setStatus(LiteProcessStatus.RUNNING);
        liteProcessInstance.setName(processPayload.getName());
        liteProcessInstance.setStartDate(Calendar.getInstance().getTime());
        liteProcessInstance.setInstanceKey(processPayload.getInstanceKey());
        liteProcessInstance.setProcessVariables(processPayload.getVariables());

        processRuntimeTaskService.executeAsyncProcess(liteProcessInstance, liteProcessDefinition);
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
