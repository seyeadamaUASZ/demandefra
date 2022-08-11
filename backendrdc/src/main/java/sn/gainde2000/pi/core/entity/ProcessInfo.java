package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessInfo{
    private String containerId;
    private String processId;
    public ProcessInfo() {

    }

    public ProcessInfo(String containerId, String processId) {
        this.containerId = containerId;
        this.processId = processId;
    }


    public String getContainerId() {
        return containerId;
    }
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    public String getProcessId() {
        return processId;
    }
    public void setProcessId(String processId) {
        this.processId = processId;
    }

}
