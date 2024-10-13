package progetto.mp.energy.events;

public interface EnergyProducerEvent {
	
	void accept(EnergyProducerEventVisitor visitor);
}
