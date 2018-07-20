package com.activiti.controller;

import com.activiti.service.OverTimerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/10
 * @description
 */
@RestController
@RequestMapping(value = "/overTimer")
public class OverTimerController {

    @Resource
    private OverTimerService overTimerService;

    @GetMapping(value = "/startWorkflow")
    public boolean startWorkflow(@RequestParam(value = "workFlowName") String workFlowName) {
        Map<String, Object> map = new HashMap<>();
        // 超时时间设置
        map.put("overTime", "PT60S");
        return overTimerService.startWorkFlow(workFlowName, map);
        //return overTimerService.startWorkFlow(workFlowName);
    }

    @GetMapping(value = "/startWorkflowNoParam")
    public boolean startWorkflowNoParam(@RequestParam(value = "workFlowName") String workFlowName) {
        return overTimerService.startWorkFlow(workFlowName);
    }
}
