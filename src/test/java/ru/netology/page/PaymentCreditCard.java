package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
public class PaymentCreditCard {

    private SelenideElement creditCardTitle = $(Selectors.withText("Кредит по данным карты"));

    public PaymentCreditCard() {
        creditCardTitle.shouldBe(Condition.visible);
    }
}
