package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import modelos.Localidad;
import sistema.Planificador;

public class Mapa {

	private JFrame frame;
	private JMapViewer mapa;
	private JPanel panelMapa;
	private JPanel panelControles;
	private EntradaDatos entradaDatos;
	private Planificador planificador;
	private Localidad origen;
	private Localidad destino;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mapa window = new Mapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Mapa() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 531, 504);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JMapViewer");
		
		panelMapa = new JPanel();
		panelMapa.setBounds(10, 11, 517, 454);
        frame.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelMapa);
    	
		planificador = new Planificador(null);
		entradaDatos = new EntradaDatos(); 
		panelControles = new JPanel();
		panelControles.setBounds(457,11,242,446);
		panelControles.setLayout(null);

		
		mapa = new JMapViewer();
		mapa.setZoomControlsVisible(true);  //quita el +/-
		
    	panelMapa.add(entradaDatos, BorderLayout.SOUTH);
    	
    	posicionarZoom(origen,destino);
		generarPuntos(origen,destino);
	}


	private void posicionarZoom(Localidad origen, Localidad destino) {
		double centroLatitud=Math.abs((origen.getLatitud()+destino.getLatitud())/2);
		double centroLongitud=Math.abs((origen.getLongitud()+destino.getLongitud())/2);

		Coordinate zoomZona = new Coordinate(centroLatitud,centroLongitud);
		mapa.setDisplayPosition(zoomZona, 12);		
	}


	private void generarPuntos(Localidad origen, Localidad destino) {
		//localidades
		MapMarker loc1 = new MapMarkerDot("Origen",origen.darCoordenada());
		loc1.getStyle().setBackColor(Color.yellow);
		loc1.getStyle().setColor(Color.yellow);
		mapa.addMapMarker(loc1);
		
		MapMarker loc2 = new MapMarkerDot("Destino",destino.darCoordenada());
		loc2.getStyle().setBackColor(Color.blue);
		loc2.getStyle().setColor(Color.blue);
		mapa.addMapMarker(loc2);
		
		//Agregar poligono
		/*
		ArrayList<Coordinate> coordenadas = new ArrayList<Coordinate>();
		coordenadas.add(new Coordinate(-34.521,-58.7008));
		coordenadas.add(new Coordinate(-34.546,-58.7191));
		coordenadas.add(new Coordinate(-34.521,-58.7008));
		coordenadas.add(new Coordinate(-34.559,-58.721));
		coordenadas.add(new Coordinate(-34.569,-58.725));
		coordenadas.add(new Coordinate(-34.532,-58.730));
		
		MapPolygon poligono = new MapPolygonImpl(coordenadas);
		mapa.addMapPolygon(poligono);
		*/
		panelMapa.add(mapa,BorderLayout.NORTH);
		
	}
}
