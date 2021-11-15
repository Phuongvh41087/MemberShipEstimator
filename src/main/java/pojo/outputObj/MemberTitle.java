package pojo.outputObj;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Member Title",
        "Count"
})

public class MemberTitle {

    @JsonProperty("Member Title")
    private String mTitle_Title;
    @JsonProperty("Count")
    private int mTitle_Count;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MemberTitle(String title, int count) {
        this.mTitle_Title = title;
        this.mTitle_Count = count;
    }

    @JsonProperty("Member Title")
    public void setmTitle_Title(String title) {
        this.mTitle_Title = title;
    }
    @JsonProperty("Member Title")
    public String getmTitle_Title() {
        return mTitle_Title;
    }

    @JsonProperty("Count")
    public void setmTitle_Count(int count) {
        this.mTitle_Count = count;
    }
    @JsonProperty("Count")
    public int getmTitle_Count() {
        return mTitle_Count;
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
