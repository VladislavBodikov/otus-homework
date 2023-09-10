package ru.bodikov.otus.domain;

public class Orange extends Fruit {

    private static final int ONE_INSTANCE_WEIGHT_IN_GRAMS = 150;

    @Override
    public int getWeightOfOneInstanceInGrams() {
        return ONE_INSTANCE_WEIGHT_IN_GRAMS;
    }
}
