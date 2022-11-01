package ru.netology.test.API;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.API;
import ru.netology.data.SQL;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class APITest {
    private static List<SQL.PaymentEntity> payment;
    private static List<SQL.CreditRequestEntity> credit;
    private static List<SQL.OrderEntity> order;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true)
                .savePageSource(true));
    }

    @AfterAll
    static void tearDownAll() {SelenideLogger.removeListener("allure");
    }

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    public void shouldApprovedPaymentCard() {
        String status = given()
                .spec(requestSpec)
                .body(API.getApiApprovedCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract()
                .path("status");
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(1, payment.size());
        assertEquals(0, credit.size());
        assertEquals(1, order.size());

        assertTrue(payment.get(0).getStatus().equalsIgnoreCase("APPROVED"));
        assertEquals(payment.get(0).getTransaction_id(), order.get(0).getPayment_id());
        assertNull(order.get(0).getCredit_id());
    }

    @Test
    public void shouldDeclinedPaymentCard() {
        String status = given()
                .spec(requestSpec)
                .body(API.getApiDeclinedCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract()
                .path("status");

        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(1, payment.size());
        assertEquals(0, credit.size());
        assertEquals(1, order.size());

        assertTrue(payment.get(0).getStatus().equalsIgnoreCase("DECLINED"));
        assertEquals(payment.get(0).getTransaction_id(), order.get(0).getPayment_id());
        assertNull(order.get(0).getCredit_id());
    }

    @Test
    public void shouldErrorPaymentCardEmptyNumber() {
        given()
                .spec(requestSpec)
                .body(API.getApiEmptyNumberCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400);
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(0, payment.size());
        assertEquals(0, credit.size());
        assertEquals(0, order.size());
    }

    @Test
    public void shouldErrorPaymentCardEmptyMonth() {
        given()
                .spec(requestSpec)
                .body(API.getApiEmptyMonthCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400);
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(0, payment.size());
        assertEquals(0, credit.size());
        assertEquals(0, order.size());
    }

    @Test
    public void shouldErrorPaymentCardEmptyYear() {
        given()
                .spec(requestSpec)
                .body(API.getApiEmptyYearCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400);
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(0, payment.size());
        assertEquals(0, credit.size());
        assertEquals(0, order.size());
    }

    @Test
    public void shouldErrorPaymentCardEmptyOwner() {
        given()
                .spec(requestSpec)
                .body(API.getApiEmptyOwnerCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400);
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(0, payment.size());
        assertEquals(0, credit.size());
        assertEquals(0, order.size());
    }

    @Test
    public void shouldErrorPaymentCardEmptyCVC() {
        given()
                .spec(requestSpec)
                .body(API.getApiEmptyCVCCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400);
        payment = SQL.getPayments();
        credit = SQL.getCreditsRequest();
        order = SQL.getOrders();
        assertEquals(0, payment.size());
        assertEquals(0, credit.size());
        assertEquals(0, order.size());
    }
}
