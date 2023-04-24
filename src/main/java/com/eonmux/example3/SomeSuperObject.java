package com.eonmux.example3;

import io.activej.serializer.BinarySerializer;
import io.activej.serializer.SerializerBuilder;

public class SomeSuperObject {
    com.eonmux.example3.Person somePerson;
    int                        variable;


    BinarySerializer<com.eonmux.example3.Person>
            serializer;

    public SomeSuperObject(int var) {
        this.variable   = var;
        this.serializer = SerializerBuilder.create().build(com.eonmux.example3.Person.class);
    }

    public byte[] getSerializedPerson() {
        byte[] buff = new byte[32];
        serializer.encode(buff, 0, this.somePerson);
        return buff;
    }

    public com.eonmux.example3.Person getSomePerson() {
        return somePerson;
    }

    public void setSomePerson(com.eonmux.example3.Person somePerson) {
        this.somePerson = somePerson;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public com.eonmux.example3.Person deserializePersonFromBytes(byte[] buff) {
        // Deserialize
        return serializer.decode(buff, 0);
    }
}
