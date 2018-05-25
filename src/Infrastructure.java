import java.util.List;

class Infrastructure {
    double defense;
    double strength;
    List<Technology> technologies;
    boolean online;
    double energyUsage;
    int technologyLevel;

    Infrastructure(double defense, double strength, List<Technology> technologies, double energyUsage, int technologyLevel) {
        this.defense = defense;
        this.strength = strength;
        this.technologies = technologies;
        this.energyUsage = energyUsage;
        this.technologyLevel = technologyLevel;
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
