package com.inigoflores.geniotest.messages.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inigoflores.geniotest.R;
import com.inigoflores.geniotest.application.GenioApplication;
import com.inigoflores.geniotest.mapplaces.view.PlaceActivity;
import com.inigoflores.geniotest.messages.utilities.DownloadImage;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by inigo on 13/11/17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{
    private int resource;
    private Activity activity;

    public MessagesAdapter(int resource, Activity activity){
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        int pos = getItemViewType(position);
        holder.messageText.setText(GenioApplication.messages.getMessages().get(pos).getText());

        boolean isLeft = false;

        if(GenioApplication.messages.getMessages()
                .get(pos).getType().toString().equals("data_0")){
            holder.messageImageLeft.setVisibility(View.INVISIBLE);
            holder.messageImageRight.setVisibility(View.VISIBLE);
            isLeft = false;
        }
        else if (GenioApplication.messages.getMessages()
                .get(pos).getType().toString().equals("data_1")){
            holder.messageImageLeft.setVisibility(View.VISIBLE);
            holder.messageImageRight.setVisibility(View.INVISIBLE);
            isLeft = true;
        }

        if (GenioApplication.messages.getMessages().get(pos).getBitmap() == null) {
          new DownloadImage(((isLeft)?holder.messageImageLeft:holder.messageImageRight), GenioApplication.messages.getMessages()
                  .get(pos)).execute(GenioApplication.messages.getMessages().get(pos).getImage());
        } else {
            ((isLeft)?holder.messageImageLeft:holder.messageImageRight)
                    .setImageBitmap(GenioApplication.messages.getMessages().get(pos).getBitmap());
        }

        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PlaceActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return GenioApplication.messages.getMessages().size();
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder{

        private View listItem;

        private CircleImageView messageImageLeft;
        private TextView messageText;
        private CircleImageView messageImageRight;

        public MessagesViewHolder(View itemView) {
            super(itemView);

            listItem = (View)itemView.findViewById(R.id.ll_list_item);

            messageImageLeft = (CircleImageView)itemView.findViewById(R.id.ci_imageLeft);
            messageText = (TextView)itemView.findViewById(R.id.tvTextMessage);
            messageImageRight = (CircleImageView)itemView.findViewById(R.id.ci_imageRight);
        }
    }
}
