public class Transaction {
    private final Producer producer;
    private final Pharmacy pharmacy;
    private final Integer amountOfVaccines;
    private final Double vaccinePrice;

    Transaction(Producer producer, Pharmacy pharmacy, Integer amountOfVaccines, Double price) {
        this.producer = producer;
        this.pharmacy = pharmacy;
        this.amountOfVaccines = amountOfVaccines;
        this.vaccinePrice = price;
    }

    public String getProducerName() {
        return producer.getName();
    }

    public String getPharmacyName() {
        return pharmacy.getName();
    }

    public Integer getAmountOfVaccines() {
        return amountOfVaccines;
    }

    public Double getVaccinePrice() {
        return vaccinePrice;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "producerId=" + producer.getName() +
                ", pharmacyId=" + pharmacy.getName() +
                ", amountOfVaccines=" + amountOfVaccines +
                ", vaccinePrice=" + vaccinePrice +
                '}';
    }
}
