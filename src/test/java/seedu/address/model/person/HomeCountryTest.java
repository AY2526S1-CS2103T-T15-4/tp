package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HomeCountryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HomeCountry(null));
    }

    @Test
    public void constructor_invalidCountry_throwsIllegalArgumentException() {
        String invalidCountry = "";
        assertThrows(IllegalArgumentException.class, () -> new HomeCountry(invalidCountry));
    }

    @Test
    public void isValidCountry() {
        // null country
        assertThrows(NullPointerException.class, () -> HomeCountry.isValidCountry(null));

        // invalid countries
        assertFalse(HomeCountry.isValidCountry("")); // empty string
        assertFalse(HomeCountry.isValidCountry(" ")); // spaces only

        // valid countries
        assertTrue(HomeCountry.isValidCountry("Singapore"));
        assertTrue(HomeCountry.isValidCountry("-")); // one character
        assertTrue(HomeCountry.isValidCountry("The United Kingdom of Great Britain and Northern Ireland"));
        // long country
    }

    @Test
    public void equals() {
        HomeCountry country = new HomeCountry("Valid Country");

        // same values -> returns true
        assertTrue(country.equals(new HomeCountry("Valid Country")));

        // same object -> returns true
        assertTrue(country.equals(country));

        // null -> returns false
        assertFalse(country.equals(null));

        // different types -> returns false
        assertFalse(country.equals(5.0f));

        // different values -> returns false
        assertFalse(country.equals(new HomeCountry("Other Valid Country")));
    }

    @Test
    public void toString_shouldReturnValue() {
        HomeCountry country = new HomeCountry("Singapore");
        assertTrue(country.toString().equals("Singapore"));
    }

    @Test
    public void hashCode_shouldMatchValueHashCode() {
        HomeCountry country = new HomeCountry("Singapore");
        assertTrue(country.hashCode() == "Singapore".hashCode());
    }
}
