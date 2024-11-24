package com.example.dx_kiosk.laundry_ticket.repository;

import com.example.dx_kiosk.laundry_ticket.domain.LaundryTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundryTicketRepository extends CrudRepository<LaundryTicket, String> {



}
