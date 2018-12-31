
public class Barn {
    private String name;
    private int id;

    Barn(String name, int id){
        this.name = name;
        this.id = id;

    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  name + "," + id;
    }
}
