package com.petClinic.api.utils;
import com.github.javafaker.Faker;
import com.petClinic.api.models.Owner;
import com.petClinic.api.models.Pet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static Owner generateOwner() {
        Owner owner = new Owner();
        owner.setId(faker.number().numberBetween(1, 20));
        owner.setFirstName(faker.name().firstName());
        owner.setLastName(faker.name().lastName());
        owner.setAddress(faker.address().streetAddress());
        owner.setCity(faker.address().city());
        owner.setTelephone(faker.number().digits(10));
        return owner;
    }

    public static Pet generatePet() {
        Pet pet = new Pet();
        pet.setId(0); // Set a default value for the ID, it will be overwritten by the server
        pet.setName(faker.pokemon().name());
        pet.setBirthDate(generateBirthDate());
        pet.setTypeId(1); // Set a default type
        pet.setOwnerId(0); // Set a default ownerId
        return pet;
    }

    private static String generateBirthDate() {
        // Generate birth date in the required format: 2024-06-03T22:00:00.000Z
        LocalDateTime birthDateTime = faker.date().birthday().toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        return birthDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}