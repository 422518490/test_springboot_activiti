package com.activiti.service.impl;

import com.activiti.service.CountersignService;
import com.activiti.service.LeaveService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/12
 * @description 会签流程
 */
@Service
public class CountersignServiceImpl implements CountersignService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private LeaveService leaveService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startWorkflow(String workFlowName, Map<String, Object> paramMap) {
        // 通过流程定义的key启动，选取最高的version启动
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName, paramMap);
        if (Optional.ofNullable(processInstance).isPresent()
                && processInstance.getProcessInstanceId().length() > 0) {
            // 获取流程启动产生的taskId
            String taskId = taskService.createTaskQuery()
                    .taskCandidateOrAssigned(paramMap.get("requestUser") + "")
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .singleResult().getId();
            // 自动完成第一个提交
            leaveService.completeTask(taskId);
            return true;
        }
        return false;
    }
}
