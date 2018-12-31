import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DatabaseUtils {

    private File animalsDatabaseFile;
    private File barnsDatabaseFile;

    DatabaseUtils(){
        animalsDatabaseFile = new File("src/database/animalsList.txt");
        barnsDatabaseFile = new File("src/database/barnsList.txt");
    }

    private void createAnimalsDatabase() {
        if(!animalsDatabaseFile.exists()){
            try {
                animalsDatabaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createBarnsDatabase() {
        if(!barnsDatabaseFile.exists()){
            try {
                barnsDatabaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> returnListOfAllAnimals(){
        List<String> animalList = null;

        try {
            createAnimalsDatabase();
            animalList = Files.readAllLines(animalsDatabaseFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animalList;
    }

    List<Animal> returnAnimalsAsObjects(){
        List<Animal> animalsObjectsList = new ArrayList<>();
        List<String> animalList = returnListOfAllAnimals();

        if(animalList != null && !animalList.isEmpty()) {
            for (int i = 0; i < animalList.size(); i++) {
                String[] animal = returnListOfAllAnimals().get(i).split(",");
                animalsObjectsList.add(new Animal(animal[0], Integer.parseInt(animal[1]), Boolean.valueOf(animal[2]),
                        Integer.parseInt(animal[3])));
            }
        }

        return animalsObjectsList;
    }

    private List<String> returnListOfAllBarns(){
        List<String> barnsList = null;

        try {
            createBarnsDatabase();
            barnsList = Files.readAllLines(barnsDatabaseFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barnsList;
    }

    List<Barn> returnBarnsAsObjects(){
        List<Barn> barnsObjectsList = new ArrayList<>();
        List<String> barnsList = returnListOfAllBarns();

        if(barnsList != null && !barnsList.isEmpty()) {
            for (String aBarnsList : barnsList) {
                String[] barnProperties = aBarnsList.split(",");

                barnsObjectsList.add(new Barn(barnProperties[0], Integer.parseInt(barnProperties[1])));
            }
        }
        return barnsObjectsList;
    }

    void addAnimalToDatabase(Animal animal) {
        try {
            createAnimalsDatabase();
            Files.write(animalsDatabaseFile.toPath(), (animal.toString() +"\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addBarnToDatabase(Barn barn) {
        try {
            createBarnsDatabase();
            Files.write(barnsDatabaseFile.toPath(), (barn.toString()+"\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<String> returnAllAddedAnimalTypes(){
        List<Animal> animalList = returnAnimalsAsObjects();

        return animalList.stream().map(Animal::getType).collect(Collectors.toList());
    }

    void removeAnimalFromDataBase(List<Animal> animalListAfterRemoval) {
        try {
            Files.newBufferedWriter(animalsDatabaseFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING);

            for(Animal animal:animalListAfterRemoval) {
                Files.write(animalsDatabaseFile.toPath(), (animal.toString() + "\r\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void removeBarnFromDataBase(List<Barn> barnListAfterRemoval) {
        try {
            Files.newBufferedWriter(barnsDatabaseFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING);

            for(Barn barn : barnListAfterRemoval) {
                Files.write(barnsDatabaseFile.toPath(), (barn.toString() + "\r\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
