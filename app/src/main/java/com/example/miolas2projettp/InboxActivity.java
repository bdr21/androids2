package com.example.miolas2projettp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore ff;
    List<QueryDocumentSnapshot> messages;
    TableLayout msgs_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        ff = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        msgs_table = (TableLayout) findViewById(R.id.table_de_messages);

        getMessages();
    }
    public void populateTable() {
        int i = 0;
        for (QueryDocumentSnapshot msg: messages) {
            populateRow(i); i++;
        }
    }
    public void populateRow(int pos) {
//        ConstraintLayout cLayout = new ConstraintLayout(this);
        TableRow tr = new TableRow(this);
        TextView tv_sender = new TextView(this);
        TextView tv_content = new TextView(this);
        tv_sender.setText(messages.get(pos).get("uid_sender").toString());
        tv_content.setText(messages.get(pos).get("text").toString());
        tr.addView(tv_sender);
        tr.addView(tv_content);
        msgs_table.addView(tr);
    }

    public void getMessages() {
        String email = auth.getCurrentUser().getEmail();
        ff.collection("messages")
                .whereEqualTo("uid_receiver", email)
                .orderBy("createdAt")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("tryingListener", "Listen failed.", e);
                            return;
                        }

                        messages = new ArrayList<>();
                        for (QueryDocumentSnapshot msg : value) {
                            messages.add(msg);
                        }
                        populateTable();
                        Log.d("tryingListener", "here is list of messages: " + messages);
                    }
                });
    }


}