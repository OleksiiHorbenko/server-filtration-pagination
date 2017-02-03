package o.horbenko.repository.extended.filtration.specification.executor;

import o.horbenko.repository.extended.constants.PriorityTable;
import o.horbenko.repository.extended.containers.FilterCriteriaHolder;
import o.horbenko.repository.extended.exceptions.specification.PolishInverseNotationExecutionException;
import o.horbenko.repository.extended.filtration.specification.CustomJpaSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

import static o.horbenko.repository.extended.constants.OperatorConstants.AND;
import static o.horbenko.repository.extended.constants.OperatorConstants.LOGIC_OPERATORS;
import static o.horbenko.repository.extended.constants.OperatorConstants.OR;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_CANT_EXECUTE_POLIN;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_UNKNOWN_LOGIC_OPERATOR;
import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_UNKNOWN_OPERATOR;

@Component
public class PolishInverseNotationSpecificationExecutor {

    private final Properties EMPTY_PROPERTIES = new Properties();

    public <DomainType> Specification<DomainType> executePolishInverseNotation(List<Object> polishInverseNotationParts) {
        return executePolishInverseNotation(polishInverseNotationParts, EMPTY_PROPERTIES);
    }

    /**
     * Transforms polish inverse notation sequence to JPA Specification instance
     *
     * @param polishInverseNotationParts - Polish inverse notation sequence
     * @param dtoMapProperties           - mapper poperties
     * @return created JPA Specification entity
     * */
    public <DomainType> Specification<DomainType> executePolishInverseNotation(List<Object> polishInverseNotationParts,
                                                                               Properties dtoMapProperties) {
        try {

            TemporaryCollections<DomainType> temporaryCollectionsRef =
                new TemporaryCollections<DomainType>(polishInverseNotationParts, dtoMapProperties);

            executePolishInverseNotationByRef(temporaryCollectionsRef);     // changes temporaryCollections by reference

//        * when execution ends, input queue and operand stack must be empty
//        * specification stack must contains ONLY one Specification - result

            throwIfResultHasExcessValues(temporaryCollectionsRef);

            Specification<DomainType> result =
                temporaryCollectionsRef.popFromSpecificationStack();

            temporaryCollectionsRef.setToNullInnerRefs();
            temporaryCollectionsRef = null;

            return result;

        } catch (Exception e) {
            throw new PolishInverseNotationExecutionException(ERR_CANT_EXECUTE_POLIN, e);
        }
    }


    private <DomainType> void executePolishInverseNotationByRef(TemporaryCollections<DomainType> temporaryCollections) {

//        for each element in polish inverse notation, po = Polish Inverse Notation
        while (!temporaryCollections.ponQueue().isEmpty())
            resolveQueueHead(temporaryCollections);
    }

    private <DomainType> void resolveQueueHead(TemporaryCollections<DomainType> temporaryCollections) {

        Object current = temporaryCollections.ponQueue().poll();

        if (PriorityTable.isOperator(current.toString())) {
            String logicOperator = current.toString();

            resolveOperator(temporaryCollections, logicOperator);

        } else {
            temporaryCollections.operandStack().push(current);
        }
    }


    private <DomainType> void resolveOperator(TemporaryCollections<DomainType> temporaryCollections,
                                              String logicOperator) {

        if (LOGIC_OPERATORS.contains(logicOperator))       // if AND or OR - need to collapse top specifications in temporaryCollections.specificationStack
            temporaryCollections.specificationStack().push(
                collectSpecificationWithTwoOperands(logicOperator, temporaryCollections));
        else
            temporaryCollections.specificationStack().push( // simple predicate, like ["doo", "<", 357]
                createPredicateWithTwoOperands(logicOperator, temporaryCollections));

    }


    private <DomainType> Specification<DomainType> createPredicateWithTwoOperands(String logicOperator,
                                                                                  TemporaryCollections<DomainType> temporaryCollections) {

        FilterCriteriaHolder<?> filterCriteriaHolder = new FilterCriteriaHolder<>()
            .value(temporaryCollections.operandStack().pop())
            .field(temporaryCollections.popMappedFieldNameFromOperandStack())
            .operator(logicOperator);

        return new CustomJpaSpecification<>(filterCriteriaHolder);
    }

    private <DomainType> Specification<DomainType> collectSpecificationWithTwoOperands(String logicOperator,
                                                                                       TemporaryCollections<DomainType> temporaryCollections) {
        Specification<DomainType> secondOperand = temporaryCollections
            .specificationStack().pop();

        Specification<DomainType> firstOperand = temporaryCollections
            .specificationStack().pop();

        return createSpecificationWith(firstOperand, secondOperand, logicOperator);
    }

    private <DomainType> Specification<DomainType> createSpecificationWith(Specification<DomainType> firstOperands,
                                                                           Specification<DomainType> secondOperand,
                                                                           String logicOperator) {
        switch (logicOperator) {

            case AND:
                return Specifications.where(firstOperands).and(secondOperand);
            case OR:
                return Specifications.where(firstOperands).or(secondOperand);

            default:
                throw new PolishInverseNotationExecutionException(
                    ERR_UNKNOWN_LOGIC_OPERATOR, ERR_UNKNOWN_OPERATOR, logicOperator);
        }
    }

    private void throwIfResultHasExcessValues(TemporaryCollections temporaryCollections) {
        if (temporaryCollections.hasExcessValues())
            throw new PolishInverseNotationExecutionException(ERR_CANT_EXECUTE_POLIN);
    }

}
