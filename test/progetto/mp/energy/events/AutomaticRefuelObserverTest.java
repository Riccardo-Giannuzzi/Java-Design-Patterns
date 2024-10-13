package progetto.mp.energy.events;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class AutomaticRefuelObserverTest {

	private EnergySystem EnergySystem;
	private Generator existingGenerator;
	private AutomaticRefuelObserver observer;
	
	@Before
	public void init() {
		EnergySystem = new EnergySystem();
		existingGenerator = new Generator(1);
		EnergySystem.add(existingGenerator);
		observer = new AutomaticRefuelObserver(EnergySystem,10,3);
	}
	
	@Test
	public void testConstructorNegativeFuelUnits() {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new AutomaticRefuelObserver(EnergySystem,-1,0))
			.withMessage("fuelUnits cannot be negative");
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new AutomaticRefuelObserver(EnergySystem,0,-1))
			.withMessage("fuelUnits cannot be negative");
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new AutomaticRefuelObserver(EnergySystem,-1,-1))
			.withMessage("fuelUnits cannot be negative");
	}
	
	@Test
	public void testPositivetRefuel() {
		observer.refuel(5);
		assertEquals(15, observer.getFuelUnits());
	}
	
	@Test
	public void testNegativeRefuel() {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> observer.refuel(-1))
			.withMessage("fuelUnits cannot be negative");
	}
	
	@Test
	public void testProduceExistingNeedsRefuel() {
		EnergySystem.produceEnergy();
		assertEquals(2, existingGenerator.getFuelUnits());
		assertEquals(8, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceExistingDoesNotNeedRefuel() {
		existingGenerator.refuel(3);
		EnergySystem.produceEnergy();
		assertEquals(3, existingGenerator.getFuelUnits());
		assertEquals(10, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceNotEnoughFuelObserver() {
		EnergySystem = new EnergySystem();
		Generator someFuelGenerator = new Generator(4);
		Generator noFuelGenerator = new Generator(0);
		EnergySystem.add(someFuelGenerator);
		EnergySystem.add(noFuelGenerator);
		observer = new AutomaticRefuelObserver(EnergySystem,1,7);
		EnergySystem.produceEnergy();
		assertEquals(3, someFuelGenerator.getFuelUnits());
		assertEquals(0, noFuelGenerator.getFuelUnits());
		assertEquals(1, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceUnknownGenerator() {
		Generator unknownGenerator = new Generator(1);
		unknownGenerator.produceEnergy();
		assertEquals(0, unknownGenerator.getFuelUnits());
		assertEquals(10, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceUnknownEnergySystem() {
		EnergySystem unknownEnergySystem = new EnergySystem();
		Generator unknownGenerator = new Generator(2);
		unknownEnergySystem.add(unknownGenerator);
		unknownEnergySystem.produceEnergy();
		assertEquals(1, unknownGenerator.getFuelUnits());
		assertEquals(10, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceAddedGenerator() {
		Generator addedGenerator = new Generator(2);
		EnergySystem.add(addedGenerator);
		EnergySystem.produceEnergy();
		assertEquals(2, addedGenerator.getFuelUnits());
		assertEquals(7, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceRemovedGenerator() {
		Generator removedGenerator = new Generator(1);
		EnergySystem.add(removedGenerator);
		EnergySystem.remove(removedGenerator);
		removedGenerator.produceEnergy();
		assertEquals(0, removedGenerator.getFuelUnits());
		assertEquals(10, observer.getFuelUnits());
	}
	
	@Test
	public void testProduceAddRemoveNestedEnergySystem() {
		EnergySystem nestedEnergySystem = new EnergySystem();
		Generator nestedGenerator = new Generator(1);
		nestedEnergySystem.add(nestedGenerator);
		EnergySystem.add(nestedEnergySystem);
		EnergySystem.produceEnergy();
		assertEquals(2, nestedGenerator.getFuelUnits());
		assertEquals(6, observer.getFuelUnits());
		EnergySystem.remove(nestedEnergySystem);
		nestedEnergySystem.produceEnergy();
		assertEquals(1, nestedGenerator.getFuelUnits());
		assertEquals(6, observer.getFuelUnits());
	}
}
