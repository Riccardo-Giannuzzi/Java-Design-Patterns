package progetto.mp.energy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.Collection;
import org.junit.Test;
import progetto.mp.energy.events.EnergyProducerObserver;
import progetto.mp.energy.events.EnergySystemAddedEvent;
import progetto.mp.energy.events.EnergySystemRemovedEvent;

public class EnergySystemTest {

	@Test
	public void testAdd() {
		EnergySystem energySystem = new EnergySystem();
		Generator generator = new Generator(10);
		energySystem.add(generator);
		EnergySystem nestedEnergySystem = new EnergySystem();
		energySystem.add(nestedEnergySystem);
		Collection<EnergyProducer> contents = energySystem.getContents();
		assertThat(contents).containsExactly(generator, nestedEnergySystem);
	}
	
	@Test
	public void testRemove() {
		EnergySystem energySystem = new EnergySystem();
		Generator notToRemove = new Generator(10);
		Generator toRemove = new Generator(5);
		EnergySystem toRemoveEnergySystem = new EnergySystem();
		Collection<EnergyProducer> contents = energySystem.getContents();
		contents.add(notToRemove);
		contents.add(toRemove);
		contents.add(toRemoveEnergySystem);
		energySystem.remove(toRemove);
		energySystem.remove(toRemoveEnergySystem);
		assertThat(contents).containsExactly(notToRemove);
	}

	@Test
	public void testEmptyProduceEnergy() {
		EnergySystem emptyEnergySystem = new EnergySystem();
		int producedEnergyUnits = emptyEnergySystem.produceEnergy();
		assertEquals(0, producedEnergyUnits);
	}
	
	@Test
	public void testNonEmptyProduceEnergy() {
		EnergySystem energySystem = new EnergySystem();
		Generator generator1 = new Generator(4);
		Generator generator2 = new Generator(6);
		Collection<EnergyProducer> contents = energySystem.getContents();
		contents.add(generator1);
		contents.add(generator2);
		int producedEnergyUnits = energySystem.produceEnergy();
		assertEquals(2, producedEnergyUnits);
		assertEquals(3, generator1.getFuelUnits());
		assertEquals(5, generator2.getFuelUnits());
	}
	
	@Test
	public void testNestedProduceEnergy() {
		EnergySystem energySystem = new EnergySystem();
		Generator generator1 = new Generator(10);
		Generator generator2 = new Generator(5);
		Collection<EnergyProducer> contents = energySystem.getContents();
		contents.add(generator1);
		contents.add(generator2);
		EnergySystem nestedEnergySystem = new EnergySystem();
		Generator nestedGenerator = new Generator(3);
		nestedEnergySystem.getContents()
			.add(nestedGenerator);
		contents.add(nestedEnergySystem);
		int producedEnergyUnits = energySystem.produceEnergy();
		assertEquals(3, producedEnergyUnits);
		assertEquals(9, generator1.getFuelUnits());
		assertEquals(4, generator2.getFuelUnits());
		assertEquals(2, nestedGenerator.getFuelUnits());
	}
	
	@Test
	public void testIterator() {
		EnergySystem energySystem = new EnergySystem();
		Generator generator1 = new Generator(3);
		Generator generator2 = new Generator(8);
		Collection<EnergyProducer> contents = energySystem.getContents();
		contents.add(generator1);
		contents.add(generator2);
		EnergySystem nestedEnergySystem = new EnergySystem();
		Generator nestedGenerator = new Generator(2);
		nestedEnergySystem.getContents()
			.add(nestedGenerator);
		contents.add(nestedEnergySystem);
		assertThat(energySystem.iterator())
			.toIterable()
			.containsExactlyInAnyOrder(generator1, generator2, nestedEnergySystem);
	}
	
	@Test
    public void testAddAndRemoveObserver() {
		EnergyProducerObserver observer = event -> {
			// Just for testing
		};
		EnergySystem energySystem = new EnergySystem();
		energySystem.addObserver(observer);
		assertThat(energySystem.getObservers()).containsExactly(observer);
		energySystem.removeObserver(observer);
		assertThat(energySystem.getObservers()).isEmpty();
	}
	
	@Test
	public void testAddedEvent() {
		EnergySystem energySystem = new EnergySystem();
		MockEnergyProducerObserver observer = new MockEnergyProducerObserver();
		energySystem.addObserver(observer);
		Generator generator = new Generator(3);
		energySystem.add(generator);
		EnergySystemAddedEvent event = (EnergySystemAddedEvent) observer.getEvent();
		assertThat(event.getProducer()).isSameAs(generator);
		assertThat(event.getEnergySystem()).isSameAs(energySystem);
	}
	
	@Test
	public void testRemovedEvent() {
		EnergySystem energySystem = new EnergySystem();
		Generator generator = new Generator(3);
		energySystem.getContents().add(generator);
		MockEnergyProducerObserver observer = new MockEnergyProducerObserver();
		energySystem.addObserver(observer);
		energySystem.remove(generator);
		EnergySystemRemovedEvent event = (EnergySystemRemovedEvent) observer.getEvent();
		assertThat(event.getProducer()).isSameAs(generator);
		assertThat(event.getEnergySystem()).isSameAs(energySystem);
	}
}
