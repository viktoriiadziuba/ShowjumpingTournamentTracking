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

@Controller
public class CommonController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/")
    public String getAllTournaments(Model model) throws Exception {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "tournaments";
    }

    @GetMapping("/tournaments/delete/{id}")
    public String deleteTournaments(@PathVariable("id") String id) {
        tournamentService.deleteTournamentById(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAdd(Tournament tournament) {
        return "add-tournament";
    }

    @PostMapping("/addTournament")
    public String addTournament(Tournament tournament, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "add-tournament";
        }
        tournamentService.save(tournament);
        model.addAttribute("tournaments", tournamentService.findAll());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "login";
    }

    @PostMapping("/admin")
    public String adminPage(Model model) throws Exception {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "admin-page";
    }

}
