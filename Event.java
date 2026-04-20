abstract class Event {

    private String eventName;
    private String eventDate;
    private String eventLocation;
    private double budget;

    public Event(String eventName, String eventDate, String eventLocation, double budget) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.budget = budget;
    }

    public String getEventName() { return eventName; }
    public String getEventDate() { return eventDate; }
    public String getEventLocation() { return eventLocation; }
    public double getBudget() { return budget; }

    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    public void setBudget(double budget) { this.budget = budget; }

    public abstract double calculateCost();

    public void displaySummary() {
        System.out.println("Event Name : " + eventName);
        System.out.println("Date       : " + eventDate);
        System.out.println("Location   : " + eventLocation);
        System.out.printf("Budget     : %.2f BDT%n", budget);
        System.out.printf("Base Cost  : %.2f BDT%n", calculateCost());
    }
}
