package com.prograpy.app2.appdev2.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.prograpy.app2.appdev2.R;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-05-15.
 */

public class ChatMainActivity extends AppCompatActivity {

    ArrayList<String> subjectList;
    ArrayAdapter<String> adapter;
    ChatDataManager chatDataManager;
    Button button;
    EditText editText;
    String s="";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatDataManager = new ChatDataManager();

        subjectList = chatDataManager.getSubjectList();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjectList);

        ListView listView = (ListView)findViewById(R.id.chatListView);
        listView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);

        button = (Button)findViewById(R.id.sendBtn);
        button.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                s = editText.getText().toString();
                chatDataManager.addData(s);
                adapter.notifyDataSetChanged();
                editText.setText("");

        }
    };

}
