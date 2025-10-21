package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Updates the link of an existing person in the address book.
 */
public class LinkCommand {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates or removes the link of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[LINK]\n"
            + "Example to add/update: " + COMMAND_WORD + " 1 https://linkedin.com/in/example\n"
            + "Example to remove: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_LINK_SUCCESS = "Added/Updated link for person: %1$s";
    public static final String MESSAGE_REMOVE_LINK_SUCCESS = "Removed link from person: %1$s";
    public static final String MESSAGE_NO_CHANGE = "Nothing to update, fields are already set to provided values";
}
