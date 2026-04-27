package modelos;

public class Conexion implements Comparable<Conexion>{

	Localidad origen;
	Localidad destino;
	double costo;
	
	public Conexion(Localidad origen, Localidad destino, double costo) {
		
		this.origen = origen;
		this.destino = destino;
		this.costo = costo;
	}
	
	public Localidad getOrigen() {
		return origen;
	}
	public Localidad getDestino() {
		return destino;
	}
	public double getCosto() {
		return costo;
	}
	
	@Override
	public String toString() {
		
		return "Desde: " + this.origen + ". Hasta: " + this.destino + ". Coste: " + this.costo;
	}

	@Override
	public int compareTo(Conexion c) {
		
		return Double.compare(this.costo, c.costo); //Devuelve el mas chico
	}
}
