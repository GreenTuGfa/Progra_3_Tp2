package sistema;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import herramientas.CalculadorCostos;
import herramientas.GeneradorConexiones;
import herramientas.GeneradorRedMinima;
import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;

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
		
		GeneradorRedMinima r = new GeneradorRedMinima();
		List<Conexion> agm = r.generar(localidades, Conexiones);
		
		return agm;
	}
	 
	public double costoTotal(List<Conexion> agm) {
        return agm.stream().mapToDouble(Conexion::getCosto).sum();
	}

	public void eliminarLocalidad(Localidad loc) {
		this.localidades.remove(loc);
	}

	public List<Localidad> localidades(){
		return localidades;
	}

}
