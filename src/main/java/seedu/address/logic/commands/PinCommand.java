package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Pins a person identified using their displayed index from the address book.
 * Pinned persons will appear at the top of the contact list.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_PINNED = "This person is already pinned.";

    private final Index targetIndex;

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());

        if (personToPin.isPinned()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_PINNED);
        }

        Person pinnedPerson = new Person(
                personToPin.getName(),
                personToPin.getPhone(),
                personToPin.getTelegramHandle(),
                personToPin.getEmail(),
                personToPin.getAddress(),
                personToPin.getTags(),
                personToPin.getNote(),
                personToPin.getLogs(),
                true
        );

        model.saveAddressBookState();
        model.setPerson(personToPin, pinnedPerson);
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, Messages.format(pinnedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand otherPinCommand = (PinCommand) other;
        return targetIndex.equals(otherPinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
