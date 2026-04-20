class BirthdayEvent extends Event {

    private String theme;

    public BirthdayEvent(String name, String date, String location, double budget, String theme) {
        super(name, date, location, budget);
        this.theme = theme;
    }

    @Override
    public double calculateCost() {
        return getBudget() + 3000;
    }

    @Override
    public void displaySummary() {
        super.displaySummary();
        System.out.println("Theme      : " + theme);
        System.out.println("Type       : Birthday");
    }
}
