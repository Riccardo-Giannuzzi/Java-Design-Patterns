package progetto.mp.energy.utils;

import progetto.mp.energy.EnergyProducerVisitorAdapter;
import progetto.mp.energy.Generator;

public class EnergyProducerFuelUnitsVisitor extends EnergyProducerVisitorAdapter {
	
	private int fuelUnits = 0;
	
	@Override
	public void visitGenerator(Generator generator) {
		fuelUnits += generator.getFuelUnits();
	}

	public int getFuelUnits() {
		return fuelUnits;
	}
}
