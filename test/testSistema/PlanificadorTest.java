package testSistema;

import sistema.herramientas.CalculadorCostos;
import sistema.herramientas.GeneradorConexiones;
import sistema.modelos.Localidad;
import sistema.modelos.ParametrosCostos;
import org.junit.jupiter.api.Test;
import sistema.Planificador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanificadorTest {

    private static final Localidad BAHIA = new Localidad("BahiaBlanca", "Buenos Aires", -38.71765, -62.26549);
    private static final Localidad PILAR = new Localidad("Pilar", "Buenos Aires", -34.4587, -58.9140);
    private static final Localidad CHARATA = new Localidad("Charata", "Chaco", -27.2179902, -61.1873617);
    private static final Localidad SANTAROSA = new Localidad("Santa Rosa", "La Pampa", -36.61617, -64.28991);

    private Planificador cuatroLocalidades(){
        Planificador planificador = new Planificador(new CalculadorCostos());

        planificador.agregarLocalidad(BAHIA);
        planificador.agregarLocalidad(PILAR);
        planificador.agregarLocalidad(CHARATA);
        planificador.agregarLocalidad(SANTAROSA);

        return planificador;
    }

    private Planificador localidadRepetida(){
        Planificador planificador = new Planificador(new CalculadorCostos());

        planificador.agregarLocalidad(BAHIA);
        planificador.agregarLocalidad(BAHIA);
        return planificador;
    }

    private Planificador tresLocalidades(){
        Planificador planificador = new Planificador(new CalculadorCostos());

        planificador.agregarLocalidad(BAHIA);
        planificador.agregarLocalidad(PILAR);
        planificador.agregarLocalidad(CHARATA);

        return planificador;
    }

    private GeneradorConexiones generador;

    @Test
    void agregarLocalidad() {
        Planificador planificador = tresLocalidades();
        planificador.agregarLocalidad(new Localidad("Santa Rosa", "La Pampa", -36.61617, -64.28991));
        assertTrue(4== planificador.localidades().size()&&
                planificador.localidades().get(3).toString().equals("Santa Rosa"));
    }

    @Test
    void eliminarLocalidad() {
        Planificador planificador = cuatroLocalidades();
        planificador.eliminarLocalidad(SANTAROSA);
        assertTrue(3== planificador.localidades().size()&&
                planificador.localidades().get(0).toString().equals("BahiaBlanca")&&
                planificador.localidades().get(1).toString().equals("Pilar")&&
                planificador.localidades().get(2).toString().equals("Charata"));
    }

    @Test
    void costoTotalDeCuatroLocalidades(){
        Planificador planificador= cuatroLocalidades();
        ParametrosCostos parametrosCostos = new ParametrosCostos(15, 8,10);
        assertEquals(26.731,17,planificador.costoTotal(planificador.planificar(parametrosCostos)));
    }

    @Test
    void costoTotalDeUnaCiudadConSigoMisma() {
        Planificador planificador= localidadRepetida();
        ParametrosCostos parametrosCostos = new ParametrosCostos(15, 8,10);
        assertEquals(0,planificador.costoTotal(planificador.planificar(parametrosCostos)));
    }

}