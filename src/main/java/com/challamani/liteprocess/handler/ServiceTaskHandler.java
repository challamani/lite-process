package com.challamani.liteprocess.handler;

import com.challamani.liteprocess.model.ServiceTask;
import com.challamani.liteprocess.runtime.LiteTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceTaskHandler  {

    private final Map<String, LiteTaskExecutor<ServiceTask>> liteServiceTaskExecutor;

    public void invoke(ServiceTask serviceTask) {

        //DB Entry with start timestamp
        log.info("liteServiceTaskExecutor {}", liteServiceTaskExecutor);

        String beanName = serviceTask.getHandler();
        LiteTaskExecutor<ServiceTask> liteTaskExecutor = liteServiceTaskExecutor.get(beanName);
        liteTaskExecutor.execute(serviceTask);

        //Update DB Entry with completed timestamp
    }
}
