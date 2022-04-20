package com.example.miolas2projettp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
//        Date dateee = new Date();
    }

    public static FirebaseFirestore fire_db;


    public static void main(String args[] ) {
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
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            System.out.println("***********************************");
                            System.out.println(document.getId() + " => " + document.getData());
                            System.out.println("***********************************");
                        }
                    }
                });
    }
//    public static void main(String args[] ) throws ParseException {
//        System.out.println("Hello, World!");
//        String date_string = "23-03-2022";
//        //Instantiating the SimpleDateFormat class
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        //Parsing the given String to Date object
////        Date date = formatter.parse(date_string);
////        System.out.println("Date value: "+date);
//
//        Date curr = new Date();
////        System.out.println(getDateDiff(date, curr, TimeUnit.DAYS));
//
//        Calendar currentCalendar = Calendar.getInstance();
//        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
//        System.out.println("curr :" +week);
//
//        LocalDate date = LocalDate.of(2017, Month.DECEMBER, 31);
//        System.out.println("ISO week of year  " + date.get(WeekFields.ISO.weekOfWeekBasedYear()));
//        System.out.println("ISO week of month " + date.get(WeekFields.ISO.weekOfMonth()));
//        System.out.println("US week of year   " + date.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear()));
//        System.out.println("US week of month  " + date.get(WeekFields.SUNDAY_START.weekOfMonth()));
//    }
}
