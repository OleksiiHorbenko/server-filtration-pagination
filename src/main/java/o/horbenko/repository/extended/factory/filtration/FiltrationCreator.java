package o.horbenko.repository.extended.factory.filtration;


import o.horbenko.repository.extended.filtration.specification.executor.PolishInverseNotationSpecificationExecutor;
import o.horbenko.repository.extended.filtration.translator.PolishInverseNotationTranslator;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Properties;

public class FiltrationCreator {

    private PolishInverseNotationTranslator translator;
    private PolishInverseNotationSpecificationExecutor executor;
    private FiltrationPreparator preparator;

    public FiltrationCreator(PolishInverseNotationTranslator translator,
                             PolishInverseNotationSpecificationExecutor executor,
                             FiltrationPreparator preparator) {

        this.translator = translator;
        this.executor = executor;
        this.preparator = preparator;
    }

    public <DomainType> Specification<DomainType> createFiltrationJpaSpecificatio(String rawInput) {

        List<String> preparedInput = preparator.prepareRawInputString(rawInput);
        List<Object> translatedPolishInverseNotation = translator.translate(preparedInput);

        return executor.executePolishInverseNotation(translatedPolishInverseNotation);
    }

    public <DomainType> Specification<DomainType> createFiltrationJpaSpecificatio(String rawInput,
                                                                                  Properties dtoFieldsMap) {

        List<String> preparedInput = preparator.prepareRawInputString(rawInput);
        List<Object> translatedPolishInverseNotation = translator.translate(preparedInput);

        return executor.executePolishInverseNotation(translatedPolishInverseNotation, dtoFieldsMap);
    }
}
