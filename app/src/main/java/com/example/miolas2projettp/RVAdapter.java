package com.example.miolas2projettp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {
//    private String[] dummy_data , filieres , addinfos;
    private Context context;
    private QuerySnapshot dummy_data;
//    public RVAdapter(String[] param_data , String[] param_filieres , String[] param_addinfo, Context context) {
//        this.dummy_data = param_data;
//        this.filieres = param_filieres;
//        this.addinfos = param_addinfo;
//        this.context = context;
//    }
    public RVAdapter(QuerySnapshot param_data, Context context) {
        this.dummy_data = param_data;
        this.context = context;
    }

    public static class RVHolder extends RecyclerView.ViewHolder {
        private final TextView tv_student_name , tv_filiere , tv_additional_info;
        private ImageView iv_student_photo;
        public RVHolder(View v) {
            super(v);
            tv_student_name = (TextView) v.findViewById(R.id.student_name);
            tv_filiere = (TextView) v.findViewById(R.id.filiere);
            tv_additional_info = (TextView) v.findViewById(R.id.additional_info);
            iv_student_photo = (ImageView) v.findViewById(R.id.student_photo);
        }
        public TextView getTv_student_name() { return tv_student_name; }
        public TextView getTv_filiere() { return tv_filiere; }
        public TextView getTv_additional_info() { return tv_additional_info; }
        public ImageView getIv_student_photo() { return iv_student_photo; }
    }

    @NonNull
    @Override
    public RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int rv_row = R.layout.rv_row_item;
        View v = inflater.inflate(rv_row, parent, false);
        return new RVHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVHolder holder, int pos) {
        holder.getTv_student_name().setText(dummy_data.getDocuments().get(0).get("full_name").toString());
        holder.getTv_filiere().setText(dummy_data.getDocuments().get(0).get("filiere").toString());
        holder.getTv_additional_info().setText(dummy_data.getDocuments().get(0).get("description").toString());
        Glide.with(context).load("https://goo.gl/gEgYUd").into(holder.getIv_student_photo());
//        holder.getIv_student_photo().setImageResource(R.drawable.ensias);
    }

    @Override
    public int getItemCount() {
        if (dummy_data == null) return 0;
        return dummy_data.getDocuments().size();
    }
}
