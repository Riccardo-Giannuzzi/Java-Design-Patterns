package progetto.mp.energy.events;

import progetto.mp.energy.EnergyProducer;
import progetto.mp.energy.EnergySystem;

public class EnergySystemRemovedEvent extends EnergySystemEvent {

	public EnergySystemRemovedEvent(EnergyProducer producer, EnergySystem powerSystem) {
		super(producer, powerSystem);
	}
	
	@Override
	public void accept(EnergyProducerEventVisitor visitor) {
		visitor.visitEnergySystemRemovedEvent(this);
	}
}
