package ru.netology.data;

import lombok.Value;

public class API {
    @Value
    public static class api {
        String number;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static api getApiApprovedCard() {
        return new api(Helper.getApprovedNumber(), Helper.getMonth(), Helper.getYear(), Helper.getEnOwner(),
                Helper.getCVC());
    }

    public static api getApiDeclinedCard() {
        return new api(Helper.getDeclinedNumber(), Helper.getMonth(), Helper.getYear(), Helper.getEnOwner(),
                Helper.getCVC());
    }

    public static api getApiEmptyNumberCard() {
        return new api(Helper.getEmptyNumber(), Helper.getMonth(), Helper.getYear(), Helper.getEnOwner(),
                Helper.getCVC());
    }

    public static api getApiEmptyMonthCard() {
        return new api(Helper.getApprovedNumber(), Helper.getEmptyMonth(), Helper.getYear(), Helper.getEnOwner(),
                Helper.getCVC());
    }

    public static api getApiEmptyYearCard() {
        return new api(Helper.getApprovedNumber(), Helper.getMonth(), Helper.getEmptyYear(), Helper.getEnOwner(),
                Helper.getCVC());
    }

    public static api getApiEmptyOwnerCard() {
        return new api(Helper.getApprovedNumber(), Helper.getMonth(), Helper.getYear(), Helper.getEmptyOwner(),
                Helper.getCVC());
    }

    public static api getApiEmptyCVCCard() {
        return new api(Helper.getApprovedNumber(), Helper.getMonth(), Helper.getYear(), Helper.getEnOwner(),
                Helper.getEmptyCVC());
    }
}
