package com.getyourguide.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActivityController {
    private final JsonDataLoader jsonDataLoader;

    public ActivityController(JsonDataLoader jsonDataLoader) {
        this.jsonDataLoader = jsonDataLoader;
    }

    @GetMapping("/debug")
    public void debug(@RequestParam(name = "title", required = false, defaultValue = "") String title,
                      @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                      @RequestParam(name = "limit", required = false, defaultValue = "1000") int limit, 
                      Model model) {
        // Read activities with filtering and pagination
        List<ActivityWithSupplier> activities = jsonDataLoader.getActivities(title, offset, limit);
        model.addAttribute("title", title);
        model.addAttribute("activities", activities);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityWithSupplier>> activities(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "1000") int limit) {
        // Return filtered and paginated activities
        return ResponseEntity.ok(jsonDataLoader.getActivities(title, offset, limit));
    }
}
