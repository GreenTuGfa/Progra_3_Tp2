package sistema;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Planificador {

	private List<Localidad> localidades;
	private GeneradorConexiones generador;
	
	public Planificador(CalculadorCostos calculador) {
		this.localidades = new ArrayList<>();
		this.generador = new GeneradorConexiones(calculador);
	}
	
	public void agregarLocalidad(Localidad loc) {
		localidades.add(loc);
	}
	
	public List<Conexion> planificar(ParametrosCostos parametro){
		List<Conexion> Conexiones = generador.generarConexiones(localidades, parametro);
		
		Conexiones.sort(Comparator.comparingDouble(Conexion::getCosto));
		Map<Localidad, Localidad> padre = new HashMap<>();
		
		for (Localidad loc : localidades) {
			padre.put(loc, loc);
		}
		
		List<Conexion> agm = new ArrayList<>();
		
		for(Conexion c : Conexiones) {
			Localidad loc1 = find(padre, c.getOrigen());
			Localidad loc2 = find(padre, c.getDestino());
			
			if(loc1 != loc2) {
				agm.add(c);
				union(padre, loc1, loc2);
			}
			
			if(agm.size() == localidades.size()- 1) break;
		}
		return agm;
	}
	 
	private Localidad find(Map<Localidad, Localidad> padre, Localidad loc) {
		if (padre.get(loc) != loc) {
			padre.put(loc, find(padre, padre.get(loc)));
		}
		return padre.get(loc);
	}

	private void union(Map<Localidad, Localidad> padre, Localidad loc1, Localidad loc2) {
		padre.put(loc1, loc2);
	}

	public double costoTotal(List<Conexion> agm) {
        return agm.stream().mapToDouble(Conexion::getCosto).sum();
	}

}
