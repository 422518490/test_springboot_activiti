package com.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/11
 * @description
 */
@Component
public class TeamListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("delegateTask.getEventName() = " + delegateTask.getEventName());

        //添加会签的人员，所有的都审批通过才可进入下一环节

        /*List<String> assigneeList = new ArrayList<String>();
        assigneeList.add("liaoyubo2");
        assigneeList.add("liaoyubo3");
        delegateTask.setVariable("parallelUserList",assigneeList);*/
    }
}
