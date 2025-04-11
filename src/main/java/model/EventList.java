package model;

public class EventList {
    private int id;
    private int eventId;
    private String listName;

    public EventList() {}
    public EventList(int id, int eventId, String listName) {
        this.id = id;
        this.eventId = eventId;
        this.listName = listName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getListName() {
        return listName;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }
}
