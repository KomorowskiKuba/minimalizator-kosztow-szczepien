import java.util.List;
import java.util.Objects;

public class Producer {
    private final Integer id;
    private final String name;
    private final Integer dailyProduction;

    Producer(Integer id, String name, Integer dailyProduction) {
        this.id = id;
        this.name = name;
        this.dailyProduction = dailyProduction;
    }

    public static Producer getProducerById(List<Producer> producerList, Integer id) {
        for (Producer p : producerList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDailyProduction() {
        return dailyProduction;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dailyProduction=" + dailyProduction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(dailyProduction, producer.dailyProduction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dailyProduction);
    }
}
