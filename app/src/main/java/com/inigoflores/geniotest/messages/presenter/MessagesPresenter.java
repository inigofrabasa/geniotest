package com.inigoflores.geniotest.messages.presenter;

import com.inigoflores.geniotest.messages.interactor.IMessagesInteractor;
import com.inigoflores.geniotest.messages.interactor.MessagesInteractor;
import com.inigoflores.geniotest.messages.repository.Messages;
import com.inigoflores.geniotest.messages.view.IMessagesView;

/**
 * Created by inigo on 13/11/17.
 */

public class MessagesPresenter implements IMessagesPresenter {

    private IMessagesView messageView;
    private IMessagesInteractor messageInteractor;

    public MessagesPresenter(IMessagesView messageView){
        this.messageView = messageView;
        messageInteractor= new MessagesInteractor(this);
    }

    @Override
    public void getMessages() {
        messageInteractor.getMessages();

    }

    @Override
    public void setMessages(Messages messages) {
        messageView.setMessages(messages);
    }
}
