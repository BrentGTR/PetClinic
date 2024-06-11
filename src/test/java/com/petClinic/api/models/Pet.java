package com.petClinic.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Pet {
    private int id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String birthDate;

    private int typeId;
    private int ownerId;

    // Constructors
    public Pet() {
    }

    public Pet(int id, String name, String birthDate, int typeId, int ownerId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.typeId = typeId;
        this.ownerId = ownerId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}