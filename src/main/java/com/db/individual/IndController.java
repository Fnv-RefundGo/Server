package com.db.individual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndController {

    @Autowired
    private IndividualRepository individualRepository;

    @PostMapping("/saveIndividual")
    public String saveIndividual(@RequestBody IndEntity individual) {
        try {
            individualRepository.save(individual);
            return "Data saved successfully";
        } catch (Exception e) {
            return "Failed to save data: " + e.getMessage();
        }
    }
}