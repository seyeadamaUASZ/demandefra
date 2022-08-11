package sn.gainde2000.pi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowItem {
    private String name;
    private String groupId;
    private  String version;
    private  String description;


    public WorkflowItem() {

    }

    public WorkflowItem(String name, String groupId,String version, String description) {
        this.name = name;
        this.groupId = groupId;
        this.version = version;
        this.description = description;


    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}


