package com.activiti.service;


import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/2
 * @description
 */
public interface LeaveService {

    /**
     * 启动流程
     *
     * @param workFlowName
     * @param paramMap
     * @return
     */
    boolean startWorkflow(String workFlowName, Map<String, Object> paramMap);

    /**
     * 根据分配人信息获取流程
     *
     * @param assignee
     * @return
     */
    List<Task> listTasks(String assignee);

    /**
     * 完成任务
     *
     * @param taskId
     */
    void completeTask(String taskId);

    /**
     * 是否同意申请
     *
     * @param paramMap
     * @param taskId
     */
    void gatewayComplete(Map<String, Object> paramMap, String taskId);

    /**
     * 获取已完成的流程按照流程名称
     *
     * @param workFlowName
     * @return
     */
    HistoricProcessInstanceQuery listFinishedTask(String workFlowName);

    /**
     * 根据分配者查询参与过的节点
     *
     * @param assignee
     * @return
     */
    HistoricActivityInstanceQuery listActivityInstance(String assignee);


    /**
     * 获取所有流程中的流程
     *
     * @return
     */
    ProcessInstanceQuery listActiveTask();

    /**
     * 按照流程名称去获取流程中的流程
     *
     * @param workFlowName
     * @return
     */
    ProcessInstanceQuery listActiveTaskByWorkFlow(String workFlowName);

    /**
     * 部署流程
     *
     * @param bpmnName
     * @param filePathAndName
     * @return
     */
    Deployment deployBpmn(String bpmnName, String filePathAndName);

    /**
     * 获取流程定义
     *
     * @return
     */
    List<ProcessDefinition> listProcessDefinition();

    /**
     * 删除流程
     *
     * @param deploymentId
     */
    void deleteDeployment(String deploymentId);

    /**
     * 根据实例id获取流程运行活动节点情况
     *
     * @param processInstanceId
     * @return
     */
    HistoricActivityInstanceQuery listHisActNodeByProcessInstanceId(String processInstanceId);

    /**
     * 根据实例id获取流程变量信息
     *
     * @param processInstanceId
     * @return
     */
    HistoricVariableInstanceQuery listHisVariableByProcessInstanceId(String processInstanceId);

    /**
     * 根据实例id获取流程任务节点信息
     * @param processInstanceId
     * @return
     */
    HistoricTaskInstanceQuery listHisTaskByProcessInstanceId(String processInstanceId);

}
