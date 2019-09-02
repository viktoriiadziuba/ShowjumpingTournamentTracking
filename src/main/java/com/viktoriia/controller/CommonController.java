package com.viktoriia.controller;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CommonController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/tournaments")
    public String getAllTournaments(Model model) throws Exception {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "tournaments";
    }

    @GetMapping("/tournaments/delete/{id}")
    public String deleteTournaments(@PathVariable("id") String id) {
        tournamentService.deleteTournamentById(id);
        return "redirect:/tournaments";
    }

    @GetMapping("/add")
    public String showAdd(Tournament tournament) {
        return "add-tournament";
    }

    @PostMapping("/addTournament")
    public String addTournament(@Valid Tournament tournament, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-tournament";
        }
        try {
            tournamentService.save(tournament);
            model.addAttribute("tournaments", tournamentService.findAll());
            return "redirect:tournaments";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
