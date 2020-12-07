import java.util.List;
import java.util.Objects;

public class Pharmacy {
    private final Integer id;
    private final String name;
    private final Integer dailyRequirement;
    public Integer iteration;

    Pharmacy(Integer id, String name, Integer dailyRequirement) {
        this.id = id;
        this.name = name;
        this.dailyRequirement = dailyRequirement;
        this.iteration = 0;
    }

    public static Pharmacy getPharmacyById(List<Pharmacy> pharmacyList, Integer id) {
        for (Pharmacy p : pharmacyList) {
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

    public Integer getDailyRequirement() {
        return dailyRequirement;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iteration=" + iteration +
                ", dailyRequirement=" + dailyRequirement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(id, pharmacy.id) &&
                Objects.equals(name, pharmacy.name) &&
                Objects.equals(dailyRequirement, pharmacy.dailyRequirement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dailyRequirement);
    }
}
