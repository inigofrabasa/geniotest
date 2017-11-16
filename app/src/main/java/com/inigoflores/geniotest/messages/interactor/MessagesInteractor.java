package com.inigoflores.geniotest.messages.interactor;

import com.inigoflores.geniotest.application.GenioApplication;
import com.inigoflores.geniotest.messages.presenter.IMessagesPresenter;
import com.inigoflores.geniotest.messages.repository.IDataService;
import com.inigoflores.geniotest.messages.repository.Messages;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by inigo on 13/11/17.
 */

public class MessagesInteractor implements IMessagesInteractor {

    private IMessagesPresenter messagesPresenter;

    public MessagesInteractor(IMessagesPresenter messagesPresenter){
        this.messagesPresenter = messagesPresenter;
    }

    @Override
    public void getMessages() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.hellogenio.com/common/global/test/android/sample/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IDataService service =  retrofit.create(IDataService.class);

        Call<Messages> messagesCall = service.getMessages();
        if(messagesCall != null){
            messagesCall.enqueue(new Callback<Messages>() {
                @Override
                public void onResponse(Call<Messages> call, Response<Messages> response) {
                    if(response.body() != null){
                        GenioApplication.messages = response.body();
                        messagesPresenter.setMessages(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Messages> call, Throwable t) {
                }
            });
        }
    }
}
