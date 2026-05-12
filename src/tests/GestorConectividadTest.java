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
    void inicializacion_autocontrol() {
        assertEquals(PILAR, gc.find(PILAR));
    }

    @Test
    void inicializacion_aislamiento() {
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

    @Test
    void union_conectaLocalidades() {
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
        
    }

    @Test
    void union_noAfectaTerceros() {
        gc.union(TIGRE, PILAR);
        assertNotEquals(gc.find(PILAR), gc.find(LUJAN));
    }

    @Test
    void union_esTransitiva() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        assertEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

    @Test
    void union_cadenaLarga() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        gc.union(LUJAN, ZARATE);
        assertEquals(gc.find(PILAR), gc.find(ZARATE));
    }

    @Test
    void union_duplicada() {
        gc.union(TIGRE, PILAR);
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
    }

    @Test
    void union_consigoMismo() {
        gc.union(TIGRE, TIGRE);
        assertEquals(TIGRE, gc.find(TIGRE));
    }

    @Test
    void find_esConsistente() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        Localidad raiz = gc.find(TIGRE);
        assertEquals(raiz, gc.find(TIGRE));
    }

    @Test
    void componentes_multiplesGrupos() {
        gc.union(TIGRE, PILAR);
        gc.union(LUJAN, ZARATE);

        assertEquals(gc.find(LUJAN), gc.find(ZARATE));
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

}