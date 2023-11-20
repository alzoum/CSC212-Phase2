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
	
	public boolean remove(T e) {
		if (isEmpty()) {
			return false;
		} else {

				Node<Event> current = head;
				boolean eventDeleted = false; // Flag to track if any events were deleted
				while (current.getNext() != null) {
					if (((Event) current.getNext().getData()).getEventUser().equals(((Event) e).getEventUser())) {
						current.setNext(current.getNext().getNext());
						eventDeleted = true; // Set the flag to true if an event was deleted
					} else {
						current = current.getNext();
					}
				}
				if (head.getData().equals(e))// to delete the last node exist
					head = head.getNext();
				return eventDeleted; // Return the flag indicating if any events were deleted
			
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

	public void printEventDetails(String value) {
		Node<Event> current = head;
		int counter = 0;
		while (current != null) {
			boolean title = ((Event) current.getData()).getTitle().equalsIgnoreCase(value);
			boolean name = ((Event) current.getData()).getEventUser().getName().equalsIgnoreCase(value);
			if (title || name) {
				System.out.println("\n Event found!");
				System.out.println(((Event) current.getData()).toString());
				counter++;
			}

			current = current.getNext();
		}
		if (counter == 0) {
			System.out.println("\nNo events found!\n");
		}
	}

	public void scheduleEvent(String title, String date, String time, String location, Contact eventUser) { //need fixing

		if (eventUser != null) {
			boolean hasConflict = false;
			Event newEvent = new Event(title, date, time, location, eventUser);

			Node current = head;
			while (current != null) {
				if (current.getData() instanceof Event) {
					Event existingEvent = (Event) current.getData();
					if (existingEvent.getEventUser().equals(eventUser)// Check for scheduling conflicts by comparing
																		// event details
							&& existingEvent.getDate().equals(newEvent.getDate())
							&& existingEvent.getTime().equals(newEvent.getTime())) {

						hasConflict = true;
						break;
					}
				}
				current = current.getNext();

			}

			if (!hasConflict) {
				insert((T) newEvent);
				System.out.println("\nEvent scheduled successfully!\n");
			} else {
				System.out.println("\nEvent scheduling conflict. Cannot schedule the event.\n");
			}

		} else {
			System.out.println("\nContact does not exist in the phonebook.\n");
		}
	}

	public void printAllEvents() {
		Node<Event> current = head;

		if (current == null) {
			System.out.println("\nNo events found.\n");
			return;
		}

		while (current != null) {
			System.out.println(current.getData().toString());
			current = current.getNext();
		}
	}

}
