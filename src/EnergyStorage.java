import java.util.List;
class EnergyStorage extends Infrastructure {
    double capacity;


    EnergyStorage(double defense, double strength, List<Technology> technologies, double energyUsage, int technologyLevel, double workers) {
        super(defense, strength, technologies, energyUsage, technologyLevel, workers);
    }
}
