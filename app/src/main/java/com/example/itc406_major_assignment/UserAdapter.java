package com.example.itc406_major_assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    ArrayList<UserModel> userList;

    FirebaseFirestore firestore;

    public UserAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModel user = userList.get(position);

        holder.txtName.setText(user.firstname + " " + user.lastname);
        holder.txtRole.setText(user.role);

        // DELETE BUTTON (FIRESTORE)
        holder.btnDelete.setOnClickListener(v -> {

            firestore.collection("Users")
                    .document(user.id)
                    .delete()
                    .addOnSuccessListener(aVoid -> {

                        userList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, userList.size());

                    });
        });

        // EDIT BUTTON
        holder.btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(context, Edit_User_Admin.class);
            intent.putExtra("id", user.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtRole;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtRowName);
            txtRole = itemView.findViewById(R.id.txtRowRole);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}