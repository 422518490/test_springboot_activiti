package com.activiti.controller;

import com.activiti.service.CountersignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/11
 * @description
 */
@RestController
@RequestMapping(value = "/countersign")
public class CountersignController {

    @Resource
    private CountersignService countersignService;

    /**
     * 会签流程启动
     *
     * @param workFlowName
     * @return
     */
    @GetMapping(value = "/startWorkFlow")
    public boolean startWorkFlow(@RequestParam(value = "workFlowName") String workFlowName) {
        Map<String, Object> map = new HashMap<>();
        map.put("requestUser", "liaoyubo");
        map.put("teamName", "liaoyubo1");
        map.put("departmentUser", "liaoyubo5");
        map.put("companyLeader", "liaoyubo6");
        map.put("hr", "liaoyubo7");
        map.put("manger", "liaoyubo8");

        List<String> assigneeList = new ArrayList<String>();
        assigneeList.add("liaoyubo21");
        assigneeList.add("liaoyubo31");
        map.put("parallelUserList", assigneeList);
        return countersignService.startWorkflow(workFlowName, map);
    }
}
