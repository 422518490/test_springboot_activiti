# test_springboot_activiti
项目主要是springboot和activiti6的整合，实现了如下功能：
* 1.流程部署相关的发布、删除及查询
* 2.流程的启动
* 3.按照分配的人员获取有效的流程、历史流程信息等相关的流程查询
* 4.定时启动流程
* 5.流程超时
* 6.超时邮件提醒
* 7.会签并行流程
* 8.流程事件类型监听(部分)<br>
项目中提供了流程画图的activiti-app.war包，将war包放到Tomcat等容器中启动后，访问http://localhost:8080/activiti-app 可以打开页面，用户名admin,密码test即可,在processes页签通过创建create process就可以画流程图。如果在开发工具中无法打开流程图的展示效果，请在页面中导入打开<br>
