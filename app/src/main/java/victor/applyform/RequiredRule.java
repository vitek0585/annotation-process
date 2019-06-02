package victor.applyform;

public class RequiredRule extends Rule<String> {

    public RequiredRule(String msg) {
        super(msg);
    }

    @Override
    boolean validate(String text) {
        return text != null;
    }
}
