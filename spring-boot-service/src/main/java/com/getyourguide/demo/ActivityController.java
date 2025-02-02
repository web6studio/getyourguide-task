package com.getyourguide.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
                      Model model) {

        PaginatedResponse<ActivityWithSupplier> paginatedActivities = jsonDataLoader.getFilteredAndPaginatedActivities(title, offset, limit);
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
            @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_LIMIT + "") int limit) {
        // Return filtered and paginated activities
        return ResponseEntity.ok(jsonDataLoader.getFilteredAndPaginatedActivities(title, offset, limit));
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<ActivityWithSupplier> getActivityById(@PathVariable long id) {
        return ResponseEntity.of(Optional.ofNullable(jsonDataLoader.getActivityById(id)));
    }
}
