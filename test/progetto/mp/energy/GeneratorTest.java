package progetto.mp.energy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.Assert.*;
import org.junit.Test;
import progetto.mp.energy.events.GeneratorProducingEnergyEvent;
import progetto.mp.energy.events.EnergyProducerObserver;

public class GeneratorTest {
	
	@Test
	public void testConstructorPositiveFuelUnits() {
		Generator generator = new Generator(8);
		assertEquals(8, generator.getFuelUnits());
	}
	
	@Test
	public void testConstructorNegativeFuelUnits() {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new Generator(-1))
			.withMessage("fuelUnits cannot be negative");
	}
	
	@Test
	public void testHasFuel() {
		Generator emptyGenerator = new Generator(0);
		Generator notEmptyGenerator = new Generator(1);
		assertFalse(emptyGenerator.hasFuel());
		assertTrue(notEmptyGenerator.hasFuel());
	}
	
	@Test
	public void testPositivetRefuel() {
		Generator generator = new Generator(1);
		generator.refuel(3);
		assertEquals(4, generator.getFuelUnits());
	}
	
	@Test
	public void testNegativeRefuel() {
		Generator generator = new Generator(2);
		assertThatIllegalArgumentException()
			.isThrownBy(() -> generator.refuel(-1))
			.withMessage("fuelUnits cannot be negative");
	}
	
	@Test
	public void testNotEmptyGeneratorProduceEnergy() {
		Generator notEmptyGenerator = new Generator(3);
		int producedEnergyUnits = notEmptyGenerator.produceEnergy();
		assertEquals(1, producedEnergyUnits);
		assertEquals(2, notEmptyGenerator.getFuelUnits());
	}
	
	@Test
	public void testEmptyGeneratorProduceEnergy() {
		Generator emptyGenerator = new Generator(0);
		int producedEnergyUnits = emptyGenerator.produceEnergy();
		assertEquals(0, producedEnergyUnits);
		assertEquals(0, emptyGenerator.getFuelUnits());
	}
	
	@Test
    public void testAddAndRemoveObserver() {
		EnergyProducerObserver observer = event -> {
			// Just for testing
		};
		Generator generator = new Generator(4);
		generator.addObserver(observer);
		assertThat(generator.getObservers()).containsExactly(observer);
		generator.removeObserver(observer);
		assertThat(generator.getObservers()).isEmpty();
	}
	
	@Test
    public void testProduceEnergyEvent() {
		Generator generator = new  Generator(3);
		MockEnergyProducerObserver observer = new MockEnergyProducerObserver();
		generator.addObserver(observer);
		generator.produceEnergy();
		GeneratorProducingEnergyEvent event = (GeneratorProducingEnergyEvent) observer.getEvent();
		assertThat(event.getGenerator()).isSameAs(generator);
 	}
}
