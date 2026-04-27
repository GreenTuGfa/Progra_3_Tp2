package modelos;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Localidad {

	String nombre;
	String provincia;
	int id;
	double latitud;
	double longitud;
	
	public Localidad(String nombre, String provincia, double latitud, double longitud, int id) {
		this.id=id;
		this.nombre = nombre;
		this.provincia = provincia;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public double getLatitud() {
		return latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public String getProvincia() {
		return provincia;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	public boolean equals(Localidad localidad) {
		return this==localidad;
	}
	
	public int hashCode() {
		return id;
	}

	public Coordinate darCoordenada() {
		Coordinate coordenada= new Coordinate(latitud,longitud);
		return coordenada;
	}
}
