package o.horbenko.repository.extended.filtration.translator;

import o.horbenko.repository.extended.constants.PriorityTable;
import o.horbenko.repository.extended.exceptions.translator.PolishInverseNotationTranslationException;
import org.springframework.stereotype.Component;

import java.util.List;

import static o.horbenko.repository.extended.exceptions.ErrMessageConstants.ERR_POLIN_TRANSLATOR;

@Component
public class PolishInverseNotationTranslator {


    public List<Object> translate(List<String> input) {
        try {

            TemporaryResultAndStack temporaryResultAndStack =
                new TemporaryResultAndStack();

            for (String lexem : input)
                collectLexem(lexem, temporaryResultAndStack);

            temporaryResultAndStack.pushOutAllFromStack();
            return temporaryResultAndStack.getResult();


        } catch (Exception e) {
            throw new PolishInverseNotationTranslationException(ERR_POLIN_TRANSLATOR, e);
        }
    }

    private void collectLexem(String lexem, TemporaryResultAndStack temporaryResultAndStack) {

        if (isOperator(lexem))
            temporaryResultAndStack.pushOutIfNeedAndPush(lexem);
        else
            temporaryResultAndStack.add(lexem);
    }

    private boolean isOperator(String lexemToCheck) {
        return PriorityTable.isOperator(lexemToCheck);
    }

}
