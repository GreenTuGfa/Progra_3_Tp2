package modelos;

/**
 *	La idea de la clase es poder tener distintos parametros con costes, para un mismo 'CalculadorCoste',
 *de manera que no sea necesario crear siempre un 'CalculadorCoste' cada vez que quieroprobar distintos valores.
 *Esto a su vez sirve para poder hacer test con distintos valores de parameros. Sin embargo, la clase no es
 *estrictamente necesaria
 *
 * */
public class ParametrosCostos {

	private double precio_km;
	private double porcentaje_extra;
	private double costo_fjo_provincia;
	
	public ParametrosCostos(double precio_km,double porcentaje_extra,double costo_fijo_provincia) {
		
		this.precio_km = precio_km;
		this.porcentaje_extra = porcentaje_extra;
		this.costo_fjo_provincia = costo_fijo_provincia;
	}
	
	public double getPrecio_km() {
		return precio_km;
	}
	public double getPorcentaje_extra() {
		return porcentaje_extra;
	}
	public double getCosto_fjo_provincia() {
		return costo_fjo_provincia;
	}
}
