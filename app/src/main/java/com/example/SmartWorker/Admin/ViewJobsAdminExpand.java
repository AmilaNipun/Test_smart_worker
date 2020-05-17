package com.example.SmartWorker.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.SmartWorker.Customer.UserProfile;
import com.example.SmartWorker.Model.ViewJobsHolder;
import com.example.SmartWorker.Model.ViewJobsModel;
import com.example.SmartWorker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewJobsAdminExpand extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    TextView jobBudget,jobCategory,jobDescription,JobDueDate,JobLocation,JobServiceType,jobTitle,JobType,contactname,contactnumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs_admin_expand);

        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.nav_view_admin);
        toolbar = findViewById(R.id.toolbar);
        jobBudget = findViewById(R.id.jobBudgetView_admin);
        jobCategory = findViewById(R.id.textViewJobCategory_admin);
        jobDescription = findViewById(R.id.textViewJobsDescription_admin);
        JobDueDate = findViewById(R.id.textViewJobDueDate_admin);
        JobLocation = findViewById(R.id.textViewJobLocation_admin);
        JobServiceType = findViewById(R.id.textViewJobServiceType_admin);
        jobTitle = findViewById(R.id.jobTitleView_admin);
        JobType = findViewById(R.id.textViewJobType_admin);
        imageView = findViewById(R.id.JobViewImage_admin);
        contactname = findViewById(R.id.textViewContactName_admin);
        contactnumber = findViewById(R.id.textViewContactNumber_admin);
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
                Intent intent = new Intent(ViewJobsAdminExpand.this, UserProfile.class);
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
