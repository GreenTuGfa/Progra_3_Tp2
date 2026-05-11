package testHerramientas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sistema.herramientas.CalculadorCostos;
import sistema.modelos.Localidad;
import sistema.modelos.ParametrosCostos;

class CalculadorCostoTest {

	@Test
	void costoMismaLocalidad() {
		
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.514508989972214,-58.7629162912831);
		ParametrosCostos param = new ParametrosCostos(100, 0, 0);
		CalculadorCostos calc = new CalculadorCostos();
		
		double costo = calc.calcular(jcp, jcp,param);
		
		assertEquals(costo,0);
	}
	
	@Test
	void costoSimetrico() {
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.514508989972214,-58.7629162912831);
		Localidad pol = new Localidad("Polvorines", "Buenos Aires",-34.49951436427241, -58.690818517755);
		
		ParametrosCostos param = new ParametrosCostos(1000, 0, 0);
		CalculadorCostos calc= new CalculadorCostos();
		
		double costo1 = calc.calcular(jcp, pol,param);
		double costo2 = calc.calcular(pol,jcp,param);
		
		assertEquals(costo1,costo2);
	}


}
