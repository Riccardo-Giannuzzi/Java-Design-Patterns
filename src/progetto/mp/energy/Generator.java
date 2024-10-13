package progetto.mp.energy;

import progetto.mp.energy.events.GeneratorProducingEnergyEvent;

public class Generator extends EnergyProducer {

	private int fuelUnits;
	
	public Generator(int fuelUnits) {
		if (fuelUnits < 0) {
			throw new IllegalArgumentException("fuelUnits cannot be negative");
		}
		this.fuelUnits = fuelUnits;
	}

	@Override
	public int produceEnergy() {
		GeneratorProducingEnergyEvent event = new GeneratorProducingEnergyEvent(this);
		notifyObservers(event);
		if(!hasFuel()) 
			return 0;
		fuelUnits--;
		return 1;
	}

	public boolean hasFuel() {
		return fuelUnits > 0;
	}
	
	public int getFuelUnits() {
		return fuelUnits;
	}
	
	public void refuel(int fuelUnitsToAdd) { 
		if (fuelUnitsToAdd < 0) {
			throw new IllegalArgumentException("fuelUnits cannot be negative");
		}
		fuelUnits += fuelUnitsToAdd;
	}
	
	@Override
	public void accept(EnergyProducerVisitor visitor) {
		visitor.visitGenerator(this);
	}	
}
