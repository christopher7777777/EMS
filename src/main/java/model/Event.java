package model;

public class Event {
    private int id;
    private String name;
    private String date;
    private String location;
    private int capacity;

    public Event() {}
    public Event(int id, String name, String date, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {

        this.id = id;
    }
    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {

        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
