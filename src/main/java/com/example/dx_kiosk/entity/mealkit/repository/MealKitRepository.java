package com.example.dx_kiosk.entity.mealkit.repository;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MealKitRepository extends CrudRepository<MealKit, String> {

    List<MealKit> findAll();
    Optional<MealKit> findMealKitByMealKitId(String mealKitId);
    List<MealKit> findMealKitListByMealKitClassification(String mealKitClassification);

}
