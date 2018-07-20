package com.activiti.service.impl;

import com.activiti.service.LeaveService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/2
 * @description
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startWorkflow(String workFlowName, Map<String, Object> paramMap) {
        // 根据流程id启动
        // runtimeService.startProcessInstanceById("leave1:3:15004",paramMap);
        // 通过流程定义的key启动，选取最高的version启动
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName, paramMap);
        if (Optional.ofNullable(processInstance).isPresent()
                && processInstance.getProcessInstanceId().length() > 0) {
            // 获取流程启动产生的taskId
            String taskId = taskService.createTaskQuery()
                    .taskCandidateOrAssigned(paramMap.get("applyUser") + "")
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .singleResult().getId();
            // 自动完成第一个提交
            completeTask(taskId);
            return true;
        }
        return false;
    }

    @Override
    public List<Task> listTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(assignee).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void gatewayComplete(Map<String, Object> paramMap, String taskId) {
        taskService.complete(taskId, paramMap);
    }

    @Override
    public HistoricProcessInstanceQuery listFinishedTask(String workFlowName) {
        return historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(workFlowName).finished()
                .orderByProcessInstanceEndTime().desc();
    }

    @Override
    public HistoricActivityInstanceQuery listActivityInstance(String assignee) {
        return historyService.createHistoricActivityInstanceQuery()
                .taskAssignee(assignee)
                .orderByProcessInstanceId()
                .desc();
    }

    @Override
    public HistoricActivityInstanceQuery listHisActNodeByProcessInstanceId(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc();
    }

    @Override
    public HistoricVariableInstanceQuery listHisVariableByProcessInstanceId(String processInstanceId) {
        return historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByVariableName()
                .desc();
    }

    @Override
    public HistoricTaskInstanceQuery listHisTaskByProcessInstanceId(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime()
                .asc();
    }

    @Override
    public ProcessInstanceQuery listActiveTask() {
        return runtimeService.createProcessInstanceQuery().active()
                .orderByProcessDefinitionKey()
                .orderByProcessInstanceId()
                .desc();
    }

    @Override
    public ProcessInstanceQuery listActiveTaskByWorkFlow(String workFlowName) {
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(workFlowName)
                .orderByProcessInstanceId()
                .desc();
    }

    @Override
    public Deployment deployBpmn(String bpmnName, String filePathAndName) {
        return repositoryService.createDeployment()
                // 部署规则的现实别名
                .name(bpmnName)
                // 规则文件部署
                .addClasspathResource(filePathAndName)
                .deploy();

    }

    @Override
    public List<ProcessDefinition> listProcessDefinition() {
        return repositoryService.createProcessDefinitionQuery()
                .orderByDeploymentId()
                .desc()
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeployment(String deploymentId) {
        // 普通删除，如果当前有未完成的流程，则抛出异常
        repositoryService.deleteDeployment(deploymentId);
        // 级联删除，删除所有的当前部署流程相关信息，包括未完成的和历史记录流程信息
        //repositoryService.deleteDeployment(deploymentId,true);
    }

}
