package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's link in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class Link {

    public static final String MESSAGE_CONSTRAINTS = "Links should adhere to the following constraints:\n"
            + "1. Must start with 'http://' or 'https://'.\n"
            + "2. The domain name must consist of alphanumeric characters and hyphens, "
            + "and end with a top-level domain (TLD) of at least 2 letters (e.g., '.com', '.sg').\n"
            + "3. May optionally include a port number (e.g., ':8080').\n"
            + "4. May include an optional path, query, or fragment, containing common URL-safe characters.";
    public static final String VALIDATION_REGEX;

    private static final String URL_SAFE_CHARACTERS = "\\w\\-._~:/?#@!$&'()*+,;=%";
    private static final String PROTOCOL_REGEX = "https?://";
    private static final String DOMAIN_LABEL = "[A-Za-z0-9]([A-Za-z0-9-]*[A-Za-z0-9])?";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_LABEL + "\\.)+[A-Za-z]{2,}";
    private static final String PORT_REGEX = "(:\\d{1,5})?";
    private static final String PATH_REGEX = "([/" + URL_SAFE_CHARACTERS + "]*)?";

    public final String value;

    static {
        VALIDATION_REGEX = PROTOCOL_REGEX + DOMAIN_REGEX + PORT_REGEX + PATH_REGEX;
    }

    /**
     * Constructs an {@code Link}.
     *
     * @param link A valid link string.
     */
    public Link(String link) {
        requireNonNull(link);
        link = link.trim();
        checkArgument(isValidLink(link), MESSAGE_CONSTRAINTS);
        value = link;
    }

    /**
     * Returns if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
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
        if (!(other instanceof Link)) {
            return false;
        }

        Link otherLink = (Link) other;
        return value.equals(otherLink.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
