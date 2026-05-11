package testHerramientas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sistema.herramientas.GeneradorRedMinima;
import sistema.modelos.Conexion;
import sistema.modelos.Localidad;

class GeneradorRedMinimaTest {

    @Test
    void cantidadAristasCorrecta() {

        List<Localidad> locs = List.of(
            new Localidad("a","",0,0),
            new Localidad("b","",0,1),
            new Localidad("c","",1,0)
        );

        List<Conexion> conexiones = new ArrayList<>(List.of(
        	    new Conexion(locs.get(0), locs.get(1), 10),
        	    new Conexion(locs.get(0), locs.get(2), 5),
        	    new Conexion(locs.get(1), locs.get(2), 20)
        	));

        GeneradorRedMinima gen = new GeneradorRedMinima();

        List<Conexion> resultado = gen.generar(locs, conexiones);

        assertEquals(locs.size() - 1, resultado.size());
    }
    
    @Test
    void LasMasBaratas() {

        Localidad a = new Localidad("a","",0,0);
        Localidad b = new Localidad("b","",0,1);
        Localidad c = new Localidad("c","",1,0);

        List<Localidad> locs = List.of(a,b,c);

        List<Conexion> conexiones = new ArrayList<>(List.of(
            new Conexion(a,b,100),
            new Conexion(a,c,1),
            new Conexion(b,c,2)
        ));

        GeneradorRedMinima gen = new GeneradorRedMinima();

        List<Conexion> res = gen.generar(locs, conexiones);

        double total = res.stream().mapToDouble(Conexion::getCosto).sum();

        assertEquals(3, total);
    }
}