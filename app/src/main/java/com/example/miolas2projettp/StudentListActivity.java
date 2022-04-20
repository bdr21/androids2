package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private QuerySnapshot example_data;
//    private String[] filieres_data;
//    private String[] addinfo_data;
    public FirebaseFirestore fire_db;
    public FirebaseStorage fb_storage;
    public static StorageReference stoRef;

    private RecyclerView rv_student_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        fire_db = FirebaseFirestore.getInstance();
        fire_db.collection("users")
                .whereEqualTo("role", "STUDENT")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Recycling", "Error getting documents: ", task.getException());
                            return;
                        }
                        example_data = task.getResult();
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                        }
                    }
                });
//        example_data = new String[]{"Omar", "Oussama", "Koksal", "Whateva", "Stone Cold"};
//        filieres_data = new String[]{"Omar FL", "Oussama FL", "Koksal FL", "Whateva FL", "Stone Cold FL"};
//        addinfo_data = new String[]{"Omar ADDINFO", "Oussama ADDINFO", "Koksal ADDINFO", "Whateva ADDINFO", "Stone Cold ADDINFO"};
        rv_student_list = (RecyclerView) findViewById(R.id.rv_student_list);

        fb_storage = FirebaseStorage.getInstance();
        stoRef = fb_storage.getReference();
        RVAdapter slAdapter = new RVAdapter(example_data, this);
        rv_student_list.setAdapter(slAdapter);
        rv_student_list.setLayoutManager(new LinearLayoutManager(this));
//        Intent tent = new Intent();
//        tent.setAction(Intent.ACTION_);
//        tent.addCategory();
//        startActivity(tent);
    }


}