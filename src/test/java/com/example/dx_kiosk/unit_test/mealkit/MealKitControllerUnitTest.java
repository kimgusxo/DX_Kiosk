package com.example.dx_kiosk.unit_test.mealkit;

import com.example.dx_kiosk.mealkit.repository.MealKitRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataRedisTest
public class MealKitControllerUnitTest {

    @Container
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:6.2.6")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    private MealKitRepository mealKitRepository;

    @Nested
    @DisplayName("Find Test")
    class Test_Find {

        @BeforeEach
        @DisplayName("테스트 데이터 생성")
        void setUp() {

        }

        @Nested
        @DisplayName("findAll 테스트")
        class Test_FindAll {
            @Test
            @DisplayName("findAll 테스트 성공")
            public void success() {



            }

            @Test
            @DisplayName("findAll 테스트 실패 - 데이터가 존재하지 않을 때")
            public void fail() {


            }
        }

    }


}