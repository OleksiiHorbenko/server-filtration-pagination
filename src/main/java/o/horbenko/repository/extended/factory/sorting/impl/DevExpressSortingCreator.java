package o.horbenko.repository.extended.factory.sorting.impl;

import com.fasterxml.jackson.databind.JsonNode;
import o.horbenko.repository.extended.exceptions.json.InvalidInputJsonStructureException;
import o.horbenko.repository.extended.factory.sorting.SortingCreator;
import o.horbenko.repository.extended.utils.JsonParserUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static o.horbenko.repository.extended.constants.RequestPathCostants.DEV_EXPRESS_PARAM_WITH_SORT_JSON;
import static o.horbenko.repository.extended.constants.RequestPathCostants.DEV_EXPRESS_SORT_IS_DESC;
import static o.horbenko.repository.extended.constants.RequestPathCostants.DEV_EXPRESS_SORT_SELECTOR;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_RESOLVE_JSON;

@Component
@Qualifier("devExpSortingCreator")
public class DevExpressSortingCreator implements SortingCreator {


    @Override
    public Sort createJpaSortingFrom(String initJson, Properties dtoFieldsMap) {
        try {

            JsonNode sortingJsonNode = JsonParserUtils
                .stringToJsonNodeBy(DEV_EXPRESS_PARAM_WITH_SORT_JSON, initJson);

            if (sortingJsonNode == null) return null;

            List<Sort> sorts = createSortsFromJson(sortingJsonNode, dtoFieldsMap);
            return collectSorts(sorts);


        } catch (Exception e) {
            throw new InvalidInputJsonStructureException(ERR_CANT_RESOLVE_JSON, e);
        }
    }


    private List<Sort> createSortsFromJson(JsonNode rootNode, Properties dtoFieldsMap) {
        List<Sort> sorts = new ArrayList<>();

        for (JsonNode sortParameter : rootNode)
            sorts.add(convertJsonToSortInstance(sortParameter, dtoFieldsMap));

        return sorts;
    }

    private Sort convertJsonToSortInstance(JsonNode sortJsoNode, Properties dtoFieldsMap) {

        if (sortJsoNode.isObject()) {

            String field = getMappedFieldName(sortJsoNode, dtoFieldsMap);
            boolean isDescDirection = sortJsoNode.get(DEV_EXPRESS_SORT_IS_DESC).booleanValue();
            return createSortIstance(field, isDescDirection);
        }
        return null;
    }

    private Sort collectSorts(List<Sort> sorts) {
        if (sorts == null || sorts.isEmpty()) return null;

        Sort toReturn = sorts.get(0);
        return collectSortingFromSecond(sorts, toReturn);
    }

    private Sort collectSortingFromSecond(List<Sort> sorts, Sort toReturn) {

        for (int i = 1; i < sorts.size(); i++) {
            toReturn = toReturn.and(sorts.get(i));
        }

        return toReturn;
    }

    private String getMappedFieldName(JsonNode jsonNode, Properties dtoFieldsMap) {

        String fieldName = jsonNode
            .get(DEV_EXPRESS_SORT_SELECTOR)
            .asText();

        return dtoFieldsMap.getProperty(fieldName, fieldName);
    }

    private Sort createSortIstance(String fieldName, boolean isDesc) {

        if (isDesc)
            return new Sort(Sort.Direction.DESC, fieldName);
        else
            return new Sort(Sort.Direction.ASC, fieldName);
    }

}
