package herramientas;
import java.util.ArrayList;
import java.util.List;

import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;
import sistema.*;

public class GeneradorConexiones {

	CalculadorCostos calc;
	
	public GeneradorConexiones(CalculadorCostos calc) {
		this.calc = calc;
	}
	
	public List<Conexion> generarConexiones(List<Localidad> localidades, ParametrosCostos param) {
		
		List<Conexion> conexiones = new ArrayList<Conexion>();
		
		for (int i = 0; i < localidades.size(); i++) {
			
			for (int j = i + 1; j < localidades.size(); j++) {
				
				Localidad a = localidades.get(i);
				Localidad b = localidades.get(j);
				
				double costo = calc.calcular(a, b,param);
				
				Conexion conexion = new Conexion(a, b, costo);
				conexiones.add(conexion);
			}
		}
		return conexiones;
	}
}
