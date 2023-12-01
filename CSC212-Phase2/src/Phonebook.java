import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Phonebook {
	Scanner scanner = new Scanner(System.in);
	private ContactBST<Contact> contactTree;
	private LinkedList<Event> events;

	public Phonebook() {
		this.contactTree = new ContactBST<>();
		this.events = new LinkedList<>();
	}

	public void addContact(Contact contact) {
		contactTree.insert(contact);
	}


	public void deleteContact(String name) {

		Contact contact = contactTree.find(name);

		if (contact == null) {
			System.out.println("\nNo contacts found.\n");
			return;
		}

		if (contactTree.delete(name)) { // Removes the contact from the contacts list
			events.remove(contact);
			System.out.println("\nContact removed successfully!\n");

		} else {
			System.out.println("Failed to remove contact.\n");
		}
	}

	public void searchContacts() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nEnter search criteria:");
		System.out.println("1.Name");
		System.out.println("2.Phone Number");
		System.out.println("3.Email Address");
		System.out.println("4.Address");
		System.out.println("5.Birthday");

		System.out.print("\nEnter your choice:");
		int choice = scanner.nextInt();

		scanner.nextLine(); // Consume the newline character

		String searchValue;
		switch (choice) {
		case 1:
			System.out.print("\nEnter the contact's name:");
			searchValue = scanner.nextLine();
			printContacts(contactTree.searchCriteria("name", searchValue));// Searches and prints contacts
																			// by name
			break;
		case 2:
			System.out.print("Enter the contact's phone number:");
			searchValue = scanner.nextLine();
			printContacts(contactTree.searchCriteria("phone", searchValue));// Searches and prints contacts
																			// by phone
			break;
		case 3:
			System.out.print("Enter the contact's email address:");
			searchValue = scanner.nextLine();
			printContacts(contactTree.searchCriteria("email", searchValue));// Searches and prints contacts
																			// by email
			break;
		case 4:
			System.out.print("Enter the contact's address:");
			searchValue = scanner.nextLine();
			printContacts(contactTree.searchCriteria("address", searchValue));// Searches and prints
																				// contacts by address
			break;
		case 5:
			System.out.print("Enter the contact's birthday:");
			searchValue = scanner.nextLine();
			printContacts(contactTree.searchCriteria("birthday", searchValue));// Searches and prints
																				// contacts by birthday
			break;
		default:
			System.out.println("Invalid choice.");
			break;
		}
	}

	public void printContacts(ContactBST<Contact> bst) {
		printContactsInOrder(bst.getRoot());
	}

	private void printContactsInOrder(BSTNode<Contact> node) {
		if (node == null) {
			return;
		}
		printContactsInOrder(node.getLeft()); // Visit left subtree
		printContact(node.getData()); // Process current node
		printContactsInOrder(node.getRight()); // Visit right subtree
	}

	private void printContact(Contact contact) {
		System.out.println("-----------------------\nName: " + contact.getName() + "\n Phone: " + contact.getPhoneNumber() + "\n Email: "
				+ contact.getEmailAddress() + "\n Address: " + contact.getAddress() + "\n Birthday: "
				+ contact.getBirthday() + "\n-----------------------") ;
	}

	public void printContactsByFirstName(String firstName) {
		BSTNode<Contact> current = contactTree.getRoot();
		ContactBST<Contact> list = new ContactBST<>();
		printContactsByFirstNameRec(current, list, firstName);
		if (list.getRoot() == null) {
			System.out.println("No contacts found.");
		} else {
			printContacts(list);
		}

	}

	public void printContactsByFirstNameRec(BSTNode<Contact> current, ContactBST<Contact> list, String firstName) {
		if (current == null) {
			return;
		}
		printContactsByFirstNameRec(current.getLeft(), list, firstName); // Visit left subtree
		if (current.getData().getFirstName().equalsIgnoreCase(firstName))
			list.insert(current.getData()); // Process current node
		printContactsByFirstNameRec(current.getRight(), list, firstName); // Visit right subtree

	}

	private static boolean isValidDate(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate date1 = LocalDate.parse(dateStr, formatter);
            LocalDate currentDate = LocalDate.now();

            // Check if the parsed date is valid and in the future
            return !date1.isBefore(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }}
	
	public void scheduleEvent() {
		String title, contactName, date, location;
		System.out.println("Enter type:\n" + "1. event\n" + "2. appointment");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		if (choice == 1) {
			System.out.print("Enter event title: ");
			title = scanner.nextLine();
			System.out.print("Enter contacts name separated by a comma: ");
			contactName = scanner.nextLine();
			String[] names = contactName.split(",");
			System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
			date = scanner.nextLine();
			if(!(isValidDate(date, "MM/dd/yyyy HH:mm"))) {
				System.out.println("the date is not correct");
				return;
			}
			System.out.print("Enter event location: ");
			location = scanner.nextLine();

			Event event = new Event(false, title, date, location);
			for (int i = 0; i < names.length; i++) {
				Contact c = contactTree.find(names[i]);
				if (c == null) {
					System.out.println("one of the names you entered is not a contact on your phone book");
					return;
				}
				event.addParticipant(c);
			}
			if(!(events.hasConflict(event)))
				events.insert(event);
			else {
				System.out.println("\nThere is a conflict\n");
			}
		}

		else if (choice == 2) {
			System.out.print("Enter event title: ");
			title = scanner.nextLine();
			System.out.print("Enter contact name: ");
			contactName = scanner.nextLine();
			System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
			date = scanner.nextLine();
			if(!(isValidDate(date, "MM/dd/yyyy HH:mm"))) {
				System.out.println("the date is not correct");
				return;
			}
			System.out.print("Enter event location: ");
			location = scanner.nextLine();

			Contact c = contactTree.find(contactName);

			if (c != null) {
				Event event = new Event(true, title, date, location);
				event.addParticipant(c);
				if(!(events.hasConflict(event)))
					events.insert(event);
				else {
					System.out.println("\nThere is a conflict\n");
				}
			} else {
				System.out.println("no contact found with this name");
				return;
			}

		} else {
			System.out.println("\nWrong input!\n");
			return;
		}
	}

	public void printEventDetails() {
		System.out.print("Enter search criteria:\n" + "1. contact name\n" + "2. Event tittle\n");
		int x = scanner.nextInt();

		if (x == 1) {
			System.out.print("Enter contact name: ");
			scanner.nextLine();
			String name = scanner.nextLine();
			Contact c = contactTree.find(name);
			if (c != null) {
				events.printEventDetails(c);
			} else {
				System.out.println("No contact found with this name");
			}
		} else if (x == 2) {
			System.out.print("Enter Event title: ");
			scanner.nextLine();
			String title = scanner.nextLine();
			events.printEventDetails(title);

		} else {
			System.out.println("No event found with this event title");
		}
	}

	public void printAllEvents() {
		events.printAllEvents();
	}


	public static void main(String[] args) {
		Phonebook phonebook = new Phonebook();

		Scanner scanner = new Scanner(System.in);
		int input = 0;
		String name, phonenumber, email, address, birthday, note;
		System.out.println("Welcome to the Linked Tree Phonebook!");
		do {
			try {
			System.out.println("Please choose an option:");
			System.out.println("1.Add a contact");
			System.out.println("2.Search for a contact");
			System.out.println("3.Delete a contact");
			System.out.println("4.Schedule an event/appointment");
			System.out.println("5.Print event details");
			System.out.println("6.Print contacts by first name");
			System.out.println("7.Pring all events alphabetically");
			System.out.println("8.Exit \n");

			System.out.print("Enter your choice:");
			
			input = scanner.nextInt();
			
			
			switch (input) {
			case 1:
				System.out.print("\nEnter the contact's name:");
				scanner.nextLine(); // Consume newline character
				name = scanner.nextLine();
				System.out.print("Enter the contact's phone number:");
				phonenumber = scanner.nextLine();
				System.out.print("Enter the contact's email address:");
				email = scanner.nextLine();
				System.out.print("Enter the contact's address:");
				address = scanner.nextLine();
				System.out.print("Enter the contact's birthday:");
				birthday = scanner.nextLine();
				System.out.print("Enter any notes for the contact:");
				note = scanner.nextLine();
				Contact C = new Contact(name, phonenumber, email, address, birthday, note);
				System.out.println();
				phonebook.addContact(C);
				System.out.println();
				break;
			case 2:
				phonebook.searchContacts();
				break;
			case 3:
				System.out.print("\nEnter contact's Name: ");
				scanner.nextLine();
				String value = scanner.nextLine();
				phonebook.deleteContact(value);
				break;
			case 4:
				phonebook.scheduleEvent();
				break;
			case 5:
				phonebook.printEventDetails();
				break;
			case 6:
				System.out.print("\nEnter the first name:");
				scanner.nextLine();
				name = scanner.nextLine();
				phonebook.printContactsByFirstName(name);
				break;
			case 7:
				phonebook.printAllEvents();
				break;
			case 8:
				System.out.println("\nGoodbye!");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("\n Invalid choice");
			}
			}catch(InputMismatchException e) {
				scanner.nextLine();
				System.out.println("\nPlease enter a number from the list!\n");
				continue;
			}
			
		} while (true);

	
	}

}
