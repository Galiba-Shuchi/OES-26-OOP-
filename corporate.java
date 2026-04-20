class CorporateEvent extends Event {

    private int numberOfDays;

    public CorporateEvent(String name, String date, String location, double budget, int days) {
        super(name, date, location, budget);
        this.numberOfDays = days;
    }

    @Override
    public double calculateCost() {
        return getBudget() * numberOfDays * 1.2;
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("Days       : " + numberOfDays);
        System.out.println("Type       : Corporate");
    }
}
