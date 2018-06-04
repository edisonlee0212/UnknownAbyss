import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Galaxy {
    private int plantarySystemAmount; //Total amount of planetary system in the galaxy.
    private int maxBound; //Boundary for the galaxy.
    private int averageRange; //Average distance between closet stars
    private int distanceLevelAmount; //Same as element amount, this val actually means nothing, it helped resource generation.
    private double[] distanceLevel; //Store distance level.
    private int minStarDistance; //Min distance between the star and its nearest planet
    private int maxPlanetAmount; //Max planet amount within a planet system
    private int maxEnergyofStar; //The maximum energy of a star in any planetary system.
    private int minStarLife; //The maximum lifetime of a star. This helped the star energy radiation speed.
    private List<PlanetarySystem> plantarySystems; //Collection of plantary system.
    private List<PlanetarySystem> planetarySystemsSorted; //Sorted by the distance to the center of the galaxy.
    private Integer[] elementUsageList;

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
        elementUsageList = new Integer[elementAmount];
        this.buildElementUsageList();
        this.buildStarSystemList();
        this.buildSortedStarSystemList();
    }

    private void buildElementUsageList(){
        int usage = 0;
        int temp = 1;
        int usageAmount = Usage.values().length;
        for(int i = this.distanceLevelAmount - 1; i >= 0; --i) {
            for (int j = usageAmount; j > 0; --j) {
                usage += Client.random.nextInt(10) * temp;
                temp *= 10;
            }
            elementUsageList[i] = usage;
            usage = 0;
            temp = 1;
        }

    }

    void printPlanetListByDistance(int index, int amount) {
        if (amount == 0) {
            for (int i = 0; i < planetarySystemsSorted.size(); ++i) {
                System.out.println(planetarySystemsSorted.get(i).description);
            }
        } else {
            for (int i = index; i < planetarySystemsSorted.size() && i < amount + index; ++i) {
                System.out.println(planetarySystemsSorted.get(i).description);
            }
        }
    } //Printer for test.

    private void buildSortedStarSystemList() {
        this.planetarySystemsSorted.addAll(plantarySystems);
        boolean needSort = true;
        PlanetarySystem temp;
        while (needSort) {
            needSort = false;
            for (int i = 0; i < plantarySystemAmount - 1; ++i) {
                if (planetarySystemsSorted.get(i).centerDistance > planetarySystemsSorted.get(i + 1).centerDistance) {
                    temp = planetarySystemsSorted.get(i);
                    planetarySystemsSorted.set(i, planetarySystemsSorted.get(i + 1));
                    planetarySystemsSorted.set(i + 1, temp);
                    needSort = true;
                }
            }
        }
    }

    private void buildStarSystemList() {
        /*
        This method randomly choose certain amount of points in a sphere as the location of each planetary system.
        This method can further improved by implementing the idea of Perlin Noice, to create star clusters.
        For now since this is not very important and the Perlin Noice for 3D is too hard, the following method is just a basic random algorithm.
         */
        double gap = maxBound / Math.cbrt(distanceLevelAmount); //The gap and following loop helped make sure that the amount of planetary system is equal in each space separated by the distance level.
        for (int i = 0; i < distanceLevelAmount; ++i) {
            distanceLevel[i] = gap * Math.cbrt(i);
        }
        /*
        The following code is the main body of random generation. Noted that these code helped make sure that the star is in a sphere in which its volume is depends on the size of the galaxy.
         */
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
        /*
        The following codes is to help me find out the algorithm actually get what I want by calculating the actual "averageRange".
        Since these codes is useless but needed for test, I kept these codes in comments.
         */
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

    //Get description of planetary system.
    String getPSDescriptionByRef(int SSreference) {
        if (this.plantarySystemAmount <= SSreference / maxPlanetAmount)
            return "There's an error with the reference. No planetary system was found.";
        return this.plantarySystems.get(SSreference).getDescription();
    }
    //Get description of stars.
    String getSDescriptionByRef(int Sreference) {
        if (this.plantarySystemAmount <= Sreference / maxPlanetAmount)
            return "There's an error with the reference. No star was found.";
        return this.plantarySystems.get(Sreference).getStar().getDescription();
    }
    //Get description of planets.
    String getPDescriptionByCode(int planetCode) {
        if (this.plantarySystemAmount <= planetCode / maxPlanetAmount || this.plantarySystems.get(planetCode / maxPlanetAmount).getPlanetAmount() <= planetCode % maxPlanetAmount)
            return "There's an error with the reference. No planet was found.";
        String description = this.plantarySystems.get(planetCode / maxPlanetAmount).getPlanets().get(planetCode % maxPlanetAmount).getDescription();
        description += ", and it's belong to the plantary system named \"" + plantarySystems.get(planetCode / maxPlanetAmount).getName() + "\".";
        return description;
    }
    //Get description of planets.
    String getPDescriptionByReferences(int planetarySystemReference, int planetReference) {
        if (this.plantarySystems.size() <= planetarySystemReference)
            return "The reference for planetary system is too large.";
        if (this.plantarySystems.get(planetarySystemReference).planets.size() <= planetReference) {
            return "The reference for planet is too large. For the planetary system you chose, the max reference for the its planets is [" + (this.plantarySystems.get(planetarySystemReference).planets.size() - 1) + "].";
        }
        return this.plantarySystems.get(planetarySystemReference).planets.get(planetReference).description;
    }
    /*
    This is the inner class of the galaxy, its said that the relationship between inner class and outer class is like the nose and the face. And the father class and its child class, obviously, father and son.
     */
    class PlanetarySystem {
        private int reference; //Used to quickly find the planetary system.
        private int planetAmount; //Total amount of planets within this planetary system.
        private int centerDistance; //The distance between this system and the center of the galaxy, used to generate resources.
        private int centerDistanceLevel;
        private List<Planet> planets; //List of planets.
        private Star star;
        private int[] location; //Location of this system in the galaxy.
        private String name;
        private String description;
        private int technologyLevel;
        private Dock dock; //Dock, store everything on this planet's orbit.

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

        /*
        The following code helped generate the planet. There's a problem about the
         */
        private void buildPlanetsAndStar(long seed, int planetAmount) {
            //Planet generation.
            int planetReference = 0;
            int starDistance;
            for (int i = 0; i < planetAmount; i++) {
                starDistance = minStarDistance + Client.random.nextInt(minStarDistance * maxPlanetAmount + 1);
                this.planets.add(new Planet(planetReference, this.reference * maxPlanetAmount + planetReference, starDistance, this.centerDistance, this.centerDistanceLevel));
                planetReference++;
            }
            //Star generation.
            double efactor = 1.0 / maxPlanetAmount;
            efactor *= planetAmount;
            efactor *= 0.5 + 0.5 * (distanceLevelAmount - this.centerDistanceLevel) / distanceLevelAmount;
            double energy = efactor * maxEnergyofStar;
            double sfactor = minStarLife / efactor;
            double rspeed = energy / sfactor;
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
            private int code;
            private int centerDistance;
            private int centerDistanceLevel;
            private String name = "Undiscovered Planet";
            private String description = "";
            private int starDistance;
            private List<Resource> resources;
            private Dock dock;
            private Civilization civilization;
            private List<Infrastructure> infrastructures;
            Planet(int reference, int code, int starDistance, int centerDistance, int centerDistanceLevel) {
                this.reference = reference;
                this.code = code;
                this.starDistance = starDistance;
                this.centerDistance = centerDistance;
                this.centerDistanceLevel = centerDistanceLevel;
                this.setDescription();
                this.resources = new ArrayList<>();
                this.infrastructures = new ArrayList<>();
                generateResources();
            }

            int getReference() {
                return reference;
            }


            String getResourcesDescription(int counter) {
                StringBuilder result = new StringBuilder();
                if (counter != 0) {
                    result.append("This is a list of the first ").append(counter < resources.size() ? counter : resources.size()).append(" kind(s) of resources on the planet named \"").append(name).append("\":\n");
                    for (int i = 0; i < counter && i < resources.size(); ++i) {
                        result.append("Name: \"").append(resources.get(i).getName()).append("\", Code: [").append(resources.get(i).getReference());
                        result.append("].\n\t - Quentity: [").append(resources.get(i).getQuantity()).append("].\n");
                    }

                } else {
                    result.append("This is a total list of resources on the planet named \"").append(name).append("\":\n");
                    for (Resource resource : resources) {
                        result.append("|Name: ").append(resource.getName()).append(", \tCode: [").append(resource.getReference());
                        result.append("].\n|\t Quentity: ").append(resource.getQuantity()).append(".\n");
                    }
                }
                return result.toString();
            }

            void setDescription() {
                this.description += "This planet is \"" + this.name;
                this.description += "\". The reference number for this planet is [" + this.reference % maxPlanetAmount + "].";
                this.description += "\nThe code for this planet is [" + code + "].";
            }

            int getCode() {
                return code;
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

            /*
            The resource generation is based on the distance for the planetary system's distance to the center of the galaxy. The further the system is, the kind of higher rank of resource will be generated.
            This design will urge a civilization to colonize other systems in a certain direction.
            This means that the civilization which spawned in the center of the galaxy will try its best to conquer the outside world and vice versa.
            This also results in more counters, more conflicts.
            BTW, the civilization will have to work out a way to find the correct direction, the direction of the center of the galaxy is never shown to the players.
            BTW, the civilization will have to work out a way to find the correct direction, the direction of the center of the galaxy is never shown to the players.
            BTW, the civilization will have to work out a way to find the correct direction, the direction of the center of the galaxy is never shown to the players.
             */
            void generateResources() {
                int totalQuantity = Client.random.nextInt(Integer.MAX_VALUE - Integer.MAX_VALUE / 16) + (Integer.MAX_VALUE / 16 + 1);
                List<Integer> scale = new ArrayList<>();
                int currentAmount = Integer.MAX_VALUE / 2 + 1;
                int totalScale = 0;
                scale.add(Integer.MAX_VALUE / 2 + 1);
                int e;
                int rand = 0;
                while (currentAmount > 0) {
                    rand = Client.random.nextInt(100);
                    e = rand < 50 ? 2 : rand < 80 ? 3 : 4;
                    currentAmount /= e;
                    scale.add(currentAmount);
                    totalScale += currentAmount;
                }
                List<Double> quentityList = new ArrayList<>();
                for (Integer aScale : scale) {
                    quentityList.add((double) totalQuantity * aScale / totalScale);
                }

                int tempReference = centerDistanceLevel; //Most possible kind of element which will be the major part ot resources.
                int counter = 1;
                ArrayList<Integer> tempReferenceList = new ArrayList<>();
                for (Double i : quentityList) {
                    tempReferenceList.add(tempReference);
                    tempReference += counter % 2 == 0 ? counter : -counter;
                    counter++;
                    if (tempReference < 0 || tempReference >= distanceLevelAmount) {
                        tempReference += counter % 2 == 0 ? counter : -counter;
                        counter++;
                    }
                }
                List<Integer> shuffleListP1 = new ArrayList<>();
                List<Integer> shuffleListP2 = new ArrayList<>();
                List<Integer> shuffleListP3 = new ArrayList<>();
                for (int i = 0; i < tempReferenceList.size(); ++i) {
                    if (i < 5) shuffleListP1.add(tempReferenceList.get(i));
                    else if (i < 12) shuffleListP2.add(tempReferenceList.get(i));
                    else shuffleListP3.add(tempReferenceList.get(i));
                }
                Collections.shuffle(shuffleListP1, Client.random);
                Collections.shuffle(shuffleListP2, Client.random);
                Collections.shuffle(shuffleListP3, Client.random);
                List<Integer> finalReferenceList = new ArrayList<>();
                finalReferenceList.addAll(shuffleListP1);
                finalReferenceList.addAll(shuffleListP2);
                finalReferenceList.addAll(shuffleListP3);
                for (int i = 0; i < finalReferenceList.size(); ++i) {
                    if (quentityList.get(i) != 0)
                        resources.add(new Resource(finalReferenceList.get(i), quentityList.get(i)));
                }

            }
        }
    }
}
