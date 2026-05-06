package main;

import javax.swing.SwingUtilities;
import interfazGrafica.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal();
        });
    }
}
