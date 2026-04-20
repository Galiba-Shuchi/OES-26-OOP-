class WeddingEvent extends Event {

    private int numberOfGuests;

    public WeddingEvent(String name, String date, String location, double budget, int guests) {
        super(name, date, location, budget);
        this.numberOfGuests = guests;
    }

    @Override
    public double calculateCost() {
        return getBudget() + (numberOfGuests * 500);
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("Guests     : " + numberOfGuests);
        System.out.println("Type       : Wedding");
    }
}
