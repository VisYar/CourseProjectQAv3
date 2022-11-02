package ru.netology.test.UI;

import ru.netology.data.SQL;
import ru.netology.page.Main;
import ru.netology.page.PaymentCard;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

public class UICardTest {

    private SelenideElement errorSpecifiedPeriodCard = $(Selectors.withText("Неверно указан срок действия карты"));
    private SelenideElement errorPeriodCard = $(Selectors.withText("Истёк срок действия карты"));
    private SelenideElement errorEmptyFieldOwner = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement errorEmptyFieldNumber = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement errorEmptyFieldMonth = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement errorEmptyFieldYear = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement errorEmptyFieldCVC = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement errorFormat = $(Selectors.withText("Неверный формат"));
    private SelenideElement sendingRequest = $(Selectors.withText("Отправляем запрос в Банк..."));
    private SelenideElement messageSuccess = $(Selectors.withText("Успешно"));
    private SelenideElement messageApprove = $(Selectors.withText("Операция одобрена Банком."));
    private SelenideElement messageError = $(Selectors.withText("Ошибка"));
    private SelenideElement messageDecline = $(Selectors.withText("Ошибка! Банк отказал в проведении операции."));

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void openSetup() {
        open("http://localhost:8080");
    }

    @AfterEach
    void clear() {
        SQL.clear();
    }

    long numberFromPayment() {
        SQL sql = new SQL();
        return sql.getNumberPaymentCard();
    }

    String statusAfterServer() {
        SQL sql = new SQL();
        return sql.getStatusPaymentCard();
    }

    public void positiveMessage() {
        messageSuccess.shouldBe(Condition.visible, Duration.ofSeconds(15));
        messageApprove.shouldBe(Condition.visible);
    }

    public void notPositiveMessage() {
        messageSuccess.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        messageApprove.shouldNotBe(Condition.visible);
    }

    public void denialMessage() {
        messageError.shouldBe(Condition.visible, Duration.ofSeconds(15));
        messageDecline.shouldBe(Condition.visible);
    }

    public void notDenialMessage() {
        messageError.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        messageDecline.shouldNotBe(Condition.visible);
    }

    public void checkNumberPayment(long initialNumberPayment, int x) {
        long finalNumberPayment = numberFromPayment();
        assertEquals(initialNumberPayment + x, finalNumberPayment);
    }

    PaymentCard choicePaymentCard() {
        Main page = new Main();
        return page.clickButtonPay();
    }

    @Test
    @DisplayName("Payment approved card")
    public void shouldSuccessfulPaymentApprovedCard() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.approvedNumberCard();
        sendingRequest.shouldBe();
        positiveMessage();
        String statusAfterServer = statusAfterServer();
        checkNumberPayment(initialNumberPayment, 1);
        assertEquals("APPROVED", statusAfterServer);
    }

    @Test
    @DisplayName("Payment declined card")
    public void shouldUnsuccessfulPaymentDeclinedCard() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.declinedNumberCard();
        String statusAfterServer = statusAfterServer();
        denialMessage();
        checkNumberPayment(initialNumberPayment, 1);
        assertEquals("DECLINED", statusAfterServer);
    }

    @Test
    @DisplayName("Empty card number")
    public void shouldErrorEmptyNumber() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.emptyNumber();
        sendingRequest.shouldNotBe(Condition.visible);
        errorEmptyFieldNumber.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Random card number")
    public void shouldErrorRandomNumber() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.randomNumber();
        notPositiveMessage();
        denialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Zero card number")
    public void shouldErrorZeroNumber() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.zeroNumber();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("One digit card number")
    public void shouldErrorOneDigitNumber() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.oneDigitNumber();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Fifteen digits card number")
    public void shouldErrorFifteenDigitsNumber() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.fifteenDigitsNumber();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Empty month")
    public void shouldErrorEmptyMonth() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.emptyMonth();
        sendingRequest.shouldNotBe(Condition.visible);
        errorEmptyFieldMonth.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("One digits number month")
    public void shouldErrorIfInvalidMonthFormat() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.oneDigitsNumberMonth();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Thirteenth month")
    public void shouldErrorIfNotExistedMonth13() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.thirteenthMonth();
        sendingRequest.shouldNotBe(Condition.visible);
        errorSpecifiedPeriodCard.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Zero month")
    public void shouldErrorZeroMonth() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.zeroMonth();
        sendingRequest.shouldNotBe(Condition.visible);
        errorSpecifiedPeriodCard.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Empty year field")
    public void shouldErrorEmptyYear() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.emptyYear();
        sendingRequest.shouldNotBe(Condition.visible);
        errorEmptyFieldYear.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("One digit year")
    public void shouldErrorOneDigitYear() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.oneDigitYear();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Year more than five")
    public void shouldErrorIfYearMoreThan5() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.yearMoreThanFive();
        sendingRequest.shouldNotBe(Condition.visible);
        errorSpecifiedPeriodCard.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Past year")
    public void shouldErrorPastYear() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.pastYear();
        sendingRequest.shouldNotBe(Condition.visible);
        errorPeriodCard.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Zero year")
    public void shouldErrorZeroYear() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.zeroYear();
        sendingRequest.shouldNotBe(Condition.visible);
        errorPeriodCard.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Empty owner")
    public void shouldErrorIfEmptyOwnerField() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.emptyOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorEmptyFieldOwner.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Cyrillic letters owner")
    public void shouldErrorIfCyrillicLettersInOwnerField() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.cyrillicLettersOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Symbols owner")
    public void shouldErrorIfsymbolsOwner() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.symbolsOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Number owner")
    public void shouldErrorNumberOwner() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.numberOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Quantity symbols owner")
    public void shouldErrorSymbolsOwner() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.quantitySymbolsOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("One word owner")
    public void shouldErrorOneWordOwner() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.OneWordOwner();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Empty CVC")
    public void shouldErrorIfEmptyCVCField() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.emptyCVC();
        sendingRequest.shouldNotBe(Condition.visible);
        errorEmptyFieldCVC.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("One digit CVC")
    public void shouldErrorOneDigitCVC() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.oneDigitCVC();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }

    @Test
    @DisplayName("Two digit CVC")
    public void shouldErrorTwoDigitsCVC() {
        long initialNumberPayment = numberFromPayment();
        var card = choicePaymentCard();
        card.twoDigitsCVC();
        sendingRequest.shouldNotBe(Condition.visible);
        errorFormat.shouldBe(Condition.visible);
        notPositiveMessage();
        notDenialMessage();
        checkNumberPayment(initialNumberPayment, 0);
    }
}