package com.activiti;

import com.activiti.listener.BootActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liaoyubo
 * @version 1.0
 * @date 2018/6/29
 * @description
 */
@SpringBootApplication
public class App {

    @Resource
    private SpringProcessEngineConfiguration springProcessEngineConfiguration;

    @Resource
    private BootActivitiEventListener bootActivitiEventListener;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * 流程事件处理，事件类型可参考
     * https://www.activiti.org/userguide/#eventDispatcherEventTypes
     */
    @PostConstruct
    public void initEventListeners() {
        // 定义监听的集合
        List<ActivitiEventListener> listeners = new ArrayList<>();
        listeners.add(bootActivitiEventListener);

        Map<String, List<ActivitiEventListener>> map = new HashMap<>();
        // 给每种事件类型添加监听集合
        map.put("JOB_EXECUTION_SUCCESS", listeners);
        springProcessEngineConfiguration.setTypedEventListeners(map);
    }

}
