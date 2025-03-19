package com.example.databaseprogrammingwithsqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private Cursor cursor;

    public UserAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Use the custom user_item.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));  // Get the ID
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));

            // Set data in the TextViews
            holder.idTextView.setText("ID: " + id);  // Display the ID
            holder.nameTextView.setText("Name: " + name);
            holder.ageTextView.setText("Age: " + age);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView nameTextView;
        TextView ageTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            // Link the TextViews from the custom layout
            idTextView = itemView.findViewById(R.id.text1);  // ID TextView
            nameTextView = itemView.findViewById(R.id.text2);  // Name TextView
            ageTextView = itemView.findViewById(R.id.text3);  // Age TextView
        }
    }
}
