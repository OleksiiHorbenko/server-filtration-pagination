package o.horbenko.repository.extended.utils;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import o.horbenko.repository.extended.exceptions.json.InvalidInputJsonStructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_RESOLVE_JSON;

public class JsonParserUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonParserUtils.class);

    public static JsonNode stringToJsonNodeBy(String innerPropertyNameToGet, String rootJsonAsString) {
        try {

            if (rootJsonAsString == null) return null;

            JsonNode rootJsonNode = stringToJsonNode(rootJsonAsString);
            if (isJsonNodeNullOrEmpty(rootJsonNode)) return null;

            JsonNode neededJsonNode = rootJsonNode.get(innerPropertyNameToGet);
            if (isJsonNodeNullOrEmpty(neededJsonNode)) return null;

            return neededJsonNode;


        } catch (Exception e) {
            throw new InvalidInputJsonStructureException(ERR_CANT_RESOLVE_JSON, e);
        }
    }

    private static JsonNode stringToJsonNode(String stringToConvert) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(stringToConvert);

            return mapper.readTree(parser);


        } catch (Exception e) {
            throw new InvalidInputJsonStructureException(ERR_CANT_RESOLVE_JSON, e);
        }
    }

    private static boolean isJsonNodeNullOrEmpty(JsonNode toCheck) {
        return toCheck == null || !toCheck.isContainerNode() || !toCheck.iterator().hasNext();
    }


}
