package com.getyourguide.demo;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ActivityController {

    private final JsonDataLoader jsonDataLoader;

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 1000;

    public ActivityController(JsonDataLoader jsonDataLoader) {
        this.jsonDataLoader = jsonDataLoader;
    }

    @GetMapping("/debug")
    public void debug(@RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "offset", required = false, defaultValue = DEFAULT_OFFSET + "") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_LIMIT + "") int limit,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "specialOffer", required = false) Boolean specialOffer,
            @RequestParam(name = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
            Model model) {

        log.info("Received /debug request with title='{}', offset={}, limit={}", title, offset, limit);

        PaginatedResponse<ActivityWithSupplier> paginatedActivities = jsonDataLoader.getFilteredSortedPaginatedActivities(title, offset, limit, minPrice, maxPrice, minRating, specialOffer, sortBy, sortOrder);
        model.addAttribute("title", title);
        model.addAttribute("activities", paginatedActivities.getData());
        model.addAttribute("total", paginatedActivities.getTotal());
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
    }

    @GetMapping("/activities")
    public ResponseEntity<PaginatedResponse<ActivityWithSupplier>> getActivities(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "offset", required = false, defaultValue = DEFAULT_OFFSET + "") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_LIMIT + "") int limit,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "specialOffer", required = false) Boolean specialOffer,
            @RequestParam(name = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder) {
    
        log.info("Received /activities request with title='{}', offset={}, limit={}, filters: minPrice={}, maxPrice={}, minRating={}, specialOffer={}, sortBy={}, sortOrder={}", 
                title, offset, limit, minPrice, maxPrice, minRating, specialOffer, sortBy, sortOrder);
    
        return ResponseEntity.ok(jsonDataLoader.getFilteredSortedPaginatedActivities(title, offset, limit, minPrice, maxPrice, minRating, specialOffer, sortBy, sortOrder));
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<ActivityWithSupplier> getActivityById(@PathVariable long id) {
        log.info("Fetching activity with id={}", id);
        return ResponseEntity.of(Optional.ofNullable(jsonDataLoader.getActivityById(id)));
    }
}
