import java.util.Scanner;

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

	public Contact findContact(String name) {
		return contactTree.find(name);
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
		System.out.println("Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber() + ", Email: "
				+ contact.getEmailAddress() + ", Address: " + contact.getAddress() + ", Birthday: "
				+ contact.getBirthday());
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

	public void scheduleEvent() {
		String title, contactName, date, time, location;
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
			String x = scanner.nextLine();
			String[] dateParts = x.split(" ");
			try {
				date = dateParts[0];
				time = dateParts[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("\nWrong input!\n");
				return;

			}
			System.out.print("Enter event location: ");
			location = scanner.nextLine();

			Event event = new Event(false, title, date, time, location);
			for (int i = 0; i < names.length; i++) {
				Contact c = contactTree.find(names[i]);
				if (c == null) {
					System.out.println("one of the names you entered is not a contact on your phone book");
					return;
				}
				event.addParticipant(c);
			}
			events.insert(event);
		}

		else if (choice == 2) {
			System.out.print("Enter event title: ");
			title = scanner.nextLine();
			System.out.print("Enter contact name: ");
			contactName = scanner.nextLine();
			System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
			String x = scanner.nextLine();
			String[] dateParts = x.split(" ");
			try {
				date = dateParts[0];
				time = dateParts[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("\nWrong input!\n");
				return;

			}
			System.out.print("Enter event location: ");
			location = scanner.nextLine();
			
			Contact c = contactTree.find(contactName);
		
			if(c!=null) {
				Event event = new Event(true, title, date, time, location);
				event.addParticipant(c);
				events.insert(event);
				}
			else {
				System.out.println("no contact found with this name");
				return;
			}
			
		} else {
			System.out.println("amr kl zg");
			return;
		}
	}
	
	public void printEventDetails() {
		System.out.print("Enter search criteria:\n"
				+ "1. contact name\n"
				+ "2. Event tittle\n");
		int x = scanner.nextInt();

		if(x == 1) {
			System.out.print("Enter contact name: ");
			scanner.nextLine();
			String name = scanner.nextLine();
			Contact c = contactTree.find(name);
			if(c != null) {
			events.printEventDetails(c);
			}
			else {
				System.out.println("amr kl zg");
			}
		}
		else if(x == 2) {
			System.out.print("Enter Event title: ");
			scanner.nextLine();
			String title = scanner.nextLine();
			events.printEventDetails(title);
			
		}
		else {
			System.out.println("amr n7bk");
		}
	}
	
	public void printAllEvents() {
		events.printAllEvents();
	}
	
	
	public static void main(String[] args) {
		Phonebook myPhonebook = new Phonebook();

		// Create and add contacts
		Contact alice = new Contact("Alice A", "12345", "alice@example.com", "123 Street", "01-01-1990", "Note");
		Contact bob = new Contact("Bob B", "54321", "bob@example.com", "321 Street", "02-02-1992", "Note");
		myPhonebook.addContact(alice);
		myPhonebook.addContact(bob);


		myPhonebook.scheduleEvent();
		myPhonebook.scheduleEvent();
	
		myPhonebook.printAllEvents();
		
	}

}
