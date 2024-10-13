package progetto.mp.energy.events;

import progetto.mp.energy.EnergyProducer;

public class ConsumedFuelUnitsObserver implements EnergyProducerObserver {

	private int burnedFuelUnits = 0;
	
	public ConsumedFuelUnitsObserver(EnergyProducer energySystem) {
		energySystem.accept(
			new EnergyProducerConsumerVisitor
				(p -> p.addObserver(ConsumedFuelUnitsObserver.this)));
	}
	
	public int getBurnedFuelUnits() {
		return burnedFuelUnits;
	}
	
	@Override
	public void notifyChange(EnergyProducerEvent event) {
		event.accept(new EnergyProducerEventVisitor() {
			@Override
			public void visitGeneratorProducingEnergyEvent(GeneratorProducingEnergyEvent event) {
				if(event.getGenerator().hasFuel())
					burnedFuelUnits++;
			}

			@Override
			public void visitEnergySystemAddedEvent(EnergySystemAddedEvent event) {
				event.getProducer()
					.accept(
						new EnergyProducerConsumerVisitor
							(p -> p.addObserver(ConsumedFuelUnitsObserver.this)));
			}

			@Override
			public void visitEnergySystemRemovedEvent(EnergySystemRemovedEvent event) {
				event.getProducer()
					.accept(
						new EnergyProducerConsumerVisitor
							(p -> p.removeObserver(ConsumedFuelUnitsObserver.this)));
			}
		});
	}
}
