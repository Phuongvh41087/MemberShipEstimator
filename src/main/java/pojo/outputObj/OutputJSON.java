package pojo.outputObj;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Total Member Found",
        "Tiers",
        "Member List",
        "Title List",
        "File Processed"
})

public class OutputJSON {

    @JsonProperty("Total Member Found")
    private int totalMember;
    @JsonProperty("Tiers")
    private List<TierList> tierList;
    @JsonProperty("Member List")
    private List<MemberList> memberList;
    @JsonProperty("Title List")
    private List<MemberTitle> titleList;
    @JsonProperty("File Processed")
    private List<String> filesProcessed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Total Member Found")
    public int getTotalMember() {
        return totalMember;
    }
    @JsonProperty("Total Member Found")
    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    @JsonProperty("Tiers")
    public List<TierList> getTierList() {
        return tierList;
    }
    @JsonProperty("Tiers")
    public void setTierList(List<TierList> tierList) {
        this.tierList = tierList;
    }

    @JsonProperty("Member List")
    public List<MemberList> getMemberLists() {
        return memberList;
    }
    @JsonProperty("Member List")
    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
    }

    @JsonProperty("Title List")
    public List<MemberTitle> getTitleList() {
        return titleList;
    }
    @JsonProperty("Title List")
    public void setTitleList(List<MemberTitle> titleList) {
        this.titleList = titleList;
    }

    @JsonProperty("File Processed")
    public List<String> getFilesProcessed() {
        return filesProcessed;
    }
    @JsonProperty("File Processed")
    public void setFilesProcessed(List<String> filesProcessed) {
        this.filesProcessed = filesProcessed;
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
