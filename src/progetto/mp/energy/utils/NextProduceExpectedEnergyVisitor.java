package progetto.mp.energy.utils;

import progetto.mp.energy.EnergyProducerVisitorAdapter;
import progetto.mp.energy.Generator;

public class NextProduceExpectedEnergyVisitor extends EnergyProducerVisitorAdapter {

	private int expectedEnergyUnits = 0;

	@Override
	public void visitGenerator(Generator generator) {
		if(generator.hasFuel())
			expectedEnergyUnits++;
	}

	public int getExpectedEnergyUnits() {
		return expectedEnergyUnits;
	}
}
