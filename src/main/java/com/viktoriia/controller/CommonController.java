package com.viktoriia.controller;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    private TournamentService tournamentService;

    private final Facebook facebook;

    @Inject
    public CommonController(Facebook facebook) {
        this.facebook = facebook;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Reference> friends = facebook.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        return "home";
    }

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
    public String addTournament(Tournament tournament, BindingResult result, Model model) {
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
