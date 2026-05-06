package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import modelos.Localidad;
import sistema.Planificador;

public class PanelLocalidades extends JPanel {

    private Planificador planificador;
    private DefaultListModel<Localidad> modelo;
    private JList<Localidad> lista;
    private JTextField nombre, provincia, lat, lon;

    public PanelLocalidades(Planificador planificador) {

        this.planificador = planificador;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 600));

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        add(verticalStrut_1);

        JLabel l1 = new JLabel("Nombre");
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l1);

        nombre = new JTextField(10);
        nombre.setMaximumSize(new Dimension(150, 25));
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(nombre);

        Component verticalStrut = Box.createVerticalStrut(20);
        add(verticalStrut);

        JLabel l2 = new JLabel("Provincia");
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l2);

        provincia = new JTextField(10);
        provincia.setMaximumSize(new Dimension(150, 25));
        provincia.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(provincia);

        Component verticalStrut_2 = Box.createVerticalStrut(20);
        add(verticalStrut_2);

        JLabel l3 = new JLabel("Latitud");
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l3);

        lat = new JTextField(10);
        lat.setMaximumSize(new Dimension(150, 25));
        lat.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lat);

        Component verticalStrut_3 = Box.createVerticalStrut(20);
        add(verticalStrut_3);

        JLabel l4 = new JLabel("Longitud");
        l4.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l4);

        lon = new JTextField(10);
        lon.setMaximumSize(new Dimension(150, 25));
        lon.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lon);

        Component verticalStrut_4 = Box.createVerticalStrut(20);
        add(verticalStrut_4);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton agregar = new JButton("Agregar");
        agregar.addActionListener(e -> agregar());

        JButton eliminar = new JButton("Eliminar");
        eliminar.addActionListener(e -> eliminar());

        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(e -> limpiarCampo());

        panelBotones.add(agregar);
        panelBotones.add(eliminar);
        panelBotones.add(limpiar);

        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelBotones);

        modelo = new DefaultListModel<>();
        
        Component verticalStrut_4_1 = Box.createVerticalStrut(20);
        add(verticalStrut_4_1);
        lista = new JList<>(modelo);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(200, 200));
        add(scroll);
    }

    private void eliminar() {

        Localidad seleccionada = lista.getSelectedValue();

        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una localidad");
            return;
        }
        modelo.removeElement(seleccionada);
    }

	private void agregar() {
        try {
            Localidad l = new Localidad(
                    nombre.getText(),
                    provincia.getText(),
                    Double.parseDouble(lat.getText()),
                    Double.parseDouble(lon.getText())
            );

            planificador.agregarLocalidad(l);
            modelo.addElement(l);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
        }
    }

	private void limpiarCampo() {
	    nombre.setText("");
	    provincia.setText("");
	    lat.setText("");
	    lon.setText("");
	}
	
    public List<Localidad> getLocalidades() {

        List<Localidad> ListaLoc = new ArrayList<>();

        for (int i = 0; i < modelo.size(); i++) {
            ListaLoc.add(modelo.getElementAt(i));
        }
         return ListaLoc;
    }
}