package com.getyourguide.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Comparator;

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
            List<Supplier> suppliers = objectMapper.readValue(suppliersInput, new TypeReference<List<Supplier>>() {
            });

            // Convert supplier list to a map for quick lookup
            Map<Long, Supplier> supplierMap = suppliers.stream()
                    .collect(Collectors.toMap(Supplier::getId, s -> s));

            // Load activities from JSON file
            var activitiesInput = new ClassPathResource("static/activities.json").getInputStream();
            List<Activity> activitiesWithSupplier = objectMapper.readValue(activitiesInput,
                    new TypeReference<List<Activity>>() {
                    });

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
                        supplier);
            }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON", e);
        }
    }

    // Filter activities based on title, price, rating, and special offers
    public List<ActivityWithSupplier> filterActivities(
            String title, Integer minPrice, Integer maxPrice, Double minRating, Boolean specialOffer) {

        return activities.stream()
                .filter(activity -> title == null || title.trim().isEmpty() ||
                        activity.getTitle().toLowerCase().contains(title.trim().toLowerCase()))
                .filter(activity -> minPrice == null || activity.getPrice() >= minPrice)
                .filter(activity -> maxPrice == null || activity.getPrice() <= maxPrice)
                .filter(activity -> minRating == null || activity.getRating() >= minRating)
                .filter(activity -> specialOffer == null || activity.isSpecialOffer() == specialOffer)
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
                .orElseThrow(() -> new NoSuchElementException("Activity with ID " + id + " not found"));
    }

    public PaginatedResponse<ActivityWithSupplier> getFilteredSortedPaginatedActivities(
            String title, int offset, int limit, Integer minPrice, Integer maxPrice,
            Double minRating, Boolean specialOffer, String sortBy, String sortOrder) {

        // Apply all filters first
        List<ActivityWithSupplier> filteredActivities = filterActivities(title, minPrice, maxPrice, minRating,
                specialOffer);

        // Sorting logic
        Comparator<ActivityWithSupplier> comparator;
        switch (sortBy.toLowerCase()) {
            case "price":
                comparator = Comparator.comparing(ActivityWithSupplier::getPrice);
                break;
            case "rating":
                comparator = Comparator.comparing(ActivityWithSupplier::getRating);
                break;
            default:
                comparator = Comparator.comparing(ActivityWithSupplier::getTitle);
        }

        // Apply sorting order
        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        filteredActivities.sort(comparator);

        // Validate pagination parameters
        if (offset > filteredActivities.size() || offset < 0 || limit < 1) {
            throw new IllegalArgumentException("Invalid parameters: check offset and limit");
        }

        // Apply pagination
        int total = filteredActivities.size();
        List<ActivityWithSupplier> paginatedActivities = paginateActivities(filteredActivities, offset, limit);

        return new PaginatedResponse<>(paginatedActivities, total, offset, limit);
    }
}
