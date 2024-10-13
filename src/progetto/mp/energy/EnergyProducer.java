package progetto.mp.energy;

import java.util.ArrayList;
import java.util.Collection;
import progetto.mp.energy.events.EnergyProducerEvent;
import progetto.mp.energy.events.EnergyProducerObserver;

public abstract class EnergyProducer {
	
	private Collection<EnergyProducerObserver> observers = new ArrayList<>();

	public abstract int produceEnergy();
	
	public abstract void accept(EnergyProducerVisitor visitor);
	
	/**
     * Just for testing
     */
	Collection<EnergyProducerObserver> getObservers() {
		return observers;
	}

	public void addObserver(EnergyProducerObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(EnergyProducerObserver observer) {
		observers.remove(observer);
	}
	
    protected void notifyObservers(EnergyProducerEvent event) {
        observers.stream()
            .forEach(observer -> observer.notifyChange(event));
    }
}
