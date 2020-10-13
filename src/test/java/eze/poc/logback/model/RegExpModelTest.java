package eze.poc.logback.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class RegExpModelTest {

    private static final ModelConfidential MODEL_CONFIDENTIAL = new ModelConfidential();
    private static final ModelMisc MODEL_MISC = new ModelMisc();

    private static final String CONFIDENTIAL_SUFFIX = ">>> SHOULD BE MASKED: ";
    private static final String REX_EXP_CONFIDENTIAL_MATCHES = "(.*?)ssn=(.*?)|(.*?)address=(.*?)";

    private static final String HIDDEN_FIELD_MASK = "*hiddenField*";
    private static final String REG_EXP_CONFIDENTIAL_REPLACE = "ssn='(.*?)'|address='(.*?)'";

    static {
        MODEL_CONFIDENTIAL.setId(123L);
        MODEL_CONFIDENTIAL.setAddress(" Any Street ");
        MODEL_CONFIDENTIAL.setCountryId(" Any Country Id ");
        MODEL_CONFIDENTIAL.setSsn(" Any Security Number ");
        MODEL_CONFIDENTIAL.setOtherField(" Any Other Field ");

        MODEL_MISC.setKey(321);
        MODEL_MISC.setValue(" Any Value ");
    }

    @Test
    public void replaceAll_withModelHavingConfidentialFields_shouldReturnMaskedString() {
        // arrange
        final String input = MODEL_CONFIDENTIAL.toString();
        final String expected = "ModelConfidential[id=123, *hiddenField*, countryId=' Any Country Id ', *hiddenField*, otherField=' Any Other Field ']";

        // act
        final String actual = input.replaceAll(REG_EXP_CONFIDENTIAL_REPLACE, HIDDEN_FIELD_MASK);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void replaceAll_withModelMiscFields_shouldReturnMaskedString() {
        // arrange
        final String input = MODEL_MISC.toString();
        final String expected = "ModelMisc[key=321, value=' Any Value ']";

        // act
        final String actual = input.replaceAll(REG_EXP_CONFIDENTIAL_REPLACE, HIDDEN_FIELD_MASK);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void matches_withConfidential_shouldReturnTrue() {
        // arrange
        final String input = MODEL_CONFIDENTIAL.toString();
        final boolean expected = true;

        // act
        final boolean actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void matches_withMisc_shouldReturnFalse() {
        // arrange
        final String input = MODEL_MISC.toString();
        final boolean expected = false;

        // act
        final boolean actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void addSuffix_withConfidential_shouldStartWithSuffix() {
        // arrange
        final String input = MODEL_CONFIDENTIAL.toString();

        // act
        final String actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES) ? CONFIDENTIAL_SUFFIX.concat(input) : input;

        // assert
        assertThat(actual, startsWith(CONFIDENTIAL_SUFFIX));
    }

    @Test
    public void addSuffix_withMisc_shouldNotStartWithSuffix() {
        // arrange
        final String input = MODEL_MISC.toString();

        // act
        final String actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES) ? CONFIDENTIAL_SUFFIX.concat(input) : input;

        // assert
        assertThat(actual, not(startsWith(CONFIDENTIAL_SUFFIX)));
    }

}
