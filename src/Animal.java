import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String type;
    private int age;
    private boolean isVaccinated;
    private int barnId;

    Animal(String type, int age, boolean isVaccinated, int barnId) {
        this.type = type;
        this.age = age;
        this.isVaccinated = isVaccinated;
        this.barnId = barnId;
    }

    int getBarnId() {
        return barnId;
    }

    void setBarnId(int barnId) {
        this.barnId = barnId;
    }

    public enum AnimalType {
        GOAT, SHEEP, PIGSCATTLE, DONKEYS, HORSE, YAK, LIAMA, PIG, MULE, ALPACA, MOOSE
    }

    String getType() {
        return type;
    }

    boolean isVaccinated() {
        return isVaccinated;
    }

    int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    static List<String> returnAnimalTypeList(){
        List<String> list = new ArrayList<>();

        for (AnimalType f : AnimalType.values()) {
            String s = f.toString();
            list.add(s);
        }
        return new ArrayList<>(list);
    }

    @Override
    public String toString() {
        return type + "," + age + "," + isVaccinated + "," + barnId;
    }
}
