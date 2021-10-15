package pojo.outputObj;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ID",
        "Name"
})

public class MemberList {

    @JsonProperty("ID")
    private String member_ID;
    @JsonProperty("Name")
    private String member_Name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ID")
    public String getID() {
        return member_ID;
    }
    @JsonProperty("ID")
    public void setID(String id) {
        this.member_ID = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return member_Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        this.member_Name = name;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
