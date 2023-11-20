
public class Event {

	private String title;
	private String date;
	private String time;
	private String location;
	private Contact eventUser;

	public Event(String title, String date, String time, String location, Contact eventUser) {
		this.title = title;
		this.date = date;
		this.time = time;
		this.location = location;
		this.eventUser = eventUser;
	}

	public boolean equals(Object e) {
		Event event = (Event) e;
		return event.getTitle().equalsIgnoreCase(title)
				|| event.eventUser.getName().equalsIgnoreCase(eventUser.getName());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Contact getEventUser() {
		return eventUser;
	}

	public void setEventUser(Contact eventUser) {
		this.eventUser = eventUser;
	}

	@Override
	public String toString() {
		return "-----------------------\n" + "Event title: " + title + "\n" + "Contact name: " + eventUser.getName()
				+ "\n" + "Event date and time (MM/DD/YYYY HH:MM): " + date + " " + time + "\n" + "Event location: "
				+ location + "\n" + "-----------------------";
	}
}
