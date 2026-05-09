package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.*;

import modelos.Conexion;
import modelos.Localidad;

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
        for (Localidad l : localidades) {
            Coordinate coord = new Coordinate(l.getLatitud(), l.getLongitud());
            MapMarkerDot marker = new MapMarkerDot(coord);
            marker.setName(l.toString());
            mapa.addMapMarker(marker);
        }

        //AGM para dibujar las lineas
        for (Conexion c : conexiones) {
            Coordinate c1 = new Coordinate(c.getOrigen().getLatitud(), c.getOrigen().getLongitud());
            Coordinate c2 = new Coordinate(c.getDestino().getLatitud(), c.getDestino().getLongitud());
            

            List<Coordinate> puntos = new ArrayList<>();
            puntos.add(c1);
            puntos.add(c2);
            puntos.add(c2); 
            // Se añade el ultimo punto dos veces para asegurar que sea compatible con MapPolygonImpl (pide 3 puntos para cerrar el "poligono")
            
            mapa.addMapPolygon(new MapPolygonImpl(puntos));
        }

        //centra el zoom en la 1era localidad si esta existe
        if (!localidades.isEmpty()) {
            Localidad l = localidades.get(0);
            mapa.setDisplayPosition(new Coordinate(l.getLatitud(), l.getLongitud()), 6);
        }
    }
}