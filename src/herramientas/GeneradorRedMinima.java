package herramientas;

import java.util.ArrayList;
import java.util.List;

import modelos.Conexion;
import modelos.Localidad;

public class GeneradorRedMinima { //IMplementa el AGM

	public List<Conexion> generar(List<Localidad> localidades, List<Conexion> conexiones) {

        //Ordena por costo, usando el compreTo de la clase Conexion
        conexiones.sort(null); 

        GestorConectividad gf = new GestorConectividad(localidades); //Union-Find

        List<Conexion> resultado = new ArrayList<>();

        for (Conexion c : conexiones) {

            Localidad a = c.getOrigen();
            Localidad b = c.getDestino();

            // Verifica si existe un circuito
            if (gf.find(a) != gf.find(b)) {

                resultado.add(c);
                gf.union(a, b);
            }

            // Condición de corte
            if (resultado.size() == localidades.size() - 1) {
                break;
            }
        }

        return resultado;
    }
}
