package sistema;

import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import herramientas.CalculadorCostos;
import herramientas.GeneradorConexiones;
import herramientas.GeneradorRedMinima;
import herramientas.GestorArchivos;
import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
