package progetto.mp.energy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import progetto.mp.energy.events.EnergySystemAddedEvent;
import progetto.mp.energy.events.EnergySystemRemovedEvent;

public class EnergySystem extends EnergyProducer {

	private Collection<EnergyProducer> energyProducers = new ArrayList<>();
	
	public EnergySystem() {
		// does nothing
	}
	
	@Override
	public int produceEnergy() {
		return energyProducers.stream()
			.map(p -> p.produceEnergy())
			.reduce(0, (a,b)-> a + b);
	}

	public void add(EnergyProducer energyProducer) {
		energyProducers.add(energyProducer);
		notifyObservers(
			new EnergySystemAddedEvent(energyProducer, this));
	}
	
	public void remove(EnergyProducer energyProducer) {
		energyProducers.remove(energyProducer);
		notifyObservers(
			new EnergySystemRemovedEvent(energyProducer, this));
	}
	
	/**
	 * Only for testing
	 */
	Collection<EnergyProducer> getContents(){
		return energyProducers;
	}
	
	public Iterator<EnergyProducer> iterator() {
		return energyProducers.iterator();
	}
	
	@Override
	public void accept(EnergyProducerVisitor visitor) {
		visitor.visitEnergySystem(this);
	}
}
