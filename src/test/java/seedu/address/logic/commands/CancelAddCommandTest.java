package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModelStubs;

/**
 * Unit tests for CancelAddCommand.
 */
public class CancelAddCommandTest {

    @Test
    void execute_withModel_returnsCancellationMessageAndDoesNotMutateModel() throws Exception {
        ModelStubs.ModelStub modelStub = new ModelStubs.ModelStub() { /* no overrides */ };

        CancelAddCommand cmd = new CancelAddCommand();
        CommandResult result = cmd.execute(modelStub);

        assertEquals("Add Command cancelled.", result.getFeedbackToUser());
    }

    @Test
    void execute_nullModel_throwsNullPointerException() {
        CancelAddCommand cmd = new CancelAddCommand();
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    void equals_twoInstances_areEqual() {
        CancelAddCommand a = new CancelAddCommand();
        CancelAddCommand b = new CancelAddCommand();

        assertEquals(a, b);
        assertEquals(b, a);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
    }

    @Test
    void toString_notEmpty() {
        CancelAddCommand cmd = new CancelAddCommand();
        String s = cmd.toString();
        assertNotNull(s);
        assertFalse(s.isEmpty());
    }
}
