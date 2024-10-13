package progetto.mp.energy.events;

import progetto.mp.energy.EnergyProducer;
import progetto.mp.energy.EnergySystem;

public abstract class EnergySystemEvent implements EnergyProducerEvent{

	EnergySystem energySystem;
	EnergyProducer producer;
	
	public EnergySystemEvent(EnergyProducer producer, EnergySystem energySystem) {
		this.producer = producer;
		this.energySystem = energySystem;
	}

	public EnergySystem getEnergySystem() {
		return energySystem;
	}

	public EnergyProducer getProducer() {
		return producer;
	}
}
