package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static java.lang.String.valueOf;

public class Helper {

    private static Random random = new Random();
    private static Faker faker = new Faker();
    private static Faker fakerEn = new Faker(new Locale("En"));
    private static Faker fakerRu = new Faker(new Locale("Ru"));

    /**
     * Валидные данные
     **/

    public static String getApprovedNumber() {
        return "1111 2222 3333 4444";
    }

    public static String getMonth() {
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        return months[random.nextInt(months.length)];
    }

    public static String getYear() {
        return valueOf(faker.number().numberBetween(23, 27));
    }

    public static String getEnOwner() {
        var randomFirstName = fakerEn.name().firstName();
        var randomLastName = fakerEn.name().lastName();
        return randomFirstName + " " + randomLastName;
    }

    public static String getCVC() {
        return valueOf(faker.number().numberBetween(100, 999));
    }

    /**
     * Невалидные данные
     **/

    public static String getDeclinedNumber() {
        return "5555 6666 7777 8888";
    }

    public static String getRandomNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String getZeroNumber() {
        return "0000 0000 0000 0000";
    }

    public static String getRandomOneDigitsNumber() {
        return faker.numerify("#");
    }

    public static String getRandomFifteenNumber() {
        return faker.numerify("#### #### #### ###");
    }

    public static String getZeroMonth() {
        return "00";
    }

    public static String getThirteenMonth() {
        return "13";
    }

    public static String getRandomOneDigitsNumberMonth() {
        return faker.numerify("#");
    }

    public static String getPastYear() {
        String[] years = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "20", "21"};
        return years[random.nextInt(years.length)];
    }

    public static String getZeroYear() {
        return "00";
    }

    public static String getMoreThanFiveYear() {
        LocalDate date = LocalDate.now().plusYears(6);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy");
        return date.format(format);
    }

    public static String getRandomOneDigitYear() {
        return faker.numerify("#");
    }

    public static String getQuantitySymbolOwner(String count) {
        return fakerEn.letterify(count).toUpperCase();
    }

    public static String getSymbolOwner() {
        return "!@#$%^& *()_+ |";
    }

    public static String getNumbersOwner() {
        return faker.numerify("####### ######");
    }

    public static String getCyrillicOwner() {
        var randomFirstName = fakerRu.name().firstName();
        var randomLastName = fakerRu.name().lastName();
        return randomFirstName + " " + randomLastName;
    }

    public static String getOneWordOwner() {
        return fakerRu.name().firstName();
    }

    public static String getOneSymbolCVC() {
        return faker.numerify("#");
    }

    public static String getTwoSymbolsCVC() {
        return faker.numerify("##");
    }

    public static String getEmptyNumber() {
        return "" ;
    }

    public static String getEmptyMonth() {
        return "" ;
    }

    public static String getEmptyYear() {
        return "" ;
    }

    public static String getEmptyOwner() {
        return "" ;
    }

    public static String getEmptyCVC() {
        return "" ;
    }
}
