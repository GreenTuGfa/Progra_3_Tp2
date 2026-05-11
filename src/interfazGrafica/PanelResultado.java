package interfazGrafica;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import sistema.modelos.Conexion;

public class PanelResultado extends JPanel {

    private JTextArea area;

    public PanelResultado() {

    	setLayout(new java.awt.BorderLayout());
        area = new JTextArea("Esperando planificación...");
        estilizarPanelResultado(area); 
        add(new JScrollPane(area), java.awt.BorderLayout.CENTER);
    }

    public void mostrar(List<Conexion> red, double total) {

        area.setText("Resultado \n\n");

        for (Conexion conexion : red) {
            area.append(
                "De " + conexion.getOrigen() + " a " + conexion.getDestino() +
                " = $" + String.format("%.2f", conexion.getCosto()) + "\n"
            );
        }

        area.append("\nValor Total: $" + String.format("%.2f", total));
    }
    
    public void estilizarPanelResultado(JTextArea areaTexto) {
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 14)); 

        areaTexto.setBackground(new Color(25, 25, 25)); 
        areaTexto.setForeground(new Color(83, 141, 78)); 
        
        areaTexto.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(83, 141, 78)), 
            "", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.PLAIN, 12), 
            Color.WHITE
        ));
    }
}