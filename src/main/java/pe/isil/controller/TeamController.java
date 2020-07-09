/*
package pe.isil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.isil.model.Team;
import pe.isil.service.TeamService;
import pe.isil.service.TournamentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {
    final TeamService teamService;
    final TournamentService tournamentService;

    public TeamController(TeamService teamService, TournamentService tournamentService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/teams")
    public String getTeamList(Model model){

        List<Team> teams = teamService.getAll();
        model.addAttribute("teams", teams);
        return "team";
    }

    @GetMapping("/tournament/{tournamentId}/teams")
    public String getTeamList(Model model, @PathVariable Long tournamentId){

        List<Team> teams = teamService.findTeamsByTournament(tournamentId);
        model.addAttribute("teams", teams);
        return "team";
    }

    @GetMapping("/teams/add")
    public String addTeam(Model model){

        model.addAttribute("team", new Team());
        model.addAttribute("tournaments", tournamentService.getAll());
        return "team-add";
    }

    @PostMapping("/teams/save")
    public String saveTeam(Model model, Team team){
        teamService.add(team);
        return "redirect:/teams";
    }

    @GetMapping("/teams/edit/{idTeam}")
    public String teamforUpdate(@PathVariable Long idTeam, Model model){
        Team currentTeam = teamService.findById(idTeam);
        model.addAttribute("team", currentTeam);
        model.addAttribute("tournaments", tournamentService.getAll());
        return "team-edit";
    }

    @PostMapping("/teams/update/{idTeam}")
    public String updateTeam(@PathVariable Long idTeam, Team team){

        //Update
        teamService.update(team);
        return "redirect:/teams";
    }

    @GetMapping("/teams/delete/{idTeam}")
    public String deleteTeam(@PathVariable Long idTeam){
        Team currentTeam = teamService.findById(idTeam);
        if(currentTeam != null){
            teamService.delete(currentTeam);
        }
        return "redirect:/teams";
    }

}
*/
