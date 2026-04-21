import java.util.ArrayList;
import java.util.Scanner;


abstract class Event {

    private String eventName;
    private String eventDate;
    private String eventLocation;
    private double budget;

    public Event(String eventName, String eventDate, String eventLocation, double budget) {
        this.eventName     = eventName;
        this.eventDate     = eventDate;
        this.eventLocation = eventLocation;
        this.budget        = budget;
    }

    public String getEventName()     { return eventName; }
    public String getEventDate()     { return eventDate; }
    public String getEventLocation() { return eventLocation; }
    public double getBudget()        { return budget; }

    public void setEventName(String eventName)         { this.eventName = eventName; }
    public void setEventDate(String eventDate)         { this.eventDate = eventDate; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    public void setBudget(double budget)               { this.budget = budget; }

    public abstract double calculateCost();

    public void displaySummary() {
        System.out.println("  Event Name : " + eventName);
        System.out.println("  Date       : " + eventDate);
        System.out.println("  Location   : " + eventLocation);
        System.out.printf ("  Budget     : %.2f BDT%n", budget);
        System.out.printf ("  Base Cost  : %.2f BDT%n", calculateCost());
    }
}


class WeddingEvent extends Event {

    private int numberOfGuests;

    public WeddingEvent(String name, String date, String location, double budget, int guests) {
        super(name, date, location, budget);   // call parent constructor
        this.numberOfGuests = guests;
    }

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int g) { this.numberOfGuests = g; }

    @Override
    public double calculateCost() {
        return getBudget() + (numberOfGuests * 500.0);
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("  Guests     : " + numberOfGuests);
        System.out.println("  Event Type : Wedding Event");
    }
}


class CorporateEvent extends Event {

    private int numberOfDays;

    public CorporateEvent(String name, String date, String location, double budget, int days) {
        super(name, date, location, budget);
        this.numberOfDays = days;
    }

    public int getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(int d) { this.numberOfDays = d; }

    @Override
    public double calculateCost() {
        return getBudget() * numberOfDays * 1.2;
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("  Days       : " + numberOfDays);
        System.out.println("  Event Type : Corporate Event");
    }
}


class BirthdayEvent extends Event {

    private String theme;

    public BirthdayEvent(String name, String date, String location, double budget, String theme) {
        super(name, date, location, budget);
        this.theme = theme;
    }

    public String getTheme() { return theme; }
    public void setTheme(String t) { this.theme = t; }

    // Birthday: base cost = budget + flat 3,000 BDT decoration charge
    @Override
    public double calculateCost() {
        return getBudget() + 3000.0;
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("  Theme      : " + theme);
        System.out.println("  Event Type : Birthday Event");
    }
}

class Service {

    private String serviceName;
    private double serviceCost;   // in BDT

    public Service(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() { return serviceName; }
    public double getServiceCost() { return serviceCost; }

    public void displayService() {
        System.out.printf("  %-15s : %.2f BDT%n", serviceName, serviceCost);
    }
}

public class EventOrganizingSystem {

    static ArrayList<Event> eventList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     EVENT ORGANIZING SYSTEM          ║");
        System.out.println("║       Welcome to Gevents!            ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> organizerMenu();
                case 2 -> clientMenu();
                case 3 -> {
                    System.out.println("\nThank you for using Event Organizing System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    static void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Event Organizer");
        System.out.println("2. Client");
        System.out.println("3. Exit");
        System.out.println("================================");
    }

    static void organizerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== EVENT ORGANIZER MENU =====");
            System.out.println("1. Add New Event");
            System.out.println("2. View All Events (Summary)");
            System.out.println("3. View Full Event Details");
            System.out.println("4. Show Total Costs (All Events)");
            System.out.println("5. Exit to Main Menu");
            System.out.println("================================");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> addNewEvent();
                case 2 -> viewAllEventsSummary();
                case 3 -> viewFullEventDetails();
                case 4 -> showTotalCostsAllEvents();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    static void addNewEvent() {
        System.out.println("\n--- ADD NEW EVENT ---");
        System.out.println("Select Event Type:");
        System.out.println("1. Wedding Event");
        System.out.println("2. Corporate Event");
        System.out.println("3. Birthday Event");

        int type = getIntInput("Enter event type: ");
        if (type < 1 || type > 3) {
            System.out.println("Invalid event type.");
            return;
        }

        System.out.print("Enter Event Name     : ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Event Date     : ");
        String date = scanner.nextLine().trim();
        System.out.print("Enter Event Location : ");
        String location = scanner.nextLine().trim();
        double budget = getDoubleInput("Enter Budget (BDT)   : ");

        switch (type) {
            case 1 -> {
                int guests = getIntInput("Enter Number of Guests: ");
                eventList.add(new WeddingEvent(name, date, location, budget, guests));
                System.out.println("✔ Wedding Event added successfully!");
            }
            case 2 -> {
                int days = getIntInput("Enter Number of Days  : ");
                eventList.add(new CorporateEvent(name, date, location, budget, days));
                System.out.println("✔ Corporate Event added successfully!");
            }
            case 3 -> {
                System.out.print("Enter Theme           : ");
                String theme = scanner.nextLine().trim();
                eventList.add(new BirthdayEvent(name, date, location, budget, theme));
                System.out.println("✔ Birthday Event added successfully!");
            }
        }
    }

    static void viewAllEventsSummary() {
        if (eventList.isEmpty()) {
            System.out.println("\nNo events found.");
            return;
        }
        System.out.println("\n--- ALL EVENTS (SUMMARY) ---");
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println("\n[" + (i + 1) + "] " + eventList.get(i).getEventName()
                    + "  |  " + eventList.get(i).getEventDate()
                    + "  |  " + eventList.get(i).getEventLocation());
        }
    }

    static void viewFullEventDetails() {
        if (eventList.isEmpty()) {
            System.out.println("\nNo events found.");
            return;
        }
        viewAllEventsSummary();
        int idx = getIntInput("\nEnter event number to view details: ") - 1;
        if (idx < 0 || idx >= eventList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        System.out.println("\n--- FULL EVENT DETAILS ---");
        eventList.get(idx).displaySummary();  
    }

    static void showTotalCostsAllEvents() {
        if (eventList.isEmpty()) {
            System.out.println("\nNo events found.");
            return;
        }
        System.out.println("\n--- TOTAL COSTS FOR ALL EVENTS ---");
        double grandTotal = 0;
        for (int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);
            double cost = e.calculateCost();   // POLYMORPHISM
            System.out.printf("[%d] %-20s  =>  %.2f BDT%n", (i + 1), e.getEventName(), cost);
            grandTotal += cost;
        }
        System.out.printf("%n    Grand Total  =>  %.2f BDT%n", grandTotal);
    }

    static void clientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n========= CLIENT MENU =========");
            System.out.println("1. Book a Service for an Event");
            System.out.println("2. Show Total Cost (for selected event)");
            System.out.println("3. Contact with Us");
            System.out.println("4. Exit to Main Menu");
            System.out.println("================================");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> bookService();
                case 2 -> showTotalCostForEvent();
                case 3 -> showContactInfo();
                case 4 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void bookService() {
        if (eventList.isEmpty()) {
            System.out.println("\nNo events available to book.");
            return;
        }

        viewAllEventsSummary();
        int idx = getIntInput("\nSelect event number: ") - 1;
        if (idx < 0 || idx >= eventList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Event selectedEvent = eventList.get(idx);
        System.out.println("\nEvent selected: " + selectedEvent.getEventName());

        ArrayList<Service> catalogue = new ArrayList<>();
        catalogue.add(new Service("Catering",     15000.0));
        catalogue.add(new Service("Decoration",   10000.0));
        catalogue.add(new Service("Photography",   8000.0));

        System.out.println("\n--- AVAILABLE SERVICES ---");
        for (int i = 0; i < catalogue.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            catalogue.get(i).displayService();
        }

        double serviceTotal = 0;
        boolean bookMore = true;
        while (bookMore) {
            int sChoice = getIntInput("\nSelect service to book (0 to finish): ");
            if (sChoice == 0) {
                bookMore = false;
            } else if (sChoice >= 1 && sChoice <= catalogue.size()) {
                Service s = catalogue.get(sChoice - 1);
                serviceTotal += s.getServiceCost();
                System.out.println("  ✔ " + s.getServiceName() + " booked! (+%.2f BDT)".formatted(s.getServiceCost()));
            } else {
                System.out.println("  Invalid service choice.");
            }
        }

        double eventCost  = selectedEvent.calculateCost();   // POLYMORPHISM
        double totalBill  = eventCost + serviceTotal;

        System.out.println("\n─────────────────────────────────");
        System.out.printf ("  Event Base Cost   : %.2f BDT%n", eventCost);
        System.out.printf ("  Services Total    : %.2f BDT%n", serviceTotal);
        System.out.println("─────────────────────────────────");
        System.out.printf ("  TOTAL BILL        : %.2f BDT%n", totalBill);
        System.out.println("─────────────────────────────────");
    }
    static void showTotalCostForEvent() {
        if (eventList.isEmpty()) {
            System.out.println("\nNo events available.");
            return;
        }
        viewAllEventsSummary();
        int idx = getIntInput("\nSelect event number: ") - 1;
        if (idx < 0 || idx >= eventList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Event e = eventList.get(idx);
        System.out.println("\n--- COST BREAKDOWN ---");
        System.out.println("  Event : " + e.getEventName());
        System.out.printf ("  Total : %.2f BDT%n", e.calculateCost());  
    }

    static void showContactInfo() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       CONTACT WITH US        ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  Name     : Galiba           ║");
        System.out.println("║  Phone    : 017              ║");
        System.out.println("║  Location : Rajshahi         ║");
        System.out.println("╚══════════════════════════════╝");
    }

    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid integer.");
            }
        }
    }
    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }
}
