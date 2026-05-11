package sistema;

import java.util.List;

import sistema.herramientas.CalculadorCostos;
import sistema.herramientas.GeneradorConexiones;
import sistema.herramientas.GeneradorRedMinima;
import sistema.herramientas.GestorArchivos;
import sistema.modelos.Conexion;
import sistema.modelos.Localidad;
import sistema.modelos.ParametrosCostos;

import java.util.ArrayList;

public class Planificador {

	private List<Localidad> localidades;
	private GeneradorConexiones generador;
	private GestorArchivos archivos = new GestorArchivos();

	
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
	
	
	//METODOS PARA EL ALMACENAMIENTO DEL ARCHIVO 
	public void guardarDatos(String ruta) {
	    archivos.guardar(this.localidades, ruta);
	}

	public void cargarDatos(String ruta) {
	    this.localidades = archivos.cargar(ruta);
	}
}
