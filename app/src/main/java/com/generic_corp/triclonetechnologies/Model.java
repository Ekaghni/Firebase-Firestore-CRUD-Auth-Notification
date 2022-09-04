package com.generic_corp.triclonetechnologies;

public class Model {
    private String Id;
    private String Name;
    private String Number;
    private Model(){}

    public Model(String id, String name, String number) {
        Id = id;
        Name = name;
        Number = number;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
