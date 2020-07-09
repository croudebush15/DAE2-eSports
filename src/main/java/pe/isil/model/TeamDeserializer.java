package pe.isil.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TeamDeserializer extends StdDeserializer<Team> {

    public TeamDeserializer() {
        this(null);
    }

    public TeamDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Team deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Team team = new Team();
        ObjectCodec codec = jsonParser.getCodec();

        JsonNode node = codec.readTree(jsonParser);
        //System.out.println(node.toString());
        JsonNode tournamentNode = node.get("participant");

        JsonNode idNode = tournamentNode.get("id");
        JsonNode nameNode = tournamentNode.get("name");
        JsonNode seedNode = tournamentNode.get("seed");
        JsonNode urlNode = tournamentNode.get("tournament_id");
        team.setIdTeam(idNode.asLong());
        team.setNameTeam(nameNode.asText());
        team.setSeed(seedNode.asLong());
        team.setTournament_id(urlNode.asLong());
        return team;
    }
}
