package org.activiti.examples.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.examples.DemoApplication;
import org.activiti.examples.entity.Person;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@RunWith(SpringJUnit4ClassRunner.class)
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiDemoTest {

    @Autowired
    ProcessEngine processEngine;

    @Test
    public void deploy() {
        //获取仓库服务 ：管理流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()//创建一个部署的构建器
                .addClasspathResource("processes/LeaveActiviti.bpmn")//从类路径中添加资源,一次只能添加一个资源
                .name("请求单流程")//设置部署的名称
                .category("办公类别")//设置部署的类别
                .deploy();

        System.out.println("部署的id" + deploy.getId());
        System.out.println("部署的名称" + deploy.getName());
    }

    @Test
    public void startProcess() {

        //指定执行我们刚才部署的工作流程
        String processDefiKey = "leaveBill";
        //取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //取得流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefiKey);//通过流程定义的key 来执行流程
        System.out.println("流程实例id:" + pi.getId());//流程实例id
        System.out.println("流程定义id:" + pi.getProcessDefinitionId());//输出流程定义的id
    }

    //查询任务
    @Test
    public void queryTask() {
        //任务的办理人
        String assignee = "Zzy";
        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //办理人的任务列表
        List<Task> list = taskQuery.taskAssignee(assignee)//指定办理人
                .list();
        //遍历任务列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务的id：" + task.getId());
                System.out.println("任务的名称：" + task.getName());
            }
        }
    }

    //完成任务
    @Test
    public void compileTask() {
        String taskId = "12504";
        //taskId：任务id
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务执行完毕");
    }

    //查看bpmn 资源图片
    @Test
    public void viewImage() throws Exception {
        String deploymentId = "5";
        String imageName = "";
        //取得某个部署的资源的名称  deploymentId
        List<String> resourceNames = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        // buybill.bpmn  buybill.png
        if (resourceNames != null && resourceNames.size() > 0) {
            for (String temp : resourceNames) {
                if (temp.indexOf(".png") > 0) {
                    imageName = temp;
                }
            }
        }

        /**
         * 读取资源
         * deploymentId:部署的id
         * resourceName：资源的文件名
         */
        InputStream resourceAsStream = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, imageName);

        //把文件输入流写入到文件中
        File file = new File("d:/" + imageName);
        FileUtils.copyInputStreamToFile(resourceAsStream, file);
    }

    //查看流程定义
    @Test
    public void queryProcessDefination() {
        String processDefiKey = "leaveBill";//流程定义key
        //获取流程定义列表
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery()
                //查询 ，好比where
//        .processDefinitionId(proDefiId) //流程定义id
                // 流程定义id  ： buyBill:2:704   组成 ： proDefikey（流程定义key）+version(版本)+自动生成id
                .processDefinitionKey(processDefiKey)//流程定义key 由bpmn 的 process 的  id属性决定
//        .processDefinitionName(name)//流程定义名称  由bpmn 的 process 的  name属性决定
//        .processDefinitionVersion(version)//流程定义的版本
                .latestVersion()//最新版本

                //排序
                .orderByProcessDefinitionVersion().desc()//按版本的降序排序

                //结果
//        .count()//统计结果
//        .listPage(arg0, arg1)//分页查询
                .list();


        //遍历结果
        if (list != null && list.size() > 0) {
            for (ProcessDefinition temp : list) {
                System.out.print("流程定义的id: " + temp.getId());
                System.out.print("流程定义的key: " + temp.getKey());
                System.out.print("流程定义的版本: " + temp.getVersion());
                System.out.print("流程定义部署的id: " + temp.getDeploymentId());
                System.out.println("流程定义的名称: " + temp.getName());
            }
        }
    }

    //获取流程实例的状态
    @Test
    public void getProcessInstanceState() {
        String processInstanceId = "2501";
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();//返回的数据要么是单行，要么是空 ，其他情况报错
        //判断流程实例的状态
        if (pi != null) {
            System.out.println("该流程实例" + processInstanceId + "正在运行...  " + "当前活动的任务:" + pi.getActivityId());
        } else {
            System.out.println("当前的流程实例" + processInstanceId + " 已经结束！");
        }

    }

    //查看历史执行流程实例信息
    @Test
    public void queryHistoryProcInst(){
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .list();
        if(list!=null&&list.size()>0){
            for(HistoricProcessInstance temp:list){
                System.out.println("历史流程实例id:"+temp.getId());
                System.out.println("历史流程定义的id:"+temp.getProcessDefinitionId());
                System.out.println("历史流程实例开始时间--结束时间:"+temp.getStartTime()+"-->"+temp.getEndTime());
            }
        }
    }

    @Test
    public void queryHistoryTask(){
        String processInstanceId="2501";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list!=null&&list.size()>0){
            for(HistoricTaskInstance temp:list){
                System.out.print("历史流程实例任务id:"+temp.getId());
                System.out.print("历史流程定义的id:"+temp.getProcessDefinitionId());
                System.out.print("历史流程实例任务名称:"+temp.getName());
                System.out.println("历史流程实例任务处理人:"+temp.getAssignee());
            }
        }
    }

    //模拟流程变量设置
    @Test
    public void  getAndSetProcessVariable(){
        //有两种服务可以设置流程变量
//        TaskService taskService = processEngine.getTaskService();
//        RuntimeService runtimeService = processEngine.getRuntimeService();

        /**1.通过 runtimeService 来设置流程变量
         * executionId: 执行对象
         * variableName：变量名
         * values：变量值
         */
//        runtimeService.setVariable(executionId, variableName, values);
//        runtimeService.setVariableLocal(executionId, variableName, values);
        //设置本执行对象的变量 ，该变量的作用域只在当前的execution对象
//        runtimeService.setVariables(executionId, variables);
        //可以设置多个变量  放在 Map<key,value>  Map<String,Object>

        /**2. 通过TaskService来设置流程变量
         * taskId：任务id
         */
//        taskService.setVariable(taskId, variableName, values);
//        taskService.setVariableLocal(taskId, variableName, values);
////        设置本执行对象的变量 ，该变量的作用域只在当前的execution对象
//        taskService.setVariables(taskId, variables); //设置的是Map<key,values>

        /**3. 当流程开始执行的时候，设置变量参数
         * processDefiKey: 流程定义的key
         * variables： 设置多个变量  Map<key,values>
         */
//        processEngine.getRuntimeService()
//        .startProcessInstanceByKey(processDefiKey, variables)

        /**4. 当任务完成时候，可以设置流程变量
         * taskId:任务id
         * variables： 设置多个变量  Map<key,values>
         */
//        processEngine.getTaskService().complete(taskId, variables);


        /** 5. 通过RuntimeService取变量值
         * exxcutionId： 执行对象
         *
         */
//        runtimeService.getVariable(executionId, variableName);//取变量
//        runtimeService.getVariableLocal(executionId, variableName);//取本执行对象的某个变量
//        runtimeService.getVariables(variablesName);//取当前执行对象的所有变量
        /** 6. 通过TaskService取变量值
         * TaskId： 执行对象
         *
         */
//        taskService.getVariable(taskId, variableName);//取变量
//        taskService.getVariableLocal(taskId, variableName);//取本执行对象的某个变量
//        taskService.getVariables(taskId);//取当前执行对象的所有变量
    }

    //设置流程变量值
    @Test
    public void setVariable(){
        String taskId="12504";//任务id
        //采用TaskService来设置流程变量

        //1. 第一次设置流程变量
//        TaskService taskService = processEngine.getTaskService();
//        taskService.setVariable(taskId, "cost", 1000);//设置单一的变量，作用域在整个流程实例
//        taskService.setVariable(taskId, "申请时间", new Date());
//        taskService.setVariableLocal(taskId, "申请人", "何某某");//该变量只有在本任务中是有效的


        //2. 在不同的任务中设置变量
//        TaskService taskService = processEngine.getTaskService();
//        taskService.setVariable(taskId, "cost", 5000);//设置单一的变量，作用域在整个流程实例
//        taskService.setVariable(taskId, "申请时间", new Date());
//        taskService.setVariableLocal(taskId, "申请人", "李某某");//该变量只有在本任务中是有效的

        /**
         * 3. 变量支持的类型
         * - 简单的类型 ：String 、boolean、Integer、double、date
         * - 自定义对象bean
         */
        TaskService taskService = processEngine.getTaskService();
        //传递的一个自定义bean对象
        Person appayBillBean=new Person();
        appayBillBean.setId(1);
        appayBillBean.setNote("300");
        appayBillBean.setDate(new Date());
        appayBillBean.setName("何某某");
        taskService.setVariable(taskId, "appayBillBean", appayBillBean);


        System.out.println("设置成功！");

    }

    //查询流程变量
    @Test
    public void getVariable(){
        String taskId="12504";//任务id
//        TaskService taskService = processEngine.getTaskService();
//        Integer cost=(Integer) taskService.getVariable(taskId, "cost");//取变量
//        Date date=(Date) taskService.getVariable(taskId, "申请时间");//取本任务中的变量
////        Date date=(Date) taskService.getVariableLocal(taskId, "申请时间");//取本任务中的变量
//        String appayPerson=(String) taskService.getVariableLocal(taskId, "申请人");//取本任务中的变量
////        String appayPerson=(String) taskService.getVariable(taskId, "申请人");//取本任务中的变量
//
//        System.out.println("金额:"+cost);
//        System.out.println("申请时间:"+date);
//        System.out.println("申请人:"+appayPerson);


        //读取实现序列化的对象变量数据
        TaskService taskService = processEngine.getTaskService();
        Person appayBillBean=(Person) taskService.getVariable(taskId, "appayBillBean");
        System.out.println(appayBillBean.getName());
        System.out.println(appayBillBean.getNote());

    }
}