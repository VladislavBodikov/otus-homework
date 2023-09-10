package ru.bodikov.otus.domain;

public class Apple extends Fruit {

    private static final int ONE_INSTANCE_WEIGHT_IN_GRAMS = 100;

    @Override
    public int getWeightOfOneInstanceInGrams() {
        return ONE_INSTANCE_WEIGHT_IN_GRAMS;
    }
}
