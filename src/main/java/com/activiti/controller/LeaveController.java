package com.activiti.controller;

import com.activiti.service.LeaveService;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/2
 * @description
 */
@RestController
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    /**
     * 部署流程
     *
     * @param bpmnName
     * @param filePathAndName
     * @return
     */
    @PostMapping(value = "/testActiviti/deployBpmn")
    public boolean deployBpmn(@RequestParam(value = "bpmnName") String bpmnName,
                              @RequestParam(value = "filePathAndName") String filePathAndName) {
        Deployment deployment = leaveService.deployBpmn(bpmnName, filePathAndName);
        if (Optional.ofNullable(deployment).isPresent()
                && !"".equals(deployment.getId())) {
            return true;
        }
        return false;
    }

    /**
     * 获取流程定义
     *
     * @return
     */
    @GetMapping(value = "/testActiviti/listProcessDefinition")
    public List<Map<String, Object>> listProcessDefinition() {
        List<Map<String, Object>> processDefinitionList = new ArrayList<>();

        leaveService.listProcessDefinition().stream()
                .forEach(processDefinition -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", processDefinition.getId());
                    map.put("key", processDefinition.getKey());
                    map.put("name", processDefinition.getName());
                    map.put("deploymentId", processDefinition.getDeploymentId());
                    map.put("tenantId", processDefinition.getTenantId());
                    map.put("version", processDefinition.getVersion());
                    processDefinitionList.add(map);
                });
        return processDefinitionList;
    }

    /**
     * 删除流程
     *
     * @param deploymentId
     * @return
     */
    @DeleteMapping(value = "/testActiviti/deleteDeployment")
    public boolean deleteDeployment(@RequestParam(value = "deploymentId") String deploymentId) {
        try {
            leaveService.deleteDeployment(deploymentId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 启动流程
     *
     * @param applyUser
     * @param manger
     * @param workFlowName
     * @return
     */
    @GetMapping(value = "/testActiviti/start")
    public boolean requestLeave(@RequestParam(value = "applyUser") String applyUser,
                                @RequestParam(value = "manger") String manger,
                                @RequestParam(value = "workFlowName") String workFlowName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("applyUser", applyUser);
        paramMap.put("manger", manger);
        paramMap.put("dueDate", LocalDate.now().toString());
        return leaveService.startWorkflow(workFlowName, paramMap);
    }

    /**
     * 分支选择完成流程走向
     *
     * @param agree
     * @param taskId
     */
    @GetMapping(value = "/testActiviti/gatewayComplete")
    public void gatewayComplete(@RequestParam(value = "agree") Boolean agree,
                                @RequestParam(value = "taskId") String taskId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agree", agree);
        leaveService.gatewayComplete(paramMap, taskId);
    }

    /**
     * 非分支选择完成流程走向
     *
     * @param taskId
     */
    @GetMapping(value = "/testActiviti/completeTask")
    public void completeTask(@RequestParam(value = "taskId") String taskId) {
        leaveService.completeTask(taskId);
    }

    /**
     * 按照分配的人员获取有效的流程
     *
     * @param assignee
     * @return
     */
    @GetMapping(value = "/testActiviti/listTasks")
    public List<Map<String, Object>> listTasks(@RequestParam(value = "assignee") String assignee) {
        List<Task> taskList = leaveService.listTasks(assignee);
        List<Map<String, Object>> list = new ArrayList<>();
        taskList.stream().forEach(task -> {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("processInstanceId", task.getProcessInstanceId());
            list.add(map);
        });
        return list;
    }


    /**
     * 获取已经完成的流程按照流程名称
     *
     * @param workFlowName
     * @return
     */
    @GetMapping(value = "/testActiviti/listFinishedTask")
    public List<Map<String, Object>> listFinishedTask(@RequestParam(value = "workFlowName") String workFlowName) {
        List<Map<String, Object>> finishedList = new ArrayList<>();
        HistoricProcessInstanceQuery instanceQuery = leaveService.listFinishedTask(workFlowName);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("startTime", historicProcessInstance.getStartTime());
            valueMap.put("finishTime", historicProcessInstance.getEndTime());
            valueMap.put("variables", historicProcessInstance.getProcessVariables());
            valueMap.put("processDefinitionName", historicProcessInstance.getProcessDefinitionName());
            finishedList.add(valueMap);
        });
        return finishedList;
    }

    /**
     * 根据分配者查询参与过的节点
     *
     * @param assignee
     * @return
     */
    @GetMapping(value = "/testActiviti/listActivityInstance")
    public List<Map<String, Object>> listActivityInstance(@RequestParam(value = "assignee") String assignee) {
        List<Map<String, Object>> instanceList = new ArrayList<>();
        HistoricActivityInstanceQuery instanceQuery = leaveService.listActivityInstance(assignee);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("processInstanceId", historicProcessInstance.getProcessInstanceId());
            valueMap.put("startTime", historicProcessInstance.getStartTime());
            valueMap.put("finishTime", historicProcessInstance.getEndTime());
            valueMap.put("activityName", historicProcessInstance.getActivityName());
            valueMap.put("assignee", historicProcessInstance.getAssignee());
            instanceList.add(valueMap);
        });
        return instanceList;
    }

    /**
     * 获取所有流程中的流程
     *
     * @return
     */
    @GetMapping(value = "/testActiviti/listActiveTask")
    public List<Map<String, Object>> listActiveTask() {
        List<Map<String, Object>> taskList = new ArrayList<>();
        Map<String, Object> valueMap = new HashMap<>();
        ProcessInstanceQuery instanceQuery = leaveService.listActiveTask();
        instanceQuery.list().stream().forEach(processInstance -> {
            valueMap.put("startTime", processInstance.getStartTime());
            valueMap.put("workFlowName", processInstance.getProcessDefinitionName());
            valueMap.put("processInstanceId", processInstance.getProcessInstanceId());
            taskList.add(valueMap);
        });
        return taskList;
    }

    /**
     * 按照流程名称去获取流程中的流程
     *
     * @param workFlowName
     * @return
     */
    @GetMapping(value = "/testActiviti/listActiveTaskByWorkFlow")
    public List<Map<String, Object>> listActiveTaskByWorkFlow(@RequestParam(value = "workFlowName") String workFlowName) {
        List<Map<String, Object>> taskList = new ArrayList<>();
        Map<String, Object> valueMap = new HashMap<>();
        ProcessInstanceQuery instanceQuery = leaveService.listActiveTaskByWorkFlow(workFlowName);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            valueMap.put("startTime", historicProcessInstance.getStartTime());
            valueMap.put("workFlowName", historicProcessInstance.getProcessDefinitionName());
            valueMap.put("processInstanceId", historicProcessInstance.getProcessInstanceId());
            taskList.add(valueMap);
        });
        return taskList;
    }

    /**
     * 根据实例id获取流程运行任务节点
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping(value = "/testActiviti/listNodeByProcessInstanceId")
    public List<Map<String, Object>> listNodeByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        List<Map<String, Object>> instanceList = new ArrayList<>();
        HistoricActivityInstanceQuery instanceQuery = leaveService.listHisActNodeByProcessInstanceId(processInstanceId);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("activityId", historicProcessInstance.getActivityId());
            valueMap.put("startTime", historicProcessInstance.getStartTime());
            valueMap.put("finishTime", historicProcessInstance.getEndTime());
            valueMap.put("activityName", historicProcessInstance.getActivityName());
            valueMap.put("assignee", historicProcessInstance.getAssignee());
            instanceList.add(valueMap);
        });
        return instanceList;
    }

    /**
     * 根据实例id获取流程变量信息
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping(value = "/testActiviti/listVariableByProcessInstanceId")
    public List<Map<String, Object>> listVariableByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        List<Map<String, Object>> instanceList = new ArrayList<>();
        HistoricVariableInstanceQuery instanceQuery = leaveService.listHisVariableByProcessInstanceId(processInstanceId);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("taskId", historicProcessInstance.getTaskId());
            valueMap.put("createTime", historicProcessInstance.getCreateTime());
            valueMap.put("id", historicProcessInstance.getId());
            valueMap.put("variableName", historicProcessInstance.getVariableName());
            valueMap.put("variableValue", historicProcessInstance.getValue());
            instanceList.add(valueMap);
        });
        return instanceList;
    }

    /**
     * 根据实例id获取流程任务节点信息
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping(value = "/testActiviti/listHisTaskByProcessInstanceId")
    public List<Map<String, Object>> listHisTaskByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        List<Map<String, Object>> instanceList = new ArrayList<>();
        HistoricTaskInstanceQuery instanceQuery = leaveService.listHisTaskByProcessInstanceId(processInstanceId);
        instanceQuery.list().stream().forEach(historicProcessInstance -> {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("taskId", historicProcessInstance.getId());
            valueMap.put("createTime", historicProcessInstance.getCreateTime());
            valueMap.put("assignee", historicProcessInstance.getAssignee());
            valueMap.put("endTime", historicProcessInstance.getEndTime());
            valueMap.put("name", historicProcessInstance.getName());
            valueMap.put("formKey", historicProcessInstance.getFormKey());
            instanceList.add(valueMap);
        });
        return instanceList;
    }

}
