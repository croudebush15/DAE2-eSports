package pe.isil.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.isil.model.Team;

import pe.isil.model.TeamDeserializer;
import pe.isil.repository.TeamRepository;
import pe.isil.repository.TournamentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TournamentRepository tournamentRepository;
    @Value("${challonge.url}")
    private String url;

    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    public Team add(Team team, RestTemplate restTemplate){
        try {
            Team team1 = generateTeam(team, restTemplate);
            if (team1 != null) {
                teamRepository.save(team1);
                return team1;
            } else return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(Team team,RestTemplate restTemplate){
        if (deleteTeam(team, restTemplate)) {
            teamRepository.delete(team);
            return true;
        } else return false;
    }

    public boolean update(Team team,RestTemplate restTemplate){
        if (updateTeam(team, restTemplate)) {
            teamRepository.save(team);
            return true;
        } else return false;
    }

    public Team findById(Long idTeam) {
        return teamRepository.findById(idTeam).orElseGet(null);
    }

    public List<Team> findTeamsByTournament(Long idTournament){
        List<Team> teams = teamRepository.findAll();
        List<Team> result = new ArrayList<>();
        System.out.println("Current teams in list: " + teams.toString());
        System.out.println(idTournament);
        for (Team team: teams) {
            System.out.println("Team tourney id: " + team.getTournament_id());
            if (team.getTournament_id() == idTournament) { //something is wrong here
                System.out.println("Team added to list.");
                result.add(team);
            }
        }
        System.out.println(result.toString());
        return result;
    }

    private Team generateTeam(Team team, RestTemplate restTemplate) throws JsonProcessingException {
        HttpEntity<Team> request = new HttpEntity<>(team);

        ResponseEntity<String> teamResponseEntity = restTemplate.exchange(url + "/" + team.getTournament_id() + "/participants.json",
                HttpMethod.POST, request, String.class);

        if(teamResponseEntity.getStatusCode().is2xxSuccessful()){
            String response = teamResponseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module =
                    new SimpleModule("TeamDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Team.class, new TeamDeserializer());
            mapper.registerModule(module);
            Team teamResult = mapper.readValue(response, Team.class);
            teamResult.setCountryTeam(team.getCountryTeam());
            System.out.println("Team generate result: " + teamResult.toString());
            return teamResult;
        } else return null;
    }

    private boolean updateTeam(Team team, RestTemplate restTemplate){
        HttpEntity<Team> request = new HttpEntity<>(team);

        ResponseEntity<Team> teamResponseEntity = restTemplate.exchange(url + "/" + team.getTournament_id() + "/participants/" + team.getIdTeam() + ".json",
                HttpMethod.PUT, request, Team.class);

        if(teamResponseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        } else return false;
    }

    private boolean deleteTeam(Team team, RestTemplate restTemplate){
        HttpEntity<Team> request = new HttpEntity<>(team);

        ResponseEntity<Team> tournamentResponseEntity = restTemplate.exchange(url + "/" + team.getTournament_id() + "/participants/" + team.getIdTeam() + ".json",
                HttpMethod.DELETE, request, Team.class);

        if(tournamentResponseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        } else return false;
    }
}
