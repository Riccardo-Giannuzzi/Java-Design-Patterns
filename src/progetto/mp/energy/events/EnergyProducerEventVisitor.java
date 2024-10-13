package progetto.mp.energy.events;

public interface EnergyProducerEventVisitor {
	
	void visitGeneratorProducingEnergyEvent(GeneratorProducingEnergyEvent event);
	
	void visitEnergySystemAddedEvent(EnergySystemAddedEvent event);
	
	void visitEnergySystemRemovedEvent(EnergySystemRemovedEvent event);
}
