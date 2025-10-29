---
layout: page
title: User Guide
---

## LinkedUp User Guide

LinkedUp is a **desktop app for university student leaders to manage contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your LinkedUp.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar linkedup.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/Takoyaki Vendor p/98765432 e/takoyakis@gmail.com a/Jane Street, block 24000, #01-01` : Adds a contact named `Takoyaki Vendor` to LinkedUp.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div style="border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip: A person can have any number of tags (including 0) </strong>
</div>

<div style="margin-top:15px; border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip 2: The command line will prompt the correct format.</strong>
</div>


Example:
* `add n/Hwang Dowon p/91897095 e/e12345678@nus.edu.sg a/Elm College, 10 College Ave t/computing t/dodgeball`

![Add](images/Add.png)

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX​`

* Autofills edit command for person at index without executing. The index **must be a positive integer** 1, 2, 3, …​
* Make changes and press enter again to confirm edits

Example:
* `edit 7`

The command line will autofill the person in index 7 as shown below

![Edit](images/Edit.png)

<div style="margin-top:5px; border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip: Use this shortcut to save time and ensure correct syntax </strong>
</div>

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Adding a note to a person : `note`

Adds or edits a freeform note for an existing person in the address book. The note will be displayed in the person info panel.

Format: `note INDEX note/[NOTE]`

* Adds or edits the note of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The note can contain any text and will be visible in the person info panel.
* To remove a note, use `note INDEX note/` without specifying any text after `note/`.
* Existing notes will be overwritten by the new note.

Examples:
* `note 1 note/Likes to swim.` Adds the note "Likes to swim." to the 1st person.
* `note 2 note/Prefers email communication` Adds a note about communication preference to the 2nd person.
* `note 1 note/` Removes the note from the 1st person.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Fuzzy matching is supported: minor typos are tolerated (e.g. \`find Jhn\` will match \`John\`).
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find mario` returns `Mario Wong` and `Maria`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Filtering persons by tags: `filter`

Filters persons who has any of the given tags.

Format: `filter TAG [MORE_TAG]`

* The search is case-insensitive. e.g `friends` will match `Friends`
* The order of the keywords does not matter. e.g. `friends colleagues` will match contacts with either tag
* Only the tags are searched.
* Only exact tag matches will be matched e.g. `friend` will not match `friends`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `filter friends colleagues` will return contacts tagged with `friends` or `colleagues` or both

Examples:
* `filter friends` returns all contacts tagged with `friends`
* `filter friends colleagues` returns all contacts tagged with either `friends` or `colleagues`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Pinning a contact : `pin`

Pins the specified contact to keep them at the top of the contact list.

Format: `pin INDEX`

* Pins the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Pinned contacts will always appear at the top of the list, even when sorting or filtering.

Examples:
* `list` followed by `pin 2` pins the 2nd person in the address book.
* `find Betsy` followed by `pin 1` pins the 1st person in the results of the `find` command.

### Unpinning a contact : `unpin`

Unpins the specified contact, removing it from the pinned position.

Format: `unpin INDEX`

* Unpins the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The person must already be pinned.

Examples:
* `list` followed by `unpin 1` unpins the 1st person in the address book.

### Undoing a command : `undo`

* Undo last action
* Reverts the effects of the following commands:
    * Add
    * Delete
    * Edit
    * Clear
    * Pin
    * Unpin

<div style="border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip: Useful for any unintentional mistakes </strong>
</div>

### Redoing a command : `redo`

* Redo last action, reverting state of application to that before previous undo
* Redo history will be cleared when commands that edit the state of the data are made

### Importing a save file : `import`

Imports the details stored in the specified file

Format: `import f/FILE`
<div style="border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip: Use relative addresses for easier referencing! </strong>
</div>

* Either a `.json` or a `.csv` file can be used.

Example:
* Importing a json file: `import f/data/addressbook.json`
* Importing a csv file: `import f/data/addressbook.csv`

### Exporting your save file to `.json` or `.csv` : `export`

Exports your saved data to a `.json` or `.csv` file.

Format: `export f/FILE [t/TAG]...`

Options:
* `FILE`: Filepath of export file. File must have extension `.json` or `.csv`.
* `[TAG]`: Tags to filter the contact list before exporting. Only contacts with all the provided tags will be exported.

<div style="border: 2px solid #d4a017; width: 97%; background-color: #fffef0; padding: 12px 16px; border-radius: 6px; display: inline-block; font-size: 14px;">
        💡 <strong style="color: #000000;">Tip: Use relative addresses for easier referencing! </strong>
</div>

### Sorting Contacts: `sort`
Sort contacts via a specific field in a specific order

Format: `sort [f/FIELD] [o/ORDER]`

Options:
* `[FIELD]`: One of:
    * `name`
    * `phone`
    * `email`
    * `address`
* `[ORDER]`: One of:
    * `asc`
    * `desc`

Example
* `sort f/name o/asc` sorts all contacts by name in ascending order
* `sort f/address o/desc` sorts all contacts by address in descending order


### Redoing a command : `redo`

* Reverts application to the state before the last undo command
* Redo history will clear everytime a new command is made

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

LinkedUp data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

LinkedUp data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, LinkedUp will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause LinkedUp to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**:
1. You can either use `export` to create a `.csv` file or use LinkedUp's default `.json` file (found in `data/addressbook.json`)
2. Copy your file of choice to your other computer.
3. Use `import f/FILE` to use your data in your other computer.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit autofill** | `edit INDEX ​`<br> e.g.,`edit 2`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Note** | `note INDEX note/[NOTE]`<br> e.g., `note 1 note/Likes to swim.`
**Filter** | `filter TAG [MORE_TAGS]`<br> e.g., `filter friends colleagues`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Undo** | `undo`
**Redo** | `redo`
**Import** | `import f/FILE`<br> e.g., `import f/data/addressbook.json`
**Export** | `export`
**Sort** | `sort [f/FIELD] [o/ORDER]`
**Pin** | `pin INDEX`<br> e.g., `pin 2`
**Unpin** | `unpin INDEX`<br> e.g., `unpin 1`
**Help** | `help`
