package com.assignment.drheart.entity;

public enum Gender {

    FEMALE("Female"),
    MALE("Male"),
    DIVERS("Divers");

    public final String label;

    private Gender(String label) {
        this.label = label;
    }
}
