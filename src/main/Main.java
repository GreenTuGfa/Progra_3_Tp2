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
				new Localidad("a","",0,0), 
                new Localidad("b","",0,0), 
                new Localidad("c","",0,0),
                new Localidad("d","",0,0)
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
