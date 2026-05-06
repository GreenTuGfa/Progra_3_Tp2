package interfazGrafica;

import javax.swing.*;
import java.awt.*;

import modelos.ParametrosCostos;

public class PanelParametros extends JPanel {

    private JTextField precioKm, porcentaje, costoFijo;

    public PanelParametros() {


    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	setPreferredSize(new Dimension(250, 200));

    	add(Box.createVerticalStrut(20));

    	JLabel l1 = new JLabel("Costo por km");
    	l1.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(l1);

    	precioKm = new JTextField(10);
    	precioKm.setMaximumSize(new Dimension(150, 25));
    	precioKm.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(precioKm);

    	add(Box.createVerticalStrut(20));

    	JLabel l2 = new JLabel("% aumento (>300km)");
    	l2.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(l2);

    	porcentaje = new JTextField(10);
    	porcentaje.setMaximumSize(new Dimension(150, 25));
    	porcentaje.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(porcentaje);

    	add(Box.createVerticalStrut(20));

    	JLabel l3 = new JLabel("Costo fijo provincia");
    	l3.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(l3);

    	costoFijo = new JTextField(10);
    	costoFijo.setMaximumSize(new Dimension(150, 25));
    	costoFijo.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(costoFijo);
    }

    public ParametrosCostos getParametros() {

        return new ParametrosCostos(
                Double.parseDouble(precioKm.getText()),
                Double.parseDouble(porcentaje.getText()),
                Double.parseDouble(costoFijo.getText())
        );
    }
}