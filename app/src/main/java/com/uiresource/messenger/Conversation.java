package com.uiresource.messenger;


import android.os.Bundle;
import android.os.Debug;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uiresource.messenger.recyclerview.Chat;
import com.uiresource.messenger.recylcerchat.ChatData;
import com.uiresource.messenger.recylcerchat.ConversationRecyclerView;
import com.uiresource.messenger.requests.DialogflowMessageClient;

import org.apache.http.HttpResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Conversation extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;

    /**
     * Diese Methode ist verantwortlich für das versenden von Nachrichten an Dialogflow
     */
    private void sendMessage(){
        if (!text.getText().toString().equals("")){
            List<ChatData> data = new ArrayList<ChatData>();
            ChatData item = new ChatData();
            item.setTime(getTime());
            item.setType("2");
            item.setText(text.getText().toString());
            data.add(item);
            mAdapter.addItem(data);
            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
            {
                DialogflowMessageClient dialogflowMessageClient = new DialogflowMessageClient();
                String response = dialogflowMessageClient.postData(text.getText().toString());
                data = new ArrayList<>();
                ChatData answer = new ChatData();
                answer.setTime(getTime());
                answer.setType("1");
                answer.setText(response);
                data.add(answer);
                mAdapter.addItem(data);
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
            }
            text.setText("");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setupToolbarWithUpNav(R.id.toolbar, "PizzaBot", R.drawable.ic_action_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(this,setData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);
        text = (EditText) findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 500);
            }
        });
        send = (Button) findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();
        String[] text = {"21 Januar", "Lust auf Pizza :)?"};
        String[] time = {"", "7:30"};
        String[] type = {"0", "1"};
        for (int i=0; i<text.length; i++){
            ChatData item = new ChatData();
            item.setType(type[i]);
            item.setText(text[i]);
            item.setTime(time[i]);
            data.add(item);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userphoto, menu);
        return true;
    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
}
