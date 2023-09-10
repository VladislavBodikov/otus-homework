package ru.bodikov.otus.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Box<T extends Fruit> {


    List<T> fruits = new ArrayList<>();


    public boolean addFruit(T newFruit) {
        Objects.requireNonNull(newFruit);
        return fruits.add(newFruit);
    }

    public long weight() {
        return fruits.stream().map(Fruit::getWeightOfOneInstanceInGrams).reduce(Integer::sum).orElse(0);
    }

    public <R extends Fruit> boolean compare(Box<R> box) {
        Objects.requireNonNull(box);
        return weight() == box.weight();
    }

    public void transferFruitToAnotherBox(Box<? super T> destinationBox) {
        Set<Class<? extends Fruit>> fruitClassesInsideTheBox = destinationBox.getFruitClassesInsideTheBox();

        Set<T> fruitsToTransfer = fruits.stream()
                .filter(fruit -> fruitClassesInsideTheBox.contains(fruit.getClass()))
                .collect(Collectors.toSet());

        fruitsToTransfer.forEach(destinationBox::addFruit);
        fruits.removeAll(fruitsToTransfer);
    }

    public Set<Class<? extends Fruit>> getFruitClassesInsideTheBox() {
        return fruits.stream().map(Fruit::getClass).collect(Collectors.toSet());
    }

    public int getFruitCount() {
        return fruits.size();
    }

}
