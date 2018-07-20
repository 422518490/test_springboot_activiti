package com.activiti.service.impl;

import com.activiti.service.OverTimerService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/10
 * @description
 */
@Service
public class OverTimerServiceImpl implements OverTimerService {

    @Resource
    private RuntimeService runtimeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startWorkFlow(String workFlowName) {
        // 通过流程定义的key启动，选取最高的version启动
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName);
        if (Optional.ofNullable(processInstance).isPresent()
                && processInstance.getProcessInstanceId().length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startWorkFlow(String workFlowName, Map<String, Object> map) {
        // 通过流程定义的key启动，选取最高的version启动
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName, map);
        if (Optional.ofNullable(processInstance).isPresent()
                && processInstance.getProcessInstanceId().length() > 0) {
            return true;
        }
        return false;
    }
}
