package com.challamani.liteprocess.service;

import com.challamani.liteprocess.model.LiteProcessDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ProcessDefinitionService {

    private Map<String, LiteProcessDefinition> liteProcessDefinitionMap;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    public ProcessDefinitionService(@Autowired ApplicationContext applicationContext) throws IOException {
        liteProcessDefinitionMap = new HashMap<>();
        Resource[] resources = applicationContext.getResources("classpath:/lite-process/*.json");
        if(Objects.nonNull(resources)){
            Arrays.stream(resources).forEach(resource -> {
                try {
                    LiteProcessDefinition liteProcessDefinition = OBJECT_MAPPER.readValue(resource.getInputStream(),LiteProcessDefinition.class);
                    liteProcessDefinitionMap.put(liteProcessDefinition.getKey(), liteProcessDefinition);
                } catch (IOException e) {
                    log.info("");
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public LiteProcessDefinition getProcessDefinitionByKey(String key) {
        if(!liteProcessDefinitionMap.containsKey(key)) {
            throw new RuntimeException("invalid process definition key found:" + key);
        }
        try {
            String liteProcessDefStr = OBJECT_MAPPER.writeValueAsString(liteProcessDefinitionMap.get(key));
            return OBJECT_MAPPER.readValue(liteProcessDefStr, LiteProcessDefinition.class);
        }catch (JsonProcessingException jsonProcessingException){
            throw new RuntimeException("failed to parse the process definition");
        }
    }

}
