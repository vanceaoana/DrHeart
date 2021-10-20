package com.assignment.drheart.entity;

public enum Unit {

    GRAM("Gram"),
    MILLIGRAM("Milligram"),
    TABLET("Tablet");

    public final String label;

    private Unit(String label) {
        this.label = label;
    }
}
