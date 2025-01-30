package com.getyourguide.demo; 

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JsonDataLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<ActivityWithSupplier> activities;

    public JsonDataLoader() {
        loadJsonData();
    }

    private void loadJsonData() {
        try {
            // Read the JSON file and convert it into a list of suppliers
            var suppliersInput = new ClassPathResource("static/suppliers.json").getInputStream();
            List<Supplier> suppliers = objectMapper.readValue(suppliersInput, new TypeReference<List<Supplier>>() {});

            // Convert to Map for quick search by ID
            Map<Long, Supplier> supplierMap = suppliers.stream()
                    .collect(Collectors.toMap(Supplier::getId, s -> s));

            // Read the JSON file and convert it into a list of activities
            var activitiesInput = new ClassPathResource("static/activities.json").getInputStream();
            List<Activity> activitiesWithSupplier = objectMapper.readValue(activitiesInput, new TypeReference<List<Activity>>() {});

            // Convert to ActivityWithSupplier with nested suppliers
            activities = activitiesWithSupplier.stream().map(activity -> {
                Supplier supplier = supplierMap.get(activity.getSupplierId());
                return new ActivityWithSupplier(
                        activity.getId(),
                        activity.getTitle(),
                        activity.getPrice(),
                        activity.getCurrency(),
                        activity.getRating(),
                        activity.isSpecialOffer(),
                        supplier
                );
            }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON", e);
        }
    }

    public List<ActivityWithSupplier> getActivities(String title, int offset, int limit) {
        // Filter activities by title if provided
        // In real life the filter is completed on the DB side with LIKE/ILIKE (PostgreSQL)
        List<ActivityWithSupplier> filteredActivities = activities.stream()
            .filter(activity -> title == null || title.trim().isEmpty() || 
                    activity.getTitle().toLowerCase().contains(title.trim().toLowerCase()))
            .collect(Collectors.toList());

        // Apply pagination
        // In real life the pagination is completed on the DB side with LIMIT and OFFSET
        int endIndex = Math.min(offset + limit, filteredActivities.size());
        return filteredActivities.subList(offset, endIndex);
    }

    public ActivityWithSupplier getActivityById(long id) {
        return activities.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
