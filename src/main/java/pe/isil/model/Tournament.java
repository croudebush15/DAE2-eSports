package pe.isil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Tournament {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    @JsonIgnore
    private String country;
    @Column
    @JsonIgnore
    private String location;
    @Column
    private String url;
    @JsonIgnore
    private Integer teamsInTournament = 0;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate dateTournament; // yyyy-MM-dd

}
