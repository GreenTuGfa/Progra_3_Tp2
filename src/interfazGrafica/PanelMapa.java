package interfazGrafica;

import javax.swing.*;
import java.awt.*;
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

        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();

        for (Localidad l : localidades) {
            Coordinate coord = new Coordinate(
                    l.getLatitud(),
                    l.getLongitud()
            );

            MapMarkerDot marker = new MapMarkerDot(coord);
            marker.setName(l.toString());
            mapa.addMapMarker(marker);
        }

        for (Conexion c : conexiones) {
            Coordinate c1 = new Coordinate(
                    c.getOrigen().getLatitud(),
                    c.getOrigen().getLongitud()
            );
            Coordinate c2 = new Coordinate(
                    c.getDestino().getLatitud(),
                    c.getDestino().getLongitud()
            );
            mapa.addMapPolygon(new MapPolygonImpl(c1, c2, c2));
        }

        if (!localidades.isEmpty()) {
            Localidad l = localidades.get(0);

            mapa.setDisplayPosition(
                    new Coordinate(l.getLatitud(), l.getLongitud()),
                    6
            );
        }
    }
}