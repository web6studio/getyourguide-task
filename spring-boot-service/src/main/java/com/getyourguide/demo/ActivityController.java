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
    public void debug(@RequestParam(name = "title", required = false, defaultValue = "NONE") String title, Model model) {
        // Read activities from jsonDataLoader service
        List<ActivityWithSupplier> activities = jsonDataLoader.getActivities();
        model.addAttribute("title", title);
        model.addAttribute("activities", activities);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityWithSupplier>> activities() {
        // Return activities from jsonDataLoader service
        return ResponseEntity.ok(jsonDataLoader.getActivities());
    }
}
