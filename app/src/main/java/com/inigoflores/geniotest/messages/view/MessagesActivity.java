package com.inigoflores.geniotest.messages.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inigoflores.geniotest.R;
import com.inigoflores.geniotest.messages.adapters.MessagesAdapter;
import com.inigoflores.geniotest.messages.presenter.IMessagesPresenter;
import com.inigoflores.geniotest.messages.presenter.MessagesPresenter;
import com.inigoflores.geniotest.messages.repository.Messages;

public class MessagesActivity extends AppCompatActivity implements IMessagesView {

    private IMessagesPresenter messagesPresenter;

    private RecyclerView messagesRecycler;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messagesRecycler = (RecyclerView)findViewById(R.id.rv_messages);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        messagesRecycler.setLayoutManager(linearLayoutManager);

        messagesPresenter = new MessagesPresenter(this);
        messagesPresenter.getMessages();
    }

    @Override
    public void setMessages(Messages messages) {
        if(messages != null){
            messagesAdapter
                    = new MessagesAdapter(R.layout.item_message_view, this);
            messagesRecycler.setAdapter(messagesAdapter);
            messagesRecycler.invalidate();
        }
    }
}
