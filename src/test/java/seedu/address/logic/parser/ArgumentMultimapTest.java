package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import org.junit.jupiter.api.Test;

public class ArgumentMultimapTest {

    @Test
    public void equals() {
        // same instance -> true
        ArgumentMultimap a = new ArgumentMultimap();
        a.put(PREFIX_NAME, "Alice");
        assertTrue(a.equals(a));

        // null and different type -> false
        assertFalse(a.equals(null));
        assertFalse(a.equals("not a map"));

        // same content -> true
        ArgumentMultimap b = new ArgumentMultimap();
        b.put(PREFIX_NAME, "Alice");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        // different prefix -> false
        ArgumentMultimap c = new ArgumentMultimap();
        c.put(PREFIX_PHONE, "Alice"); // different prefix, same string
        assertFalse(a.equals(c));
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        ArgumentMultimap x = new ArgumentMultimap();
        x.put(PREFIX_NAME, "Alice");
        ArgumentMultimap y = new ArgumentMultimap();
        y.put(PREFIX_NAME, "Alice");

        assertTrue(x.equals(y));
        assertEquals(x.hashCode(), y.hashCode());
    }
}
