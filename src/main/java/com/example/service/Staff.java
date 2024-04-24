package com.example.service;

public class Staff {
    public int staffId;
    public String name;
    public String job;

    public int score;



    public Staff (int staffId, String name, String job, int score){

        this.staffId=staffId;
        this.name=name;
        this.job=job;
        this.score=score;

    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



}
