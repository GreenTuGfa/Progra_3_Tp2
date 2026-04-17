package sistema;

public class Conexion {

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
}
