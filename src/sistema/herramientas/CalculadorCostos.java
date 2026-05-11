package sistema.herramientas;

import sistema.modelos.Localidad;
import sistema.modelos.ParametrosCostos;

public class CalculadorCostos {

	private CalculadorDistancia calc;
	
	public CalculadorCostos() {
		this.calc = new CalculadorDistancia();
	}
	
	public double calcular(Localidad a, Localidad b,ParametrosCostos param) {
		if(param==null)
			return 0;
			
		double distancia = calc.calcular(a, b);
		double costo = distancia*param.getPrecio_km();
		
		if(!a.getProvincia().equals(b.getProvincia())) { //Si son provincias dstintas..
			costo += param.getCosto_fjo_provincia(); 
		}
		if(distancia > 300) {
			costo += costo * (param.getPorcentaje_extra() / 100);
		}
		return costo;
	}
}
