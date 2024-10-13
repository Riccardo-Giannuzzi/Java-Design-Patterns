package progetto.mp.energy;

public interface EnergyProducerVisitor {
	
	void visitEnergySystem(EnergySystem energySystem);
	
	void visitGenerator(Generator generator);
}
