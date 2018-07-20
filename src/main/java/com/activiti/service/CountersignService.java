package com.activiti.service;

import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/12
 * @description
 */
public interface CountersignService {

    public boolean startWorkflow(String workFlowName, Map<String, Object> paramMap);

}
