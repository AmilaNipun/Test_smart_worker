package com.example.SmartWorker.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmartWorker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewJobsExpand extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    Button requestJobBTN;
    ProgressBar progressBar;
    TextView jobBudget,jobCategory,jobDescription,JobDueDate,JobLocation,JobServiceType,jobTitle,JobType,contactname,contactnumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs_expand);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        requestJobBTN = findViewById(R.id.JobRequestButton);
        jobBudget = findViewById(R.id.jobBudgetView);
        jobCategory = findViewById(R.id.textViewJobCategory);
        jobDescription = findViewById(R.id.textViewJobsDescription);
        JobDueDate = findViewById(R.id.textViewJobDueDate);
        JobLocation = findViewById(R.id.textViewJobLocation);
        JobServiceType = findViewById(R.id.textViewJobServiceType);
        jobTitle = findViewById(R.id.jobTitleView);
        JobType = findViewById(R.id.textViewJobType);
        imageView = findViewById(R.id.JobViewImage);
        progressBar = findViewById(R.id.prograssBarRequest);
        contactname = findViewById(R.id.textViewContactName);
        contactnumber = findViewById(R.id.textViewContactNumber);
        ref = FirebaseDatabase.getInstance().getReference().child("Add Jobs");
        mAuth = FirebaseAuth.getInstance();


        //toolbar

        setSupportActionBar(toolbar);


        //navigation drawer menu

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final String jobkey = getIntent().getStringExtra("JobKey");
        ref.child(jobkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String job_title = dataSnapshot.child("JobTile").getValue().toString();
                    String job_budget = dataSnapshot.child("JobBudget").getValue().toString();
                    String job_category = dataSnapshot.child("JobCategory").getValue().toString();
                    String description = dataSnapshot.child("JobDescription").getValue().toString();
                    String job_due_date = dataSnapshot.child("JobDueDate").getValue().toString();
                    String job_location = dataSnapshot.child("JobLocation").getValue().toString();
                    String job_service_type = dataSnapshot.child("JobServiceType").getValue().toString();
                    String job_type = dataSnapshot.child("JobType").getValue().toString();
                    String imageURL = dataSnapshot.child("ImageURL").getValue().toString();
                    String conname = dataSnapshot.child("ContactName").getValue().toString();
                    String connum = dataSnapshot.child("ContactNumber").getValue().toString();


                    Picasso.get().load(imageURL).into(imageView);
                    jobBudget.setText(job_budget);
                    jobCategory.setText(job_category);
                    jobDescription.setText(description);
                    JobDueDate.setText(job_due_date);
                    JobLocation.setText(job_location);
                    JobServiceType.setText(job_service_type);
                    jobTitle.setText(job_title);
                    JobType.setText(job_type);
                    contactname.setText(conname);
                    contactnumber.setText(connum);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        requestJobBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ref.child(jobkey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String job_title = dataSnapshot.child("JobTile").getValue().toString();
                            String job_budget = dataSnapshot.child("JobBudget").getValue().toString();
                            String job_category = dataSnapshot.child("JobCategory").getValue().toString();
                            String description = dataSnapshot.child("JobDescription").getValue().toString();
                            String job_due_date = dataSnapshot.child("JobDueDate").getValue().toString();
                            String job_location = dataSnapshot.child("JobLocation").getValue().toString();
                            String job_service_type = dataSnapshot.child("JobServiceType").getValue().toString();
                            String job_type = dataSnapshot.child("JobType").getValue().toString();
                            String conname = dataSnapshot.child("ContactName").getValue().toString();
                            String connum = dataSnapshot.child("ContactNumber").getValue().toString();

                            String imageURL = dataSnapshot.child("ImageURL").getValue().toString();

                            String uid = mAuth.getCurrentUser().getUid();
                            ref = FirebaseDatabase.getInstance().getReference().child("My Jobs").child(uid).child(jobkey);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("Title",job_title);
                            hashMap.put("Budget",job_budget);
                            hashMap.put("Category",job_category);
                            hashMap.put("Description",description);
                            hashMap.put("DueDate",job_due_date);
                            hashMap.put("JobLocation",job_location);
                            hashMap.put("ServiceType",job_service_type);
                            hashMap.put("JobType",job_type);
                            hashMap.put("ContactName",conname);
                            hashMap.put("ContactNumber",connum);
                            hashMap.put("ImageUrl",imageURL);

                            ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ViewJobsExpand.this,"You Requested Job Successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ViewJobsExpand.this,Dashboard.class));
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }else{
                                        Toast.makeText(ViewJobsExpand.this,"Error",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_profile:
                Intent intent = new Intent(ViewJobsExpand.this, UserProfile.class);
                startActivity(intent);
                break;

            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
