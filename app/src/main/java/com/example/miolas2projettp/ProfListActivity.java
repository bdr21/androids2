package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfListActivity extends AppCompatActivity {

    RecyclerView profListRV;
    ArrayList<QueryDocumentSnapshot> profs = new ArrayList<>();
    PRVAdapter prvAdapter;
    private FirebaseFirestore ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_list);

        ff = FirebaseFirestore.getInstance();
        profListRV = (RecyclerView) findViewById(R.id.prof_list_rv);
        getProfs();
//        ArrayList<User> profs = new ArrayList<>();
//        profs.add(new User("faqihi@s.co","ahmed faqihi","WME","docteur bla bla",R.drawable.icon_gerer_profile));
//        profs.add(new User("faqihi23@s.co","ahmed faqihi23","BI","docteur wld rachidiya",R.drawable.icon_gerer_profile));
//        profs.add(new User("faqihi46@s.co","ahmed faqihi46","IOT","9ra f fsr",R.drawable.icon_gerer_profile));

        prvAdapter = new PRVAdapter(profs);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);

        profListRV.setHasFixedSize(true);
        profListRV.setLayoutManager(lm);
        profListRV.setAdapter(prvAdapter);

        Log.d("recycling","this is proflistactivity");
    }

    public void getProfs() {
        ff.collection("users")
                .whereEqualTo("role", "PROFESSOR")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("tryingListener", "Listen failed.", e);
                            return;
                        }

                        profs = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            profs.add(doc);
                        }
                        Log.d("tryingListener", "here is list of profs: " + profs);
                        prvAdapter.updateContent(profs);
                    }
                });
    }
}

//ff.collection("users")
//        .whereEqualTo("role", "PROFESSOR")
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//@Override
//public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                        Log.d("Rec","oncomp");
//        if (!task.isSuccessful()) Log.d("Recycling", "Error getting documents: ", task.getException());
//        else {
//        profs = (ArrayList<DocumentSnapshot>) task.getResult().getDocuments();
////                            prvAdapter.notifyDataSetChanged();
////                            profListRV.setAdapter(new PRVAdapter(profs));
////                            Log.d("Rec",example_data.toString());
//        }
//        }
//        });