package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid companies
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only

        // valid companies
        assertTrue(Company.isValidCompany("Shopee"));
        assertTrue(Company.isValidCompany("-")); // one character
        assertTrue(Company.isValidCompany("Newark Museum of Art Consumers Limited Warranty To Good Housekeeping"));
        // long company
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company")));
    }

    @Test
    public void toString_shouldReturnValue() {
        Company company = new Company("Shopee");
        assertTrue(company.toString().equals("Shopee"));
    }

    @Test
    public void hashCode_shouldMatchValueHashCode() {
        Company company = new Company("Shopee");
        assertTrue(company.hashCode() == "Shopee".hashCode());
    }

}