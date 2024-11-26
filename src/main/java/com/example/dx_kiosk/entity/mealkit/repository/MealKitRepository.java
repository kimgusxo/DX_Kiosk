package com.example.dx_kiosk.entity.mealkit.repository;

import com.example.dx_kiosk.entity.laundry_supplies.repository.custom.CustomLaundrySuppliesRepository;
import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.repository.custom.CustomMealKitRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface MealKitRepository extends CrudRepository<MealKit, String>, CustomMealKitRepository {

    List<MealKit> findAll();
    Optional<MealKit> findMealKitByMealKitId(String mealKitId);
    List<MealKit> findMealKitListByMealKitClassification(String mealKitClassification);

}
