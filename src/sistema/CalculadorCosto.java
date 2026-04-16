package sistema;

public class CalculadorCosto {

	private CalculadorDistancia calc;
	
	private double costo_km;
	private double porcentaje_300km;
	private double costo_fijo_povincia;
	
	public CalculadorCosto(double costo_km,double porcentaje_300km,double costo_fijo_provincia) {
		
		this.costo_km = costo_km;
		this.porcentaje_300km = porcentaje_300km;
		this.costo_fijo_povincia = costo_fijo_provincia;
		this.calc = new CalculadorDistancia();
	}
	
	public double calcular(Localidad a, Localidad b) {
		
		double distancia = calc.calcular(a, b);
		double costo = distancia*costo_km;
		
		if(a.getProvincia().equals(b.getProvincia())) { //Si son provincias dstintas..
			costo += costo_fijo_povincia; 
		}
		if(distancia > 300) {
			costo += costo*this.porcentaje_300km;
		}
		return costo;
	}
}
