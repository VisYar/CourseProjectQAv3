package ru.netology.page;
import ru.netology.data.Helper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class PaymentCard {

    private SelenideElement cardTitle = $(Selectors.withText("Оплата по карте"));
    private SelenideElement buttonContinue = $(Selectors.withText("Продолжить"));
    private SelenideElement cardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement cardMonth = $("[placeholder=\"08\"]");
    private SelenideElement cardYear = $("[placeholder=\"22\"]");
    private SelenideElement cardOwner = $("div:nth-child(3) span:nth-child(1) span.input__box input");
    private SelenideElement cardCVC = $("[placeholder=\"999\"]");

    public PaymentCard() {
        cardTitle.shouldBe(Condition.visible);
    }

    public void validNumber() {
        cardNumber.setValue(Helper.getApprovedNumber());
    }

    public void validMonth() {
        cardMonth.setValue(Helper.getMonth());
    }

    public void validYear() {
        cardYear.setValue(Helper.getYear());
    }

    public void validOwner() {
        cardOwner.setValue(Helper.getEnOwner());
    }

    public void validCVC() {
        cardCVC.setValue(Helper.getCVC());
    }

    public void approvedNumberCard() {
        validNumber();
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void declinedNumberCard() {
        cardNumber.setValue(Helper.getDeclinedNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void emptyNumber() {
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void randomNumber() {
        cardNumber.setValue(Helper.getRandomNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void zeroNumber() {
        cardNumber.setValue(Helper.getZeroNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void oneDigitNumber() {
        cardNumber.setValue(Helper.getRandomOneDigitsNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void fifteenDigitsNumber() {
        cardNumber.setValue(Helper.getRandomFifteenNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void emptyMonth() {
        validNumber();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void zeroMonth() {
        validNumber();
        cardMonth.setValue(Helper.getZeroMonth());
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void thirteenthMonth() {
        validNumber();
        cardMonth.setValue(Helper.getThirteenMonth());
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void oneDigitsNumberMonth() {
        validNumber();
        cardMonth.setValue(Helper.getRandomOneDigitsNumberMonth());
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void emptyYear() {
        validNumber();
        validMonth();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void yearMoreThanFive() {
        validNumber();
        validMonth();
        cardYear.setValue(Helper.getMoreThanFiveYear());
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void pastYear() {
        validNumber();
        validMonth();
        cardYear.setValue(Helper.getPastYear());
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void zeroYear() {
        validNumber();
        validMonth();
        cardYear.setValue(Helper.getZeroYear());
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void oneDigitYear() {
        validNumber();
        validMonth();
        cardYear.setValue(Helper.getRandomOneDigitYear());
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void emptyOwner() {
        validNumber();
        validMonth();
        validYear();
        validCVC();
        buttonContinue.click();
    }

    public void quantitySymbolsOwner() {
        validNumber();
        validMonth();
        validYear();
        cardOwner.setValue(Helper.getQuantitySymbolOwner(String.valueOf(21)));
        validCVC();
        buttonContinue.click();
    }

    public void OneWordOwner() {
        validNumber();
        validMonth();
        validYear();
        cardOwner.setValue(Helper.getOneWordOwner());
        validCVC();
        buttonContinue.click();
    }

    public void cyrillicLettersOwner() {
        validNumber();
        validMonth();
        validYear();
        cardOwner.setValue(Helper.getCyrillicOwner());
        validCVC();
        buttonContinue.click();
    }
    public void numberOwner() {
        validNumber();
        validMonth();
        validYear();
        cardOwner.setValue(Helper.getNumbersOwner());
        validCVC();
        buttonContinue.click();
    }

    public void symbolsOwner() {
        validNumber();
        validMonth();
        validYear();
        cardOwner.setValue(Helper.getSymbolOwner());
        validCVC();
        buttonContinue.click();
    }

    public void emptyCVC() {
        validNumber();
        validMonth();
        validYear();
        validOwner();
        buttonContinue.click();
    }

    public void oneDigitCVC() {
        validNumber();
        validMonth();
        validYear();
        validOwner();
        cardCVC.setValue(Helper.getOneSymbolCVC());
        buttonContinue.click();
    }

    public void twoDigitsCVC() {
        validNumber();
        validMonth();
        validYear();
        validOwner();
        cardCVC.setValue(Helper.getTwoSymbolsCVC());
        buttonContinue.click();
    }
}