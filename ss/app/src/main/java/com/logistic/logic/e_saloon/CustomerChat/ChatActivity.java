package com.logistic.logic.e_saloon.CustomerChat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    EditText editText;
    Button button;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    List<ChatMessage> chatMessageList=new ArrayList<>();
    CollectionReference collectionReference;
    Bundle bundle;
    String saloonName;
    String msgContent;
    String msgTimeStamp;
    LinearLayoutManager layoutManager;
    String msgType;
    String status="abc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
            Intent intent=getIntent();
        saloonName=intent.getStringExtra("sname");
        Log.e("Chat Activity","Saloon Name :"+saloonName);
        progressBar=findViewById(R.id.chat_progress_bar);
        recyclerView=findViewById(R.id.chatRecyclerView);
        editText=findViewById(R.id.chatMessageTextView);
        button=findViewById(R.id.sendMessage);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        fetchChats();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString()!=null){

                    ChatMessage chatMessage=new ChatMessage();

                    String timeStamp=
                            new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                            .format(new java.util.Date());
                    Log.e("ChatActivity","TimeStamp"+timeStamp);
                    chatMessage= new ChatMessage
                                    (editText.getText().toString(),
                                    timeStamp
                                            ,chatMessage.MSG_TYPE_SENT);
                    editText.setText("");
                    collectionReference=firestore
                            .collection("ESaloon")
                            .document("Chats")
                            .collection(saloonName)
                            .document("Messages")
                            .collection(firebaseAuth.getUid());

                    collectionReference.add(chatMessage).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Log.e("ChatActivity","Send Chat Content Success ! ");
                            }
                        }
                    });
                    chatMessageList.add(chatMessage);
                    ChatMessageAdapter adapter=new
                            ChatMessageAdapter(recyclerView,
                            ChatActivity.this
                    ,chatMessageList);
                    recyclerView.setLayoutManager(
                            new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void fetchChats(){

        collectionReference=firestore
                .collection("ESaloon")
                .document("Chats")
                .collection(saloonName)
                .document("Messages")
                .collection(firebaseAuth.getUid());

        Log.e("ChatActivty ","Fetch Firestore Called");
        collectionReference.orderBy("timeStamp")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot :task.getResult()){
                        status="abcYes";
                        Log.e("ChatActivity",""+snapshot.getData());
                        ChatMessage chatMessage=new ChatMessage();
                        msgContent=snapshot.get("messageContent").toString();
                        msgTimeStamp=snapshot.get("timeStamp").toString();
                        msgType=snapshot.get("messageType").toString();

                        chatMessage.setMessageContent(msgContent);
                        chatMessage.setTimeStamp(msgTimeStamp);
                        chatMessage.setMessageType(msgType);
                        chatMessageList.add(chatMessage);
                        Log.e("ChatActivity","data is"+msgContent+" "+msgType);
                    }
                    layoutManager=new LinearLayoutManager(getApplicationContext());
                    layoutManager.setReverseLayout(false);
                    layoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(layoutManager);
                    ChatMessageAdapter adapter=new ChatMessageAdapter(recyclerView
                            ,ChatActivity.this,chatMessageList);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                }
                else {
                    Log.e("ChatActivity"," Task is Unsucess");
                }


            }
        });
        if (status.equals("abc")){
            collectionReference=firestore
                    .collection("ESaloon")
                    .document("Chats")
                    .collection(saloonName);
            progressBar.setVisibility(View.GONE);
            Map<String,String> map=new HashMap<>();
            map.put("AuthId",firebaseAuth.getUid());
            collectionReference
                    .document(firebaseAuth.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.e("ChatActivity"," AuthIDs Send Success !");
                    }

                }
            });
        }


    }
}
