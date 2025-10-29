---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

*Target user profile*:

* has a need to manage a significant number of contacts
* contacts are from many different time zones and communication platforms
* contacts are also both long and short term
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

*Value proposition*: manage contacts faster than a typical mouse/GUI driven app, in an organised way.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​        | I want to …​                                     | So that I can…​                                         |
|----------|----------------|--------------------------------------------------|---------------------------------------------------------|
| * * *    | user           | add new contacts with their relevant information | check and contact relevant personnel when necessary.    |
| * * *    | user           | delete a person                                  | remove contacts that I no longer need.                  |
| * * *    | user           | save and read contacts on shutdown/startup       | maintain contact information across instances of usage. |
| * * *    | busy user      | search contacts by company                       | sift through contacts easily.                           |
| * * *    | user           | add meetings to clients                          | be reminded of our next meeting.                        |
| * * *    | user           | delete past meetings                             | de-clutter the contact list.                            |
| * *      | user           | check client's local time zone                   | contact them at appropriate times.                      |
| * *      | user           | edit my contacts                                 | update client information easily.                       |
| * *      | user           | attach tags to clients                           | recall connections to the client.                       |
| * *      | user           | get alerts for clashing meetings                 | prevent double booking.                                 |
| * *      | user           | search contacts by country                       | contact groups of people under the filter               |
| * *      | user           | search contacts by tag                           | contact groups of people under the filter.              |
| * *      | user           | search contacts by name                          | contact groups of people under the filter.              |
| * *      | user           | Search contacts by phone number                  | contact groups of people under the filter.              |
| *        | user           | attach links to clients                          | go to their websites easily.                            |
| *        | user           | flag some clients                                | quickly find them in the list.                          |
| *        | user           | set multiple tags on one contact                 | maintain overlapping projects without confusion.        |
| *        | impatient user | see my frequently contacted users                | save time.                                              |
| *        | user           | auto-convert meeting times into my timezone      | prevent making scheduling mistakes.                     |

### Use cases

**Use case: UC01 - Add a contact**

**MSS**

1.  User requests to add a new contact.
2.  Wi-Find validates the fields.
3.  Wi-Find adds the new contact.
4.  Wi-Find displays a success message.

    Use case ends.

**Extensions**

* 2a. A required field is empty.

    * 2a1. Wi-Find shows an error message.

      Use case ends.

* 2b. The input format for one or more of fields are invalid.

    * 2b1. Wi-Find shows an error message.

      Use case ends.

* 2c. The contact already exists (duplicate contact number or email).

    * 2c1. Wi-Find warns and asks for confirmation from user to proceed with adding contact.
    * If user cancels, use case ends.
    * Else use case continues from step 3.

**Use case: UC02 - Delete a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to list contacts.
2. Wi-Find shows a list of contacts.
3. User requests to delete a specific contact in the list.
4. Wi-Find deletes the contact.
5. Wi-Find shows a success message.

    Use case ends.

**Extensions**

* 3a. The given identifier is invalid (no contact found).

    * 3a1. Wi-Find shows an error message.

      Use case ends.

**Use case: Save and read contacts on shutdown/startup**

**MSS**

1. User boots up Wi-Find.
2. Wi-Find loads the previously saved contact list.
3. User interacts with Wi-Find.
4. Wi-Find automatically saves the updated contact list after each command.
5. User shuts down the application.

    Use case ends.

**Extensions**

* 1a. Saving fails due to missing file.

    * 2a1. Wi-Find requests permission to create a new file.

    * 2a2. If permission is granted, Wi-Find creates a new file with sample contacts.

      Use case resumes at step 2.

* 1b. Wi-find is unable to read due to lack of permission.

    * 1b1. Wi-Find requests permission to read/write.

    * 1b2. User approves.

      Use case resumes at step 2.

**Use case: UC03 Search contacts by company**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to find contacts with a specified field.
2. Wi-Find displays all contacts whose company matches the given name.

   Use case ends.

**Extensions**

* 1a. One or more of the inputs are invalid

    * 2a1. Wi-Find shows an error message.

      Use case ends.

* 3a. No contacts match the company name.

    * 3a1. Wi-Find shows an empty list message.

      Use case ends.

**Use case: UC04 - Add meeting to a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to list contacts.
2. Wi-Find shows a list of contacts.
3. User requests to add a meeting to a specific contact in the list.
4. Wi-Find adds the meeting provided to the contact.

    Use case ends.

**Extensions**

* 3a. The meeting provided is of invalid format.

    * 3a1. Wi-Find shows an error message.

        Use case ends.

* 3b. The given identifier is invalid (no contact found).

    * 3b1. Wi-Find shows an error message.

        Use case ends.

* 3c. The meeting time provided clashes with another meeting.

    * 3c1. Wi-Find shows an error message.

        Use case ends.

**Use case: UC05 - Delete meeting from a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to list contacts.
2. Wi-Find shows a list of contacts.
3. User requests to delete a meeting from a specific contact in the list.
4. Wi-Find deletes the meeting provided from the contact.

   Use case ends.

**Extensions**

* 3a. The meeting provided is of invalid format.

    * 3a1. Wi-Find shows an error message.

      Use case ends.

* 3b. The given identifier is invalid (no contact found).

    * 3b1. Wi-Find shows an error message.

      Use case ends.

* 3c. The contact does not have a meeting with the meeting time provided.

    * 3c1. Wi-Find shows an error message.

      Use case ends.

**Use case: UC06 - Edit the information of a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to edit fields of a contact.
2. Wi-Find edits the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. One or more of the fields provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.

* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

* 1c. The field the user is trying to change is the identifier and already exists.

    * 1c1. Wi-Find warns and asks for confirmation from user to proceed with editing contact.
    * If user cancels, use case ends.
    * Else use case continues from step 2.

**Use case: UC07 - Flag a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to flag a contact.
2. Wi-Find flags the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. The field provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.

* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

* 1c. The contact is already flagged.

    * 1c1. Wi-Find shows an error message. 
      
      Use case ends.

**Use case: UC08 - Unflag a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to unflag a contact.
2. Wi-Find unflags the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. The field provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.

* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

* 1c. The contact is already unflagged.

    * 1c1. Wi-Find shows an error message.

      Use case ends.

**Use case: UC09 - Update a link to a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to update a link to a contact.
2. Wi-Find updates the link to the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. The field provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.
  
* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

**Use case: UC10 - Removes a link from a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to remove a link from a contact.
2. Wi-Find removes the link from the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. The field provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.

* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

* 1c. The contact does not have a link.

    * 1c1. Wi-Find shows an error message.

      Use case ends.

**Use case: UC11 - Add a link to a contact**

**Preconditions: There exists at least one person in the list**

**MSS**

1. User requests to add a link to a contact.
2. Wi-Find adds the link to the contact.
3. Wi-Find displays a success message.

   Use case ends.

**Extensions**

* 1a. The field provided is of invalid format.

    * 1a1. Wi-Find shows an error message.

      Use case ends.

* 1b. The given identifier is invalid (no contact found).

    * 1b1. Wi-Find shows an error message.

      Use case ends.

* 1c. The contact already has a link.

    * 1c1. Wi-Find <u>updates the link of the contact (UC09)</u>.

* 1d. The link field provided is empty.

    * 1d1. Wi-Find <u>removes the link from the contact (UC10)</u>.

### Non-Functional Requirements

1.  Environment Requirements
    - Should work on any _mainstream OS_ as long as it has Java `17`.
    - Should be runnable without requiring installation.
    - Should not depend on any remote server.
2.  Data Requirements
    - User data should be locally in a human-editable text file.
    - Data should be automatically saved after each modification to prevent accidental loss.
3.  Performance Requirements
    - The system should start up within 3 seconds on a modern computer.
    - Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
    - Search/filter operations should return results within 1 second for 1000 contacts.
4.  Usability Requirements
    - A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
    - The system should provide clear, human-readable error messages when invalid input is given.
    - The GUI should remain usable across standard screen resolutions (≥1280x720) and scaling settings (100%, 125%, 150%).
5.  Maintainability Requirements
    - The system should follow object-oriented principles to support modularity and extensibility.
    - Code should follow a consistent style guide.
6.  Portability Requirements
    - The product should be packaged as a single `.jar` file (≤100 MB).
    - Documents should not exceed 15 MB each.
7.  Reliability Requirements
    - The system should not crash under normal usage (adding, editing, deleting contacts).
    - Invalid input should not cause data corruption or loss.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
