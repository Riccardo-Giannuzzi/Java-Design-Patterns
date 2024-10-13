package progetto.mp.energy;

import progetto.mp.energy.events.EnergyProducerEvent;
import progetto.mp.energy.events.EnergyProducerObserver;

public class MockEnergyProducerObserver implements EnergyProducerObserver {

	private EnergyProducerEvent event;

	@Override
	public void notifyChange(EnergyProducerEvent event) {
		this.event = event;
	}

	public EnergyProducerEvent getEvent() {
		return event;
	}
}
