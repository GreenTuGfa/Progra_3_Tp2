package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import sistema.*;

class GeneradorConexionesTest {

	
	@Test
	void cantidadConexiones() {
		List<Localidad>localidades = List.of(
				new Localidad("a","",0,0), 
				new Localidad("b","",0,0), 
				new Localidad("c","",0,0),
				new Localidad("d","",0,0));
		
		GeneradorConexiones generadorConexiones = new GeneradorConexiones();
		
		List<Conexion>conexiones = generadorConexiones.generarConexiones(localidades);
		
		assertEquals(conexiones.size(),6);
	}
	@Test
	void conexionesSinLoops() {
		List<Localidad>localidades = List.of(
				new Localidad("a","",0,0), 
				new Localidad("b","",0,0), 
				new Localidad("c","",0,0),
				new Localidad("d","",0,0));
		
		GeneradorConexiones generadorConexiones = new GeneradorConexiones();
		
		List<Conexion>conexiones = generadorConexiones.generarConexiones(localidades);
		
		for (Conexion conexion : conexiones) {
			assertNotEquals(conexion.getOrigen(), conexion.getDestino());
		}
	}

}
