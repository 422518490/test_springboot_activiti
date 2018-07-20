package com.activiti.service.impl;

import com.activiti.service.PrototypeServiceTaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/16
 * @description 每一次都是一个单独生成一个bean，线程安全
 */
@Service(value = "prototypeServiceTaskService")
@Scope("prototype")
public class PrototypeServiceTaskServiceImpl implements PrototypeServiceTaskService {

    private Expression param1;

    private Expression param2;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("当前节点:" + delegateExecution.getCurrentFlowElement().getName());
        // 传入表达式的参数
        delegateExecution.setVariable("input", 1000);
        Object obj1 = param1.getValue(delegateExecution);
        Object obj2 = param2.getValue(delegateExecution);
    }
}
