package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import sistema.*;

class GeneradorConexionesTest {

	List<Localidad>localidades = List.of(
			new Localidad("a","",0,0), 
			new Localidad("b","",0,0), 
			new Localidad("c","",0,0),
			new Localidad("d","",0,0));
	
	GeneradorConexiones generadorConexiones = new GeneradorConexiones();
	
	List<Conexion>conexiones = generadorConexiones.generarConexiones(localidades);
	
	@Test
	void cantidadConexionesCorretas() {
		
		/***
		 * Cantidad de conexiones = n(n-1)/2. Con n siendo la cantidad de nodos (localidades)
		 * */
		
		assertEquals(conexiones.size(),6);
	}
	@Test
	void conexionesSinLoops() {
		
		for (Conexion conexion : conexiones) {
			assertNotEquals(conexion.getOrigen(), conexion.getDestino());
		}
	}

}
