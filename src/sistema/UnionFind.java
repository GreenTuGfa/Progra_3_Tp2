package sistema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFind {

	private Map<Localidad,Localidad> padre;
	
	public UnionFind(List<Localidad> localidades) {
		
        for (Localidad l : localidades) {
            padre.put(l, l); //Al inicializarse cada localidad se "representa" a si misma
        }
    }
	public Localidad find(Localidad l) {
        if (padre.get(l) != l) {
            padre.put(l, find(padre.get(l))); // compresión de camino
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

	public static void main(String[] args) {
		Map<Integer,Integer>mapa = new HashMap<Integer,Integer>();
		
		mapa.put(1, 1);
		mapa.put(1, 2);
		mapa.put(1, 3);
		System.out.println(mapa.get(1));
		
	}
}
