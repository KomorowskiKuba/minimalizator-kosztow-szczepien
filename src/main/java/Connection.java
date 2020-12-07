import java.util.Objects;

public class Connection {
    private final Producer producer;
    private final Pharmacy pharmacy;
    private final Double vaccinePrice;
    private final Integer maxDailySupply;

    Connection(Producer producer, Pharmacy pharmacy, Integer maxDailySupply, Double vaccinePrice) {
        this.producer = producer;
        this.pharmacy = pharmacy;
        this.maxDailySupply = maxDailySupply;
        this.vaccinePrice = vaccinePrice;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public Integer getMaxDailySupply() {
        return maxDailySupply;
    }

    public Producer getProducer() {
        return producer;
    }

    public Double getVaccinePrice() {
        return vaccinePrice;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "producerId=" + producer.getName() +
                ", pharmacyId=" + pharmacy.getName() +
                ", maxDailySupply=" + maxDailySupply +
                ", vaccinePrice=" + vaccinePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return Objects.equals(producer, that.producer) &&
                Objects.equals(pharmacy, that.pharmacy) &&
                Objects.equals(vaccinePrice, that.vaccinePrice) &&
                Objects.equals(maxDailySupply, that.maxDailySupply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, pharmacy, vaccinePrice, maxDailySupply);
    }
}
