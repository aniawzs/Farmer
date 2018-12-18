import java.util.Scanner;

class Menu {
    private boolean isMenuOpen;
    private Farmer farmer;
    private Scanner scanner;

    Menu() {
        this.isMenuOpen = true;
        farmer = new Farmer();
        this.scanner = new Scanner(System.in);
    }


    void runMenu() {
        printProgramGreetings();

        while (isMenuOpen) {
            printMenuOptions();

            String input = scanner.next();

            switch (input) {
                case "1":
                    farmer.addAnimal();
                    break;
                case "2":
                    farmer.removeAnimal();
                    break;
                case "3":
                    farmer.addBarn();
                    break;
                case "4":
                    farmer.removeBarn();
                    break;
                case "5":
                    farmer.return5OldestAnimals();
                    break;
                case "6":
                    farmer.return5YoungestAnimals();
                    break;
                case "7":
                    farmer.returnBarnWithGreatestAnimalsNumber();
                    break;
                case "8":
                    farmer.returnMostPopularAnimal();
                    break;
                case "9":
                    farmer.returnAllVaccinatedAnimals();
                    break;
                case "10":
                    exitMenu();
                    break;

            }
        }
    }

    private void printMenuOptions() {
        System.out.println("Wybierz jedną z poniższych opcji:\n" +
                "1. Dodaj zwierzę \n" +
                "2. Usuń zwierzę \n" +
                "3. Dodaj stodołę \n" +
                "4. Usuń stodołę \n" +
                "5. Zobacz 5 najstarszych zwierząt \n" +
                "6. Zobacz 5 najmłodszych zwierząt \n" +
                "7. Zobacz stodołę z największą iloscią zwierząt\n" +
                "8. Zobacz najliczniejszy gatunek\n" +
                "9. Zobacz szczepione zwierzęta\n" +
                "10. Wyjdź z programu.");
    }

    private void exitMenu() {
        isMenuOpen = false;
    }

    private void printProgramGreetings(){
        System.out.println("Witaj w programie do zarządzania farmą!");
    }

}
