package main;
import java.util.ArrayList;
import java.util.List;

import herramientas.CalculadorCostos;
import herramientas.GeneradorConexiones;
import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;
import sistema.*;

public class Main {

	public static void main(String[] args) {
		
		List<Localidad>localidades = List.of(
				new Localidad("San Miguel","BsAs",0,0,1), 
                new Localidad("Villa Carlos Paz","Cordoba",0,0,1), 
                new Localidad("Bariloche","Rio Negro",0,0,1),
                new Localidad("San Justo","BsAs",0,0,1),
                new Localidad("San Justo","BsAs",0,0,1)
				);
		
		ParametrosCostos param = new ParametrosCostos(1000, 0, 0);
		CalculadorCostos calc = new CalculadorCostos();
		
		List<Conexion>conexiones = new ArrayList<Conexion>();
		
		GeneradorConexiones generadorConexiones = new GeneradorConexiones(calc);
		conexiones = generadorConexiones.generarConexiones(localidades, param);
		
		
		for (Conexion conexion : conexiones) {
			System.out.println(conexion.toString());
			System.out.println("\n");
		}
		
	}
}
