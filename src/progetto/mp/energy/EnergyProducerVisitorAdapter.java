package progetto.mp.energy;

import java.util.Iterator;

public abstract class EnergyProducerVisitorAdapter implements EnergyProducerVisitor {

	@Override
	public void visitEnergySystem(EnergySystem energySystem) {
		Iterator<EnergyProducer> iterator = energySystem.iterator();
        while (iterator.hasNext()) 
            iterator.next()
            	.accept(this);
	}

	@Override
	public void visitGenerator(Generator generator) {
		// does nothing
    }
}

