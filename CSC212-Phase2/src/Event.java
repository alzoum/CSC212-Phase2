import java.util.List;
import java.util.ArrayList;
public class Event {

	private Boolean appointment;
	private String title;
	private String date;
	private String time;
	private String location;
	private List<Contact> participants;

	public Event(Boolean appointment, String title, String date, String time, String location) {
		this.appointment = appointment;
		this.title = title;
		this.date = date;
		this.time = time;
		this.location = location;
		 this.participants = new ArrayList<>();
		
	}

	public void addParticipant(Contact contact) {
        if (this.appointment == true && !this.participants.isEmpty()) {
            throw new IllegalStateException("An appointment can only have one participant.");
        }
        this.participants.add(contact);
    }
	
	public void removeParticipant(Contact contact) {
        this.participants.remove(contact);
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

    public List<Contact> getParticipants() {
        return participants;
    }

	public Boolean getAppointment() {
		return appointment;
	}

	public void setAppointment(Boolean appointment) {
		this.appointment = appointment;
	}

	public void setParticipants(List<Contact> participants) {
		this.participants = participants;
	}

	@Override
    public String toString() {
        StringBuilder participantNames = new StringBuilder();
        for (Contact participant : participants) {
            if (participantNames.length() > 0) {
                participantNames.append(", ");
            }
            participantNames.append(participant.getName());
        }

        return "Event Type: " + (appointment == true ? "Appointment" : "Event") 
               + ", Title: " + title
               + ", date and time (MM/DD/YYYY HH:MM): " + date + " " + time
               + ", Location: " + location
               + ", Participants: " + participantNames.toString();
    }
	
	//@Override
	//public String toString() {
	//	return "-----------------------\n" + "Event title: " + title + "\n" + "Contact name: " + eventUser.getName()
	//			+ "\n" + "Event date and time (MM/DD/YYYY HH:MM): " + date + " " + time + "\n" + "Event location: "
	//			+ location + "\n" + "-----------------------";
	//}
}
