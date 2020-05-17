package com.example.SmartWorker.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartWorker.R;

public class MyJobsHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView jTitle,JobLocation,JobDueDate,JobCategory,JobBudget,Contactnum;
    public View v;


    public MyJobsHolder(@NonNull View itemView) {
        super(itemView);


            imageView = itemView.findViewById(R.id.jobImage_myjobs);
            jTitle = itemView.findViewById(R.id.job_title_single_myjobs);
            JobLocation = itemView.findViewById(R.id.addJobLocation_myjobs);
            JobDueDate = itemView.findViewById(R.id.dueDate_myjobs);
            JobCategory = itemView.findViewById(R.id.jobCategory_myjobs);
            JobBudget = itemView.findViewById(R.id.jobBudget_myjobs);
            Contactnum = itemView.findViewById(R.id.textviewcontactnum_myjobs);
            v = itemView;


    }
}
