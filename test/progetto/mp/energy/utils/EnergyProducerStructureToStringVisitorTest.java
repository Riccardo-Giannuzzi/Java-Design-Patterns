package progetto.mp.energy.utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import progetto.mp.energy.EnergySystem;
import progetto.mp.energy.Generator;

public class EnergyProducerStructureToStringVisitorTest {

	private EnergyProducerStructureToStringVisitor visitor;
	
	@Before
	public void setup() {
		visitor = new EnergyProducerStructureToStringVisitor();
	}
	
	@Test
	public void testGeneratorVisit() {
		Generator generator = new Generator(0);
		generator.accept(visitor);
		assertEquals("(0)", visitor.getStructureString());
	}

	@Test
	public void testEmptyEnergySystemVisit() {
		EnergySystem emptyEnergySystem = new EnergySystem();
		emptyEnergySystem.accept(visitor);
		assertEquals("[]", visitor.getStructureString());
	}
	
	@Test
	public void testNotEmptyEnergySystemVisit() {
		EnergySystem notEmptyEnergySystem = new EnergySystem();
		notEmptyEnergySystem.add(new Generator(4));
		notEmptyEnergySystem.add(new Generator(3));
		notEmptyEnergySystem.add(new Generator(0));
		notEmptyEnergySystem.accept(visitor);
		assertEquals("[(4)(3)(0)]", visitor.getStructureString());
	}
	
	@Test
	public void testNestedEnergySystemVisit() {
		EnergySystem mainEnergySystem = new EnergySystem();
		mainEnergySystem.add(new Generator(2));
		EnergySystem nestedEnergySystem = new EnergySystem();
		nestedEnergySystem.add(new Generator(8));
		nestedEnergySystem.add(new EnergySystem());
		nestedEnergySystem.add(new Generator(0));
		mainEnergySystem.add(nestedEnergySystem);
		mainEnergySystem.accept(visitor);
		assertEquals("[(2)[(8)[](0)]]", visitor.getStructureString());
	}
}
