package progetto.mp.energy.events;

import progetto.mp.energy.Generator;

public class GeneratorProducingEnergyEvent implements EnergyProducerEvent {

	private Generator generator;

	public GeneratorProducingEnergyEvent(Generator generator) {
		this.generator = generator;
	}
	
	public Generator getGenerator() {
		return generator;
	}

	@Override
	public void accept(EnergyProducerEventVisitor visitor) {
		visitor.visitGeneratorProducingEnergyEvent(this);
	}
}
