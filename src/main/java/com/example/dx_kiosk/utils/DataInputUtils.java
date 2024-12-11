package com.example.dx_kiosk.utils;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;
import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.entity.laundry_ticket.domain.LaundryTicket;
import com.example.dx_kiosk.entity.laundry_ticket.repository.LaundryTicketRepository;
import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DataInputUtils {

    private final MealKitRepository mealKitRepository;
    private final LaundrySuppliesRepository laundrySuppliesRepository;
    private final LaundryTicketRepository laundryTicketRepository;

    public void loadCsvToRedis(String filePath, String keyPrefix) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                String[] columns = line.split(",");

                switch (keyPrefix.toLowerCase()) {
                    case "meal_kit":
                        // Column order: meal_kit_price, meal_kit_id, meal_kit_classification, meal_kit_company_name, meal_kit_description, meal_kit_food_classification, meal_kit_name, meal_kit_url
                        MealKit mealKit = MealKit.builder()
                                .mealKitPrice(Integer.parseInt(columns[0].trim()))
                                .mealKitId(Long.parseLong(columns[1].trim()))
                                .mealKitClassification(columns[2].trim())
                                .mealKitCompanyName(columns[3].trim())
                                .mealKitDescription(columns[4].trim())
                                .mealKitFoodClassification(columns[5].trim())
                                .mealKitName(columns[6].trim())
                                .mealKitUrl(columns[7].trim())
                                .build();
                        mealKitRepository.save(mealKit);
                        break;

                    case "laundry_supplies":
                        // Column order: laundry_supplies_price, laundry_supplies_id, laundry_supplies_classification, laundry_supplies_company_name, laundry_supplies_description, laundry_supplies_name, laundry_supplies_url
                        LaundrySupplies laundrySupplies = LaundrySupplies.builder()
                                .laundrySuppliesPrice(Integer.parseInt(columns[0].trim()))
                                .laundrySuppliesId(Long.parseLong(columns[1].trim()))
                                .laundrySuppliesClassification(columns[2].trim())
                                .laundrySuppliesCompanyName(columns[3].trim())
                                .laundrySuppliesDescription(columns[4].trim())
                                .laundrySuppliesName(columns[5].trim())
                                .laundrySuppliesUrl(columns[6].trim())
                                .build();
                        laundrySuppliesRepository.save(laundrySupplies);
                        break;

                    case "laundry_ticket":
                        // Column order: laundry_ticket_price, laundry_ticket_id, laundry_ticket_classification
                        LaundryTicket laundryTicket = LaundryTicket.builder()
                                .laundryTicketPrice(Integer.parseInt(columns[0].trim()))
                                .laundryTicketId(Long.parseLong(columns[1].trim()))
                                .laundryTicketClassification(columns[2].trim())
                                .laundryTicketName(columns[3].trim())
                                .laundryTicketUrl(columns[4].trim())
                                .build();
                        laundryTicketRepository.save(laundryTicket);
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid keyPrefix: " + keyPrefix);
                }
            }
            System.out.println("Data successfully loaded into Redis for keyPrefix: " + keyPrefix);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing data: " + e.getMessage());
        }
    }

}
