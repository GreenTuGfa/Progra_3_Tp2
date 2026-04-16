package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import sistema.*;

class CalculadorDistanciaTest {

	//jose c paz -34.514508989972214, -58.7629162912831
	//polvorines -34.49951436427241, -58.690818517755
	//tigre      -34.4267663319639, -58.57717868796918
	
	@Test
	void distanciaMismaLocalidad() {
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.51394320352423,-58.76943963087526);
		CalculadorDistancia calcDis = new CalculadorDistancia();
		
		double distancia = calcDis.calcular(jcp, jcp);
		assertEquals(0, distancia); 
	}
	@Test
	void distanciaEsSimetrica() {
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.51394320352423,-58.76943963087526);
		Localidad pol = new Localidad("Polvorines", "Buenos Aires",-34.49929991316347, -58.69336057451757);
		CalculadorDistancia calcDis = new CalculadorDistancia();
		
		double distancia1 = calcDis.calcular(jcp, pol);
		double distancia2 = calcDis.calcular(pol, jcp);
		
		assertEquals(distancia1, distancia2);
	
	}
	
	@Test
	void distanciaMayor() {
		
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.514508989972214,-58.7629162912831);
		Localidad pol = new Localidad("Polvorines", "Buenos Aires",-34.49951436427241, -58.690818517755);
		Localidad tig = new Localidad("Tigre","Buenos Aires",-34.4267663319639,-58.57717868796918);
		CalculadorDistancia calcDis = new CalculadorDistancia();
		
		double distancia_jcp_pol = calcDis.calcular(pol, tig);
		double distancia_jcp_tig = calcDis.calcular(jcp, tig);
		
		assertTrue(distancia_jcp_tig > distancia_jcp_pol);
	}
	@Test
	
	void distanciaGrande() {
		Localidad bsas = new Localidad("Buenos Aires", "Argentina",-34.61144087673769, -58.42073845086299);
		Localidad madr = new Localidad("Madrid", "España", 40.41064833747105, -3.5885587184389824);
		CalculadorDistancia calc = new CalculadorDistancia();
		
		double distancia = calc.calcular(madr, bsas);
		
		assertTrue(distancia > 10000 && distancia < 10250); //La distancia entre buenos aires y madrid es aprox 10.000km
	}
	
	@Test
	void distanciaDosLocalidades() {
		Localidad jcp = new Localidad("Jose C Paz", "Buenos Aires",-34.51394320352423,-58.76943963087526);
		Localidad pol = new Localidad("Polvorines", "Buenos Aires",-34.49929991316347, -58.69336057451757);
		CalculadorDistancia calcDis = new CalculadorDistancia();
		
		double distancia = calcDis.calcular(jcp, pol);
		
		assertEquals(distancia, 7.13,0.1); //Rango de tolerancia 0.1 - 1km
	}

}
