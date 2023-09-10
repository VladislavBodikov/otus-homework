package ru.bodikov.otus.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void weight(){
        Box<Orange> orangeBox = new Box<>();
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());
        int oneOrangeWeight = new Orange().getWeightOfOneInstanceInGrams();
        int expectedBoxWeight = 3 * oneOrangeWeight;


        Assertions.assertEquals(expectedBoxWeight,orangeBox.weight());
    }

    @Test
    void compare_equalWeight(){
        Box<Orange> orangeBox = new Box<>();
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());

        Box<Apple> appleBox = new Box<>();
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());


        Assertions.assertTrue(orangeBox.compare(appleBox));
    }

    @Test
    void compare_notEqualWeight(){
        Box<Orange> orangeBox = new Box<>();
        orangeBox.addFruit(new Orange());

        Box<Apple> appleBox = new Box<>();
        appleBox.addFruit(new Apple());


        Assertions.assertFalse(orangeBox.compare(appleBox));
    }

    @Test
    void transferOrangeToAnotherBox(){
        Box<Orange> orangeBoxSource = new Box<>();
        orangeBoxSource.addFruit(new Orange());
        int fruitCountInBox1BeforeTransfer = orangeBoxSource.getFruitCount();

        Box<Orange> orangeBoxDestination = new Box<>();
        orangeBoxDestination.addFruit(new Orange());
        int fruitCountInBox2BeforeTransfer = orangeBoxDestination.getFruitCount();


        orangeBoxSource.transferFruitToAnotherBox(orangeBoxDestination);


        Assertions.assertEquals(1,fruitCountInBox1BeforeTransfer);
        Assertions.assertEquals(1,fruitCountInBox2BeforeTransfer);
        Assertions.assertEquals(0,orangeBoxSource.getFruitCount());
        Assertions.assertEquals(2,orangeBoxDestination.getFruitCount());
    }

    @Test
    void transferOrangeToFruitBox(){
        Box<Orange> fruitBoxSource = new Box<>();
        fruitBoxSource.addFruit(new Orange());
        int fruitCountInBox1BeforeTransfer = fruitBoxSource.getFruitCount();

        Box<Fruit> fruitBoxDestination = new Box<>();
        fruitBoxDestination.addFruit(new Orange());
        fruitBoxDestination.addFruit(new Apple());
        int fruitCountInBox2BeforeTransfer = fruitBoxDestination.getFruitCount();


        fruitBoxSource.transferFruitToAnotherBox(fruitBoxDestination);


        Assertions.assertEquals(1,fruitCountInBox1BeforeTransfer);
        Assertions.assertEquals(2,fruitCountInBox2BeforeTransfer);
        Assertions.assertEquals(0,fruitBoxSource.getFruitCount());
        Assertions.assertEquals(3,fruitBoxDestination.getFruitCount());
    }
}