package progetto.mp.energy.utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class EnergyPorducerFuelUnitsVisitorTest {

	private EnergyProducerFuelUnitsVisitor visitor;
	
	@Before
	public void setup() {
		visitor = new EnergyProducerFuelUnitsVisitor();
	}
	
	@Test
	public void testEmptyGeneratorVisit() {
		Generator emptyGenerator = new Generator(0);
		emptyGenerator.accept(visitor);
		assertEquals(0, visitor.getFuelUnits());
	}
	
	@Test
	public void testNonEmptyGeneratorVisit() {
		Generator nonEmptyGenerator = new Generator(5);
		nonEmptyGenerator.accept(visitor);
		assertEquals(5, visitor.getFuelUnits());
	}

	@Test
	public void testEmptyEnergySystemVisit() {
		EnergySystem emptyEnergySystem = new EnergySystem();
		emptyEnergySystem.accept(visitor);
		assertEquals(0, visitor.getFuelUnits());
	}
	
	@Test
	public void testNonEmptyEnergySystemVisit() {
		EnergySystem notEmptyEnergySystem = new EnergySystem();
		notEmptyEnergySystem.add(new Generator(4));
		notEmptyEnergySystem.add(new Generator(3));
		notEmptyEnergySystem.add(new Generator(0));
		notEmptyEnergySystem.accept(visitor);
		assertEquals(7, visitor.getFuelUnits());
	}
	
	@Test
	public void testNestedEnergySystemVisit() {
		EnergySystem mainEnergySystem = new EnergySystem();
		mainEnergySystem.add(new Generator(2));
		EnergySystem nestedEnergySystem = new EnergySystem();
		nestedEnergySystem.add(new Generator(8));
		nestedEnergySystem.add(new Generator(0));
		mainEnergySystem.add(nestedEnergySystem);
		mainEnergySystem.accept(visitor);
		assertEquals(10, visitor.getFuelUnits());
	}
}
