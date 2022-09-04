package com.generic_corp.triclonetechnologies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ReadData extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.readData);

        Query query = FirebaseFirestore.getInstance().collection("Documents");
        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query,Model.class).build();

         adapter = new FirestoreRecyclerAdapter<Model, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull Model model) {
                holder.r_id.setText(model.getId());
                holder.r_name.setText(model.getName());
                holder.r_number.setText(model.getNumber());
            }
        };
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(adapter);


    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView r_id,r_name,r_number;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            r_id = itemView.findViewById(R.id.getData_ID);
            r_name = itemView.findViewById(R.id.getData_Name);
            r_number = itemView.findViewById(R.id.getData_Number);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}