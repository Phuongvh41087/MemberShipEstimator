package pojo.outputObj;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Tier Message",
        "Count"
})

public class TierList {

    @JsonProperty("Tier Message")
    private String tierMessage;
    @JsonProperty("Count")
    private int tierCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Tier Message")
    public String getTierMessage() {
        return tierMessage;
    }
    @JsonProperty("Tier Message")
    public void setTierMessage(String tierMessage) {
        this.tierMessage = tierMessage;
    }

    @JsonProperty("Count")
    public int getTierCount() {
        return tierCount;
    }
    @JsonProperty("Count")
    public void setTierCount(int tierCount) {
        this.tierCount = tierCount;
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
