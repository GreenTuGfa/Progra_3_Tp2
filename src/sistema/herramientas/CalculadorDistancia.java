package sistema.herramientas;

import sistema.modelos.Localidad;

public class CalculadorDistancia {

	private static final double RADIO_TIERRA = 6371;
	
	public CalculadorDistancia() {}
	
	public double calcular(Localidad a, Localidad b) {
		
		double latitud1 = Math.toRadians(a.getLatitud());
        double longitud1 = Math.toRadians(a.getLongitud());
        double latitud2 = Math.toRadians(b.getLatitud());
        double longitud2 = Math.toRadians(b.getLongitud());

        double dLat = latitud2 - latitud1;
        double dLon = longitud2 - longitud1;

        double valor = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                       Math.cos(latitud1) * Math.cos(latitud2) *
                       Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double distaciaAngular = 2 * Math.asin(Math.sqrt(valor));

        return RADIO_TIERRA * distaciaAngular;
	}
}
