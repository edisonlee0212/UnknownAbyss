import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Galaxy {
    private int plantarySystemAmount;
    private int maxBound; //Boundary for the galaxy.
    private int averageRange; //Average distance between closet stars
    private int distanceLevelAmount;
    private double[] distanceLevel;
    private int minStarDistance; //Min distance between the star and its nearest planet
    private int maxPlanetAmount; //Max planet amount within a planet system
    private int maxEnergyofStar;
    private int minStarLife;
    private List<PlanetarySystem> plantarySystems;
    private List<PlanetarySystem> planetarySystemsSorted;


    Galaxy(int plantarySystemAmount, int averageRange, int minStarDistance, int maxPlanetAmount, int elementAmount, int maxEnergyofStar, int minStarLife) {
        this.plantarySystems = new ArrayList<>();
        this.planetarySystemsSorted = new ArrayList<>();
        this.maxPlanetAmount = maxPlanetAmount;
        this.averageRange = averageRange;
        this.minStarDistance = minStarDistance;
        this.plantarySystemAmount = plantarySystemAmount;
        this.distanceLevelAmount = elementAmount;
        this.maxEnergyofStar = maxEnergyofStar;
        this.minStarLife = minStarLife;
        this.distanceLevel = new double[distanceLevelAmount];
        double length = Math.cbrt(plantarySystemAmount);
        this.maxBound = (int) length * averageRange;
        this.buildStarSystemList();
        this.buildSortedStarSystemList();
        //System.out.println(planetarySystemsSorted.size());
    }

    void printPlanetByDistance(int amount){
        if(amount == 0){
            for(int i = 0; i < planetarySystemsSorted.size(); ++i){
                System.out.println(planetarySystemsSorted.get(i).description);
            }
        }else {
            for(int i = 0; i < planetarySystemsSorted.size() && i < amount; ++i){
                System.out.println(planetarySystemsSorted.get(i).description);
            }
        }
    }
    private void buildSortedStarSystemList(){
        this.planetarySystemsSorted.addAll(plantarySystems);
        boolean needSort = true;
        PlanetarySystem temp;
        while(needSort){
            needSort = false;
            for(int i = 0; i < plantarySystemAmount - 1; ++i){
                if(planetarySystemsSorted.get(i).centerDistance > planetarySystemsSorted.get(i + 1).centerDistance){
                    temp = planetarySystemsSorted.get(i);
                    planetarySystemsSorted.set(i, planetarySystemsSorted.get(i + 1));
                    planetarySystemsSorted.set(i + 1, temp);
                    needSort = true;
                }
            }
        }
    }

    private void buildStarSystemList() {
        double gap = maxBound / Math.cbrt(distanceLevelAmount);
        for (int i = 0; i < distanceLevelAmount; ++i) {
            distanceLevel[i] = gap * Math.cbrt(i);
        }
        int x, y, z;
        int[][] xyz = new int[plantarySystemAmount][3];
        boolean valid = true;
        int counter = 0;
        double centerDistance;
        int centerDistanceLevel;
        double length = Math.cbrt(plantarySystemAmount);
        while (counter < plantarySystemAmount) {
            x = Client.random.nextInt(((int) (2 * averageRange * length)) + 1) - (int) (length * averageRange);
            y = Client.random.nextInt(((int) (2 * averageRange * length)) + 1) - (int) (length * averageRange);
            z = Client.random.nextInt(((int) (2 * averageRange * length)) + 1) - (int) (length * averageRange);
            if (Math.sqrt(x * x + y * y + z * z) > length * averageRange) valid = false;
            for (int i = 0; i < counter; ++i) {
                if (xyz[i][0] != x || xyz[i][1] != y || xyz[i][2] != z) {
                    continue;
                }
                valid = false;
            }
            if (valid) {
                xyz[counter][0] = x;
                xyz[counter][1] = y;
                xyz[counter][2] = z;
                centerDistance = Math.sqrt(x * x + y * y + z * z);
                centerDistanceLevel = 0;
                for (int j = 0; j < distanceLevelAmount - 1; ++j) {
                    if (centerDistance < distanceLevel[j]) break;
                    centerDistanceLevel++;
                }
                plantarySystems.add(new PlanetarySystem(counter, xyz[counter], (Client.random.nextInt(maxPlanetAmount + 1)), (int) centerDistance, centerDistanceLevel));
                counter++;
            }
            valid = true;
        }
        /*double distanceSumX = 0;
        double distanceSumY = 0;
        double distanceSumZ = 0;
        double distanceSum = 0;
        double min = 999999999;
        for(int i = 0; i < starSystemAmount; ++i) {
            for(int j = 0; j < starSystemAmount; ++j){
                if(i != j) {
                    distanceSumX = Math.abs(xyz[i][0] - xyz[j][0]);
                    distanceSumY = Math.abs(xyz[i][1] - xyz[j][1]);
                    distanceSumZ = Math.abs(xyz[i][2] - xyz[j][2]);
                    double temp = Math.sqrt(distanceSumX * distanceSumX + distanceSumY * distanceSumY + distanceSumZ * distanceSumZ);
                    if(temp < min) min = temp;
                }
            }
            distanceSum += min;
            min = 999999999;
        }
        distanceSum /= starSystemAmount;
        System.out.println("The real average distance is: " + distanceSum + ".");
        */

    }

    int getStarSystemAmount() {
        return plantarySystemAmount;
    }

    int getMaxBound() {
        return maxBound;
    }

    int getAverageRange() {
        return averageRange;
    }

    int getDistanceLevelAmount() {
        return distanceLevelAmount;
    }

    double[] getDistanceLevel() {
        return distanceLevel;
    }

    List<PlanetarySystem> getPlantarySystems() {
        return plantarySystems;
    }

    String getSSDescriptionByRef(int SSreference) {
        if (this.plantarySystemAmount <= SSreference / maxPlanetAmount)
            return "There's an error with the reference. No star system was found.";
        return this.plantarySystems.get(SSreference).getDescription();
    }

    String getSDescriptionByRef(int Sreference) {
        if (this.plantarySystemAmount <= Sreference / maxPlanetAmount)
            return "There's an error with the reference. No star was found.";
        return this.plantarySystems.get(Sreference).getStar().getDescription();
    }

    String getPDescriptionByRef(int Preference) {
        if (this.plantarySystemAmount <= Preference / maxPlanetAmount || this.plantarySystems.get(Preference / maxPlanetAmount).getPlanetAmount() <= Preference % maxPlanetAmount)
            return "There's an error with the reference. No planet was found.";
        String description = this.plantarySystems.get(Preference / maxPlanetAmount).getPlanets().get(Preference % maxPlanetAmount).getDescription();
        description += ", and it's belong to the plantary system named \"" + plantarySystems.get(Preference / maxPlanetAmount).getName() + "\".";
        return description;
    }

    class PlanetarySystem {
        private int reference;
        private int planetAmount;
        private int centerDistance;
        private int centerDistanceLevel;
        private List<Planet> planets;
        private Star star;
        private int[] location;
        private String name;
        private String description;
        private int technologyLevel;
        private Dock dock;

        PlanetarySystem(int reference, int[] location, int planetAmount, int centerDistance, int centerDistanceLevel) {
            this.planets = new ArrayList<>();
            this.location = new int[3];
            this.reference = reference;
            this.location = location;
            this.planetAmount = planetAmount;
            this.centerDistance = centerDistance;
            this.centerDistanceLevel = centerDistanceLevel;
            this.technologyLevel = 1;
            this.name = "";
            this.name += "SS" + reference;
            this.buildPlanetsAndStar(Client.seed + reference, planetAmount);
            this.setDescription();
        }

        private void buildPlanetsAndStar(long seed, int planetAmount) {
            //Planet generation.
            int planetReference = 0;
            int starDistance;
            for (int i = 0; i < planetAmount; i++) {
                starDistance = minStarDistance + Client.random.nextInt(minStarDistance * maxPlanetAmount + 1);
                this.planets.add(new Planet(this.reference * maxPlanetAmount + planetReference, starDistance, this.centerDistance, this.centerDistanceLevel));
                planetReference++;
            }
            //Star generation.
            double efactor = 1.0 / maxPlanetAmount;
            efactor *= planetAmount;
            efactor *= 0.5 + 0.5 * (distanceLevelAmount - this.centerDistanceLevel)/distanceLevelAmount;
            double energy = efactor * maxEnergyofStar;
            double sfactor = minStarLife/efactor;
            double rspeed = energy/sfactor;
            this.star = new Star(this.reference, energy, rspeed);
        }

        private void setDescription() {
            this.description = "";
            this.description += "The plantary system \"" + name;
            this.description += "\" with its reference number [" + reference;
            this.description += "] is located at [" + location[0] + ", " + location[1] + ", " + location[2];
            this.description += "], with [" + centerDistance;
            this.description += "] distance unit to the center of the galaxy. The distance level is [" + centerDistanceLevel;
            this.description += "]. The technology level here is Lv. [" + technologyLevel + "].";
        }

        List<Planet> getPlanets() {
            return planets;
        }

        Star getStar() {
            return star;
        }

        int getReference() {
            return reference;
        }

        int getPlanetAmount() {
            return planetAmount;
        }

        int getCenterDistance() {
            return centerDistance;
        }

        int getCenterDistanceLevel() {
            return centerDistanceLevel;
        }

        int[] getLocation() {
            return location;
        }

        String getName() {
            return name;
        }

        String getDescription() {
            return description;
        }

        int getTechnologyLevel() {
            return technologyLevel;
        }

        class Star {
            private int reference;
            private double energy;
            private String name;
            private String description;
            private double rspeed;
            private int starAmount = 1;
            private Dock dock;

            Star(int reference, double energy, double rspeed) {
                this.reference = reference;
                this.energy = energy;
                this.rspeed = rspeed;
                this.name = "";
                this.name = "S" + reference;
                setDescription();
            }

            void setDescription() {
                this.description = "";
                this.description += "The star No.[" + reference;
                this.description += "] is called \"" + name;
                this.description += "\".\nThe current total amount of energy of this star is [" + energy;
                this.description += "], and the energy flow is [" + rspeed + "].\n";
            }

            int getReference() {
                return reference;
            }

            double getEnergy() {
                return energy;
            }

            String getName() {
                return name;
            }

            String getDescription() {
                return description;
            }

            double getRspeed() {
                return rspeed;
            }

        }

        class Planet {
            private int reference;
            private int centerDistance;
            private int centerDistanceLevel;
            private String name = "Undiscovered Planet";
            private String description = "";
            private int starDistance;
            private List<Resource> resources = new ArrayList<>();
            private Dock dock;
            private Civilization civilization;

            Planet(int reference, int starDistance, int centerDistance, int centerDistanceLevel) {
                this.reference = reference;
                this.starDistance = starDistance;
                this.centerDistance = centerDistance;
                this.centerDistanceLevel = centerDistanceLevel;
                this.setDescription();
                generateResources();
            }

            String getResourcesDescription(int counter) {
                StringBuilder result = new StringBuilder();
                if(counter != 0){
                    result.append("This is a list of the first ").append(counter < resources.size() ? counter : resources.size()).append(" kind(s) of resources on the planet named \"").append(name).append("\":\n");
                    for (int i = 0; i < counter && i < resources.size(); ++i) {
                        result.append("Name: \"").append(resources.get(i).getName()).append("\", Code: [").append(resources.get(i).getReference());
                        result.append("].\n\t - Quentity: [").append(resources.get(i).getQuantity()).append("].\n");
                    }

                }else{
                    result.append("This is a total list of resources on the planet named \"").append(name).append("\":\n");
                    for (int i = 0; i < resources.size(); ++i) {
                        result.append("|Name: ").append(resources.get(i).getName()).append(", \tCode: [").append(resources.get(i).getReference());
                        result.append("].\n|\t Quentity: ").append(resources.get(i).getQuantity()).append(".\n");
                    }
                }
                return result.toString();
            }

            void setDescription() {
                this.description += "This planet is \"" + this.name;
                this.description += "\". The reference number for this planet is [" + this.reference % maxPlanetAmount + "]";
            }

            int getReference() {
                return reference;
            }

            int getCenterDistance() {
                return centerDistance;
            }

            int getCenterDistanceLevel() {
                return centerDistanceLevel;
            }

            String getName() {
                return name;
            }

            String getDescription() {
                return description;
            }

            Civilization getCivilization() {
                return civilization;
            }

            int getStarDistance() {
                return starDistance;
            }

            List<Resource> getResources() {
                return resources;
            }

            void generateResources() {
                int totalQuentity = Client.random.nextInt(Integer.MAX_VALUE - Integer.MAX_VALUE / 16) + (Integer.MAX_VALUE / 16 + 1);
                List<Integer> scale = new ArrayList<>();
                int currentAmount = Integer.MAX_VALUE / 2 + 1;
                int totalScale = 0;
                scale.add(Integer.MAX_VALUE / 2 + 1);
                int e;
                int rand = 0;
                while(currentAmount > 0){
                    rand = Client.random.nextInt(100);
                    e = rand < 50 ? 2 : rand < 80 ? 3 : 4;
                    currentAmount /= e;
                    scale.add(currentAmount);
                    totalScale += currentAmount;
                }
                    List<Double> quentityList = new ArrayList<>();
                for(int i = 0; i < scale.size(); ++i) {
                    quentityList.add((double)totalQuentity * scale.get(i) / totalScale);
                }

                int tempReference = centerDistanceLevel; //Most possible kind of element which will be the major part ot resources.
                int counter = 1;
                boolean[] referenceCaptureList = new boolean[distanceLevelAmount];
                ArrayList<Integer> tempReferenceList = new ArrayList<>();
                for(boolean i : referenceCaptureList){
                    i = true;
                }

                for(Double i : quentityList){
                    tempReferenceList.add(tempReference);
                    tempReference += counter % 2 == 0 ? counter : -counter;
                    counter++;
                    if(tempReference < 0 || tempReference >= distanceLevelAmount){
                        tempReference += counter % 2 == 0 ? counter : -counter;
                        counter++;
                    }
                }
                List<Integer> shuffleListP1 = new ArrayList<>();
                List<Integer> shuffleListP2 = new ArrayList<>();
                List<Integer> shuffleListP3 = new ArrayList<>();
                for(int i = 0; i < tempReferenceList.size(); ++i){
                    if(i < 5) shuffleListP1.add(tempReferenceList.get(i));
                    else if(i < 12)shuffleListP2.add(tempReferenceList.get(i));
                    else shuffleListP3.add(tempReferenceList.get(i));
                }
                Collections.shuffle(shuffleListP1, Client.random);
                Collections.shuffle(shuffleListP2, Client.random);
                Collections.shuffle(shuffleListP3, Client.random);
                List<Integer> finalReferenceList = new ArrayList<>();
                finalReferenceList.addAll(shuffleListP1);
                finalReferenceList.addAll(shuffleListP2);
                finalReferenceList.addAll(shuffleListP3);
                for(int i = 0; i < finalReferenceList.size(); ++i){
                    if(quentityList.get(i) != 0)resources.add(new Resource(finalReferenceList.get(i), quentityList.get(i)));
                }
                /*int upperBound = 4;
                int lowerBound = 1;
                int total = 0;
                List<Integer> tempResources = new ArrayList<>();
                for (int i = 0; i < 25; i++) { //Max 26 kinds of elements appear on any single planet. This is unchangeable due to the algorithm.
                    tempResources.add(Client.random.nextInt(upperBound - lowerBound + 1) + lowerBound);
                    upperBound *= 2;
                    lowerBound *= 2;
                    total += tempResources.get(i);
                }

                tempResources.add((Integer.MAX_VALUE / 16) - total);
                double factor = (double) totalQuentity / (Integer.MAX_VALUE / 16);
                double temp;
                List<Integer> resourceResult = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    temp = factor * tempResources.get(i);
                    resourceResult.add((int) temp);
                    totalQuentity -= (int) temp;
                }
                resourceResult.add(totalQuentity);*/


                /*List<Integer> elementReference = new ArrayList<>();
                List<Integer> elementReferenceT1 = new ArrayList<>();
                while (elementReferenceT1.size() < 3) {
                    if (tempReference >= 0 && tempReference < 26) {
                        elementReferenceT1.add(tempReference);
                    }
                    tempReference += counter % 2 == 0 ? counter : -1 * counter;
                    counter++;
                }
                List<Integer> elementReferenceT2 = new ArrayList<>();
                while (elementReferenceT2.size() < 8) {
                    if (tempReference >= 0 && tempReference < 26) {
                        elementReferenceT2.add(tempReference);
                    }
                    tempReference += counter % 2 == 0 ? counter : -1 * counter;
                    counter++;
                }
                List<Integer> elementReferenceT3 = new ArrayList<>();
                while (elementReferenceT3.size() < 15) {
                    if (tempReference >= 0 && tempReference < 26) {
                        elementReferenceT3.add(tempReference);
                    }
                    tempReference += counter % 2 == 0 ? counter : -1 * counter;
                    counter++;
                }
                Collections.shuffle(elementReferenceT1, Client.random);
                Collections.shuffle(elementReferenceT2, Client.random);
                Collections.shuffle(elementReferenceT3, Client.random);
                elementReference.addAll(elementReferenceT1);
                elementReference.addAll(elementReferenceT2);
                elementReference.addAll(elementReferenceT3);
                for (int i = 0; i < 26; i++) {
                    resources.add(new Resource(elementReference.get(i), resourceResult.get(i)));
                }*/
            }
        }
    }
}
