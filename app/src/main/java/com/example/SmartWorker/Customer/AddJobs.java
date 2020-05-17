package com.example.SmartWorker.Customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.SmartWorker.R;
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

public class AddJobs extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    //variables
    ImageView addJob_ImageView;
    TextView textViewProgress;
    ProgressBar progressBar;
    Button addJobSave;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText addLocation,addServiceType,addJobCategory,addTitle,addDesc,addBudget,addDueDate,contactName,contactNumber;
    Uri imageUri;
    boolean isImageAdded = false;

    DatabaseReference dataReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        //hooks

        addJob_ImageView = findViewById(R.id.image_view_add);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.addCar_progressBar);
        addJobSave = findViewById(R.id.addjob_save_btn);
        addLocation = findViewById(R.id.add_location);
        addServiceType = findViewById(R.id.add_service_type);
        addJobCategory = findViewById(R.id.add_job_category);
        addTitle = findViewById(R.id.add_title);
        addDesc = findViewById(R.id.add_job_descrip);
        addBudget = findViewById(R.id.add_budget);
        addDueDate = findViewById(R.id.add_due_date);
        contactName = findViewById(R.id.addJobContactName);
        contactNumber = findViewById(R.id.addJobContactNumber);
        radioGroup = findViewById(R.id.addJobRadioGroup);



        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        dataReference = FirebaseDatabase.getInstance().getReference().child("Add Jobs");
        storageReference = FirebaseStorage.getInstance().getReference().child("AddJobImages");



        addJob_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE && data != null){
            imageUri = data.getData();
            isImageAdded = true;
            addJob_ImageView.setImageURI(imageUri);
        }
    }

    public void checkButton(View view){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
    }

    private boolean validateLocation(){
        String val = addLocation.getText().toString();

        if(val.isEmpty()) {
            addLocation.setError("Field Cannot be Empty");
            return false;
        }else if(val.length() >= 50) {
            addLocation.setError("Topic is Too Long");
            return false;
        }else{
            addLocation.setError(null);
            return true;
        }
    }
    private boolean validateServiceType(){
        String val = addServiceType.getText().toString();

        if(val.isEmpty()) {
            addServiceType.setError("Field Cannot be Empty");
            return false;
        }else{
            addServiceType.setError(null);
            return true;
        }
    }
    private boolean validateCategory(){
        String val = addJobCategory.getText().toString();

        if(val.isEmpty()) {
            addJobCategory.setError("Field Cannot be Empty");
            return false;
        }else{
            addJobCategory.setError(null);
            return true;
        }
    }
    private boolean validateTitle(){
        String val = addTitle.getText().toString();

        if(val.isEmpty()) {
            addTitle.setError("Field Cannot be Empty");
            return false;
        }else{
            addTitle.setError(null);
            return true;
        }
    }

    private boolean validateDescription(){
        String val = addDesc.getText().toString();

        if(val.isEmpty()) {
            addDesc.setError("Field Cannot be Empty");
            return false;
        }else if(val.length() >= 1500) {
            addDesc.setError("Description is Too Long");
            return false;
        }else{
            addDesc.setError(null);
            return true;
        }
    }
    private boolean validateBudget(){
        String val = addBudget.getText().toString();

        if(val.isEmpty()) {
            addBudget.setError("Field Cannot be Empty");
            return false;
        }else{
            addBudget.setError(null);
            return true;
        }
    }



    public void uploadAddCar(View view){
        if(!validateLocation() | !validateServiceType() | !validateCategory() | !validateTitle() | !validateDescription() | !validateBudget() ){
            return;
        }else{
            addCarDetails();
        }
    }

    private void addCarDetails(){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        final String radioBtnValue = radioButton.getText().toString();


        final String add_location = addLocation.getText().toString();
        final String add_service_type = addServiceType.getText().toString();
        final String add_job_category = addJobCategory.getText().toString();
        final String add_title = addTitle.getText().toString();
        final String add_desc = addDesc.getText().toString();
        final String add_Budget = addBudget.getText().toString();
        final String add_due_date = addDueDate.getText().toString();
        final String add_contact_name = contactName.getText().toString();
        final String add_contact_number = contactNumber.getText().toString();



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
                            hashMap.put("JobLocation",add_location);
                            hashMap.put("JobServiceType",add_service_type);
                            hashMap.put("JobCategory",add_job_category);
                            hashMap.put("JobTile",add_title);
                            hashMap.put("JobDescription",add_desc);
                            hashMap.put("JobBudget",add_Budget);
                            hashMap.put("JobDueDate",add_due_date);
                            hashMap.put("JobType",radioBtnValue);
                            hashMap.put("ContactName",add_contact_name);
                            hashMap.put("ContactNumber",add_contact_number);
                            hashMap.put("ImageURL",uri.toString());

                            dataReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddJobs.this,"Data Successfully Uploaded",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddJobs.this,Dashboard.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddJobs.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddJobs.this, "Error", Toast.LENGTH_SHORT).show();
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
