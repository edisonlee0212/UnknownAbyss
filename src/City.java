import java.util.List;

/*
The city support civilization by provide a place for its ppl to live.
The larger the city is constructed, the more resources and energy it will consume in each time period. Of course larger city will provide more space for more population.
We hope player will choose its size smartly. Not too big, not too small.
There can be multiple cities on a planet.
 */
class City extends Infrastructure {
    double supportPopulation;
    double activePopulation;
    List<Resource> resourcesUsage;


    City(double defense, double strength, List<Technology> technologies, double energyUsage, int technologyLevel, double workers) {
        super(defense, strength, technologies, energyUsage, technologyLevel, workers);
    }
}
