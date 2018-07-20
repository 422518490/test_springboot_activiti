package com.activiti.controller;

import com.activiti.service.OverTimerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/10
 * @description
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Resource
    private OverTimerService overTimerService;

    /**
     * 启动流程完成前一个节点后再指定时间间隔向下一个节点发送，相当于延时
     *
     * @param workFlowName
     * @return
     */
    @GetMapping(value = "/startJobToNextNode")
    public boolean startJobToNextNode(@RequestParam(value = "workFlowName") String workFlowName) {
        return overTimerService.startWorkFlow(workFlowName);
    }

}
