package pe.isil.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.isil.model.Tournament;
import pe.isil.model.TournamentDeserializer;
import pe.isil.repository.TournamentRepository;

import java.util.*;

@Service
public class TournamentService {

    @Value("${challonge.url}")
    private String url;

    @Autowired
    TournamentRepository tournamentRepository;


    public List<Tournament> getAll() {
        List<Tournament> tournamentList = tournamentRepository.findAll();
        if (!tournamentList.isEmpty()){
            return tournamentList;
        } else return null;

    }

    public Tournament add(Tournament tournament,RestTemplate restTemplate){
        try {
            Tournament tournament1 = generateTournament(tournament, restTemplate);
            if (tournament1 != null) {
                tournamentRepository.save(tournament1);
                return tournament1;
            } else return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(Tournament tournament,RestTemplate restTemplate){
        if (deleteTournament(tournament, restTemplate)) {
            tournamentRepository.delete(tournament);
            return true;
        } else return false;
    }

    public boolean update(Tournament tournament, RestTemplate restTemplate){
        if (updateTournament(tournament, restTemplate)) {
            tournamentRepository.save(tournament);
            return true;
        } else return false;
    }

    public Tournament findById(Long id) {
        return tournamentRepository.findById(id).orElseGet(null);
    }

    //CRUD con el servicio consumiendo challonge
    private Tournament generateTournament(Tournament tournament,RestTemplate restTemplate) throws JsonProcessingException {
        if (tournament.getUrl() == null){
            tournament.setUrl(generateRandomUrl());
        }

        HttpEntity<Tournament> request = new HttpEntity<>(tournament);

        ResponseEntity<String> tournamentResponseEntity = restTemplate.exchange(url + ".json",
                HttpMethod.POST, request, String.class);

        if(tournamentResponseEntity.getStatusCode().is2xxSuccessful()){
            String response = tournamentResponseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module =
                    new SimpleModule("TournamentDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Tournament.class, new TournamentDeserializer());
            mapper.registerModule(module);
            Tournament tournamentResult = mapper.readValue(response, Tournament.class);
            tournamentResult.setLocation(tournament.getLocation());
            tournamentResult.setCountry(tournament.getCountry());
            System.out.println("Generated tourney: " + tournamentResult.toString());
            return tournamentResult;
        } else return null;
    }

    private boolean updateTournament(Tournament tournament,RestTemplate restTemplate){
        HttpEntity<Tournament> request = new HttpEntity<>(tournament);

        ResponseEntity<Tournament> tournamentResponseEntity = restTemplate.exchange(url + "/" + tournament.getUrl() + ".json",
                HttpMethod.PUT, request, Tournament.class);

        if(tournamentResponseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        } else return false;
    }

    private boolean deleteTournament(Tournament tournament,RestTemplate restTemplate){
        HttpEntity<Tournament> request = new HttpEntity<>(tournament);

        ResponseEntity<Tournament> tournamentResponseEntity = restTemplate.exchange(url + "/" + tournament.getUrl() + ".json",
                HttpMethod.DELETE, request, Tournament.class);

        if(tournamentResponseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        } else return false;
    }

    //genera un String aleatorio de 10 caracteres
    private String generateRandomUrl() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

}
