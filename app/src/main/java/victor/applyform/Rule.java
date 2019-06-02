package victor.applyform;

abstract class Rule<T> {
    private final String _msg;

    protected Rule(String msg) {
        _msg = msg;
    }

    abstract boolean validate(T value);

    public String getMsg() {
        return _msg;
    }
}
