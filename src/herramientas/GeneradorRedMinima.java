package herramientas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import modelos.Conexion;
import modelos.Localidad;

public class GeneradorRedMinima { //IMplementa el AGM

	public List<Conexion> generar(List<Localidad> localidades, List<Conexion> conexiones) {

        //Ordena por costo
        conexiones.sort(Comparator.comparingDouble(Conexion::getCosto));

        UnionFind uf = new UnionFind(localidades);

        List<Conexion> resultado = new ArrayList<>();

        for (Conexion c : conexiones) {

            Localidad a = c.getOrigen();
            Localidad b = c.getDestino();

            // Verifica si existe un circuito
            if (uf.find(a) != uf.find(b)) {

                resultado.add(c);
                uf.union(a, b);
            }

            // Condición de corte
            if (resultado.size() == localidades.size() - 1) {
                break;
            }
        }

        return resultado;
    }
}
