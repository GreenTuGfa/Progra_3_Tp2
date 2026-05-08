package interfazGrafica;

import javax.swing.*;
import javax.swing.border.TitledBorder;

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
        estilizarContenedor(this,"Registro de Localidades");
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 600));

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        add(verticalStrut_1);

        JLabel l1 = new JLabel("Nombre");
        l1.setForeground(Color.WHITE);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l1);

        nombre = new JTextField(10);
        estilizarEntradaDatos(nombre);
        add(nombre);

        Component verticalStrut = Box.createVerticalStrut(20);
        add(verticalStrut);

        JLabel l2 = new JLabel("Provincia");
        l2.setForeground(Color.WHITE);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l2);

        provincia = new JTextField(10);
        estilizarEntradaDatos(provincia);
        add(provincia);

        Component verticalStrut_2 = Box.createVerticalStrut(20);
        add(verticalStrut_2);

        JLabel l3 = new JLabel("Latitud");
        l3.setForeground(Color.WHITE);
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l3);

        lat = new JTextField(10);
        estilizarEntradaDatos(lat);
        add(lat);

        Component verticalStrut_3 = Box.createVerticalStrut(20);
        add(verticalStrut_3);

        JLabel l4 = new JLabel("Longitud");
        l4.setForeground(Color.WHITE);
        l4.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(l4);

        lon = new JTextField(10);
        estilizarEntradaDatos(lon);
        add(lon);

        Component verticalStrut_4 = Box.createVerticalStrut(20);
        add(verticalStrut_4);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5)); 
        panelBotones.setBackground(new Color(30, 30, 30));
        panelBotones.setOpaque(true);
        
        JButton agregar = new JButton("Agregar");
        estilizarBoton(agregar);
        agregar.addActionListener(e -> agregar());

        JButton eliminar = new JButton("Eliminar");
        estilizarBoton(eliminar);
        eliminar.addActionListener(e -> eliminar());

        JButton limpiar = new JButton("Limpiar");
        estilizarBoton(limpiar);
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
        
        lista.setBackground(new Color(25, 25, 25)); 
        lista.setForeground(Color.WHITE);          
        lista.setSelectionBackground(new Color(83, 141, 78));
        lista.setSelectionForeground(Color.WHITE);
        lista.setBorder(BorderFactory.createEmptyBorder());

        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        scroll.getViewport().setBackground(new Color(25, 25, 25));
        
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(200, 200));
        add(scroll);
    }

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
    
    
    public void estilizarBoton(JButton boton) {
    	boton.setPreferredSize(new Dimension(80, 30)); 
        
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setForeground(Color.WHITE);

        if (boton.getText().equals("Agregar")) {
            boton.setBackground(new Color(83, 141, 78));
            boton.setBorder(BorderFactory.createLineBorder(new Color(45, 75, 42), 1));
        } else if (boton.getText().equals("Eliminar")) {
            boton.setBackground(new Color(150, 50, 50));
            boton.setBorder(BorderFactory.createLineBorder(new Color(90, 30, 30), 1));
        } else {
            boton.setBackground(new Color(70, 70, 70));
            boton.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        }
    }

	private void eliminar() {

        Localidad seleccionada = lista.getSelectedValue();

        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una localidad");
            return;
        }
        modelo.removeElement(seleccionada);
        planificador.eliminarLocalidad(seleccionada);
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