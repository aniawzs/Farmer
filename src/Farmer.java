import java.util.*;
import java.util.stream.Collectors;


class Farmer {
    private Scanner scanner;
    private DatabaseUtils database;

    Farmer() {
        this.scanner = new Scanner(System.in);
        this.database = new DatabaseUtils();
    }

    void addAnimal() {
        database.addAnimalToDatabase(createAnimal());
        System.out.println("Nowe zwierzę zostało dodane.");
    }

    void removeAnimal() {
        //dodać walidację, że nie da się usunąć zwierząt gdy ich nie ma
        database.removeAnimalFromDataBase(passNewAnimalListToBeingStoreInDatabase());
        System.out.println("Zwierzęta zostały usunięte z farmy!");

    }

    void addBarn() {
        database.addBarnToDatabase(createBarn());
        System.out.println("Nowa stodoła została dodana.");
    }

    void removeBarn() {
        //dodać walidację, że nie da się usunąć farmy gdy nie ma żadnej dodanej
        database.removeBarnFromDataBase(passNewBarnsListToBeingStoreInDatabase());
        System.out.println("Stodoła została usunięta!");
    }

    void return5OldestAnimals() {

    }

    void return5YoungestAnimals() {
    }

    void returnBarnWithGreatestAnimalsNumber() {
        List<Animal> animalList = database.returnAnimalsAsObjects();
        List<Barn> barnsList = database.returnBarnsAsObjects();

        Map<Integer, Integer> animalCountMap = new HashMap<>();
        if (!animalList.isEmpty()) {
            for (Animal animal : animalList) {
                int barnsId = animal.getBarnId();
                if (animalCountMap.containsKey(barnsId)) {
                    animalCountMap.put(barnsId, animalCountMap.get(barnsId) + 1);
                } else {
                    animalCountMap.put(barnsId, 1);
                }
            }

            Map<Integer, Integer> sortedMap = returnSortedMap(animalCountMap);

            int barnIdWithMostAnimal = sortedMap.entrySet().iterator().next().getKey();

            String barnNameWithMostAnimal = "";

            for (Barn barn : barnsList) {
                if (barn.getId() == barnIdWithMostAnimal) {
                    barnNameWithMostAnimal = barn.getName();
                    break;
                }
            }

            System.out.println("Stodoła z największą ilością zwierząt to: " + barnNameWithMostAnimal);

        } else {
            System.out.println("List zwierząt jest pusta!");
        }
    }

    void returnMostPopularAnimal() {
        List<Animal> animalList = database.returnAnimalsAsObjects();
        Map<String, Integer> animalCountMap = new HashMap<>();

        if (!animalList.isEmpty()) {
            for (Animal animal : animalList) {
                String animalType = animal.getType();
                if (animalCountMap.containsKey(animalType)) {
                    animalCountMap.put(animalType, animalCountMap.get(animalType) + 1);
                } else {
                    animalCountMap.put(animalType, 1);
                }
            }

            Map<String, Integer> sortedList = returnSortedMap(animalCountMap);

            System.out.println("Najliczniejszy gatunek to: " + sortedList.entrySet().iterator().next().getKey());

        } else {
            System.out.println("List zwierząt jest pusta!");
        }
    }

    private <K, V extends Comparable<? super V>> Map<K, V> returnSortedMap(Map<K, V> animalCountMap) {
        return animalCountMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    void returnAllVaccinatedAnimals() {
        List<Animal> animalList = database.returnAnimalsAsObjects();
        List<String> vaccinatedAnimalsList = new ArrayList<>();

        if (!animalList.isEmpty()) {
            for (Animal animal : animalList) {
                if (animal.isVaccinated()) {
                    vaccinatedAnimalsList.add(animal.getType());
                }
            }
            System.out.println("Lista wszystkich zaszczepionych zwierząt na farmie: " + vaccinatedAnimalsList);
        } else {
            System.out.println("Na farmie nie ma żadnych zwierząt!");
        }
    }

    private Animal createAnimal() {
        System.out.println("Podaj gatunek zwierzęcia");
        String value = validateAnimalType();

        System.out.println("Podaj wiek zwierzęcia");
        int age = validateAnimalAge();

        System.out.println("Czy zwierze jest szczepione? true/false?");
        String isAnimalVaccinated = validateVaccinatedInput();

        System.out.println("Podaj numer stodoły, do której zostanie przypisane zwierzę");
        int barnId = validateBarnIdInputWhileCreatingAnimal();

        return new Animal(value, age, Boolean.valueOf(isAnimalVaccinated), barnId);
    }

    private int validateAnimalAge() {
        int animalAge = scanner.nextInt();

        while (animalAge <= 0) {
            System.out.println("Wiek zwierzęcia nie może być mniejszy lub równy 0!");
            animalAge = scanner.nextInt();
        }
        return animalAge;
    }

    private int validateBarnIdInputWhileCreatingAnimal() {
        int barnId = scanner.nextInt();
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        if (availableBarnsIdList == null || availableBarnsIdList.isEmpty()) {
            System.out.println("Nie masz stodoły, do której możesz dodać zwierzę. Dodaj najpierw stodołę!");
            return createBarn().getId();
        }

        while (!availableBarnsIdList.contains(barnId)) {
            System.out.println("Podaj prawidłowe id stodoły z listy: " + availableBarnsIdList);
            barnId = scanner.nextInt();
        }

        return barnId;
    }

    private int validateBarnIdInputUsedToTransferAnimalWhileDeletingBarn(int barnIdToRemove) {
        int barnId = scanner.nextInt();
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        while (barnId == barnIdToRemove || !availableBarnsIdList.contains(barnId)) {
            System.out.println("Nie możesz przypisać zwierząt do stodoły, która jest do usunięcia lub tej, której nie ma!\n" +
                    "Użyj id z listy:" + returnBarnsIdList());

            barnId = scanner.nextInt();
        }

        return barnId;
    }

    private String validateVaccinatedInput() {
        String input = scanner.next();

        while (!input.equals("true") && !input.equals("false")) {
            System.out.println("Wprowadziłeś nieprawidłową wartość. Wartość musi być 'true' albo 'false'");
            input = scanner.next();
        }

        return input;
    }

    private String validateAnimalType() {
        String value = scanner.next();
        List<String> availableAnimalList = Animal.returnAnimalTypeList();

        while (!availableAnimalList.contains(value)) {
            System.out.println("Wprowadziłeś nieprawidłową nazwę zwierzęcia. Dostępne opcje: " + availableAnimalList);
            value = scanner.next();
        }

        return value;
    }

    private List<Integer> returnBarnsIdList() {
        List<Integer> barnsIdList = new ArrayList<>();
        List<Barn> barnsList = database.returnBarnsAsObjects();

        if (barnsList != null && !barnsList.isEmpty()) {
            for (Barn barn : barnsList) {
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

        if (availableBarnsIdList == null || availableBarnsIdList.isEmpty()) {
            return barnId;
        } else {
            while (availableBarnsIdList.contains(barnId)) {
                System.out.println("Podany numer id został już użyty. Podaj inny numer!");
                barnId = scanner.nextInt();
            }
        }

        return barnId;
    }


    private List<Animal> passNewAnimalListToBeingStoreInDatabase() {
        List<Animal> animalList = database.returnAnimalsAsObjects();
        List<Animal> animalListAfterRemoval = new ArrayList<>();

        if (animalList != null && !animalList.isEmpty()) {
            System.out.println("Podaj nazwę zwierzęcia do usunięcia.");
            String animalToRemove = validateAnimalToRemove();

            animalListAfterRemoval = animalList.stream()
                    .filter(e -> !e.getType().equals(animalToRemove))
                    .collect(Collectors.toList());

            System.out.println(animalListAfterRemoval);
        } else {
            System.out.println("Nie zostało żadne zwierzę, które mogłoby zostać usunięte!");
        }

        return animalListAfterRemoval;
    }

    private String validateAnimalToRemove() {
        List<String> allAvailableAnimals = database.returnAllAddedAnimalTypes();
        String animalToRemove = scanner.next();

        while (!allAvailableAnimals.contains(animalToRemove)) {
            System.out.println("Nie posiadasz podanego zwierzęcia! Wpisz zwierzę z listy: " + allAvailableAnimals);
            animalToRemove = scanner.next();
        }

        return animalToRemove;
    }

    private List<Barn> passNewBarnsListToBeingStoreInDatabase() {
        List<Barn> barnList = database.returnBarnsAsObjects();
        List<Barn> barnListAfterRemoval = new ArrayList<>();
        List<Animal> animalList = database.returnAnimalsAsObjects();

        if (barnList != null && !barnList.isEmpty()) {
            System.out.println("Podaj id stodoły do usunięcia.");

            int barnIdToRemove = validateBarnIdToRemove();

            barnListAfterRemoval.addAll(barnList.stream()
                    .filter(e -> e.getId() != barnIdToRemove)
                    .collect(Collectors.toList()));

            List<Animal> animalsBeingAssignToBarnForRemoval = returnAnimalAssignToBarnForRemoval(animalList, barnIdToRemove);

            selectAnimalOptionAfterRemovingBarn(barnIdToRemove, animalsBeingAssignToBarnForRemoval);
        }

        return barnListAfterRemoval;
    }

    private List<Animal> returnAnimalAssignToBarnForRemoval(List<Animal> animalList, int barnIdToRemove) {
        return animalList
                .stream()
                .filter(e -> e.getBarnId() == barnIdToRemove)
                .collect(Collectors.toList());
    }

    private void selectAnimalOptionAfterRemovingBarn(int barnIdToRemove, List<Animal> animalBeingAssignToBarnToRemove) {
        if (!animalBeingAssignToBarnToRemove.isEmpty() && returnBarnsIdList().size() > 1) {
            System.out.println("Co chcesz zrobić ze zwierzętami znajdującymi się w stodole? Wybierz jedną z opcji\n" +
                    "1.Usuń zwierzęta z farmy\n" +
                    "2.Przypisz zwierzęta znajdujące się w stodole do nowej");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    removeAnimalFromDataBaseAfterRemovingBarn(barnIdToRemove);
                    break;
                case 2:
                    changeBarnIdForAnimalsWhichBarnHasBeenRemoved(barnIdToRemove);
                    break;
            }
        } else if (!animalBeingAssignToBarnToRemove.isEmpty()) {
            removeAnimalFromDataBaseAfterRemovingBarn(barnIdToRemove);
        }
    }

    private void changeBarnIdForAnimalsWhichBarnHasBeenRemoved(int barnIdToRemove) {
        database.removeAnimalFromDataBase(changeAnimalBarnIdForAnimalsFromBarnToRemove(barnIdToRemove));
        System.out.println("Zwierzęta zostaly przypisane do nowej farmy!");
    }

    private void removeAnimalFromDataBaseAfterRemovingBarn(int barnIdToRemove) {
        database.removeAnimalFromDataBase(removeAnimalAssignToBarnForRemoval(barnIdToRemove));
        System.out.println("Zwierzęta umieszczone w stodole zostały usunięte!");
    }

    private List<Animal> changeAnimalBarnIdForAnimalsFromBarnToRemove(int barnIdToRemove) {
        List<Animal> animalsList = database.returnAnimalsAsObjects();
        System.out.println("Podaj nowy numer stodoły, do której zostaną przypisane zwierzęta!");
        int id = validateBarnIdInputUsedToTransferAnimalWhileDeletingBarn(barnIdToRemove);

        return animalsList.stream().peek(e -> {
            if (e.getBarnId() == barnIdToRemove) {
                e.setBarnId(id);
            }
        }).collect(Collectors.toList());
    }

    private List<Animal> removeAnimalAssignToBarnForRemoval(int barnIdToRemove) {
        List<Animal> animalsList = database.returnAnimalsAsObjects();

        return animalsList.stream()
                .filter(e -> e.getBarnId() != barnIdToRemove).collect(Collectors.toList());
    }

    private int validateBarnIdToRemove() {
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        int barnIdToRemove = scanner.nextInt();

        while (availableBarnsIdList.contains(barnIdToRemove)) {
            System.out.println("Stodoła o podanym id nie istnieje! Wybierz id z listy: " + availableBarnsIdList);

            barnIdToRemove = scanner.nextInt();
        }

        return barnIdToRemove;
    }
}
