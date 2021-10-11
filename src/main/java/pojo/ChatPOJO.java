package pojo;

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

import pojo.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "action_type",
    "author",
    "message",
    "message_id",
    "message_type",
    "time_in_seconds",
    "time_text",
    "timestamp",
    "emotes",
    "header_primary_text",
    "header_secondary_text"
})
@Generated("jsonschema2pojo")
public class ChatPOJO {

    @JsonProperty("action_type")
    private String actionType;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("message")
    private String message;
    @JsonProperty("message_id")
    private String messageId;
    @JsonProperty("message_type")
    private String messageType;
    @JsonProperty("time_in_seconds")
    private Integer timeInSeconds;
    @JsonProperty("time_text")
    private String timeText;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("emotes")
    private List<Emote> emotes = null;
    @JsonProperty("header_primary_text")
    private String headerPrimaryText;
    @JsonProperty("header_secondary_text")
    private String headerSecondaryText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("action_type")
    public String getActionType() {
        return actionType;
    }

    @JsonProperty("action_type")
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @JsonProperty("author")
    public Author getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author author) {
        this.author = author;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("message_id")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("message_type")
    public String getMessageType() {
        return messageType;
    }

    @JsonProperty("message_type")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @JsonProperty("time_in_seconds")
    public Integer getTimeInSeconds() {
        return timeInSeconds;
    }

    @JsonProperty("time_in_seconds")
    public void setTimeInSeconds(Integer timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    @JsonProperty("time_text")
    public String getTimeText() {
        return timeText;
    }

    @JsonProperty("time_text")
    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("emotes")
    public List<Emote> getEmotes() {
        return emotes;
    }

    @JsonProperty("emotes")
    public void setEmotes(List<Emote> emotes) {
        this.emotes = emotes;
    }

    @JsonProperty("header_primary_text")
    public String getHeaderPrimaryText() {
        return headerPrimaryText;
    }

    @JsonProperty("header_primary_text")
    public void setHeaderPrimaryText(String headerPrimaryText) {
        this.headerPrimaryText = headerPrimaryText;
    }

    @JsonProperty("header_secondary_text")
    public String getHeaderSecondaryText() {
        return headerSecondaryText;
    }

    @JsonProperty("header_secondary_text")
    public void setHeaderSecondaryText(String headerSecondaryText) {
        this.headerSecondaryText = headerSecondaryText;
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
