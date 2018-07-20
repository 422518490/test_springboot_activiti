package com.activiti.service.impl;

import com.activiti.service.ServiceTaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateHelper;
import org.activiti.engine.delegate.Expression;
import org.springframework.stereotype.Service;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/12
 * @description
 */
@Service(value = "serviceTaskService")
public class ServiceTaskServiceImpl implements ServiceTaskService {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("当前节点:" + delegateExecution.getCurrentFlowElement().getName());
    }

    @Override
    public void hr(DelegateExecution delegateExecution) {
        System.out.println("当前节点:" + delegateExecution.getCurrentFlowElement().getName());
        /* 这种方式根据官网介绍不是线程安全的
            参考这个https://www.activiti.org/userguide/#bpmnJavaServiceTask下面的
            “Field injection and thread safety”章节
         */
        Expression hr1 = DelegateHelper.getFieldExpression(delegateExecution, "param1");
        String hr11 = hr1.getValue(delegateExecution).toString();
        Expression hr2 = DelegateHelper.getFieldExpression(delegateExecution, "param2");
        String hr22 = hr2.getValue(delegateExecution).toString();
        delegateExecution.setVariable("hr", hr11.concat(",").concat(hr22));
    }
}
