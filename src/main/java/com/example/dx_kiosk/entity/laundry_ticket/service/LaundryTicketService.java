package com.example.dx_kiosk.entity.laundry_ticket.service;

import com.example.dx_kiosk.entity.laundry_ticket.domain.LaundryTicket;
import com.example.dx_kiosk.entity.laundry_ticket.domain.dto.LaundryTicketDTO;
import com.example.dx_kiosk.entity.laundry_ticket.repository.LaundryTicketRepository;
import com.example.dx_kiosk.exception.ListEmptyException;
import com.example.dx_kiosk.exception.ObjectEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaundryTicketService {

    private final LaundryTicketRepository laundryTicketRepository;

    public List<LaundryTicketDTO> getLaundryTicketList() {
        List<LaundryTicket> results =
                laundryTicketRepository.findAll();

        if(results.isEmpty()) {
            throw new ListEmptyException();
        }

        List<LaundryTicketDTO> resultsDTO = new ArrayList<>();

        results.forEach( r -> {
            resultsDTO.add(LaundryTicketDTO.from(r));
        });

        return resultsDTO;
    }

    public LaundryTicketDTO getLaundryTicketByLaundryTicketId(String laundryTicketId) {
        Optional<LaundryTicket> result =
                laundryTicketRepository.findById(laundryTicketId);

        if(result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        return LaundryTicketDTO.from(result.get());
    }
}
