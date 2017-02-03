package o.horbenko.repository.extended.filtration.specification.executor;


import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class TemporaryCollections<DomainType> {

    private Properties dtoFieldMap;
    private Queue<Object> ponQueue; //polish inverse notation parts queue
    private Stack<Object> operandStack;
    private Stack<Specification<DomainType>> specificationStack;

    public TemporaryCollections(List<Object> polishInverseNotationParts, Properties dtoFieldMap) {
        this.dtoFieldMap = dtoFieldMap;
        ponQueue = new ArrayDeque<>(polishInverseNotationParts);
        operandStack = new Stack<>();
        specificationStack = new Stack<>();
    }

    Queue<Object> ponQueue() {
        return this.ponQueue;
    }

    Stack<Specification<DomainType>> specificationStack() {
        return this.specificationStack;
    }

    Specification<DomainType> popFromSpecificationStack() {
        if (specificationStack.isEmpty()) return null;

        return specificationStack.pop();
    }

    String popMappedFieldNameFromOperandStack() {
        String fieldName = operandStack.pop().toString();
        return dtoFieldMap.getProperty(fieldName, fieldName);
    }

    Stack<Object> operandStack() {
        return this.operandStack;
    }

    void setToNullInnerRefs() {
        ponQueue = null;
        operandStack = null;
        specificationStack = null;
    }

    boolean hasExcessValues() {
        return !ponQueue.isEmpty() ||
            !operandStack.isEmpty() ||
            specificationStack.size() > 1;
    }

}
