package testSistema;

import herramientas.CalculadorCostos;
import herramientas.GeneradorConexiones;
import modelos.Localidad;
import modelos.ParametrosCostos;
import org.junit.jupiter.api.Test;
import sistema.Planificador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanificadorTest {

    private static final Localidad BAHIA = new Localidad("BahiaBlanca", "Buenos Aires", -38.71765, -62.26549);
    private static final Localidad PILAR = new Localidad("Pilar", "Buenos Aires", -34.4587, -58.9140);
    private static final Localidad CHARATA = new Localidad("Charata", "Chaco", -27.2179902, -61.1873617);
    private static final Localidad SANTAROSA = new Localidad("Santa Rosa", "La Pampa", -36.61617, -64.28991);

    private Planificador localidades1(){
        Planificador planificador1 = new Planificador(new CalculadorCostos());

        planificador1.agregarLocalidad(BAHIA);
        planificador1.agregarLocalidad(PILAR);
        planificador1.agregarLocalidad(CHARATA);
        planificador1.agregarLocalidad(SANTAROSA);

        return planificador1;
    }

    private Planificador localidades2(){
        Planificador planificador1 = new Planificador(new CalculadorCostos());

        planificador1.agregarLocalidad(BAHIA);
        planificador1.agregarLocalidad(BAHIA);
        return planificador1;
    }

    private Planificador localidades3(){
        Planificador planificador3 = new Planificador(new CalculadorCostos());

        planificador3.agregarLocalidad(BAHIA);
        planificador3.agregarLocalidad(PILAR);
        planificador3.agregarLocalidad(CHARATA);

        return planificador3;
    }

    private GeneradorConexiones generador;

    @Test
    void agregarLocalidad() {
        Planificador planificador = localidades3();
        planificador.agregarLocalidad(new Localidad("Santa Rosa", "La Pampa", -36.61617, -64.28991));
        assertTrue(4== planificador.localidades().size()&&
                planificador.localidades().get(3).toString().equals("Santa Rosa"));
    }

    @Test
    void eliminarLocalidad() {
        Planificador planificador = localidades1();
        planificador.eliminarLocalidad(SANTAROSA);
        assertTrue(3== planificador.localidades().size()&&
                planificador.localidades().get(0).toString().equals("BahiaBlanca")&&
                planificador.localidades().get(1).toString().equals("Pilar")&&
                planificador.localidades().get(2).toString().equals("Charata"));
    }

    @Test
    void costoTotalDeLocalidades1(){
        Planificador planificador= localidades1();
        ParametrosCostos parametrosCostos = new ParametrosCostos(15, 8,10);
        assertEquals(26.731,17,planificador.costoTotal(planificador.planificar(parametrosCostos)));
    }

    @Test
    void costoTotalDeUnaCiudadConSigoMisma() {
        Planificador planificador= localidades2();
        ParametrosCostos parametrosCostos = new ParametrosCostos(15, 8,10);
        assertEquals(0,planificador.costoTotal(planificador.planificar(parametrosCostos)));
    }

}