package com.inigoflores.geniotest.messages.repository;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by inigo on 13/11/17.
 */

public class Messages {
    @SerializedName("array")
    @Expose
    private List<Message> messages = null;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(List<Message> message) {
        this.messages = message;
    }

}
