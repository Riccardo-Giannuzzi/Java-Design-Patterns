package progetto.mp.energy.utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class NextProduceExpectedEnergyVisitorTest {

	private NextProduceExpectedEnergyVisitor visitor;
	
	@Before
	public void setup() {
		visitor = new NextProduceExpectedEnergyVisitor();
	}
	
	@Test
	public void testEmptyGeneratorVisit() {
		Generator emptyGenerator = new Generator(0);
		emptyGenerator.accept(visitor);
		assertEquals(0, visitor.getExpectedEnergyUnits());
	}
	
	@Test
	public void testNotEmptyGeneratorVisit() {
		Generator notEmptyGenerator = new Generator(3);
		notEmptyGenerator.accept(visitor);
		assertEquals(1, visitor.getExpectedEnergyUnits());
	}

	@Test
	public void testEmptyEnergySystemVisit() {
		EnergySystem emptyEnergySystem = new EnergySystem();
		emptyEnergySystem.accept(visitor);
		assertEquals(0, visitor.getExpectedEnergyUnits());
	}
	
	@Test
	public void testNotEmptyEnergySystemVisit() {
		EnergySystem notEmptyEnergySystem = new EnergySystem();
		notEmptyEnergySystem.add(new Generator(3));
		notEmptyEnergySystem.add(new Generator(5));
		notEmptyEnergySystem.add(new Generator(0));
		notEmptyEnergySystem.accept(visitor);
		assertEquals(2, visitor.getExpectedEnergyUnits());
	}
	
	@Test
	public void testNestedEnergySystemVisit() {
		EnergySystem mainEnergySystem = new EnergySystem();
		mainEnergySystem.add(new Generator(3));
		EnergySystem nestedEnergySystem = new EnergySystem();
		nestedEnergySystem.add(new Generator(5));
		nestedEnergySystem.add(new Generator(0));
		mainEnergySystem.add(nestedEnergySystem);
		mainEnergySystem.accept(visitor);
		assertEquals(2, visitor.getExpectedEnergyUnits());
	}
}
