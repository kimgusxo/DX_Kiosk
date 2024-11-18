package com.example.dx_kiosk.unit_test.mealkit;

import com.example.dx_kiosk.mealkit.domain.MealKit;
import com.example.dx_kiosk.mealkit.repository.MealKitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataRedisTest
class MealKitControllerUnitTest {

    @Container
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:6.2.6")
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort())
            .withStartupTimeout(Duration.ofSeconds(30));;

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    private MealKitRepository mealKitRepository;

    @Nested
    @DisplayName("Find Test")
    class Test_Find {

        @BeforeEach
        @DisplayName("테스트 데이터 생성")
        void setUp() {
            MealKit mealKit1 = MealKit.builder()
                    .mealKitId("1")
                    .mealKitName("Spaghetti")
                    .mealKitPrice(10000)
                    .mealKitCount(50)
                    .mealKitClassification("Italian")
                    .mealKitFoodClassification("Pasta")
                    .mealKitCompanyName("Delicious Meals Inc.")
                    .mealKitDescription("Delicious spaghetti kit")
                    .mealKitUrl("http://example.com/spaghetti")
                    .build();

            MealKit mealKit2 = MealKit.builder()
                    .mealKitId("2")
                    .mealKitName("Tacos")
                    .mealKitPrice(15000)
                    .mealKitCount(30)
                    .mealKitClassification("v")
                    .mealKitFoodClassification("Tortilla")
                    .mealKitCompanyName("Mexican Fiesta")
                    .mealKitDescription("Authentic taco kit")
                    .mealKitUrl("http://example.com/tacos")
                    .build();

            mealKitRepository.save(mealKit1);
            mealKitRepository.save(mealKit2);
        }

        @Nested
        @DisplayName("findAll 테스트")
        class Test_FindAll {
            @Test
            @DisplayName("findAll 테스트 성공")
            public void success() {
                // given

                // when
                List<MealKit> mealKitList = mealKitRepository.findAll();

                // then
                assertThat(mealKitList).isNotEmpty();
                assertThat(mealKitList.size()).isEqualTo(2);
                assertThat(mealKitList).extracting("mealKitName")
                        .containsExactlyInAnyOrder("Spaghetti", "Tacos");
            }

            @Test
            @DisplayName("findAll 테스트 실패 - 데이터가 존재하지 않을 때")
            public void fail() {
                // given
                mealKitRepository.deleteAll();

                // when
                List<MealKit> mealKitList = mealKitRepository.findAll();

                // then
                assertThat(mealKitList).isEmpty();
            }
        }

        @Nested
        @DisplayName("findMealKitByMealKitId 테스트")
        class Test_FindMealKitByMealKitId {

            @Test
            @DisplayName("findMealKitByMealKitId 테스트 성공")
            public void success() {
                // given
                String mealKitId = "1";

                // when
                Optional<MealKit> mealKit = mealKitRepository.findMealKitByMealKitId(mealKitId);

                // then
                assertThat(mealKit).isPresent(); // Optional이 비어 있지 않은지 확인
                assertThat(mealKit.get().getMealKitName()).isEqualTo("Spaghetti"); // 특정 필드 값 확인

            }

            @Test
            @DisplayName("findMealKitByMealKitId 테스트 실패 - 존재하지 않는 밀키트 아이디")
            public void fail() {
                // given
                String mealKitId = "3";

                // when
                Optional<MealKit> mealKit = mealKitRepository.findMealKitByMealKitId(mealKitId);

                // then
                assertThat

            }

        }

        @Nested
        @DisplayName("findMealKitListByMealKitClassification 테스트")
        class Test_FindMealKitListByMealKitClassification {

            @Test
            @DisplayName("findMealKitListByMealKitClassification 테스트 성공")
            public void success() {
                // given
                String mealKitClassification = "Mexican";

                // when
                List<MealKit> mealKitList = mealKitRepository.findMealKitListByMealKitClassification(mealKitClassification);

                // then
                assertThat(mealKitList).isNotEmpty();
                assertThat(mealKitList.size()).isEqualTo(1);
                assertThat(mealKitList).extracting("mealKitName")
                        .containsExactlyInAnyOrder("Tacos"); // "Mexican" 분류에 맞는 아이템으로 수정
            }

            @Test
            @DisplayName("findMealKitListByMealKitClassification 테스트 실패 - 데이터가 존재하지 않을 때")
            public void fail() {
                // given
                String mealKitClassification = "Mexican";

                // when
                List<MealKit> mealKitList = mealKitRepository.findMealKitListByMealKitClassification(mealKitClassification);

                // then
                assertThat(mealKitList).isEmpty();
            }

        }
    }
}
