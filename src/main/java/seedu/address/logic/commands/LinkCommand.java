package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;

/**
 * Updates the link of an existing person in the address book.
 */
public class LinkCommand extends Command {
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates or removes the link of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[LINK]\n"
            + "Example to add/update: " + COMMAND_WORD + " 1 https://linkedin.com/in/example\n"
            + "Example to remove: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_LINK_SUCCESS = "Added link for person: %1$s";
    public static final String MESSAGE_UPDATE_LINK_SUCCESS = "Updated link for person: %1$s";
    public static final String MESSAGE_REMOVE_LINK_SUCCESS = "Removed link from person: %1$s";
    public static final String MESSAGE_NO_CHANGE = "Nothing to update, fields are already set to provided values";

    private final Index index;
    private final Link link;

    /**
     * @param index of the person in the filtered person list to update/remove the link
     * @param link link to update person with, null means remove link
     */
    public LinkCommand(Index index, Link link) {
        requireNonNull(index);

        this.index = index;
        this.link = link;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getCountry(),
                personToEdit.getCompany(),
                personToEdit.getTags(),
                personToEdit.isFlagged(),
                personToEdit.getMeetings(),
                link
        );

        if (editedPerson.equals(personToEdit)) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }

        model.setPerson(personToEdit, editedPerson);

        String resultMessage;

        if (link == null) {
            resultMessage = String.format(MESSAGE_REMOVE_LINK_SUCCESS, Messages.format(editedPerson));
        } else if (personToEdit.getLink() == null) {
            resultMessage = String.format(MESSAGE_ADD_LINK_SUCCESS, Messages.format(editedPerson));
        } else {
            resultMessage = String.format(MESSAGE_UPDATE_LINK_SUCCESS, Messages.format(editedPerson));
        }

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherLinkCommand = (LinkCommand) other;
        return index.equals(otherLinkCommand.index)
                && (link == null ? otherLinkCommand.link == null : link.equals(otherLinkCommand.link));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("link", link)
                .toString();
    }
}
