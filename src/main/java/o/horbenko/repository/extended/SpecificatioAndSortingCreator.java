package o.horbenko.repository.extended;

import o.horbenko.repository.extended.containers.SpecificationAndSorting;
import o.horbenko.repository.extended.factory.FiltrationAndSortingAbstractFactory;
import o.horbenko.repository.extended.factory.GridTypeEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Properties;

@Component
public class SpecificatioAndSortingCreator {

    private final Properties EMPLTY_MAPPER_PROPERTIES = new Properties();

    @Inject
    private FiltrationAndSortingAbstractFactory filtrationAndSortingFactory;

    public <DomainType> SpecificationAndSorting<DomainType> createSpecificationsAndSorting(String inputParamsAsString,
                                                                                           GridTypeEnum callerTable) {

        Specification<DomainType> specification = filtrationAndSortingFactory
            .getSuitableFiltrationCreator(callerTable)
            .createFiltrationJpaSpecificatio(inputParamsAsString);

        Sort sorting = filtrationAndSortingFactory
            .getSuitableSortingCreator(callerTable)
            .createJpaSortingFrom(inputParamsAsString, EMPLTY_MAPPER_PROPERTIES);

        return new SpecificationAndSorting<>(specification, sorting);
    }

    public <DomainType> SpecificationAndSorting<DomainType> createSpecificationsAndSorting(String inputParamsAsString,
                                                                                           Properties dtoFieldsMap,
                                                                                           GridTypeEnum callerTable) {

        Specification<DomainType> specification = filtrationAndSortingFactory
            .getSuitableFiltrationCreator(callerTable)
            .createFiltrationJpaSpecificatio(inputParamsAsString, dtoFieldsMap);

        Sort sorting = filtrationAndSortingFactory
            .getSuitableSortingCreator(callerTable)
            .createJpaSortingFrom(inputParamsAsString, dtoFieldsMap);

        return new SpecificationAndSorting<>(specification, sorting);
    }

}
