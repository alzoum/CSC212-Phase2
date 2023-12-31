public class LinkedList<T> {

	private Node head;

	public LinkedList() {
		head = null;
	}

	public Node<T> getHead() {
		return head;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void insert(T e) {
		Node<Event> newNode = new Node<>((Event) e);

		if (head == null || ((Event) e).getTitle().compareToIgnoreCase(((Event) head.getData()).getTitle()) < 0) {
			// If the list is empty or the event's title comes before the head's event title
			// set the new node as the head
			newNode.setNext(head);
			head = newNode;
		} else {// Find the appropriate position to insert the new node
			Node<Event> current = head;
			while (current.getNext() != null && ((Event) e).getTitle()
					.compareToIgnoreCase(((Event) current.getNext().getData()).getTitle()) >= 0) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}

	}

	public boolean remove(Contact contact) {
		if (isEmpty()) {
			return false;
		} else {
			Node<Event> current = head;
			Node<Event> prev = null;
			boolean eventDeleted = false;

			while (current != null) {
				Event event = current.getData();
				Contact c = event.getParticipants().find(contact.getName());
				if (c != null) {
					if (event.getAppointment()) {
						// Remove the whole event if it's an appointment and the contact is a
						// participant
						if (prev == null) {
							head = current.getNext(); // Removing head
						} else {
							prev.setNext(current.getNext()); // Removing in-between node
						}
						eventDeleted = true;
					} else {
						// Just remove the contact from the event
						event.removeParticipant(contact.getName());
						if (event.getParticipants().isEmpty()) {
							// Remove event if no participants left
							if (prev == null) {
								head = current.getNext(); // Removing head
							} else {
								prev.setNext(current.getNext()); // Removing in-between node
							}
							eventDeleted = true;
						}
					}
				}

				// Update prev and current pointers only if the current event was not deleted
				if (!eventDeleted) {
					prev = current;
				}
				current = current.getNext();
			}
			return eventDeleted;
		}
	}

	public T search(String value) {// value is the event title

		Node<T> current = head;

		while (current != null) {
			Event event = (Event) current.getData();
			if (value.equalsIgnoreCase(event.getTitle())) {
				return current.getData();
			}

			current = current.getNext();
		}

		return null;
	}

	public void printEventDetails(String title) {
		Node<Event> current = head;
		int counter = 0;
		while (current != null) {
			if (current.getData().getTitle().equalsIgnoreCase(title)) {
				System.out.println(current.getData().toString());

				counter++;
			}

			current = current.getNext();
		}
		if (counter == 0) {
			System.out.println("\nNo events\\appointments found!\n");
		}

	}

	public void printEventDetails(Contact contact) {
		Node<Event> current = head;
		int counter = 0;
		while (current != null) {
			Contact c = current.getData().getParticipants().find(contact.getName());
			if (c != null) {
				System.out.println(current.getData().toString());

				counter++;
			}
			current = current.getNext();
		}
		if (counter == 0) {
			System.out.println("\nNo events\\appointments found!\n");
		}

	}

	public void printAllEvents() {
		Node<Event> current = head;

		if (current == null) {
			System.out.println("\nNo events\\appointments found.\n");
			return;
		}

		while (current != null) {

			System.out.println(current.getData().toString());
			current = current.getNext();
		}
	}

	public boolean hasConflict(Event e) {
		Node<Event> current = head;
		while (current != null) {
			boolean conflict = recHasConflict(e.getParticipants().getRoot(), e, current);
			if (conflict == true)
				return conflict;
			current = current.getNext();
		}
		return false;
	}

	public boolean recHasConflict(BSTNode<Contact> current, Event e, Node<Event> events) {

		if (current == null) {
			return false;
		}

		// Recursively check left subtree
		if (recHasConflict(current.getLeft(), e, events)) {
			return true;
		}

		// Check the current node for both conditions
		if (events.getData().getParticipants().find(current.getData().getName()) != null
				&& e.getDate().equals(events.getData().getDate())) {
			return true;
		}

		// Recursively check right subtree
		return recHasConflict(current.getRight(), e, events);
	}

}