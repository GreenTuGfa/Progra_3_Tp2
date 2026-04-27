package interfazGrafica;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import modelos.Localidad;


public class EntradaDatos extends JPanel{
	private Mapa mapa;
	private String origen;
	private double latitud_origen;
	private double longitud_origen;
	private String destino;
	private double latitud_destino;
	private double longitud_destino;
	
	
	private JButton botonAceptar;

	public EntradaDatos() {
	    Color colorSeccionBaja = new Color(37, 37, 37);
	    this.setPreferredSize(new Dimension(500, 100));
		this.setLayout(new BorderLayout());
		setBackground(new Color(15, 15, 15));

		generarSeccionBaja();
	}

	private void generarSeccionBaja() {
		origen = crearEntradaEstandar("Ingrese el localidad y provincia").getText();
		latitud_origen  = volverDouble(crearEntradaEstandar("Ingrese latitud").getText());
		longitud_origen = volverDouble(crearEntradaEstandar("Ingrese longitud").getText());
		
		destino = crearEntradaEstandar("Ingrese el destino").getText();
		latitud_destino  = volverDouble(crearEntradaEstandar("Ingrese latitud").getText());
		longitud_destino = volverDouble(crearEntradaEstandar("Ingrese longitud").getText());
		
		botonAceptar = new JButton("ENVIAR");
		botonAceptar.setPreferredSize(new Dimension(120, 50));
		botonAceptar.setBackground(new Color(83, 141, 78));
		botonAceptar.setForeground(Color.WHITE);
		botonAceptar.setFocusPainted(false);
		botonAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botonAceptar.setFont(new Font("Arial", Font.BOLD, 14));

		
		botonAceptar.addActionListener(e -> {
			Localidad localidad1 = new Localidad(conseguirLocalidad(origen),conseguirProvincia(origen),latitud_origen,longitud_origen,1);
			Localidad localidad2 = new Localidad(conseguirLocalidad(destino),conseguirProvincia(destino),latitud_destino,longitud_destino,1);
			
		});
        add(botonAceptar, BorderLayout.EAST);
        revalidate();
        repaint();
	}
	
	private void eventosDelTeclado(JTextField campoTexto) {
	    campoTexto.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent teclaPresionada) {
	            char tecla = teclaPresionada.getKeyChar();
	            
	            if (campoTexto.getText().length() >= 5 || !Character.isLetter(tecla)) {
	            	teclaPresionada.consume(); 
	            }
	        }

	        @Override
	        public void keyPressed(KeyEvent teclaPresionada) {
	            if (teclaPresionada.getKeyCode() == KeyEvent.VK_ENTER) {
	                botonAceptar.doClick(); 
	            }
	        }
	    });
	}
	
	private JTextField crearEntradaEstandar(String mensaje) {
		JTextField campoTexto = new JTextField(mensaje);
		campoTexto.setPreferredSize(new Dimension(200, 50));
        campoTexto.setBackground(new Color(58, 58, 60));
        campoTexto.setForeground(Color.WHITE);
        campoTexto.setFont(new Font("Arial", Font.BOLD, 16));
		campoTexto.setHorizontalAlignment(JTextField.CENTER);
		campoTexto.setCaretColor(Color.WHITE); 														
		campoTexto.setBorder(BorderFactory.createLineBorder(new Color(58, 58, 60), 2)); 	
		add(campoTexto, BorderLayout.CENTER);
		eventosDelTeclado(campoTexto);
        return campoTexto;
    }
	
	public String conseguirLocalidad(String texto) {
		return texto.split(",")[0];
	}
	
	public String conseguirProvincia(String texto) {
		return texto.split(",")[1];
	}
	
	public double volverDouble(String texto) {
		double valor = Double.parseDouble(texto);
		return valor;
	}
}
