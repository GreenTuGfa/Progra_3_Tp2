package interfazGrafica;

import javax.swing.*;
import java.util.List;

import modelos.Conexion;

public class PanelResultado extends JPanel {

    private JTextArea area;

    public PanelResultado() {

        setLayout(new java.awt.BorderLayout());

        area = new JTextArea();
        add(new JScrollPane(area), java.awt.BorderLayout.CENTER);
    }

    public void mostrar(List<Conexion> red, double total) {

        area.setText("Resultado \n\n");

        for (Conexion c : red) {
            area.append(
                c.getOrigen() + " -> " + c.getDestino() +
                " | $" + String.format("%.2f", c.getCosto()) + "\n"
            );
        }

        area.append("\nValor Total: $" + String.format("%.2f", total));
    }
}