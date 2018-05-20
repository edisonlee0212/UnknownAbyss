import java.util.Random;
/*
    You should only change values in Client constructor.
 */
public class Client {
    static long seed = 2;
    //All the comments below are based on seed = 2.
    static Random random = new Random(seed);
    private Galaxy galaxy;
    private Science science;
    public static void main(String[] args){
        Client client = new Client();
    }
    private Client(){
        //Below are settings for galaxy builder.
        /*Changeable*/int maxPlanetAmount = 15;
        /*Changeable*/int plantarySystemAmount = 100; //Do not exceed 100000.
        /*Changeable*/int averageDistance = 1000;
        /*Changeable*/int minStarDistance = 200;
        /*Changeable*/int elementAmount = 118;
        /*Changeable*/int maxScienceTreeLevel = 9;
        /*Changeable*/int maxScienceTreeBranch = 3;
        //==============================================================================================================
        //Below are the settings for information printers.
        /*Changeable*/int planetReferenceForPrint = 95;
                    /*Planet reference = planet number(max 15) + 15 * planetary system number.
                    Exp. 17 = 2 + 1 * 15 -> The second planet on the first planetary system.
                    Try 118, this will leads to undefined reference, since not all planetary system has 15 planets.
                    */
        /*Changeable*/int resourceListSizeForPrint = 10; //Do not exceed 25. Set to 0 for the complete list.
        /*Changeable*/int scienceTreePrinterStartingIndex = 100;
        /*Changeable*/int scienceTreePrinterNodeAmount = 10;
        /*Changeable*/int amountForPlanetDefPrinter = 10;
        //==============================================================================================================
        galaxy = new Galaxy(plantarySystemAmount, averageDistance, minStarDistance, maxPlanetAmount, elementAmount);
        science = new Science(elementAmount, maxScienceTreeLevel, maxScienceTreeBranch);
        //==============================================================================================================
        planetFullDescription(resourceListSizeForPrint, planetReferenceForPrint, maxPlanetAmount, plantarySystemAmount);
        //==============================================================================================================
        scienceDescription(scienceTreePrinterStartingIndex,scienceTreePrinterNodeAmount);
        //==============================================================================================================
        System.out.println("Here is a part  of a list of the descriptions of the planetary system, sorted by their distance to the center of the galaxy:");
        galaxy.printPlanetByDistance(amountForPlanetDefPrinter);
    }

    private void scienceDescription(int start, int amount){
        System.out.println("The science tree generator has generated " + science.countNode() + " science nodes.");
        if(amount == 0) {
            System.out.println("Here is a full list of the science tree:");
            science.printTree();
        }else if((start + amount) < science.getNodeAmount()){
            System.out.println("Here are the " + amount + " branch(es) of the science tree, starting from the No." + start + " branch:");
            science.printTree(start, amount);
        }else{
            System.out.println("Index out of bound.");
        }
    }

    private void planetFullDescription(int resourceListSize, int planetReference, int maxPlanetAmount, int plantarySystemAmount){
        if(planetReference >= plantarySystemAmount * maxPlanetAmount){
            System.out.println("The number is too large, you may try another number between 0 and " + (plantarySystemAmount * maxPlanetAmount - 1));
            return;
        }
        int starReference = planetReference/maxPlanetAmount;
        int planetNo = planetReference % maxPlanetAmount;
        if(galaxy.getPlantarySystems().get(starReference).getPlanets().size() > planetNo) {
            System.out.println(galaxy.getPDescriptionByRef(planetReference));
            System.out.println(galaxy.getSSDescriptionByRef(starReference));
            System.out.print("The above star system has a star. ");
            System.out.print(galaxy.getPlantarySystems().get(starReference).getStar().getDescription());
            System.out.println(galaxy.getPlantarySystems().get(starReference).getPlanets().get(planetNo).getResourcesDescription(resourceListSize));
        }else{
            int temp = planetReference - planetNo;
            int amount =  galaxy.getPlantarySystems().get(starReference).getPlanetAmount();
            if(amount > 1) System.out.println("There's an error with the reference. No planet was found.\n\tYou may try another number between: " + temp + " and " + (temp + amount - 1) + ".");
            else if(amount == 1){
                System.out.println("There's an error with the reference. No planet was found.\n\tYou may try another number like: " + temp + ".");
            }else {
                System.out.println("There's no planet in the target system, no planet was found.");
                System.out.println("You can try another number besides " + temp + " and " + (temp + 14) + ".");
            }
        }
    }

}
