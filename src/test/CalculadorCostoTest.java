package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import sistema.*;

class CalculadorCostoTest {

	@Test
	void costoMismaLocalidad() {
		
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.514508989972214,-58.7629162912831);
		CalculadorCosto calc = new CalculadorCosto(1000, 0, 0);
		
		double costo = calc.calcular(jcp, jcp);
		
		assertEquals(costo,0);
	}
	
	@Test
	void costoSimetrico() {
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.514508989972214,-58.7629162912831);
		Localidad pol = new Localidad("Polvorines", "Buenos Aires",-34.49951436427241, -58.690818517755);
		CalculadorCosto calc= new CalculadorCosto(1000,0,0);
		
		double costo1 = calc.calcular(jcp, pol);
		double costo2 = calc.calcular(pol,jcp);
		
		assertEquals(costo1,costo2);
	}
	

}
