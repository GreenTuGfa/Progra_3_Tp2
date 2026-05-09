package testHerramientas;

import herramientas.GestorConectividad;
import modelos.Conexion;
import modelos.Localidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // ── Inicialización ──────────────────────────────────────────────────────

    @Test
    void testInicializacion_PilarEsSuPropioRepresentante() {
        assertEquals(PILAR, gc.find(PILAR));
    }

    @Test
    void testInicializacion_TigreYLujanEnDistintosComponentes() {
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

    // ── Union básica ────────────────────────────────────────────────────────

    @Test
    void testUnion_TigreYPilar_quedanEnMismoComponente() {
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
    }

    @Test
    void testUnion_TigreYPilar_noAfectaALujan_desdePilar() {
        gc.union(TIGRE, PILAR);
        assertNotEquals(gc.find(PILAR), gc.find(LUJAN));
    }

    // ── Transitividad ───────────────────────────────────────────────────────

    @Test
    void testUnion_transitiva_TigreConectadoALujan() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        assertEquals(gc.find(TIGRE), gc.find(LUJAN));
    }
    @Test
    void testUnion_transitiva_PilarConectadoAZarate() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        gc.union(LUJAN, ZARATE);
        assertEquals(gc.find(PILAR), gc.find(ZARATE));
    }

    // ── Idempotencia ────────────────────────────────────────────────────────

    @Test
    void testUnion_duplicada_sigueConectado() {
        gc.union(TIGRE, PILAR);
        gc.union(TIGRE, PILAR);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
    }

    @Test
    void testUnion_consigoMismo_sigueRepresentandoseASiMismo() {
        gc.union(TIGRE, TIGRE);
        assertEquals(TIGRE, gc.find(TIGRE));
    }

    // ── Path compression ────────────────────────────────────────────────────

    @Test
    void testFind_pathCompression_TigreDevuelveMismaRaizDespuesDeComprimir() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        gc.union(LUJAN, ZARATE);
        Localidad raiz = gc.find(TIGRE);
        assertEquals(raiz, gc.find(TIGRE));
    }

    @Test
    void testFind_pathCompression_PilarApuntaAMismaRaizQueTigre() {
        gc.union(TIGRE, PILAR);
        gc.union(PILAR, LUJAN);
        gc.union(LUJAN, ZARATE);
        assertEquals(gc.find(TIGRE), gc.find(PILAR));
    }


    // ── Componentes independientes ──────────────────────────────────────────

    @Test
    void testDosComponentes_LujanYZarateConectados() {
        gc.union(TIGRE, PILAR);
        gc.union(LUJAN, ZARATE);
        assertEquals(gc.find(LUJAN), gc.find(ZARATE));
    }

    @Test
    void testDosComponentes_TigreYLujanEnDistintosComponentes() {
        gc.union(TIGRE, PILAR);
        gc.union(LUJAN, ZARATE);
        assertNotEquals(gc.find(TIGRE), gc.find(LUJAN));
    }

}