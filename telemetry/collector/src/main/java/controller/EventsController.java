package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {
    @PostMapping("/sensors")
    public void sensors() {}

    @PostMapping("/hubs")
    public void hubs() {}
}
