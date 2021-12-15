package com.example.liftingapp.models;

import com.google.firebase.firestore.FieldValue;

public class Maxes {
    private String bench;
    private String deadlift;
    private String squat;
    private String email;
    private FieldValue created;
    public Maxes(){
    }

    public Maxes(String bench, String deadlift, String squat, String email, FieldValue created){
        this.bench = bench;
        this.deadlift = deadlift;
        this.squat = squat;
        this.email = email;
        this.created = created;
    }

    public String getEmail() {
        return this.email;
    }

    public String getBench() {
        return this.bench;
    }
    public String getDeadlift(){
        return this.deadlift;
    }

    public String getSquat() {
        return this.squat;
    }

    public FieldValue getCreated() {
        return this.created;
    }
}
