package pe.isil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pe.isil.model.Team;
import pe.isil.model.Tournament;
import pe.isil.service.TeamService;
import pe.isil.service.TournamentService;

import java.util.List;

@SpringBootApplication
public class EsportApplication{

    public static void main(String[] args) {
        SpringApplication.run(EsportApplication.class, args);
    }


}
