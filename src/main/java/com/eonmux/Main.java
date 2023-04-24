package com.eonmux;

import com.eonmux.example3.SomeSuperObject;
import io.activej.serializer.BinarySerializer;
import io.activej.serializer.SerializerBuilder;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try {
            example1();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            example2();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            example3();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void example1() {
        // Example 1, this works as expected except when launched via maven goal exec:java

        // Create object and buffer
        com.eonmux.example1.Person person = new com.eonmux.example1.Person(17, "Mark");
        System.out.println("Example 1 - Generated: " + person.toString());
        byte[] buff = new byte[32];

        // Serialize
        BinarySerializer<com.eonmux.example1.Person>
                serializer =
                SerializerBuilder.create().build(com.eonmux.example1.Person.class);
        serializer.encode(buff, 0, person);
        System.out.println("Example 1 - Serialized: " + Arrays.toString(buff));

        // Deserialize
        com.eonmux.example1.Person personCopy = serializer.decode(buff, 0);

        System.out.println("Example 1 - Deserialized: " + personCopy.toString());
    }

    public static void example2() {
        // Example 2, doesn't work whether or not launched via maven goal exec:java
        // this doesn't work even though it matches an example on: https://activej.io/serializer/examples except that it is non-static outer class instead of inner class

        // Create object and buffer
        com.eonmux.example2.Person person = new com.eonmux.example2.Person(17, "Mark");
        System.out.println("Example 2 - Generated: " + person.toString());
        byte[] buff = new byte[32];

        // Serialize
        BinarySerializer<com.eonmux.example2.Person>
                serializer =
                SerializerBuilder.create().build(com.eonmux.example2.Person.class);
        serializer.encode(buff, 0, person);
        System.out.println("Example 2 - Serialized: " + Arrays.toString(buff));

        // Deserialize
        com.eonmux.example2.Person personCopy = serializer.decode(buff, 0);

        System.out.println("Example 2 - Deserialized: " + personCopy.toString());
    }

    public static void example3() {
        // Example 3 won't fail here but will when launched via maven goal exec:java

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Create object and buffer
                com.eonmux.example3.Person person = new com.eonmux.example3.Person(17, "Mark");
                System.out.println("Example 3 - Generated: " + person.toString());
                SomeSuperObject someSuperObject = new SomeSuperObject(4);
                someSuperObject.setSomePerson(person);

                // Serialize
                System.out.println("Example 3 - Serialized: " +
                                   Arrays.toString(someSuperObject.getSerializedPerson()));

                // Deserialize
                System.out.println("Example 3 - Deserialized: " +
                                   someSuperObject.deserializePersonFromBytes(someSuperObject.getSerializedPerson()));
            }
        });
    }
}