package com.example.dx_kiosk.entity.laundry_ticket.repository;

import com.example.dx_kiosk.entity.laundry_ticket.domain.LaundryTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaundryTicketRepository extends CrudRepository<LaundryTicket, String> {

    List<LaundryTicket> findAll();
    Optional<LaundryTicket> findLaundryTicketByLaundryTicketId(String laundryTicketId);

}
