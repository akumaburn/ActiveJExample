package com.eonmux.example2;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;

public class Person {
    public Person(@Deserialize ("age") int age, @Deserialize ("name") String name) {
        this.age  = age;
        this.name = name;
    }

    @Serialize public final int    age;
    @Serialize public final String name;

    private String surname;

    @Serialize
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Age: " + this.age;
    }
}