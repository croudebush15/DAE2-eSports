package pe.isil.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TournamentDeserializer extends StdDeserializer<Tournament> {

    public TournamentDeserializer() {
        this(null);
    }

    public TournamentDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Tournament deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Tournament tournament = new Tournament();
        ObjectCodec codec = jsonParser.getCodec();

        JsonNode node = codec.readTree(jsonParser);
        //System.out.println(node.toString());
        JsonNode tournamentNode = node.get("tournament");

        JsonNode idNode = tournamentNode.get("id");
        JsonNode nameNode = tournamentNode.get("name");
        JsonNode urlNode = tournamentNode.get("url");
        tournament.setId(idNode.asLong());
        tournament.setName(nameNode.asText());
        tournament.setUrl(urlNode.asText());
        return tournament;
    }
}
