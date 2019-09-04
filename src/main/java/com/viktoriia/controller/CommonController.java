package com.viktoriia.controller;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.TournamentService;
import com.viktoriia.user.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class CommonController {

    Logger log;

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
