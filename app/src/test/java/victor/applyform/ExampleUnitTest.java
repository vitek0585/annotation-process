package victor.applyform;

import android.view.animation.ScaleAnimation;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        Object f = (ScaleAnimation)null;
        Object f1 = (Comparable)f;
        Validator<String> validator = new Validator<String>()
                .addRule(new RequiredRule("Cann't be empty"))
                .addRule(new LengthRule("The length should at least", 2, 4));

        boolean isValid = validator.isValid("qw", System.out::println);
    }
}