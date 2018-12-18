import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private File animalsDatabasePath;
    private File barnsDatabasePath;

    Database(){
        animalsDatabasePath = new File("src/database/animalsList.txt");
        barnsDatabasePath = new File("src/database/barnsList.txt");
    }

    public void createAnimalsDatabase() {
        if(!animalsDatabasePath.exists()){
            try {
                animalsDatabasePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createBarnsDatabase() {
        File animalsDataBase = new File("src/database/barnsList.txt");

        if(!animalsDataBase.exists()){
            try {
                animalsDataBase.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<String> returnListOfAllAnimals(){
        List<String> animalList = null;

        try {
            createAnimalsDatabase();
            animalList = Files.readAllLines(Paths.get("src/database/animalsList.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animalList;
    }

    public List<Animal> presentAnimalsAsObjects(){
        List<Animal> animalsObjectsList = null;
        List<String> animalList = returnListOfAllAnimals();

        if(animalList.size() > 0) {
            for (int i = 0; i < animalList.size(); i++) {
                String[] animal = returnListOfAllAnimals().get(i).split(",");
                animalsObjectsList.get(i).setType(animal[0]);
                animalsObjectsList.get(i).setAge(Integer.parseInt(animal[1]));
                animalsObjectsList.get(i).setVaccinated(Boolean.valueOf(animal[2]));
                animalsObjectsList.get(i).setBarnId(Integer.parseInt(animal[3]));
            }
        }

        return animalsObjectsList;
    }


    public List<String> returnListOfAllBarns(){
        List<String> barnsList = null;

        try {
            createBarnsDatabase();
            barnsList = Files.readAllLines(Paths.get("src/database/barnsList.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barnsList;
    }


    public List<Barn> returnBarnsAsObjects(){
        List<Barn> barnsObjectsList = new ArrayList<>();
        List<String> barnsList = returnListOfAllBarns();

        if(barnsList != null && !barnsList.isEmpty()) {
            for (int i = 0; i < barnsList.size(); i++) {
                String[] barnProperties = barnsList.get(i).split(",");

                barnsObjectsList.add(new Barn(barnProperties[0], Integer.parseInt(barnProperties[1])));
            }
        }

        System.out.println( barnsObjectsList);

        return barnsObjectsList;

    }

    public void addAnimalToDatabase(Animal animal) {
        try {
            createAnimalsDatabase();
            Files.write(animalsDatabasePath.toPath(), (animal.toString() +"\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addBarnToDatabase(Barn barn) {
        try {
            createBarnsDatabase();
            Files.write(barnsDatabasePath.toPath(), (barn.toString()+"\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
