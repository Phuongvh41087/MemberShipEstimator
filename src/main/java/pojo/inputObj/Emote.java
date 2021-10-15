package pojo.inputObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "images",
    "is_custom_emoji",
    "name",
    "search_terms",
    "shortcuts"
})
@Generated("jsonschema2pojo")
public class Emote {

    @JsonProperty("id")
    private String id;
    @JsonProperty("images")
    private List<Image__1> images = null;
    @JsonProperty("is_custom_emoji")
    private Boolean isCustomEmoji;
    @JsonProperty("name")
    private String name;
    @JsonProperty("search_terms")
    private List<String> searchTerms = null;
    @JsonProperty("shortcuts")
    private List<String> shortcuts = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("images")
    public List<Image__1> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<Image__1> images) {
        this.images = images;
    }

    @JsonProperty("is_custom_emoji")
    public Boolean getIsCustomEmoji() {
        return isCustomEmoji;
    }

    @JsonProperty("is_custom_emoji")
    public void setIsCustomEmoji(Boolean isCustomEmoji) {
        this.isCustomEmoji = isCustomEmoji;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("search_terms")
    public List<String> getSearchTerms() {
        return searchTerms;
    }

    @JsonProperty("search_terms")
    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    @JsonProperty("shortcuts")
    public List<String> getShortcuts() {
        return shortcuts;
    }

    @JsonProperty("shortcuts")
    public void setShortcuts(List<String> shortcuts) {
        this.shortcuts = shortcuts;
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
