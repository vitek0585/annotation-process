package victor.applyform;

import java.util.ArrayList;
import java.util.List;

class Validator<T> {

    private List<Rule<T>> rule;

    public Validator() {
        rule = new ArrayList<>();
    }

    public Validator addRule(Rule<T> rule) {
        this.rule.add(rule);
        return this;
    }

    public boolean isValid(T v, IValidationError cb) {
        for (Rule rule1 : rule) {
            if (!rule1.validate(v)) {
                cb.error(rule1.getMsg());
                return false;}
        }

        return true;
    }

    interface IValidationError{
        void error(String msg);
    }
}
