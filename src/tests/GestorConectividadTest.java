package tests;

import sistema.herramientas.GestorConectividad;

import sistema.modelos.Localidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class GestorConectividadTest {

    private static final Localidad TIGRE  = new Localidad("Tigre",  "Buenos Aires", -34.4267, -58.5771);
    private static final Localidad PILAR  = new Localidad("Pilar",  "Buenos Aires", -34.4587, -58.9140);
    private static final Localidad LUJAN  = new Localidad("Luján",  "Buenos Aires", -34.5700, -59.1031);
    private static final Localidad ZARATE = new Localidad("Zárate", "Buenos Aires", -34.0983, -59.0289);

    private List<Localidad> localidades;
    private GestorConectividad gc;

    @BeforeEach
    void setUp() {
        localidades = new ArrayList<>(List.of(TIGRE, PILAR, LUJAN, ZARATE));
        gc = new GestorConectividad(localidades);
    }
    @Test
    void localidadSeRepresentaAsiMisma() {
        assertEquals(PILAR, gc.find(PILAR));
    }

    @Test
    void localidadesNoEstanConectadas() {
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

    @Test
    void conexionEntreDosLocalidades() {
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
        
    }

    @Test
    void conexionEntreDosLocalidadesNoAfectaAlRestoDeLocalidades() {
        gc.union(TIGRE, PILAR);
        assertNotEquals(gc.find(PILAR), gc.find(LUJAN));
    }

    @Test
    void conexionEsTransitiva() { //TEST IMPORTANTE - Pues demuestra que el nodo padre se actualiza con la union
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        assertEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

    @Test
    void conexionesVarias() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        gc.union(LUJAN, ZARATE);
        assertEquals(gc.find(PILAR), gc.find(ZARATE));
    }

    @Test
    void dobleConexion() {
        gc.union(TIGRE, PILAR);
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
    }

    @Test
    void ConexionConSigoMismo() {
        gc.union(TIGRE, TIGRE);
        assertEquals(TIGRE, gc.find(TIGRE));
    }


    @Test
    void independenciaDeGruposConexos() {
        gc.union(TIGRE, PILAR);
        gc.union(LUJAN, ZARATE);

        assertEquals(gc.find(LUJAN), gc.find(ZARATE));
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

}