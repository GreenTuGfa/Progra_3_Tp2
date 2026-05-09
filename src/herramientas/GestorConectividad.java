package herramientas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelos.Localidad;

/**
 * IMPLEMENTA EL UNION FIND
 * 	La clase esta implementada a partir de un Map que almacena las componentes conexas, y no a partir de un Array,
 * que es como se enseño en clase. De esta forma me parece mas legible y facil de entender, aunque desconosco 
 * si tiene un mejor o peor rendimiento en comparacion a un array con indices. Por las dudas recomiendo crear una 
 * alternativa hacha a partir de un Array para tener a mano.
 * */
public class GestorConectividad {

	private Map<Localidad,Localidad> padre;
	
	public GestorConectividad(List<Localidad> localidades) {
		padre = new HashMap<>();
		
        for (Localidad l : localidades) {
            padre.put(l, l); //Al inicializarse cada localidad se "representa" a si misma
        }
    }
	public Localidad find(Localidad l) {
        if (padre.get(l) != l) {
            padre.put(l, find(padre.get(l))); // Se pone como padre, al padre del padre. 
            								 //  Ejm: 3->2, 2->1, 1->1 pasa a 3->1, 2->1, 1->1			
        }
        return padre.get(l);
    }
	
	public void union(Localidad a, Localidad b) {
        Localidad rootA = find(a);
        Localidad rootB = find(b);

        if (rootA != rootB) {
            padre.put(rootA, rootB);
        }
    }


}
