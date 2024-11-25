package com.example.dx_kiosk.entity.laundry_supplies.repository;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaundrySuppliesRepository extends CrudRepository<LaundrySupplies, String> {

    List<LaundrySupplies> findAll();
    Optional<LaundrySupplies> findLaundrySuppliesByLaundrySuppliesId(String laundrySuppliesId);

}
