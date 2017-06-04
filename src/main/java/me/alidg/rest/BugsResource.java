package me.alidg.rest;

import me.alidg.service.BugService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author Ali Dehghani
 */
@RestController
@RequestMapping("/bugs")
public class BugsResource {

    private final BugService bugService;

    public BugsResource(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping
    public List<String> listAll() {
        return Collections.emptyList();
    }
}
