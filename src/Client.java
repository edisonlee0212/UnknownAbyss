import java.util.Random;
import java.util.Scanner;

/*
    You should only change values in Client constructor.
 */
public class Client {
    static long seed;
    //All the comments below are based on seed = 2.
    //Same seed, same settings, exactly the same world. Any slightest difference will result in a complete new world.
    static Random random;
    static Scanner publicScanner;
    //Below are settings for galaxy builder.
    /*Changeable*/private int maxPlanetAmount;                //|Test 15| The up limit of the amount of planets within a planetary system.
    /*Changeable*/private int plantarySystemAmount;          //|Test 100|Do not exceed 100000.
    /*Changeable*/private int averageDistance;               //|Test 1000|The average distance of the nearest planetary system.
    /*Changeable*/private int minStarDistance;               //|Test 200|The minimum distance between the star and its nearest planet.
    /*Changeable*/private int elementAmount;                 //|Test 118|The total amount of chemical/resource element of the game. Only 0-117 has its name, but u can still define it as a number larger than 118.
    /*Changeable*/private int maxScienceTreeLevel;          //|Test 9|The maximum level amount of the science tree, the larger level amount, the larger the tree will be.
    /*Changeable*/private int maxScienceTreeBranch;         //|Test 3|The maximum branch amount of the star, the larger branch, the larger tree.
    /*Changeable*/private int maxEnergyofStar;              //|Test 10000000|The up limit of the total energy stored within a star. This should also be proportional to the mass of the star.
    /*Changeable*/private int minStarLife;                   //|Test 2000000|The minimum life time of the star, 1/time unit. The larger the star is, the shorter its life will be.
    //==============================================================================================================
    //Below are the settings for information printers.
    /*Changeable*/private int planetCodeForPrint;                      //|Test 95|
    /*Planet reference = planet number(max 15) + 15 * planetary system number.
    Exp. 17 = 2 + 1 * 15 -> The second planet on the first planetary system.
    Try 118, this will leads to undefined reference, since not all planetary system has 15 planets.
    */
    /*Changeable*/private int indexForPlanetDefPrinter;
    /*Changeable*/private int planetarySystemReferenceForPrint; //|Test 6|
    /*Changeable*/private int resourceListSizeForPrint; //|Test 10|Do not exceed 25. Set to 0 for the complete list.
    /*Changeable*/private int scienceTreePrinterStartingIndex; //|Test 100|
    /*Changeable*/private int scienceTreePrinterNodeAmount; //|Test 10|
    /*Changeable*/private int amountForPlanetDefPrinter; //|Test 10|
    private Galaxy galaxy;
    private Science science;
    private Scanner scanner;

    private Client() {
        scanner = new Scanner(System.in);
        System.out.println("Choose creation mode:");
        System.out.println("\tEnter 1 for No.1 mode, All settings are pre-defined.");
        System.out.println("\tEnter 2 for No.2 mode. You need to implement every setting.");
        System.out.println("\tEnter 3 for No.3 mode, You only change the planetary system's amount.");
        System.out.print("\tWaiting for input...");
        int mode = 1;
        if (scanner.hasNextInt()) {
            mode = scanner.nextInt();
        }
        System.out.println("You selected mode " + mode + ".");
        //Below are settings for galaxy builder.
        /*Changeable*/
        this.maxPlanetAmount = 15;
        /*Changeable*/
        this.plantarySystemAmount = 100;
        /*Changeable*/
        this.averageDistance = 1000;
        /*Changeable*/
        this.minStarDistance = 200;
        /*Changeable*/
        this.elementAmount = 118;
        /*Changeable*/
        this.maxScienceTreeLevel = 9;
        /*Changeable*/
        this.maxScienceTreeBranch = 3;
        /*Changeable*/
        this.maxEnergyofStar = 10000000;
        /*Changeable*/
        this.minStarLife = 2000000;
        /*Changeable*/
        this.indexForPlanetDefPrinter = 0;
        /*Changeable*/
        this.planetCodeForPrint = 95;
        /*Changeable*/
        this.planetarySystemReferenceForPrint = 6;
        /*Changeable*/
        this.resourceListSizeForPrint = 10;
        /*Changeable*/
        this.scienceTreePrinterStartingIndex = 100;
        /*Changeable*/
        this.scienceTreePrinterNodeAmount = 10;
        /*Changeable*/
        this.amountForPlanetDefPrinter = 10;
        if (mode != 1) {
            switch (mode) {
                case 2:
                    System.out.println("Please enter the planetary system's amount.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.plantarySystemAmount = scanner.nextInt();
                    System.out.println("Please enter the max amount of planet within a planetary system.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.maxPlanetAmount = scanner.nextInt();
                    System.out.println("Please enter the average distance of the nearest planetary system.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.averageDistance = scanner.nextInt();
                    System.out.println("Please enter the minimum distance between the star and its nearest planet.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.minStarDistance = scanner.nextInt();
                    System.out.println("Please enter the total amount of chemical/resource element of the game. Only 0-117 has its name, but u can still define it as a number larger than 118.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.elementAmount = scanner.nextInt();
                    System.out.println("Please enter the maximum level amount of the science tree, the larger level amount, the larger the tree will be.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.maxScienceTreeLevel = scanner.nextInt();
                    System.out.println("Please enter the maximum branch amount of the star, the larger branch, the larger tree.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.maxScienceTreeBranch = scanner.nextInt();
                    System.out.println("Please enter the up limit of the total energy stored within a star. This should also be proportional to the mass of the star.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.maxEnergyofStar = scanner.nextInt();
                    System.out.println("Please enter the minimum life time of the star, 1/time unit. The larger the star is, the shorter its life will be.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.minStarLife = scanner.nextInt();
                    break;
                case 3:
                    System.out.println("Please enter the planetary system's amount.");
                    System.out.print("Waiting for input...");
                    if (scanner.hasNextInt()) this.plantarySystemAmount = scanner.nextInt();
                    break;
            }
        }
        System.out.println("\nAll set. Now create the galaxy...");
        galaxy = new Galaxy(plantarySystemAmount, averageDistance, minStarDistance, maxPlanetAmount, elementAmount, maxEnergyofStar, minStarLife);
        science = new Science(elementAmount, maxScienceTreeLevel, maxScienceTreeBranch);
        System.out.println("\nCreation process complete. Now tell me what kinds of information you want to know.");
        boolean c = true;
        while (c) {
            System.out.println("Here is a list of information you can get, to get what you need, enter its number.");
            System.out.println("0. A demo of information printer.");
            System.out.println("1. Any planetary system basic information by its reference number.");
            System.out.println("2. Any planet basic information by its reference number and the number of the planetary system it belongs to.");
            System.out.println("3. A full information list of any planet by its code.(You can find the code from 2)");
            System.out.println("4. A list of branches for the current science tree.");
            System.out.println("5. A list of basic information of the planetary system, sorted by their distance");
            System.out.print("Waiting for input...");
            if (scanner.hasNextInt()) {
                int i, j, in;
                in = scanner.nextInt();
                System.out.println("You selected: " + in + ".");
                switch (in) {
                    case 0:
                        System.out.println(galaxy.getPlantarySystems().get(planetarySystemReferenceForPrint).getDescription());
                        planetFullDescription(resourceListSizeForPrint, planetCodeForPrint, maxPlanetAmount, plantarySystemAmount);
                        scienceDescription(scienceTreePrinterStartingIndex, scienceTreePrinterNodeAmount);
                        System.out.println("Here is a part of a list of the descriptions of the planetary system, sorted by their distance to the center of the galaxy:");
                        galaxy.printPlanetListByDistance(indexForPlanetDefPrinter, amountForPlanetDefPrinter);
                        break;
                    case 1:
                        i = 0;
                        System.out.println("Please enter the reference number for the planetary system.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) i = scanner.nextInt();
                        System.out.println(galaxy.getPlantarySystems().get(i).getDescription());
                        break;
                    case 2:
                        i = 0;
                        j = 0;
                        System.out.println("Please enter the reference number for the planetary system.");
                        if (scanner.hasNextInt()) i = scanner.nextInt();
                        System.out.println("Please enter the reference number for the planet.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) j = scanner.nextInt();
                        System.out.println(galaxy.getPDescriptionByReferences(i, j));
                        break;
                    case 3:
                        i = 0;
                        j = 0;
                        System.out.println("Please enter the code for the planet.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) i = scanner.nextInt();
                        System.out.println("Please enter the size of the resource list you want to get.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) j = scanner.nextInt();
                        planetFullDescription(j, i, maxPlanetAmount, plantarySystemAmount);
                        break;
                    case 4:
                        i = 0;
                        j = 0;
                        System.out.println("Please enter the starting index.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) i = scanner.nextInt();
                        System.out.println("Please enter the total amount of branch(es) you want to get.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) j = scanner.nextInt();
                        scienceDescription(i, j);
                        break;
                    case 5:
                        i = 0;
                        j = 0;
                        System.out.println("Please enter the starting index.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) i = scanner.nextInt();
                        System.out.println("Please enter the total amount of planetary system you want to get.");
                        System.out.print("Waiting for input...");
                        if (scanner.hasNextInt()) j = scanner.nextInt();
                        System.out.println("Here is a part of a list of the descriptions of the planetary system, sorted by their distance to the center of the galaxy:");
                        galaxy.printPlanetListByDistance(i, j);
                        break;
                }
            }
            System.out.println("Would you like to know some more? Enter 0 to continue, enter other number to quit.");
            System.out.print("Waiting for input...");
            if (scanner.hasNextInt()) c = scanner.nextInt() == 0;
        }
        scanner.close();
    }

    public static void main(String[] args) {
        publicScanner = new Scanner(System.in);
        System.out.println("Please enter the seed.");
        System.out.print("\tWaiting for input...");
        if (publicScanner.hasNextInt()) seed = publicScanner.nextInt();
        System.out.print("You entered the seed as " + seed + ".\n");
        random = new Random(seed);
        Client client = new Client();
    }

    private void scienceDescription(int start, int amount) {
        System.out.println("The science tree generator has generated " + science.countNode() + " science nodes.");
        if (amount == 0) {
            System.out.println("Here is a full list of the science tree:");
            science.printTree();
        } else if ((start + amount) < science.getNodeAmount()) {
            System.out.println("Here are the " + amount + " branch(es) of the science tree, starting from the No." + start + " branch:");
            science.printTree(start, amount);
        } else {
            System.out.println("Index out of bound.");
        }
    }

    private void planetFullDescription(int resourceListSize, int planetCode, int maxPlanetAmount, int plantarySystemAmount) {
        if (planetCode >= plantarySystemAmount * maxPlanetAmount) {
            System.out.println("The number is too large, you may try another number between 0 and " + (plantarySystemAmount * maxPlanetAmount - 1));
            return;
        }
        int starReference = planetCode / maxPlanetAmount;
        int planetNo = planetCode % maxPlanetAmount;
        if (galaxy.getPlantarySystems().get(starReference).getPlanets().size() > planetNo) {
            System.out.println(galaxy.getPDescriptionByCode(planetCode));
            System.out.println(galaxy.getSSDescriptionByRef(starReference));
            System.out.print("The above star system has a star. ");
            System.out.print(galaxy.getPlantarySystems().get(starReference).getStar().getDescription());
            System.out.println(galaxy.getPlantarySystems().get(starReference).getPlanets().get(planetNo).getResourcesDescription(resourceListSize));
        } else {
            int temp = planetCode - planetNo;
            int amount = galaxy.getPlantarySystems().get(starReference).getPlanetAmount();
            if (amount > 1)
                System.out.println("There's an error with the reference. No planet was found.\n\tYou may try another number between: " + temp + " and " + (temp + amount - 1) + ".");
            else if (amount == 1) {
                System.out.println("There's an error with the reference. No planet was found.\n\tYou may try another number like: " + temp + ".");
            } else {
                System.out.println("There's no planet in the target system, no planet was found.");
                System.out.println("You can try another number besides " + temp + " and " + (temp + 14) + ".");
            }
        }
    }

}
