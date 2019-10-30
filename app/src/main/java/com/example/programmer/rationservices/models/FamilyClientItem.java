package com.example.programmer.rationservices.models;

public class FamilyClientItem {
    private String name;
    private String id_card;
    private String age;
    private String Date;

    public FamilyClientItem(String name, String id_card, String age, String date) {
        this.name = name;
        this.id_card = id_card;
        this.age = age;
        Date = date;
    }

    public FamilyClientItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
