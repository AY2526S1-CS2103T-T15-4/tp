package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's country in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCountry(String)}
 */
public class HomeCountry {

    public static final String MESSAGE_CONSTRAINTS = "Countries can take any values, and it should not be blank";

    /*
     * The first character of the country must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code HomeCountry}.
     *
     * @param country A valid country.
     */
    public HomeCountry(String country) {
        requireNonNull(country);
        checkArgument(isValidCountry(country), MESSAGE_CONSTRAINTS);
        value = country;
    }

    /**
     * Returns true if a given string is a valid country.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HomeCountry otherCountry)) {
            return false;
        }

        return value.equals(otherCountry.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
