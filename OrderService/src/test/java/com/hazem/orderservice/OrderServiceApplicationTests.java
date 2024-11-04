package com.hazem.orderservice;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class OrderServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
