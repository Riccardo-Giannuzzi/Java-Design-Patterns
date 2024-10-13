package progetto.mp.energy.utils;

import progetto.mp.energy.EnergyProducerVisitorAdapter;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class EnergyProducerStructureToStringVisitor extends EnergyProducerVisitorAdapter {

	private StringBuilder structureString = new StringBuilder();
	
	@Override
	public void visitEnergySystem(EnergySystem energySystem) {
		structureString.append("[");
		super.visitEnergySystem(energySystem);
		structureString.append("]");
	}

	@Override
	public void visitGenerator(Generator generator) {
		structureString.append("(");
		structureString.append(generator.getFuelUnits());
		structureString.append(")");
	}
	
	public String getStructureString() {
		return structureString.toString();
	}
}
