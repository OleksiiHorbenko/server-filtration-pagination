package o.horbenko.repository.extended.filtration.translator;

import o.horbenko.repository.extended.constants.PriorityTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static o.horbenko.repository.extended.constants.OperatorConstants.*;

public class TemporaryResultAndStack {

    private List<Object> result;
    private Stack<String> stack;

    TemporaryResultAndStack() {
        result = new ArrayList<>();
        stack = new Stack<>();
    }


    void pushOutIfNeedAndPush(String operator) {

        if (operator.equals(INNER_OBJECT_START)) {
            stack.push(operator);
            return;
        }

        if (operator.equals(INNER_OBJECT_END)) {
            pushOutAllUtilOpenBracket();
            return;
        }

        while (!stack.empty() && isNeedToPushOut(operator)) {
            result.add(stack.pop());
        }

        stack.push(operator);
    }

    void pushOutAllFromStack() {
        while(!stack.isEmpty())
            result.add(stack.pop());
    }

    private void pushOutAllUtilOpenBracket() {

        while (!stack.empty() && !isPeekEquals(INNER_OBJECT_START)) {
            result.add(stack.pop());
        }

        stack.pop();
    }


//    SECONDARY

    private boolean isPeekEquals(String eqWith) {
        return stack.peek().equals(eqWith);
    }

    private boolean isNeedToPushOut(String operator) {
        return PriorityTable.comparePriority(stack.peek(), operator) >= 0 ||
            operator.equals(INNER_OBJECT_END);
    }

    public void push(Object lexemPushTo) {
        this.stack.push(lexemPushTo.toString());
    }

    public Object pop() {
        return this.stack.pop();
    }

    public void add(Object toAdd) {
        this.result.add(toAdd);
    }

    public List<Object> getResult() {
        return result;
    }

}
