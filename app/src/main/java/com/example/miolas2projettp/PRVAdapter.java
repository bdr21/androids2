package com.example.miolas2projettp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PRVAdapter extends RecyclerView.Adapter<PRVAdapter.PViewHolder> {
    ArrayList<QueryDocumentSnapshot> profs = new ArrayList<>();

    public PRVAdapter(ArrayList<QueryDocumentSnapshot> profs) {
        this.profs = profs;
    }

    @NonNull @Override
    public PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theRowItem = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.prv_row_item,null,false);
        PViewHolder viewHolder = new PViewHolder(theRowItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PViewHolder holder, int position) {
        QueryDocumentSnapshot prof = profs.get(position);
        holder.prof_name.setText(prof.get("full_name").toString());
        holder.departement.setText(prof.get("departement").toString());
        holder.additional_info.setText(prof.get("additional_info").toString());
//        Glide.with(context).load("https://goo.gl/gEgYUd").into(holder.getIv_student_photo());
        holder.prof_photo.setImageResource(R.drawable.icon_gerer_profile);
    }

    @Override
    public int getItemCount() {
        return profs == null ? 0 : profs.size();
    }

    public void updateContent(ArrayList<QueryDocumentSnapshot> profs) {
        this.profs = profs;
        this.notifyDataSetChanged();
    }

    class PViewHolder extends RecyclerView.ViewHolder {
        ImageView prof_photo;
        TextView additional_info , departement , prof_name;
        public PViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_photo = itemView.findViewById(R.id.prof_photo);
            additional_info = itemView.findViewById(R.id.additional_info);
            departement = itemView.findViewById(R.id.departement);
            prof_name = itemView.findViewById(R.id.prof_name);
        }
    }
}
