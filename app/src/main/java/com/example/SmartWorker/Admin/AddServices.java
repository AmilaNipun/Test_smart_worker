package com.example.SmartWorker.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SmartWorker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddServices extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    //variables
    ImageView addPromotion_ImageView;
    TextView textViewProgress;
    ProgressBar progressBar;
    Button addPromotionSave;
    EditText addServiceTopic,addServiceDesc,addServicelocation,addServicecon;
    Uri imageUri;
    boolean isImageAdded = false;

    DatabaseReference dataReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        addPromotion_ImageView = findViewById(R.id.image_view_add_promotion);
        textViewProgress = findViewById(R.id.textViewProgress_Promotion);
        progressBar = findViewById(R.id.addPromotion_progressBar);
        addPromotionSave = findViewById(R.id.addPromotion_save_btn);
        addServiceTopic = findViewById(R.id.add_service_topic);
        addServiceDesc = findViewById(R.id.add_service_descrip);
        addServicelocation = findViewById(R.id.add_service_location);
        addServicecon = findViewById(R.id.add_service_contact);

        dataReference = FirebaseDatabase.getInstance().getReference().child("Add Services");
        storageReference = FirebaseStorage.getInstance().getReference().child("AddPromotionImages");

        addPromotion_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });

    }

    private boolean validateTopic(){
        String val = addServiceTopic.getText().toString();

        if(val.isEmpty()) {
            addServiceTopic.setError("Field Cannot be Empty");
            return false;
        }else if(val.length() >= 150) {
            addServiceTopic.setError("Topic is Too Long");
            return false;
        }else{
            addServiceTopic.setError(null);
            return true;
        }
    }
    private boolean validateDescription(){
        String val = addServiceDesc.getText().toString();

        if(val.isEmpty()) {
            addServiceDesc.setError("Field Cannot be Empty");
            return false;
        }else if(val.length() >= 1500) {
            addServiceDesc.setError("Description is Too Long");
            return false;
        }else{
            addServiceDesc.setError(null);
            return true;
        }
    }
    private boolean validateLocation(){
        String val = addServicelocation.getText().toString();

        if(val.isEmpty()) {
            addServicelocation.setError("Field Cannot be Empty");
            return false;
        }else{
            addServicelocation.setError(null);
            return true;
        }
    }

    private boolean validateCon(){
        String val = addServicecon.getText().toString();

        if(val.isEmpty()) {
            addServicecon.setError("Field Cannot be Empty");
            return false;
        }else{
            addServicecon.setError(null);
            return true;
        }
    }

    public void uploadPromotion(View view){
        if(!validateTopic() | !validateDescription() | !validateCon() | !validateLocation()){
            return;
        }else{
            addPromotionDetails();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE && data != null){
            imageUri = data.getData();
            isImageAdded = true;
            addPromotion_ImageView.setImageURI(imageUri);
        }
    }

    private void addPromotionDetails(){

        final String add_service_topic = addServiceTopic.getText().toString();
        final String add_service_description = addServiceDesc.getText().toString();
        final String add_service_location = addServicelocation.getText().toString();
        final String add_service_contact = addServicelocation.getText().toString();


        if(isImageAdded!=false){

            textViewProgress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);


            final String key = dataReference.push().getKey();
            storageReference.child(key+ "jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child(key+ "jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("ServiceTopic",add_service_topic);
                            hashMap.put("ServiceDescription",add_service_description);
                            hashMap.put("ServiceLocation",add_service_location);
                            hashMap.put("ServiceContact",add_service_contact);
                            hashMap.put("ImageURL",uri.toString());

                            dataReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddServices.this,"Data Successfully Uploaded",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddServices.this,AdminDashboard.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddServices.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddServices.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                    textViewProgress.setText(progress+"%");
                }
            });
        }
    }
}
