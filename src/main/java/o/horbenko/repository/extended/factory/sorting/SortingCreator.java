package o.horbenko.repository.extended.factory.sorting;


import org.springframework.data.domain.Sort;

import java.util.Properties;

public interface SortingCreator {

    Sort createJpaSortingFrom(String initJson, Properties dtoFieldsMap);

}
