package com.project.utils;

import com.github.javafaker.Faker;

public class FakerClass {

    static Faker faker = new Faker();

    public static int randomNumber(int digit) {
        return Integer.parseInt(faker.number().digits(digit));
    }

    public static String randomName() {
        return faker.name().fullName();
    }

    public static String randomEmail() {
        return faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com";
    }
}