import java.util.*;
import java.util.stream.Collectors;


class Farmer {
    private Scanner scanner;
    private DatabaseUtils databaseUtils;

    Farmer() {
        this.scanner = new Scanner(System.in);
        this.databaseUtils = new DatabaseUtils();
    }

    void addAnimal() {
        databaseUtils.addAnimalToDatabase(createAnimal());
        System.out.println("Nowe zwierzę zostało dodane.");
    }

    void removeAnimal() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();

        if (animalsList != null && !animalsList.isEmpty()) {
            databaseUtils.removeAnimalFromDataBase(passNewAnimalListToBeingStoredInDatabase(animalsList));
            System.out.println("Zwierzęta zostały usunięte z farmy!");
        } else {
            System.out.println("Na farmie nie ma żadnego zwierzęcia, które mogłoby zostać usunięte!");
        }
    }

    void addBarn() {
        databaseUtils.addBarnToDatabase(createBarn());
        System.out.println("Nowa stodoła została dodana.");
    }

    void removeBarn() {
        List<Barn> barnsList = databaseUtils.returnBarnsAsObjects();

        if (barnsList != null && !barnsList.isEmpty()) {
            databaseUtils.removeBarnFromDataBase(passNewBarnsListToBeingStoreInDatabase(barnsList));
            System.out.println("Stodoła została usunięta!");
        } else {
            System.out.println("Nie ma żadnej stodoły, którą można byłoby usunąć!");
        }
    }

    void return5OldestAnimals() {
        List<String> animalsReversedSortedListByAge = getAnimalsReversedSortedListByAge();
        String OLDEST_ANIMALS_TEXT = "Najstarsze zwierzęta na farmie to: ";

        printInformationAboutOldestAndYoungestAnimals(animalsReversedSortedListByAge, OLDEST_ANIMALS_TEXT);
    }

    private void printInformationAboutOldestAndYoungestAnimals(List<String> animalsSortedListByAge, String information) {
        if (animalsSortedListByAge.isEmpty()) {
            System.out.println("Nie ma żadnych zwierząt na farmie!");

        } else if (animalsSortedListByAge.size() < 5) {
            System.out.println(information);

            for (String animalName : animalsSortedListByAge) {
                System.out.println(animalName);
            }
        } else {
            System.out.println(information);
            for (int i = 0; i <= 5; i++) {
                System.out.println(animalsSortedListByAge.get(i));
            }
        }
    }

    private List<String> getAnimalsReversedSortedListByAge() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();

        return animalsList.stream()
                .sorted(Comparator.comparing(Animal::getAge).reversed())
                .map(Animal::getType)
                .collect(Collectors.toList());
    }

    void return5YoungestAnimals() {
        List<String> animalsSortedListByAge = getAnimalsSortedListByAge();
        String YOUNGEST_ANIMALS_TEXT = "Najmłodsze zwierzęta na farmie to: ";

        printInformationAboutOldestAndYoungestAnimals(animalsSortedListByAge, YOUNGEST_ANIMALS_TEXT);
    }

    private List<String> getAnimalsSortedListByAge() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();

        return animalsList.stream()
                .sorted(Comparator.comparing(Animal::getAge))
                .map(Animal::getType)
                .collect(Collectors.toList());
    }

    void returnBarnWithGreatestAnimalsNumber() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();
        List<Barn> barnsList = databaseUtils.returnBarnsAsObjects();
        Map<Integer, Integer> animalsCountMap = new HashMap<>();

        if (animalsList != null && !animalsList.isEmpty()) {
            for (Animal animal : animalsList) {
                int barnsId = animal.getBarnId();
                if (animalsCountMap.containsKey(barnsId)) {
                    animalsCountMap.put(barnsId, animalsCountMap.get(barnsId) + 1);
                } else {
                    animalsCountMap.put(barnsId, 1);
                }
            }

            Map<Integer, Integer> animalsCountSortedMap = returnReverseOrderSortedMap(animalsCountMap);

            int barnIdWithMostAnimal = animalsCountSortedMap.entrySet().iterator().next().getKey();

            String barnNameWithMostAnimal = "";

            for (Barn barn : barnsList) {
                if (barn.getId() == barnIdWithMostAnimal) {
                    barnNameWithMostAnimal = barn.getName();
                    break;
                }
            }

            System.out.println("Stodoła z największą ilością zwierząt to: " + barnNameWithMostAnimal);

        } else {
            System.out.println("Lista zwierząt jest pusta!");
        }
    }

    void returnMostPopularAnimal() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();
        Map<String, Integer> animalCountMap = new HashMap<>();

        if (animalsList != null && !animalsList.isEmpty()) {
            for (Animal animal : animalsList) {
                String animalType = animal.getType();
                if (animalCountMap.containsKey(animalType)) {
                    animalCountMap.put(animalType, animalCountMap.get(animalType) + 1);
                } else {
                    animalCountMap.put(animalType, 1);
                }
            }

            Map<String, Integer> sortedList = returnReverseOrderSortedMap(animalCountMap);

            System.out.println("Najliczniejszy gatunek to: " + sortedList.entrySet().iterator().next().getKey());

        } else {
            System.out.println("Lista zwierząt jest pusta!");
        }
    }

    private <K, V extends Comparable<? super V>> Map<K, V> returnReverseOrderSortedMap(Map<K, V> animalCountMap) {
        return animalCountMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    void returnAllVaccinatedAnimals() {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();
        List<String> vaccinatedAnimalsList = new ArrayList<>();

        if (animalsList != null && !animalsList.isEmpty()) {
            for (Animal animal : animalsList) {
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

            Barn barn = createBarn();
            databaseUtils.addBarnToDatabase(barn);

            return barn.getId();
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
            System.out.println("Wprowadziłeś nieprawidłową wartość. Wartość musi być 'true' albo 'false'!");
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
        List<Barn> barnsList = databaseUtils.returnBarnsAsObjects();

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


    private List<Animal> passNewAnimalListToBeingStoredInDatabase(List<Animal> animalsList) {
        System.out.println("Podaj nazwę zwierzęcia do usunięcia.");
        String animalToRemove = validateAnimalToRemove();

        return animalsList.stream()
                .filter(e -> !e.getType().equals(animalToRemove))
                .collect(Collectors.toList());
    }

    private String validateAnimalToRemove() {
        List<String> allAvailableAnimals = databaseUtils.returnAllAddedAnimalTypes();
        String animalToRemove = scanner.next();

        while (!allAvailableAnimals.contains(animalToRemove)) {
            System.out.println("Nie posiadasz podanego zwierzęcia! Wpisz zwierzę z listy: " + allAvailableAnimals);
            animalToRemove = scanner.next();
        }

        return animalToRemove;
    }

    private List<Barn> passNewBarnsListToBeingStoreInDatabase(List<Barn> barnsList) {
        List<Animal> animalList = databaseUtils.returnAnimalsAsObjects();

        System.out.println("Podaj id stodoły do usunięcia.");
        int barnIdToRemove = validateBarnIdToRemove();

        List<Barn> barnListAfterRemoval = barnsList.stream()
                .filter(e -> e.getId() != barnIdToRemove)
                .collect(Collectors.toList());

        List<Animal> animalsBeingAssignToBarnForRemoval = returnAnimalAssignToBarnForRemoval(animalList, barnIdToRemove);
        selectAnimalOptionAfterRemovingBarn(barnIdToRemove, animalsBeingAssignToBarnForRemoval);

        return barnListAfterRemoval;
    }

    private List<Animal> returnAnimalAssignToBarnForRemoval(List<Animal> animalList, int barnIdToRemove) {
        return animalList.stream()
                .filter(e -> e.getBarnId() == barnIdToRemove)
                .collect(Collectors.toList());
    }

    private void selectAnimalOptionAfterRemovingBarn(int barnIdToRemove, List<Animal> animalBeingAssignToBarnToRemove) {
        System.out.println("barns list size: " + returnBarnsIdList().size());
        if (returnBarnsIdList().size() > 1 && !animalBeingAssignToBarnToRemove.isEmpty()) {
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
        databaseUtils.removeAnimalFromDataBase(changeAnimalBarnIdForAnimalsFromBarnToRemove(barnIdToRemove));
        System.out.println("Zwierzęta zostaly przypisane do nowej farmy!");
    }

    private void removeAnimalFromDataBaseAfterRemovingBarn(int barnIdToRemove) {
        databaseUtils.removeAnimalFromDataBase(removeAnimalAssignToBarnForRemoval(barnIdToRemove));
        System.out.println("Zwierzęta umieszczone w stodole zostały usunięte!");
    }

    private List<Animal> changeAnimalBarnIdForAnimalsFromBarnToRemove(int barnIdToRemove) {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();
        System.out.println("Podaj nowy numer stodoły, do której zostaną przypisane zwierzęta!");
        int id = validateBarnIdInputUsedToTransferAnimalWhileDeletingBarn(barnIdToRemove);

        return animalsList.stream()
                .peek(e -> {
                    if (e.getBarnId() == barnIdToRemove) {
                        e.setBarnId(id);
                    }
                }).collect(Collectors.toList());
    }

    private List<Animal> removeAnimalAssignToBarnForRemoval(int barnIdToRemove) {
        List<Animal> animalsList = databaseUtils.returnAnimalsAsObjects();

        return animalsList.stream()
                .filter(e -> e.getBarnId() != barnIdToRemove)
                .collect(Collectors.toList());
    }

    private int validateBarnIdToRemove() {
        List<Integer> availableBarnsIdList = returnBarnsIdList();

        int barnIdToRemove = scanner.nextInt();

        while (!availableBarnsIdList.contains(barnIdToRemove)) {
            System.out.println("Stodoła o podanym id nie istnieje! Wybierz id z listy: " + availableBarnsIdList);

            barnIdToRemove = scanner.nextInt();
        }

        return barnIdToRemove;
    }
}
