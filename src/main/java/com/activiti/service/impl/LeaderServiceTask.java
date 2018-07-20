package com.activiti.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/16
 * @description 这种方式是线程安全的
 */
public class LeaderServiceTask implements JavaDelegate {

    /**
     * 流程中的变量字段定义
     */
    private Expression param1;

    private Expression param2;


    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("当前节点:" + delegateExecution.getCurrentFlowElement().getName());

        // 给定义的表达式变量赋值
        delegateExecution.setVariable("input", 50);
        Object obj1 = param1.getValue(delegateExecution);
        Object obj2 = param2.getValue(delegateExecution);
    }
}
