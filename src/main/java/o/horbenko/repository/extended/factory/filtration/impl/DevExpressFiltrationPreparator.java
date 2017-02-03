package o.horbenko.repository.extended.factory.filtration.impl;

import com.fasterxml.jackson.databind.JsonNode;
import o.horbenko.repository.extended.exceptions.json.InvalidInputJsonStructureException;
import o.horbenko.repository.extended.factory.filtration.FiltrationPreparator;
import o.horbenko.repository.extended.utils.JsonParserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static o.horbenko.repository.extended.constants.OperatorConstants.INNER_OBJECT_END;
import static o.horbenko.repository.extended.constants.OperatorConstants.INNER_OBJECT_START;
import static o.horbenko.repository.extended.constants.RequestPathCostants.DEV_EXPRESS_JSON_PARAM_WITH_FILTER;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_RESOLVE_JSON;

@Component
@Qualifier("devExpFiltrationPreparator")
public class DevExpressFiltrationPreparator implements FiltrationPreparator{

    private static final Logger log = LoggerFactory.getLogger(DevExpressFiltrationPreparator.class);


    @Override
    public List<String> prepareRawInputString(String rawInputJsonAsString) {

        try {
            JsonNode filtrationJsonNode = JsonParserUtils
                .stringToJsonNodeBy(DEV_EXPRESS_JSON_PARAM_WITH_FILTER, rawInputJsonAsString);

            if (filtrationJsonNode == null) return Collections.emptyList();

            List<String> resultStackref = new LinkedList<>();
            collectFromContainer(filtrationJsonNode, resultStackref);

            return resultStackref;


        } catch (Exception e) {
            log.error(ERR_CANT_RESOLVE_JSON, e);
            throw new InvalidInputJsonStructureException(ERR_CANT_RESOLVE_JSON, e);
        }

    }

    private void collectFromContainer(JsonNode node, List<String> resultStackRef) {

        for (JsonNode innerNode : node)
            collectNode(innerNode, resultStackRef);
    }

    private void collectNode(JsonNode innerNode, List<String> resultStackRef) {
        if (innerNode.isContainerNode()) {

            resultStackRef.add(INNER_OBJECT_START);
            collectFromContainer(innerNode, resultStackRef);
            resultStackRef.add(INNER_OBJECT_END);

        } else {
            resultStackRef.add(innerNode.asText());
        }
    }



}
