package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ConfirmableCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FlagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnflagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @AfterEach
    public void tearDown() {
        // clear pending confirmable commands between tests
        AddressBookParser.setPendingCommand(null);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        Command cmd = parser.parseCommand("find n/Alice Bob c/Singapore");
        assertTrue(cmd instanceof FindCommand);
    }

    @Test
    public void parseCommand_flag() throws Exception {
        FlagCommand command = (FlagCommand) parser.parseCommand(FlagCommand.COMMAND_WORD + " 1");
        assertEquals(new FlagCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unflag() throws Exception {
        UnflagCommand command = (UnflagCommand) parser.parseCommand(UnflagCommand.COMMAND_WORD + " 1");
        assertEquals(new UnflagCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addMeeting() throws Exception {
        // Example: "addm 1 m/20-10-2025 14:30"
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        Meeting meeting = new Meeting(meetingTime);
        AddMeetingCommand expectedCommand = new AddMeetingCommand(INDEX_FIRST_PERSON, meeting);

        AddMeetingCommand command = (AddMeetingCommand) parser.parseCommand(
                AddMeetingCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_MEETING + "20-10-2025 14:30");

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_deleteMeeting() throws Exception {
        // Example: "deletem 1 m/20-10-2025 14:30"
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        DeleteMeetingCommand expectedCommand = new DeleteMeetingCommand(INDEX_FIRST_PERSON, meetingTime);

        DeleteMeetingCommand command = (DeleteMeetingCommand) parser.parseCommand(
                DeleteMeetingCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_MEETING + "20-10-2025 14:30");

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_confirmationFlow_cancelsOnNonY() throws Exception {
        Person duplicate = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(duplicate);
        AddressBookParser.setPendingCommand(addCommand);

        assertThrows(ParseException.class, () -> parser.parseCommand("n"));
    }

    @Test
    public void parseCommand_pendingCommandNull_yThrowsException() {
        AddressBookParser.setPendingCommand(null);
        assertThrows(ParseException.class, () -> parser.parseCommand("y"));
    }

    @Test
    public void setPendingCommand_executesAndClears() throws Exception {
        ConfirmableCommand stub = new ConfirmableCommand(false) {
            @Override
            public ConfirmableCommand withConfirmed() {
                return this; // for this test, we can return itself
            }

            @Override
            public CommandResult execute(Model model) {
                return new CommandResult("stub executed");
            }

        };

        AddressBookParser.setPendingCommand(stub);

        Command result = parser.parseCommand("y");
        assertNotNull(result, "Expected a Command instance, but got null");
        assertEquals("stub executed", result.execute(null).getFeedbackToUser());
        assertThrows(ParseException.class, () -> parser.parseCommand("y"));
    }
}
