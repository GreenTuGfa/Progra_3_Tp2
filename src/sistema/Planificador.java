package sistema;

import java.util.List;
import java.util.Scanner;

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
	
	public void guardarLocalidadesEnArchivo(String rutaDestino) {
	    try (FileOutputStream flujoSalida = new FileOutputStream(rutaDestino);
	         OutputStreamWriter escritorTexto = new OutputStreamWriter(flujoSalida)) {
	        
	        for (Localidad localidadActual : localidades) {
	        	escritorTexto.write(localidadActual.generarLineaArchivo() + "\r\n");
	        }
	        
	    } catch (IOException error) {
	        System.err.println("Error tecnico al intentar escribir el archivo: " + error.getMessage());
	    }
	}
	public void cargarLocalidadesDesdeArchivo(String rutaOrigen) {
	    File archivoReferenciado = new File(rutaOrigen);
	    
	    if (!archivoReferenciado.exists()) {
	        return;
	    }

	    try (FileInputStream flujoEntradaArchivo = new FileInputStream(archivoReferenciado);
	         Scanner lectorDeArchivo = new Scanner(flujoEntradaArchivo)) {
	        
	        while (lectorDeArchivo.hasNextLine()) {
	            String lineaLeida = lectorDeArchivo.nextLine();
	            String[] atributosDeLocalidad = lineaLeida.split(";");
	            
	            // Validamos que la línea tenga exactamente nombre, provincia, lat y lon
	            if (atributosDeLocalidad.length == 4) {
	                String nombre = atributosDeLocalidad[0];
	                String provincia = atributosDeLocalidad[1];
	                double latitud = Double.parseDouble(atributosDeLocalidad[2]);
	                double longitud = Double.parseDouble(atributosDeLocalidad[3]);
	                
	                Localidad localidadCargada = new Localidad(nombre, provincia, latitud, longitud);
	                this.agregarLocalidad(localidadCargada);
	            }
	        }
	        
	    } catch (Exception excepcionError) { 
	        System.err.println("Error al intentar procesar la lectura del archivo: " + excepcionError.getMessage());
	    }
	}
}
