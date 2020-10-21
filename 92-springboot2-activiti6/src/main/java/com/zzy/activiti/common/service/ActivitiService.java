package com.zzy.activiti.common.service;

import com.zzy.activiti.common.dto.TaskQueryDTO;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2020/10/21 14:43
 * @Created by Zzy
 */

public interface ActivitiService {

    void deploy(String var1);

    void deploy(String var1, Object var2);

    ProcessInstance startProcessInstance(String var1, Map var2);

    List queryNoClaimTaskOnTargetId(String var1, int var2, int var3);

    List queryNoClaimTask(int var1, int var2);

    void claimTask(String var1, String var2);

    List queryClaimedTask(String var1, int var2, int var3);

    List queryTaskHistory(String var1);

    List queryHistoricVariableInstance(String var1);

    void completeTask(String var1, String var2, String var3, Map<String, Object> var4);

    Map getTaskvariables(String var1);

    void rejectTask(String var1, String var2, Map<String, Object> var3);

    ProcessInstance queryProcessInstanceById(String var1);

    void deleteProcess(String var1, String var2);

    Map<String, Object> getProcessVariable(String var1, List<String> var2);

    List<Task> queryTask(TaskQueryDTO var1);

}

