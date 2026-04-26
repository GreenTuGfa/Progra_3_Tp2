package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import herramientas.CalculadorCostos;
import herramientas.GeneradorConexiones;
import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;
import sistema.*;

class GeneradorConexionesTest {
	
	private List<Localidad> localidades;
    private List<Conexion> conexiones;

    @BeforeEach
    public void inicializar() {
        localidades = List.of(
                new Localidad("a","",-54.550,34.123), 
                new Localidad("b","",108.23,15.423), 
                new Localidad("c","",-15.783,27.909),
                new Localidad("d","",-32.947,-21.672)
        );

        ParametrosCostos costos = new ParametrosCostos(1000, 30, 10000);
        CalculadorCostos calc = new CalculadorCostos();
        GeneradorConexiones generadorConexiones = new GeneradorConexiones(calc);
        conexiones = generadorConexiones.generarConexiones(localidades,costos);
    }

    @Test
    void cantidadConexionesCorrectas() {
        // n(n-1)/2 = 4*3/2 = 6
        assertEquals(6, conexiones.size());
        
    }

    @Test
    void conexionesSinLoops() {
        for (Conexion conexion : conexiones) {
            assertNotEquals(conexion.getOrigen(), conexion.getDestino());
        }
    }
 
    @Test
    void costosConexionCorrecto() {
    	Conexion c = conexiones.get(0);
    	
    	ParametrosCostos costos = new ParametrosCostos(1000, 30, 10000);
    	CalculadorCostos calc = new CalculadorCostos();
        double esperado = calc.calcular(c.getOrigen(),c.getDestino(),costos);

        assertEquals(esperado, c.getCosto(), 0.0001);
    }

}

