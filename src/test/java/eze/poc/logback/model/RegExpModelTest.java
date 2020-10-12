package eze.poc.logback.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class RegExpModelTest {

    private static final String REG_EXP_CONFIDENTIAL_REPLACE = "ssn='(.*?)'|address='(.*?)'";
    private static final String REX_EXP_CONFIDENTIAL_MATCHES = "(.*?)ssn=(.*?)|(.*?)address=(.*?)";
    private static final String CONFIDENTIAL_SUFFIX = ">>> SHOULD BE MASKED: ";

    @Test
    public void replaceAll_withModelHavingConfidentialFields_shouldReturnMaskedString() {
        // arrange
        final String input = buildConfidentialModel(1).toString();
        final String expected = "ModelWithConfidential[id=1, *hiddenField*, countryId=' Any Country Id ', *hiddenField*, otherField=' Any Other Field ']";

        // act
        final String actual = input.replaceAll(REG_EXP_CONFIDENTIAL_REPLACE, "*hiddenField*");

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void replaceAll_withModelMiscFields_shouldReturnMaskedString() {
        // arrange
        final String input = buildMiscModel(2).toString();
        final String expected = "ModelMisc[key=2, value=' Any Value ']";

        // act
        final String actual = input.replaceAll(REG_EXP_CONFIDENTIAL_REPLACE, "*hiddenField*");

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void matches_withConfidential_shouldReturnTrue() {
        // arrange
        final String input = buildConfidentialModel(123).toString();
        final boolean expected = true;

        // act
        final boolean actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void matches_withMisc_shouldReturnFalse() {
        // arrange
        final String input = buildMiscModel(123).toString();
        final boolean expected = false;

        // act
        final boolean actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES);

        // assert
        assertThat(actual, is(expected));
    }

    @Test
    public void addSuffix_withConfidential_shouldStartWithSuffix() {
        // arrange
        final String input = buildConfidentialModel(123).toString();

        // act
        final String actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES) ? CONFIDENTIAL_SUFFIX.concat(input) : input;

        // assert
        assertThat(actual, startsWith(">>> SHOULD BE MASKED: "));
    }

    @Test
    public void addSuffix_withMisc_shouldNotStartWithSuffix() {
        // arrange
        final String input = buildMiscModel(123).toString();

        // act
        final String actual = input.matches(REX_EXP_CONFIDENTIAL_MATCHES) ? CONFIDENTIAL_SUFFIX.concat(input) : input;

        // assert
        assertThat(actual, not(startsWith(">>> SHOULD BE MASKED: ")));
    }

    private ModelConfidential buildConfidentialModel(final int id) {
        final ModelConfidential model = new ModelConfidential();
        model.setId(id);
        model.setCountryId(" Any Country Id ");
        model.setSsn(" Any Security Number ");
        model.setAddress(" Any Street ");
        model.setOtherField(" Any Other Field ");
        return model;
    }

    private ModelMisc buildMiscModel(final int key) {
        final ModelMisc model = new ModelMisc();
        model.setKey(key);
        model.setValue(" Any Value ");
        return model;
    }
}
