package pe.isil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Team {

    @Id
    @JsonProperty("id")
    private Long idTeam;
    @Column
    @JsonProperty("name")
    private String nameTeam;
    @JsonIgnore
    @Column
    private String countryTeam;
    @Column
    private Long seed;
    @JsonIgnore
    @Column
    private Long tournament_id;

}
