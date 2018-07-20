package com.activiti.service;

import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/10
 * @description
 */
public interface OverTimerService {

    boolean startWorkFlow(String workFlowName);

    boolean startWorkFlow(String workFlowName, Map<String, Object> map);
}
