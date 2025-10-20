package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's link in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class Link {

    public static final String MESSAGE_CONSTRAINTS =
            "Links should be a valid URL starting with http:// or https://, contain no spaces, "
                    + "and can be any social or professional link (LinkedIn, Indeed, personal website, etc.)";

    // Accepts http:// or https:// followed by any non-space characters
    public static final String VALIDATION_REGEX = "https?://\\S+";

    public final String value;

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
