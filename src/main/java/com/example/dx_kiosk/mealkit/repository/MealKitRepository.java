package com.example.dx_kiosk.mealkit.repository;

import com.example.dx_kiosk.mealkit.domain.MealKit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MealKitRepository extends CrudRepository<MealKit, String> {

    List<MealKit> findAll();
    List<MealKit> findMealKitListByMealKitClassification(String mealKitClassification);
    Optional<MealKit> findMealKitByMealKitId(String mealKitId);

}
