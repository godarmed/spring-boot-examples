package com.zzy.activiti.common.dto;

import java.util.Objects;

/**
 * @Classname Task
 * @Description TODO
 * @Date 2020/10/21 14:46
 * @Created by Zzy
 */
public class TaskQueryDTO {
    private String candidate;
    private String assignee;
    private String taskId;
    private String taskDefKey;
    private String processDefId;
    private String processInstId;

    public TaskQueryDTO() {
    }

    public String getCandidate() {
        return this.candidate;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskDefKey() {
        return this.taskDefKey;
    }

    public String getProcessDefId() {
        return this.processDefId;
    }

    public String getProcessInstId() {
        return this.processInstId;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    protected boolean canEqual(Object other) {
        return other instanceof TaskQueryDTO;
    }

    @Override
    public int hashCode() {

        return Objects.hash(candidate, assignee, taskId, taskDefKey, processDefId, processInstId);
    }

    @Override
    public String toString() {
        return "TaskQueryDTO{" +
                "candidate='" + candidate + '\'' +
                ", assignee='" + assignee + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskDefKey='" + taskDefKey + '\'' +
                ", processDefId='" + processDefId + '\'' +
                ", processInstId='" + processInstId + '\'' +
                '}';
    }
}

