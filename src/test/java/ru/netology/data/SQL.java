package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQL {
    public static QueryRunner runner = new QueryRunner();

    public static String url = System.getProperty("db.url");
    public static String user = System.getProperty("db.user");
    public static String password = System.getProperty("db.password");


    @SneakyThrows
    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            conn = DriverManager.getConnection(System.getProperty("db.urlpostgresql"), user, password);
        }
        return conn;
    }
    @SneakyThrows
    public static void clear() {
        val deletePayment = "DELETE FROM payment_entity;";
        val deleteCredit = "DELETE FROM credit_request_entity;";
        val deleteOrder = "DELETE FROM order_entity;";
        runner.update(connection(), deletePayment);
        runner.update(connection(), deleteCredit);
        runner.update(connection(), deleteOrder);
    }

    @SneakyThrows
    public String getStatusPaymentCard() {
        String dataSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        String status;
        {
            status = runner.query(connection(), dataSQL, new ScalarHandler<>());
        }
        return status;
    }

    @SneakyThrows
    public long getNumberPaymentCard() {
        String dataSQL = "SELECT COUNT(transaction_id) FROM payment_entity";
        long number = 0;
        {
            number = runner.query(connection(), dataSQL, new ScalarHandler<>());
        }
        return number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntity {
        private String id;
        private int amount;
        private Timestamp created;
        private String status;
        private String transaction_id;
    }

    @SneakyThrows
    public static List<PaymentEntity> getPayments() {
        var dataSQL = "SELECT * FROM payment_entity ORDER BY created DESC;";
        ResultSetHandler<List<PaymentEntity>> resultHandler = new BeanListHandler<>(PaymentEntity.class);
        return runner.query(connection(), dataSQL, resultHandler);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditRequestEntity {
        private String id;
        private String bank_id;
        private Timestamp created;
        private String status;
    }

    @SneakyThrows
    public static List<CreditRequestEntity> getCreditsRequest() {
        var dataSQL = "SELECT * FROM credit_request_entity ORDER BY created DESC;";
        ResultSetHandler<List<CreditRequestEntity>> resultHandler = new BeanListHandler<>(CreditRequestEntity.class);
        return runner.query(connection(), dataSQL, resultHandler);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderEntity {
        private String id;
        private Timestamp created;
        private String credit_id;
        private String payment_id;
    }

    @SneakyThrows
    public static List<OrderEntity> getOrders() {
        var dataSQL = "SELECT * FROM order_entity ORDER BY created DESC;";
        ResultSetHandler<List<OrderEntity>> resultHandler = new BeanListHandler<>(OrderEntity.class);
        return runner.query(connection(), dataSQL, resultHandler);
    }
}
