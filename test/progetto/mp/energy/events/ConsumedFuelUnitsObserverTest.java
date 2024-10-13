package progetto.mp.energy.events;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class ConsumedFuelUnitsObserverTest {

	private EnergySystem energySystem;
	private Generator existingGenerator;
	private ConsumedFuelUnitsObserver observer;
	
	@Before
	public void init() {
		energySystem = new EnergySystem();
		existingGenerator = new Generator(3);
		energySystem.add(existingGenerator);
		observer = new ConsumedFuelUnitsObserver(energySystem);
	}
	
	@Test
	public void testProduceExistingEnergySystem() {
		energySystem.produceEnergy();
		assertEquals(1, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceExistingGenerator() {
		existingGenerator.produceEnergy();
		assertEquals(1, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceUnknownEletricPowerSystem() {
		EnergySystem unknownEletricPowerSystem = new EnergySystem();
		unknownEletricPowerSystem.add(new Generator(2));
		unknownEletricPowerSystem.produceEnergy();
		assertEquals(0, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceUnknownGenerator() {
		Generator unknownGenerator = new Generator(5);
		unknownGenerator.produceEnergy();
		assertEquals(0, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceAddedGenerator() {
		energySystem.add(new Generator(5));
		energySystem.produceEnergy();
		assertEquals(2, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceAddedEmptyGenerator() {
		energySystem.add(new Generator(0));
		energySystem.produceEnergy();
		assertEquals(1, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceRemovedGenerator() {
		Generator removedGenerator = new Generator(5);
		energySystem.add(removedGenerator);
		energySystem.remove(removedGenerator);
		energySystem.produceEnergy();
		assertEquals(1, observer.getBurnedFuelUnits());
	}
	
	@Test
	public void testProduceAddRemoveNestedEletricPowerSystem() {
		EnergySystem nestedEletricPowerSystem = new EnergySystem();
		Generator nestedGenerator = new Generator(3);
		nestedEletricPowerSystem.add(nestedGenerator);
		energySystem.add(nestedEletricPowerSystem);
		energySystem.produceEnergy();
		assertEquals(2, observer.getBurnedFuelUnits());
		energySystem.remove(nestedEletricPowerSystem);
		energySystem.produceEnergy();
		assertEquals(3, observer.getBurnedFuelUnits());
	}
}
