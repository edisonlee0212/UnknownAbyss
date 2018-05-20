class Resource {
    private int reference;
    private String name;
    private double quantity;

    Resource(int reference, double quantity) {
        this.reference = reference;
        if(reference < Element.values().length) {
            this.name = Element.values()[reference].name();
        } else this.name = "UE" + reference;
        this.quantity = quantity;
    }

    int getReference() {
        return reference;
    }

    String getName() {
        return name;
    }

    double getQuantity() {
        return quantity;
    }

    double updateQuentity(double val){
        quantity += val;
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
