package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import sistema.modelos.Conexion;
import sistema.modelos.Localidad;

public class PanelMapa extends JPanel {

    private static JMapViewer mapa;

    public PanelMapa() {
        setLayout(new BorderLayout());
        mapa = new JMapViewer();
        mapa.setTileSource(new OsmTileSource.TransportMap());
        add(mapa, BorderLayout.CENTER);
    }

    public static void mostrar(List<Localidad> localidades, List<Conexion> conexiones) {
        //Limpiamos el mapa antes de dibujar o redibujar
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();

        //dibujar puntos localidades
        for (Localidad localidad : localidades) {
            Coordinate coord = new Coordinate(localidad.getLatitud(), localidad.getLongitud());
            MapMarkerDot marker = new MapMarkerDot(localidad.toString(), coord);
            
            estilizarPunto(marker);
            
            mapa.addMapMarker(marker);
        }

        //AGM para dibujar las lineas
        for (Conexion conexion : conexiones) {
            List<Coordinate> puntos = new ArrayList<>();
            puntos.add(new Coordinate(conexion.getOrigen().getLatitud(), conexion.getOrigen().getLongitud()));
            puntos.add(new Coordinate(conexion.getDestino().getLatitud(), conexion.getDestino().getLongitud()));
            puntos.add(new Coordinate(conexion.getDestino().getLatitud(), conexion.getDestino().getLongitud()));

            MapPolygonImpl linea = new MapPolygonImpl(puntos);
            
            estilizarLinea(linea);
            
            mapa.addMapPolygon(linea);
        }

        //centra el zoom en la 1era localidad (si es que existe claro esta)
        if (!localidades.isEmpty()) {
            Localidad localidad = localidades.get(0);
            mapa.setDisplayPosition(new Coordinate(localidad.getLatitud(), localidad.getLongitud()), 6);
        }
    }
    
    public static void estilizarPunto(MapMarkerDot marker) {
    	 marker.getStyle().setBackColor(new Color(83, 141, 78));
         marker.getStyle().setColor(Color.WHITE);              
         marker.getStyle().setFont(new Font("Arial", Font.BOLD, 12));
    }
    public static void estilizarLinea(MapPolygonImpl linea) {
    	linea.getStyle().setColor(new Color(45, 75, 42)); 
        linea.getStyle().setStroke(new BasicStroke(3f));
   }
}