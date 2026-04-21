import java.util.*;

// Abstract class (Abstraction)
abstract class User {
    private String name, email, password; // Encapsulation

    public User(String name, String email, String password) {
        this.name = name; this.email = email; this.password = password;
    }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public abstract void showMenu(Scanner sc, List<Booking> bookings); // Abstraction
    public abstract String getRole();
}

// Booking entity
class Booking {
    private String clientName, contact, eventType;
    private int guests;
    private static int counter = 1;
    private int id;

    public Booking(String clientName, String contact, String eventType, int guests) {
        this.clientName = clientName; this.contact = contact;
        this.eventType = eventType;  this.guests = guests;
        this.id = counter++;
    }
    @Override  // Polymorphism: method overriding
    public String toString() {
        return String.format("[#%d] %s | %s | Guests: %d | Contact: %s",
                id, eventType, clientName, guests, contact);
    }
    public String getEventType() { return eventType; }
    public int getGuests()       { return guests; }
}

// Client (Inheritance)
class Client extends User {
    public Client(String name, String email, String password) { super(name, email, password); }

    @Override public String getRole() { return "Client"; }

    @Override
    public void showMenu(Scanner sc, List<Booking> bookings) {
        int choice;
        do {
            System.out.println("\n=== CLIENT MENU ===\n1. Book an Event\n2. Logout");
            System.out.print("Choice: ");
            choice = readInt(sc);
            if (choice == 1) bookEvent(sc, bookings);
            else if (choice != 2) System.out.println("Invalid choice.");
        } while (choice != 2);
    }

    private void bookEvent(Scanner sc, List<Booking> bookings) {
        System.out.println("\n-- Event Types: 1.Birthday  2.Wedding  3.Corporate --");
        System.out.print("Choose type: ");
        int t = readInt(sc);
        if (t < 1 || t > 3) { System.out.println("Invalid event type."); return; }
        String eventType = new String[]{"Birthday", "Wedding", "Corporate"}[t - 1];

        System.out.print("Client name: ");    String cName   = sc.nextLine().trim();
        System.out.print("Contact number: "); String contact = sc.nextLine().trim();
        System.out.print("Number of guests: "); int guests   = readInt(sc);

        if (cName.isEmpty() || contact.isEmpty() || guests <= 0) {
            System.out.println("Invalid input. Booking cancelled."); return;
        }
        bookings.add(new Booking(cName, contact, eventType, guests));
        System.out.println("Booking confirmed for: " + eventType);
    }

    private int readInt(Scanner sc) {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }
}

// Organizer (Inheritance + Polymorphism)
class Organizer extends User {
    public Organizer(String name, String email, String password) { super(name, email, password); }

    @Override public String getRole() { return "Organizer"; }

    @Override
    public void showMenu(Scanner sc, List<Booking> bookings) {
        int choice;
        do {
            System.out.println("\n=== ORGANIZER MENU ===\n1. View All Bookings\n2. Event Summary\n3. Logout");
            System.out.print("Choice: ");
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { choice = -1; }
            if      (choice == 1) viewAll(bookings);
            else if (choice == 2) summary(bookings);
            else if (choice != 3) System.out.println("Invalid choice.");
        } while (choice != 3);
    }

    private void viewAll(List<Booking> bookings) {
        if (bookings.isEmpty()) { System.out.println("No bookings found."); return; }
        System.out.println("\n--- ALL BOOKINGS ---");
        bookings.forEach(System.out::println);
    }

    private void summary(List<Booking> bookings) {
        if (bookings.isEmpty()) { System.out.println("No bookings yet."); return; }
        Map<String, Long>    count    = new LinkedHashMap<>();
        Map<String, Integer> guestSum = new LinkedHashMap<>();
        for (Booking b : bookings) {
            count.merge(b.getEventType(), 1L, Long::sum);
            guestSum.merge(b.getEventType(), b.getGuests(), Integer::sum);
        }
        System.out.println("\n--- EVENT SUMMARY ---");
        System.out.printf("%-12s %-10s %-10s%n", "Type", "Bookings", "TotalGuests");
        count.forEach((type, cnt) ->
            System.out.printf("%-12s %-10d %-10d%n", type, cnt, guestSum.get(type)));
        System.out.println("Total Bookings: " + bookings.size());
    }
}

// Main driver class
public class Gevents {
    static List<User>    users    = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        users.add(new Organizer("Admin", "admin@gevents.com", "1234")); // Demo account
        System.out.println("=== Welcome to GEVENTS - Event Management System ===");
        int choice;
        do {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choice: ");
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { choice = -1; }
            if      (choice == 1) register();
            else if (choice == 2) login();
            else if (choice == 3) System.out.println("Goodbye!");
            else System.out.println("Invalid choice. Try again.");
        } while (choice != 3);
    }

    static void register() {
        System.out.println("\n-- REGISTER --");
        System.out.print("Name: ");     String name  = sc.nextLine().trim();
        System.out.print("Email: ");    String email = sc.nextLine().trim();
        System.out.print("Password: "); String pass  = sc.nextLine().trim();
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            System.out.println("All fields are required."); return;
        }
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already registered."); return;
            }
        }
        System.out.print("Role (1.Client / 2.Organizer): ");
        String r = sc.nextLine().trim();
        if      (r.equals("1")) users.add(new Client(name, email, pass));
        else if (r.equals("2")) users.add(new Organizer(name, email, pass));
        else { System.out.println("Invalid role selected."); return; }
        System.out.println("Registration successful! Welcome, " + name);
    }

    static void login() {
        System.out.println("\n-- LOGIN --");
        System.out.print("Email: ");    String email = sc.nextLine().trim();
        System.out.print("Password: "); String pass  = sc.nextLine().trim();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(pass)) {
                System.out.println("Login successful! Hello, " + u.getName() + " (" + u.getRole() + ")");
                u.showMenu(sc, bookings); // Polymorphism: calls correct subclass menu
                return;
            }
        }
        System.out.println("Invalid email or password.");
    }
}
