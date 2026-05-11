package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.*;

import sistema.modelos.Conexion;
import sistema.modelos.Localidad;

public class PanelMapa extends JPanel {

    private static JMapViewer mapa;

    public PanelMapa() {
        setLayout(new BorderLayout());
        mapa = new JMapViewer();
        add(mapa, BorderLayout.CENTER);
    }

    public static void mostrar(List<Localidad> localidades, List<Conexion> conexiones) {
        //Limpiamos el mapa antes de dibujar o redibujar
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();

        //dibujar puntos localidades
        for (Localidad localidad : localidades) {
            Coordinate coord = new Coordinate(localidad.getLatitud(), localidad.getLongitud());
            MapMarkerDot marker = new MapMarkerDot(coord);
            marker.setName(localidad.toString());
            mapa.addMapMarker(marker);
        }

        //AGM para dibujar las lineas
        for (Conexion conexion : conexiones) {
            Coordinate conexion1 = new Coordinate(conexion.getOrigen().getLatitud(), conexion.getOrigen().getLongitud());
            Coordinate conexion2 = new Coordinate(conexion.getDestino().getLatitud(), conexion.getDestino().getLongitud());
            

            List<Coordinate> puntos = new ArrayList<>();
            puntos.add(conexion1);
            puntos.add(conexion2);
            puntos.add(conexion2); 
            // Se añade el ultimo punto dos veces para asegurar que sea compatible con MapPolygonImpl (pide 3 puntos para cerrar el "poligono")
            
            mapa.addMapPolygon(new MapPolygonImpl(puntos));
        }

        //centra el zoom en la 1era localidad si esta existe
        if (!localidades.isEmpty()) {
            Localidad localidad = localidades.get(0);
            mapa.setDisplayPosition(new Coordinate(localidad.getLatitud(), localidad.getLongitud()), 6);
        }
    }
}