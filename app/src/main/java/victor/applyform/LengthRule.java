package victor.applyform;

public class LengthRule extends Rule<String> {

    private final int _min;
    private final int _max;

    public LengthRule(String msg, int min, int max) {
        super(msg);
        _min = min;
        _max = max;
    }

    @Override
    boolean validate(String text) {
        return text.length() > _min && text.length() < _max;
    }
}
