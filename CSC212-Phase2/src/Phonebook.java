import java.util.Scanner;

public class Phonebook {

	private ContactBST<Contact> contactTree;

	public Phonebook() {
		 this.contactTree = new ContactBST<>();
	}

	public void addContact(Contact contact) {
		contactTree.insert(contact);
	}

	public Contact findContact(String name) {
		return contactTree.find(name);
	}

	public boolean deleteContact(String name) {
		return contactTree.delete(name);
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
        printContactsInOrder(node.getLeft());   // Visit left subtree
        printContact(node.getData());           // Process current node
        printContactsInOrder(node.getRight());  // Visit right subtree
    }

    private void printContact(Contact contact) {
        System.out.println("Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber()
                + ", Email: " + contact.getEmailAddress() + ", Address: " + contact.getAddress()
                + ", Birthday: " + contact.getBirthday());
    }
    
	public static void main(String[] args) {
        Phonebook myPhonebook = new Phonebook();
        
        Contact c1 = new Contact("a a", "5", "a", "f", "c", "f");
        Contact c2 = new Contact("b b", "6", "a", "z", "c", "d");
        Contact c3 = new Contact("c c", "7", "z", "b", "c", "d");
        Contact c4 = new Contact("d d", "8", "g", "b", "c", "d");
        Contact c5 = new Contact("d d", "8", "g", "b", "c", "d");
        
        myPhonebook.addContact(c1);
        myPhonebook.addContact(c2);
        myPhonebook.addContact(c3);
        myPhonebook.addContact(c4);
        myPhonebook.addContact(c5);
        
        
        
        myPhonebook.searchContacts();
    }
	
}
