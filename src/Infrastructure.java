import java.util.List;

class Infrastructure {
    double defense;
    double strength;
    List<Technology> technologies;
    boolean online;
    double energyUsage;
    int technologyLevel;
    double workers;
    Infrastructure(double defense, double strength, List<Technology> technologies, double energyUsage, int technologyLevel, double workers) {
        this.defense = defense;
        this.strength = strength;
        this.technologies = technologies;
        this.energyUsage = energyUsage;
        this.technologyLevel = technologyLevel;
        this.workers = workers;
        this.online = false;
    }


    double getDefense() {
        return defense;
    }

    double getStrength() {
        return strength;
    }

    List<Technology> getTechnologies() {
        return technologies;
    }

    boolean isOnline() {
        return online;
    }

    double getEnergyUsage() {
        return energyUsage;
    }

    int getTechnologyLevel() {
        return technologyLevel;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
