package sistema;
import java.util.ArrayList;
import java.util.List;

import sistema.*;

public class GeneradorConexiones {

	CalculadorCosto calc;
	
	public GeneradorConexiones(CalculadorCosto calc) {
		this.calc = calc;
	}
	
	public List<Conexion> generarConexiones(List<Localidad> localidades) {
		
		List<Conexion> conexiones = new ArrayList<Conexion>();
		
		for (int i = 0; i < localidades.size(); i++) {
			for (int j = i + 1; j < localidades.size(); j++) {
				
				Localidad a = localidades.get(i);
				Localidad b = localidades.get(j);
				
				double costo = calc.calcular(a, b);
				
				Conexion conexion = new Conexion(a, b, costo);
				conexiones.add(conexion);
			}
		}
		return conexiones;
	}
}
