package org.example.movieclient.model.movie;

public enum MovieRateRange {
    ONE ,TWO,THREE,FOUR,FIVE;

    private final int value;

    MovieRateRange() {
        this.value = ordinal() + 1; // Automatically assigns values starting from 2 for TWO, 3 for THREE, and so on
    }

    MovieRateRange(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
