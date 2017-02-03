package o.horbenko.repository.extended.factory;

import o.horbenko.repository.extended.factory.filtration.FiltrationCreator;
import o.horbenko.repository.extended.factory.filtration.FiltrationPreparator;
import o.horbenko.repository.extended.factory.sorting.SortingCreator;
import o.horbenko.repository.extended.filtration.specification.executor.PolishInverseNotationSpecificationExecutor;
import o.horbenko.repository.extended.filtration.translator.PolishInverseNotationTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.PostPersist;

@Component
public class FiltrationAndSortingAbstractFactory {

    @Inject
    private PolishInverseNotationTranslator translator;
    @Inject
    private PolishInverseNotationSpecificationExecutor executor;

    @Inject
    @Qualifier("devExpSortingCreator")
    private SortingCreator devExpressSortingCreator;
    private FiltrationCreator devExpFiltrationCreator;

    @PostPersist
    @Autowired
    protected void instantiateDevExpFiltrationCreator(
        @Qualifier("devExpFiltrationPreparator") FiltrationPreparator devExpFiltrationPreparator) {

        this.devExpFiltrationCreator = new FiltrationCreator(translator, executor, devExpFiltrationPreparator);
    }

    public FiltrationCreator getSuitableFiltrationCreator(GridTypeEnum gridType) {

        switch (gridType) {
            case DEV_EXPRESS:
                return devExpFiltrationCreator;

            default:
                return devExpFiltrationCreator;
        }
    }


    public SortingCreator getSuitableSortingCreator(GridTypeEnum gridType) {

        switch (gridType) {
            case DEV_EXPRESS:
                return devExpressSortingCreator;

            default:
                return devExpressSortingCreator;
        }
    }

}
