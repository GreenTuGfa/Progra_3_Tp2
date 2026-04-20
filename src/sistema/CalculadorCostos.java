package sistema;

public class CalculadorCostos {

	private CalculadorDistancia calc;
	
	public CalculadorCostos() {
		this.calc = new CalculadorDistancia();
	}
	
	public double calcular(Localidad a, Localidad b,ParametrosCostos param) {
		
		double distancia = calc.calcular(a, b);
		double costo = distancia*param.precio_km;
		
		if(a.getProvincia().equals(b.getProvincia())) { //Si son provincias dstintas..
			costo += param.costo_fjo_provincia; 
		}
		if(distancia > 300) {
			costo += costo*param.porcentaje_extra;
		}
		return costo;
	}
}
