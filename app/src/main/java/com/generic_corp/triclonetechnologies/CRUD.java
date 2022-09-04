package com.generic_corp.triclonetechnologies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CRUD extends AppCompatActivity {

    private Button mSaveBtn,updateButton,deleteButton,readButton;
    private EditText ID,Name,number,update_existName,
            update_newName,update_existNumber,update_newNumer,delete_name;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        readButton = findViewById(R.id.readButton);
        ID = findViewById(R.id.editTextTextPersonName);
        Name = findViewById(R.id.editTextTextPersonName2);
        number = findViewById(R.id.editTextTextPersonName3);
        mSaveBtn = findViewById(R.id.button2);
        update_existName = findViewById(R.id.update_existingName);
        update_newName = findViewById(R.id.update_newName);
        update_existNumber = findViewById(R.id.update_existingNumber);
        update_newNumer = findViewById(R.id.update_newNumber);
        updateButton = findViewById(R.id.updateButton);
        delete_name = findViewById(R.id.delete_name);
        deleteButton = findViewById(R.id.deleteButton);
        db= FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_f = ID.getText().toString();
                //String id_f = UUID.randomUUID().toString();
                String  name_f = Name.getText().toString();
                String  number_f = number.getText().toString();

                 saveToFireStore(id_f,name_f,number_f);
            }
        });



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ex_name = update_existName.getText().toString();
                String new_name = update_newName.getText().toString();
                String  ex_number = update_existNumber.getText().toString();
                String new_number = update_newNumer.getText().toString();
                Updatedata(ex_name,new_name,ex_number,new_number);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteName = delete_name.getText().toString();
                DeleteData(deleteName);
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CRUD.this,ReadData.class);
                startActivity(i);

            }
        });
    }

    private void DeleteData(String deleteName) {

        db.collection("Documents").whereEqualTo("Name",deleteName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentId = documentSnapshot.getId();
                    db.collection("Documents").document(documentId)
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CRUD.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CRUD.this, "Failed delete", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(CRUD.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Updatedata(String ex_name, String new_name, String ex_number, String new_number) {

        Map<String,Object> userDetail = new HashMap<>();
        userDetail.put("Name",new_name);
        userDetail.put("Number",new_number);

        db.collection("Documents").whereEqualTo("Name",ex_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentId = documentSnapshot.getId();
                    db.collection("Documents").document(documentId)
                            .update(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CRUD.this, "Name Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CRUD.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(CRUD.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        db.collection("Documents").whereEqualTo("Number",ex_number).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentId = documentSnapshot.getId();
                    db.collection("Documents").document(documentId)
                            .update(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CRUD.this, "Number Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CRUD.this, "Failed to update number", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(CRUD.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void saveToFireStore(String id , String title , String desc){

        if (!title.isEmpty() && !desc.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("Id" , id);
            map.put("Name" , title);
            map.put("Number" , desc);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CRUD.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CRUD.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}