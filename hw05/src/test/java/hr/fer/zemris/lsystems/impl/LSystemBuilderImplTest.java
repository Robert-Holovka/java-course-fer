package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;

public class LSystemBuilderImplTest {
	private LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder().registerCommand('F', "draw 1").registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60").setOrigin(0.05, 0.4).setAngle(0).setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0).registerProduction('F', "F+F--F+F").setAxiom("F").build();
	}

	@Test
	public void generateAxiom() {
		var system = createKochCurve(LSystemBuilderImpl::new);
		assertEquals("F", system.generate(0));
	}
	
	@Test
	public void generateLevelOne() {
		var system = createKochCurve(LSystemBuilderImpl::new);
		assertEquals("F+F--F+F", system.generate(1));
	}
	
	@Test
	public void generateLevelTwo() {
		var system = createKochCurve(LSystemBuilderImpl::new);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}
}
