package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EmploiActivity extends AppCompatActivity {

    private TextView dateTV;
    private TableLayout emploiTL;
    private TextView creneauxTV;
    private FirebaseFirestore FF;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi);

        auth = FirebaseAuth.getInstance();

//        Log.d("emploi","Emploi Act was run");
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(EmploiActivity.this, MainActivity.class));
            finish();
            return;
        }

        dateTV = (TextView) findViewById(R.id.dateTV);
        emploiTL = (TableLayout) findViewById(R.id.emploi);
        creneauxTV = (TextView) findViewById(R.id.creneaux_header);

        dateTV.append(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        FF = FirebaseFirestore.getInstance();

        /* we want les jours qui appartiennent à la semaine courante */
        int curr_week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
//        Log.d("date_debugging",""+curr_week);
        /* build query => Task is q.get() => add listeners to the task */
        /* building the doc ref */
        FF = FirebaseFirestore.getInstance();
        DocumentReference profRef = FF.collection("users").document(auth.getCurrentUser().getEmail());
        Task<QuerySnapshot> get_creneaux = FF.collection("jours")
                .whereEqualTo("semaine", curr_week).whereEqualTo("professeur", profRef).get();
        get_creneaux.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("date_debugging", "onComplete");
                if (!task.isSuccessful()) { Log.d("date_debugging", "Error getting documents.", task.getException()); return; }
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Timestamp cd = doc.getTimestamp("créneau_début"),
                                cf = doc.getTimestamp("créneau_fin");
                    SimpleDateFormat fmtr = new SimpleDateFormat("HH:mm"),
                                    fmtr2 = new SimpleDateFormat("dd-MM-yyyy"),
                                    fmtr3 = new SimpleDateFormat("EEEE");
                    String day_date = fmtr3.format(cd.toDate()) + "\n" + fmtr2.format(Objects.requireNonNull(cd).toDate()).toString(),
                            cren_deb = fmtr.format(cd.toDate()).toString() ,
                            cren_fin = fmtr.format(Objects.requireNonNull(cf,"doc dot get shud !benull").toDate()).toString() ,
                            matiere = doc.getString("matière") ,
                            salle = doc.getString("salle");

                    TableRow dyn_tr = new TableRow(EmploiActivity.this);
                    TextView day_cell_TV = new TextView(EmploiActivity.this);
                    day_cell_TV.setText(day_date);
                    TextView cren_cell_TV = new TextView(EmploiActivity.this);
                    String str = cren_deb + " => " + cren_fin + "\n" + salle + " => " + matiere + " <= ";
                    cren_cell_TV.setText(str);
                    dyn_tr.addView(day_cell_TV); dyn_tr.addView(cren_cell_TV);

                    emploiTL.addView(dyn_tr);
                }
            }
        });

    }


}


//        FF.collection("users")
//                .whereEqualTo("role", "STUDENT")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (!task.isSuccessful()) {
//                            Log.d("Recycling", "Error getting documents: ", task.getException());
//                            return;
//                        }
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d("YOOO", "****   *******************************");
//                            Log.d("YOOO" , document.getId() + " => " + document.getData());
//                            Log.d("YOOO", "***********************************");
//                        }
//                    }
//                });


//for (QueryDocumentSnapshot document : task.getResult()) {
//    Date date1 = document.getTimestamp("créneau_début").toDate();
//    Calendar cal = Calendar.getInstance();
//    cal.setTime(date1);
//    int month1 = cal.get(Calendar.WEEK_OF_YEAR);
//    int month2 = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
//    creneauxTV.append(document.getId() + " => " + month1 + " => " + month2 + " => "
//    + document.getTimestamp("créneau_début").toDate());
//}

//public static boolean isDateInCurrentWeek(Date date) {
//    Calendar currCal = Calendar.getInstance();
//    int week = currCal.get(Calendar.WEEK_OF_YEAR);
//    int year = currCal.get(Calendar.YEAR);
//    Calendar targetCal = Calendar.getInstance();
//    targetCal.setTime(date);
//    return week == targetCal.get(Calendar.WEEK_OF_YEAR) && year == targetCal.get(Calendar.YEAR);
//}

//doc.getDocumentReference("professeur").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if(!task.isSuccessful()) { Log.d("date_debugging", "get prof doc failed with ", task.getException()); return; }
//        DocumentSnapshot prof_doc = task.getResult();
//        if (!prof_doc.exists()) { Log.d("date_debugging", "Couldn't find prof document"); return; }
////                            Log.d("date_debugging", "DocumentSnapshot prof doc data: " + prof_doc.getData());
//        String replace = cren_cell_TV.getText().toString().replace("nom_du_prof",prof_doc.getString("full_name"));
//        cren_cell_TV.setText(String.valueOf(replace));
//    }
//});

//DocumentReference userRef = FF.collection("users").document(auth.getCurrentUser().getEmail());
//userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//    @Override
//    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if (!task.isSuccessful()) {
//            Log.d("check_user_role", "get failed with ", task.getException()); return;
//        }
//        DocumentSnapshot doc = task.getResult();
//        if (!doc.exists()) {
//            Log.d("check_user_role", "No such document"); return;
//        }
//        if (!doc.getString("role").toLowerCase().equals("professor")) {
//            good;
//        } else {
//            startActivity(new Intent(EmploiActivity.this, UnauthorizedActivity.class));
//            finish();   return;
//        };
//    }
//});