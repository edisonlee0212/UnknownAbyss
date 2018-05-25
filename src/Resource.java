class Resource {
    private int reference;
    private double quantity;

    Resource(int reference, double quantity) {
        this.reference = reference;
        this.quantity = quantity;
    }

    int getReference() {
        return reference;
    }

    String getName() {
        String name;
        if(reference < Element.values().length) {
            name = Element.values()[reference].name();
        } else name = "UE" + reference;
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
