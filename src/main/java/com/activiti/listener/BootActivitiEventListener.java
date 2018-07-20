package com.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.stereotype.Component;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/18
 * @description 流程中发生事件时的处理器
 */
@Component(value = "bootActivitiEventListener")
public class BootActivitiEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        String print = "产生定义的流程id：".concat(activitiEvent.getProcessDefinitionId())
                .concat("，流程类型是：")
                .concat(activitiEvent.getType().name());
        System.out.println(print);
    }

    @Override
    public boolean isFailOnException() {
        // false忽略onEvent产生的异常,true则向上抛出异常
        return false;
    }
}
