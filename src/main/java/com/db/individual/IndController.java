package com.db.individual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndController {

    @Autowired
    private IndividualRepository individualRepository;

    @PostMapping("/compareIndividual")
    public String compareIndividual(@RequestBody IndEntity inputIndividual) {

        IndEntity dbIndividual = individualRepository.findById(inputIndividual.getId()).orElse(null);


        if (dbIndividual != null && dbIndividual.equals(inputIndividual)) {
            return "입력된 값과 DB의 값이 일치합니다.";
        } else {
            return "입력된 값과 DB의 값이 일치하지 않습니다.";
        }
    }
}