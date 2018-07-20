package com.activiti.service;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/7/12
 * @description
 */
public interface ServiceTaskService extends JavaDelegate {

    void hr(DelegateExecution delegateExecution);

}
