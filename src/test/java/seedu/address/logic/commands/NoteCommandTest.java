package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class NoteCommandTest {
    private static final String NOTE_STUB = "Some note";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNoteUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote(NOTE_STUB).build();

        NoteCommand remarkCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().value));

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNoteUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNote("").build();

        NoteCommand remarkCommand = new NoteCommand(INDEX_FIRST_PERSON,
                new Note(editedPerson.getNote().toString()));

        String expectedMessage = String.format(NoteCommand.MESSAGE_DELETE_NOTE_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNote(NOTE_STUB).build();

        NoteCommand remarkCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(editedPerson.getNote().value));

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB));

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NoteCommand remarkCommand = new NoteCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB));
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteNoteWhenPersonHasNoNote_failure() {
        // Choose a person who initially has no note (third person in TypicalPersons: CARL)
        NoteCommand deleteNoteCommand = new NoteCommand(INDEX_THIRD_PERSON, new Note(""));
        assertCommandFailure(deleteNoteCommand, model, NoteCommand.MESSAGE_NO_NOTE);
    }

    @Test
    public void equals() {
        final NoteCommand standardCommand = new NoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_AMY);

        // same values -> returns true
        NoteCommand commandWithSameValues = new NoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(true)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_SECOND_PERSON, VALID_NOTE_AMY)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_BOB)));
    }

}
