package progetto.mp.energy.events;

public interface EnergyProducerObserver {
	
	void notifyChange(EnergyProducerEvent event);
}
