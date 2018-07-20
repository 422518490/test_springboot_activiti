package com.activiti.listener;

import com.activiti.service.LeaveService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/6
 * @description
 */
@Component
public class LeaveMangerTakeListener implements ExecutionListener {

    @Resource
    private LeaveService leaveService;

    @Resource
    private TaskService taskService;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        // 获取当前任务
        TaskQuery taskQuery = taskService.createTaskQuery()
                .processInstanceId(delegateExecution.getProcessInstanceId());
        List<Task> taskList = taskQuery.list();
        Task task = taskQuery.singleResult();
        leaveService.completeTask(task.getId());
    }
}
