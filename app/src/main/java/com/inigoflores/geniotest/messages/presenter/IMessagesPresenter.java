package com.inigoflores.geniotest.messages.presenter;

import com.inigoflores.geniotest.messages.repository.Messages;

/**
 * Created by inigo on 13/11/17.
 */

public interface IMessagesPresenter {
    void getMessages();
    void setMessages(Messages messages);
}
