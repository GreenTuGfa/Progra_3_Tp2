package sistema;

public class CalculadorDistancia {

	private static final double RADIO_TIERRA = 6371;
	
	public CalculadorDistancia() {}
	
	public double calcular(Localidad a, Localidad b) {
		
		double lat1 = Math.toRadians(a.getLatitud());
        double lon1 = Math.toRadians(a.getLongitud());
        double lat2 = Math.toRadians(b.getLatitud());
        double lon2 = Math.toRadians(b.getLongitud());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double valor = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                       Math.cos(lat1) * Math.cos(lat2) *
                       Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.asin(Math.sqrt(valor));

        return RADIO_TIERRA * c;
	}
}
