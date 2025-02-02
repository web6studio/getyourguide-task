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

    // Load activities and suppliers data from JSON files
    private void loadJsonData() {
        try {
            // Load suppliers from JSON file
            var suppliersInput = new ClassPathResource("static/suppliers.json").getInputStream();
            List<Supplier> suppliers = objectMapper.readValue(suppliersInput, new TypeReference<List<Supplier>>() {});

            // Convert supplier list to a map for quick lookup
            Map<Long, Supplier> supplierMap = suppliers.stream()
                    .collect(Collectors.toMap(Supplier::getId, s -> s));

            // Load activities from JSON file
            var activitiesInput = new ClassPathResource("static/activities.json").getInputStream();
            List<Activity> activitiesWithSupplier = objectMapper.readValue(activitiesInput, new TypeReference<List<Activity>>() {});

            // Convert activities to ActivityWithSupplier, adding supplier details
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

    // Filter activities by title (case-insensitive)
    public List<ActivityWithSupplier> filterActivities(String title) {
        // In real life the filter is completed on the DB side with LIKE/ILIKE (PostgreSQL)
        return activities.stream()
                .filter(activity -> title == null || title.trim().isEmpty() || 
                        activity.getTitle().toLowerCase().contains(title.trim().toLowerCase()))
                .collect(Collectors.toList());
    }

    // Apply pagination to a list of activities
    // In real life the pagination is completed on the DB side with LIMIT and OFFSET
    public List<ActivityWithSupplier> paginateActivities(List<ActivityWithSupplier> activities, int offset, int limit) {
        int endIndex = Math.min(offset + limit, activities.size());
        return activities.subList(offset, endIndex);
    }

    // Find an activity by its ID
    public ActivityWithSupplier getActivityById(long id) {
        return activities.stream()
                .filter(activity -> activity.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Filter + pagination
    public PaginatedResponse<ActivityWithSupplier> getFilteredAndPaginatedActivities(String title, int offset, int limit) {
        List<ActivityWithSupplier> filteredActivities = filterActivities(title);
        if (offset >= filteredActivities.size() || offset < 0 || limit < 0) {
            throw new IllegalArgumentException("Invalid parameters: check offset and limit");
        }
        int total = filteredActivities.size();
        List<ActivityWithSupplier> paginatedActivities = paginateActivities(filteredActivities, offset, limit);
        return new PaginatedResponse<>(paginatedActivities, total, offset, limit);
    }
}
