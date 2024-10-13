package progetto.mp.energy.events;

import java.util.function.Consumer;
import progetto.mp.energy.EnergyProducer;
import progetto.mp.energy.EnergyProducerVisitorAdapter;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class EnergyProducerConsumerVisitor extends EnergyProducerVisitorAdapter {

	private final Consumer<EnergyProducer> consumer;
	
	public EnergyProducerConsumerVisitor(Consumer<EnergyProducer> producerConsumer) {
		consumer = producerConsumer;
	}
	
	@Override
	public void visitEnergySystem(EnergySystem energySystem) {
		consumer.accept(energySystem);
		super.visitEnergySystem(energySystem);
	}

	@Override
	public void visitGenerator(Generator generator) {
		consumer.accept(generator);
	}
}