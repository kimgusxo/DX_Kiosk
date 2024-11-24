package com.example.dx_kiosk.laundry_supplies.repository;

import com.example.dx_kiosk.laundry_supplies.domain.LaundrySupplies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundrySuppliesRepository extends CrudRepository<LaundrySupplies, Long> {



}
