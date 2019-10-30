package com.example.programmer.rationservices.models;

import java.util.List;

public class ClientItem {
    private String name;
    private String phoneNumber;
    private String email;
    private String street;
    private String city;
    private String id_card;
    private String Date;
    private Double balance;
    private List<FamilyClientItem> familyClientItemList;

    public ClientItem() {
        balance = 0.0;
        familyClientItemList = null;
    }

    public ClientItem(String name, String phoneNumber, String email, String street, String city, String id_card, String date, Double balance, List<FamilyClientItem> familyClientItemList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.street = street;
        this.city = city;
        this.id_card = id_card;
        Date = date;
        this.balance = balance;
        this.familyClientItemList = familyClientItemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<FamilyClientItem> getFamilyClientItemList() {
        return familyClientItemList;
    }

    public void setFamilyClientItemList(List<FamilyClientItem> familyClientItemList) {
        this.familyClientItemList = familyClientItemList;
    }
}
