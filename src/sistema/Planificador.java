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
	
	public void guardarLocalidadesJSON(String rutaArchivo) {
	    Gson configuradorGson = new GsonBuilder().setPrettyPrinting().create();
	    String contenidoJson = configuradorGson.toJson(this.localidades);

	    try (FileOutputStream flujoSalida = new FileOutputStream(rutaArchivo);
	         OutputStreamWriter escritor = new OutputStreamWriter(flujoSalida)) {
	        escritor.write(contenidoJson);
	    } catch (IOException e) {
	        System.err.println("Error al guardar: " + e.getMessage());
	    }
	}
	
	public void cargarLocalidadesJSON(String rutaArchivo) {
	    File archivo = new File(rutaArchivo);
	    if (!archivo.exists()) return;

	    try (FileInputStream flujoEntrada = new FileInputStream(archivo);
	         Scanner lector = new Scanner(flujoEntrada)) {
	        
	        // Leer todo el archivo en un String
	        StringBuilder constructorTexto = new StringBuilder();
	        while (lector.hasNextLine()) {
	            constructorTexto.append(lector.nextLine());
	        }

	        // Definir el tipo de la lista para GSON
	        Type tipoLista = new TypeToken<ArrayList<Localidad>>(){}.getType();
	        
	        Gson configuradorGson = new Gson();
	        this.localidades = configuradorGson.fromJson(constructorTexto.toString(), tipoLista);
	        
	    } catch (Exception e) {
	        System.err.println("Error al cargar: " + e.getMessage());
	    }
	}
}
