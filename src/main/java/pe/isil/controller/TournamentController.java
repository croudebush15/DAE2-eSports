package pe.isil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import pe.isil.model.Team;
import pe.isil.model.Tournament;
import pe.isil.service.TeamService;
import pe.isil.service.TournamentService;

import java.util.List;

@Controller
public class TournamentController {
    final TournamentService tournamentService;
    final TeamService teamService;

    public TournamentController(TournamentService tournamentService, TeamService teamService){
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Value("${challonge.user}")
    private String apiUsername;
    @Value("${challonge.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;
    //HTTP Basic Authentication es necesario para el consumo del API challonge
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.basicAuthentication(apiUsername, apiKey).build();
    }

    @GetMapping("/tournaments")
    public String getTournament(Model model) {
        List<Tournament> tournaments = tournamentService.getAll();
        model.addAttribute("tournaments", tournaments);
        return "tournament";
    }

    @GetMapping("/tournaments/add")
    public String addTournament(Model model){
        model.addAttribute("tournament", new Tournament());
        return "tournament-add";
    }

    @PostMapping("/tournaments/save")
    public String saveTournament(Model model, Tournament tournament){
        tournamentService.add(tournament, restTemplate);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/edit/{id}")
    public String tournamentForUpdate(@PathVariable Long id, Model model){
        Tournament currentTournament = tournamentService.findById(id);
        model.addAttribute("tournament", currentTournament);
        return "tournament-edit";
    }

    @PostMapping("/tournaments/update/{id}")
    public String updateTournament(@PathVariable Long id, Tournament tournament){
        System.out.println("update tourney: " + tournament.toString());
        tournamentService.update(tournament, restTemplate);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/delete/{id}")
    public String deleteTournament(@PathVariable Long id){
        Tournament currentTournament = tournamentService.findById(id);
        if (currentTournament != null){
            tournamentService.delete(currentTournament,restTemplate);
        }
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/{idTournament}")
    public String getTournamentBracket(Model model, @PathVariable Long idTournament){
        Tournament currentTournament = tournamentService.findById(idTournament);
        if (currentTournament != null){
            model.addAttribute("tournament", currentTournament);
            model.addAttribute("url", "http://challonge.com/" + currentTournament.getUrl() + "/module");
        }

        return "tournament-bracket";
    }

    /*@PostMapping("/tournamenttables")
    public String getTablaTorneo(Model model){
        List<Team> teams = teamService.getAll();
        model.addAttribute("teams", teams);
        return "tournament-bracket";
    }*/

    //Team mappings

    @GetMapping("/teams")
    public String getTeamList(Model model){

        List<Team> teams = teamService.getAll();
        model.addAttribute("teams", teams);
        return "team";
    }

    @GetMapping("/tournament/{tournamentId}/teams")
    public String getTeamsByTournament(Model model, @PathVariable Long tournamentId){
        System.out.println("Tourney ID: " + tournamentId);
        List<Team> teams = teamService.findTeamsByTournament(tournamentId);
        model.addAttribute("teams", teams);
        model.addAttribute("tournament", tournamentService.findById(tournamentId));
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
        Team team1 = teamService.add(team,restTemplate);
        Long tournamentId = team1.getTournament_id();
        return "redirect:/tournament/" + tournamentId + "/teams";
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
        teamService.update(team,restTemplate);
        return "redirect:/teams";
    }

    @GetMapping("/teams/delete/{idTeam}")
    public String deleteTeam(@PathVariable Long idTeam){
        Team currentTeam = teamService.findById(idTeam);
        if(currentTeam != null){
            teamService.delete(currentTeam,restTemplate);
        }
        return "redirect:/teams";
    }


}
