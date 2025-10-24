package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Link(null));
    }

    @Test
    public void constructor_invalidLink_throwsIllegalArgumentException() {
        String invalidLink = "";
        assertThrows(IllegalArgumentException.class, () -> new Link(invalidLink));
    }

    @Test
    public void isValidLink() {
        // null link
        assertThrows(NullPointerException.class, () -> Link.isValidLink(null));

        // blank or whitespace
        assertFalse(Link.isValidLink(""));
        assertFalse(Link.isValidLink(" "));

        // missing or invalid protocol
        assertFalse(Link.isValidLink("www.example.com"));
        assertFalse(Link.isValidLink("ftp://example.com"));

        // invalid domains
        assertFalse(Link.isValidLink("http://-example.com")); // starts with hyphen
        assertFalse(Link.isValidLink("http://example-.com")); // ends with hyphen
        assertFalse(Link.isValidLink("http://example.c")); // TLD too short

        // invalid characters in path
        assertFalse(Link.isValidLink("https://example.com/pa th")); // space
        assertFalse(Link.isValidLink("https://example.com/<script>")); // invalid symbols

        // valid links
        assertTrue(Link.isValidLink("http://example.com"));
        assertTrue(Link.isValidLink("https://example.com"));
        assertTrue(Link.isValidLink("https://sub.example.com"));
        assertTrue(Link.isValidLink("https://example.com:8080"));
        assertTrue(Link.isValidLink("https://example.com/path/to/resource"));
        assertTrue(Link.isValidLink("https://example.com/path?query=123#section")); // combined query + fragment
        assertTrue(Link.isValidLink("https://example.com/~user"));
        assertTrue(Link.isValidLink("https://example.com/path-with-dash"));
        assertTrue(Link.isValidLink("https://example.com/path.with.period"));
        assertTrue(Link.isValidLink("https://example.com/path_with_underscore"));
        assertTrue(Link.isValidLink("https://example.com/path+plus"));
    }

    @Test
    public void equals() {
        Link link = new Link("https://example.com");

        // same values -> returns true
        assertTrue(link.equals(new Link("https://example.com")));

        // same object -> returns true
        assertTrue(link.equals(link));

        // null -> returns false
        assertFalse(link.equals(null));

        // different types -> returns false
        assertFalse(link.equals(5.0f));

        // different values -> returns false
        assertFalse(link.equals(new Link("https://other.com")));
    }

    @Test
    public void constructor_trimsWhitespace() {
        Link link = new Link("  https://example.com  ");
        assertTrue(link.value.equals("https://example.com"));
    }

    @Test
    public void toString_returnsLinkValue() {
        Link link = new Link("https://example.com");
        assertTrue(link.toString().equals("https://example.com"));
    }

    @Test
    public void hashCode_sameValue_sameHash() {
        Link link1 = new Link("https://example.com");
        Link link2 = new Link("https://example.com");
        assertTrue(link1.hashCode() == link2.hashCode());
    }
}
