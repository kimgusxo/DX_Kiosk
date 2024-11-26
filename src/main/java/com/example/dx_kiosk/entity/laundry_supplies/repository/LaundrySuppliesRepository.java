package com.example.dx_kiosk.entity.laundry_supplies.repository;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;
import com.example.dx_kiosk.entity.laundry_supplies.repository.custom.CustomLaundrySuppliesRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaundrySuppliesRepository extends CrudRepository<LaundrySupplies, Long>, CustomLaundrySuppliesRepository {

    List<LaundrySupplies> findAll();
    Optional<LaundrySupplies> findLaundrySuppliesByLaundrySuppliesId(Long laundrySuppliesId);

}
