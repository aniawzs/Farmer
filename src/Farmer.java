import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Farmer {
    private List<Barn> barnsList;
    private Scanner scanner;
    private Database database;

    Farmer(){
        this.barnsList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.database = new Database();
    }

    public void addAnimal() {
        database.addAnimalToDatabase(createAnimal());
        System.out.println("Nowe zwierzę zostało dodane.");
    }

    public void removeAnimal() {

    }

    public void addBarn() {
        database.addBarnToDatabase(createBarn());
        System.out.println("Nowa stodoła została dodana.");
    }

    public void removeBarn() {
    }

    public void return5OldestAnimals() {

    }


    public void return5YoungestAnimals() {
    }

    public void returnBarnWithGreatestAnimalsNumber() {
    }

    public void returnMostPopularAnimal() {
    }

    public void returnAllVaccinatedAnimals() {
    }

    public Animal createAnimal() {
        System.out.println("Podaj gatunek zwierzęcia");
        String value = validateAnimalType();

        System.out.println("Podaj wiek zwierzęcia");
        int age = scanner.nextInt();

        System.out.println("Czy zwierze jest szczepione? true/false?");
        String isAnimalVaccinated = validateVaccinatedInput();

        System.out.println("Podaj numer stodoły, do której zostanie przypisane zwierzę");
        int barnId = validateBarnIdInputWhileCreatingAnimal();

        return new Animal(value, age, Boolean.valueOf(isAnimalVaccinated), barnId);
    }

    private int validateBarnIdInputWhileCreatingAnimal() {
        int barnId = scanner.nextInt();
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        if(availableBarnsIdList == null || availableBarnsIdList.isEmpty()){
            System.out.println("Nie masz stodoły, do której możesz dodać zwierzę. Dodaj najpierw stodołę!");
            addBarn();
        }

        while(!availableBarnsIdList.contains(barnId)){
            System.out.println("Podaj prawidłowe id stodoły z listy: " + availableBarnsIdList);
            barnId = scanner.nextInt();
        }

        return barnId;
    }

    private String validateVaccinatedInput() {
        String input = scanner.next();

        while(!input.equals("true") && !input.equals("false")){
            System.out.println("Wprowadziłeś nieprawidłową wartość. Wartość musi być 'true' albo 'false'");
            input = scanner.next();
        }

        return input;
    }

    private String validateAnimalType(){
        String value = scanner.next();
        List<String> availableAnimalList = Animal.returnAnimalTypeList();

        while(!availableAnimalList.contains(value)){
            System.out.println("Wprowadziłeś nieprawidłową nazwę zwierzęcia. Dostępne opcje: " + availableAnimalList);
            value = scanner.next();
        }

        return value;
    }

    private List<Integer> returnBarnsIdList (){
        List<Integer> barnsIdList = new ArrayList<>();
        List<Barn> barnsList = database.returnBarnsAsObjects();

        if(barnsList != null && !barnsList.isEmpty()){
            for(Barn barn:barnsList){
                barnsIdList.add(barn.getId());
            }
        }

        return barnsIdList;
    }


    private Barn createBarn() {
        System.out.println("Podaj nazwę stodoły.");
        String name = scanner.next();

        System.out.println("Podaj id stodoły.");
        int id = validateBarnIdWhileCreatingNewBarn();

        return new Barn(name, id);
    }

    private int validateBarnIdWhileCreatingNewBarn() {
        int barnId = scanner.nextInt();
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        if(availableBarnsIdList == null || availableBarnsIdList.isEmpty()){
            return barnId;
        } else {
            while(availableBarnsIdList.contains(barnId)){
                System.out.println("Podany numer id został już użyty. Podaj inny numer!");
                barnId = scanner.nextInt();
            }
        }

        return barnId;
    }

}
