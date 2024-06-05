package brian.example.boot.jms.client.model;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
