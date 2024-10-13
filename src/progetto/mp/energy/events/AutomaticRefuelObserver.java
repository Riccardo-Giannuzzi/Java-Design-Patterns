package progetto.mp.energy.events;

import progetto.mp.energy.EnergyProducer;
import progetto.mp.energy.Generator;

public class AutomaticRefuelObserver implements EnergyProducerObserver {

	private int minimumGeneratorFuelUnits = 0;
	private int observerFuelUnits;
	
	public AutomaticRefuelObserver(EnergyProducer energyProducer, int observerFuelUnits, int minimumGeneratorFuelUnits) {
		if (observerFuelUnits < 0 || minimumGeneratorFuelUnits < 0) {
			throw new IllegalArgumentException("fuelUnits cannot be negative");
		}
		this.observerFuelUnits = observerFuelUnits;
		this.minimumGeneratorFuelUnits = minimumGeneratorFuelUnits;
		energyProducer.accept(
			new EnergyProducerConsumerVisitor
				(p -> p.addObserver(AutomaticRefuelObserver.this)));
	}
	
	public int getMinimumGeneratorFuelUnits() {
		return minimumGeneratorFuelUnits;
	}
	
	public int getFuelUnits() {
		return observerFuelUnits;
	}

	public void refuel(int fuelUnitsToAdd) { 
		if (fuelUnitsToAdd < 0) {
			throw new IllegalArgumentException("fuelUnits cannot be negative");
		}
		observerFuelUnits += fuelUnitsToAdd;
	}
	
	@Override
	public void notifyChange(EnergyProducerEvent event) {
		event.accept(new EnergyProducerEventVisitor() {
			@Override
			public void visitGeneratorProducingEnergyEvent(GeneratorProducingEnergyEvent event) {
				Generator generator = event.getGenerator();
				int missingFuelUnits = minimumGeneratorFuelUnits - generator.getFuelUnits();
				if(missingFuelUnits > 0 && observerFuelUnits >= missingFuelUnits) {
					generator.refuel(missingFuelUnits);
					observerFuelUnits -= missingFuelUnits;
				}
			}

			@Override
			public void visitEnergySystemAddedEvent(EnergySystemAddedEvent event) {
				event.getProducer()
					.accept(
						new EnergyProducerConsumerVisitor
							(p -> p.addObserver(AutomaticRefuelObserver.this)));
			}

			@Override
			public void visitEnergySystemRemovedEvent(EnergySystemRemovedEvent event) {
				event.getProducer()
					.accept(
						new EnergyProducerConsumerVisitor
							(p -> p.removeObserver(AutomaticRefuelObserver.this)));
			}
		});
	}
}
