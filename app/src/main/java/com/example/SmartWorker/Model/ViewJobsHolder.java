package com.example.SmartWorker.Model;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartWorker.R;

public class ViewJobsHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView jTitle,JobLocation,JobDueDate,JobCategory,JobBudget;
    public View v;


    public ViewJobsHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.jobImage);
        jTitle = itemView.findViewById(R.id.job_title_single);
        JobLocation = itemView.findViewById(R.id.addJobLocation);
        JobDueDate = itemView.findViewById(R.id.dueDate);
        JobCategory = itemView.findViewById(R.id.jobCategory);
        JobBudget = itemView.findViewById(R.id.jobBudget);
        v = itemView;
    }
}
