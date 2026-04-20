class Service {

    private String serviceName;
    private double serviceCost;

    public Service(String name, double cost) {
        this.serviceName = name;
        this.serviceCost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getServiceCost() { return serviceCost; }

    public void displayService() {
        System.out.printf("%s : %.2f BDT%n", serviceName, serviceCost);
    }
}
