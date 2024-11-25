package com.example.dx_kiosk.unit_test.mealkit.repository;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.dao.EmptyResultDataAccessException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Testcontainers
@DataRedisTest
class MealKitRepositoryUnitTest {

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
                assertThat(mealKit).isPresent();
                assertThat(mealKit.get().getMealKitName()).isEqualTo("Spaghetti");

            }

            @Test
            @DisplayName("findMealKitByMealKitId 테스트 실패 - 존재하지 않는 밀키트 아이디")
            public void fail() {
                // given
                String mealKitId = "3"; // 존재하지 않는 밀키트 ID

                // when
                Optional<MealKit> mealKit = mealKitRepository.findMealKitByMealKitId(mealKitId);

                // then
                assertThat(mealKit).isNotPresent();
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

        @Nested
        @DisplayName("findMealKitListByMealKitFoodClassification 테스트")
        class Test_FindMealKitListByMealKitFoodClassification {

            @Test
            @DisplayName("findMealKitListByMealKitFoodClassification 테스트 성공")
            public void success() {
                // given
                String mealKitFoodClassification = "Pasta";

                // when
                List<MealKit> mealKitList = mealKitRepository.findMealKitListByMealKitFoodClassification(mealKitFoodClassification);

                // then
                assertThat(mealKitList).isNotEmpty();
                assertThat(mealKitList.size()).isEqualTo(1);
                assertThat(mealKitList).extracting("mealKitName")
                        .containsExactlyInAnyOrder("Spaghetti");
            }

            @Test
            @DisplayName("findMealKitListByMealKitFoodClassification 테스트 실패 - 데이터가 존재하지 않을 때")
            public void fail() {
                // given
                String mealKitFoodClassification = "Bread";

                // when
                List<MealKit> mealKitList = mealKitRepository.findMealKitListByMealKitFoodClassification(mealKitFoodClassification);

                // then
                assertThat(mealKitList).isEmpty();
            }

        }
    }

    @Nested
    @DisplayName("Create 테스트")
    class Test_Create {

        @Test
        @DisplayName("save 테스트 성공")
        public void success() {
            // given
            MealKit newMealKit = MealKit.builder()
                    .mealKitId("3")
                    .mealKitName("Sushi")
                    .mealKitPrice(20000)
                    .mealKitCount(10)
                    .mealKitClassification("Japanese")
                    .mealKitFoodClassification("Seafood")
                    .mealKitCompanyName("Sushi House")
                    .mealKitDescription("Fresh sushi kit")
                    .mealKitUrl("http://example.com/sushi")
                    .build();

            // when
            MealKit savedMealKit = mealKitRepository.save(newMealKit);

            // then
            assertThat(savedMealKit).isNotNull();
            assertThat(savedMealKit.getMealKitName()).isEqualTo("Sushi");
        }

        @Test
        @DisplayName("save 테스트 실패")
        public void fail() {
            // given
            MealKit invalidMealKit = null;

            // when & then
            assertThatThrownBy(() -> mealKitRepository.save(invalidMealKit))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Update 테스트")
    public class Test_Update {

        @Test
        @DisplayName("update 테스트 성공")
        public void success() {
            // given
            String mealKitId = "1";
            Optional<MealKit> mealKitOptional = mealKitRepository.findMealKitByMealKitId(mealKitId);

            // when
            if (mealKitOptional.isPresent()) {
                MealKit mealKit = mealKitOptional.get();
                mealKit.setMealKitPrice(12000);
                MealKit updatedMealKit = mealKitRepository.save(mealKit);

                // then
                assertThat(updatedMealKit.getMealKitPrice()).isEqualTo(12000);
            }
        }

        @Test
        @DisplayName("update 테스트 실패")
        public void fail() {
            // given
            String invalidMealKitId = "999"; // 존재하지 않는 ID

            // when
            Optional<MealKit> mealKitOptional = mealKitRepository.findMealKitByMealKitId(invalidMealKitId);

            // then
            assertThat(mealKitOptional).isNotPresent();
        }
    }

    @Nested
    @DisplayName("Delete 테스트")
    public class Test_Delete {

        @Nested
        @DisplayName("deleteMealKitByMealKitId 테스트")
        class Test_deleteMealKitByMealKitId {

            @Test
            @DisplayName("deleteMealKitByMealKitId 테스트 성공")
            public void success() {
                // given
                String mealKitId = "1";
                mealKitRepository.deleteById(mealKitId);

                // when
                Optional<MealKit> deletedMealKit = mealKitRepository.findMealKitByMealKitId(mealKitId);

                // then
                assertThat(deletedMealKit).isNotPresent();
            }

            @Test
            @DisplayName("deleteMealKitByMealKitId 테스트 실패")
            public void fail() {
                // given
                String invalidMealKitId = "999"; // 존재하지 않는 ID

                // when
                assertThatThrownBy(() -> mealKitRepository.deleteById(invalidMealKitId))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }

        @Nested
        @DisplayName("deleteAll 테스트")
        class Test_deleteAll {

            @Test
            @DisplayName("deleteAll 테스트 성공")
            public void success() {
                // given
                mealKitRepository.deleteAll();

                // when
                List<MealKit> allMealKits = mealKitRepository.findAll();

                // then
                assertThat(allMealKits).isEmpty();
            }

            @Test
            @DisplayName("deleteAll 테스트 실패")
            public void fail() {
                // when
                mealKitRepository.deleteAll();
                List<MealKit> allMealKits = mealKitRepository.findAll();

                // then
                assertThat(allMealKits).isEmpty(); // 실패 조건을 명확히 하기 위해 에러 메시지를 설정할 수 있음
            }
        }
    }
}