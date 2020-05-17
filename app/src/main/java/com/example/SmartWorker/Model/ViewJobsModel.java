package com.example.SmartWorker.Model;

public class ViewJobsModel {
    private String JobBudget,JobCategory,JobDescription,ImageURL,JobDueDate,JobLocation,JobServiceType,JobTile,JobType,Mobilenum;

    public ViewJobsModel() {
    }

    public ViewJobsModel(String jobBudget, String jobCategory, String jobDescription, String imageURL, String jobDueDate, String jobLocation, String jobServiceType, String jobTile, String jobType,String mobilenum) {
        JobBudget = jobBudget;
        JobCategory = jobCategory;
        JobDescription = jobDescription;
        ImageURL = imageURL;
        JobDueDate = jobDueDate;
        JobLocation = jobLocation;
        JobServiceType = jobServiceType;
        JobTile = jobTile;
        JobType = jobType;
        Mobilenum = mobilenum;
    }

    public String getMobilenum() {
        return Mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        Mobilenum = mobilenum;
    }

    public String getJobBudget() {
        return JobBudget;
    }

    public void setJobBudget(String jobBudget) {
        JobBudget = jobBudget;
    }

    public String getJobCategory() {
        return JobCategory;
    }

    public void setJobCategory(String jobCategory) {
        JobCategory = jobCategory;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getJobDueDate() {
        return JobDueDate;
    }

    public void setJobDueDate(String jobDueDate) {
        JobDueDate = jobDueDate;
    }

    public String getJobLocation() {
        return JobLocation;
    }

    public void setJobLocation(String jobLocation) {
        JobLocation = jobLocation;
    }

    public String getJobServiceType() {
        return JobServiceType;
    }

    public void setJobServiceType(String jobServiceType) {
        JobServiceType = jobServiceType;
    }

    public String getJobTile() {
        return JobTile;
    }

    public void setJobTile(String jobTile) {
        JobTile = jobTile;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }
}
