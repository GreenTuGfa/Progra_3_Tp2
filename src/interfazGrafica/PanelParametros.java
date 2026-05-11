package interfazGrafica;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

import sistema.modelos.ParametrosCostos;

public class PanelParametros extends JPanel {

    private JTextField precioKm, porcentaje, costoFijo;

    public PanelParametros() {

    	estilizarContenedor(this,"Parametros");

    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	setPreferredSize(new Dimension(250, 200));

    	add(Box.createVerticalStrut(20));

    	JLabel l1 = new JLabel("Costo por km");
    	estilizarJLabel(l1);
    	add(l1);

    	precioKm = new JTextField(10);
    	estilizarEntradaDatos(precioKm);
    	add(precioKm);

    	add(Box.createVerticalStrut(20));

    	JLabel l2 = new JLabel("% aumento (>300km)");
    	estilizarJLabel(l2);
    	add(l2);

    	porcentaje = new JTextField(10);
    	estilizarEntradaDatos(porcentaje);
    	add(porcentaje);

    	add(Box.createVerticalStrut(20));

    	JLabel l3 = new JLabel("Costo fijo provincia");
    	estilizarJLabel(l3);
    	add(l3);

    	costoFijo = new JTextField(10);
    	estilizarEntradaDatos(costoFijo);
    	add(costoFijo);
    }

    public ParametrosCostos getParametros() {

        return new ParametrosCostos(
                Double.parseDouble(precioKm.getText()),
                Double.parseDouble(porcentaje.getText()),
                Double.parseDouble(costoFijo.getText())
        );
    }
    
    //DISEÑO
    
    public void estilizarEntradaDatos(JTextField campoTexto) {
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        campoTexto.setForeground(new Color(45, 45, 45));
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        Dimension dim = new Dimension(150, 25);
        campoTexto.setPreferredSize(dim);
        campoTexto.setMaximumSize(dim); 
        
        campoTexto.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
    }
    
    public void estilizarContenedor(JPanel panel, String titulo) {
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)), 
            titulo, 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 12), 
            Color.GRAY
        ));
    }
    
    public void estilizarJLabel(JLabel label) {
    	label.setForeground(Color.WHITE);
    	label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}