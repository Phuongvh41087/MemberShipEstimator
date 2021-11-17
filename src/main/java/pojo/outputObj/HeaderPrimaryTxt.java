package pojo.outputObj;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Member Header",
        "Count"
})

public class HeaderPrimaryTxt {

    @JsonProperty("Member Header")
    private String member_Header;
    @JsonProperty("Count")
    private int count;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public HeaderPrimaryTxt(String member_Header, int count) {
        this.member_Header = member_Header;
        this.count = count;
    }

    @JsonProperty("Member Header")
    public void setMember_Header(String member_Header) {
        this.member_Header = member_Header;
    }
    @JsonProperty("Member Header")
    public String getMember_Header() {
        return member_Header;
    }

    @JsonProperty("Count")
    public void setCount(int count) {
        this.count = count;
    }
    @JsonProperty("Count")
    public int getCount() {
        return count;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}