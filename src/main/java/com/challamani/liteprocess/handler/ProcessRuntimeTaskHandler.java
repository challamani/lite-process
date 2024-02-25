package com.challamani.liteprocess.handler;

import com.challamani.liteprocess.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessRuntimeTaskHandler {

    private final ServiceTaskHandler serviceTaskHandler;

    @Async
    public CompletableFuture<LiteProcessInstance> executeAsyncProcess(LiteProcessInstance liteProcessInstance,
                                                                      LiteProcessDefinition liteProcessDefinition) {

        List<ServiceTask> serviceTasks = liteProcessDefinition.getServiceTasks();

        ServiceTask currentTask = serviceTasks.stream().filter(serviceTask -> serviceTask.getExecutionOrder() == 1)
                .findFirst().orElseThrow(() -> new RuntimeException("service tasks not configured with execution order"));

        String processInstanceId = liteProcessInstance.getId();
        currentTask.setLiteProcessInstanceId(processInstanceId);

        //Need to handle this block with failure cases.
        Map<String, Object> inboundVariables = new HashMap<>();
        inboundVariables.putAll(liteProcessInstance.getProcessVariables());
        currentTask.setInboundVariables(inboundVariables);

        executeServiceTasks(serviceTasks, currentTask, null);

        liteProcessInstance.setCompletedDate(Calendar.getInstance().getTime());
        liteProcessInstance.setStatus(LiteProcessStatus.COMPLETED);
        log.info("<<< End lite-process instance {}",liteProcessInstance);
        return CompletableFuture.completedFuture(liteProcessInstance);
    }

    //Recursive service-task execution.
    private void executeServiceTasks(List<ServiceTask> serviceTasks, ServiceTask currentTask, ServiceTask previous){

        if(Objects.nonNull(previous)){
            populateVariables(currentTask, previous);
        }
        log.info(">>> START service-task {} inbound variables {}",currentTask.getName(), currentTask.getInboundVariables());
        //store in DB
        serviceTaskHandler.invoke(currentTask);
        //update the service-task details
        String nextTaskName;
        if(Objects.nonNull(currentTask.getExclusiveGateway())) {
            Gateway exclusiveGateway =  currentTask.getExclusiveGateway();

            ExpressionParser expressionParser = new SpelExpressionParser();
            StandardEvaluationContext serviceTaskContext = new StandardEvaluationContext(currentTask);
            Expression expression = expressionParser.parseExpression(exclusiveGateway.getExpression());

            Boolean gatewayResult = expression.getValue(serviceTaskContext, Boolean.class);
            log.info(">>> service-task {} expression {} value {}", currentTask.getName(), exclusiveGateway.getExpression(), gatewayResult);
            nextTaskName = gatewayResult ? exclusiveGateway.getOnTrue() : exclusiveGateway.getOnFalse();
        } else {
            nextTaskName = currentTask.getNextTask();
        }

        if(Objects.nonNull(nextTaskName) && !nextTaskName.equalsIgnoreCase("_end")) {
            log.info(">>> next serviceTask: {}",nextTaskName);

            ServiceTask nextTask = serviceTasks.stream().filter(serviceTask ->
                            serviceTask.getName().equalsIgnoreCase(nextTaskName)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid next service-task configured either in exclusive gateway section (or) nextTask"));

            log.info(">>> END service-task {} outbound variables {}", currentTask.getName(), currentTask.getOutboundVariables());
            executeServiceTasks(serviceTasks, nextTask, currentTask);
        }
    }

    private void populateVariables(ServiceTask current, ServiceTask previous) {
        Map<String, Object> inboundVariables = new HashMap<>();
        /*current.getMandatoryVariables().forEach(variableName ->
                inboundVariables.put(variableName, previous.getOutboundVariables().get(variableName)));*/

        inboundVariables.putAll(previous.getOutboundVariables());
        previous.getInboundVariables().forEach((key, value) -> inboundVariables.putIfAbsent(key,value));
        current.setInboundVariables(inboundVariables);
    }

    public LiteProcessInstance executeLiteProcess(LiteProcessInstance liteProcessInstance){
        return null;
    }


}
