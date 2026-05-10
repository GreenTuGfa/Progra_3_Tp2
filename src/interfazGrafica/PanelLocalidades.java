package interfazGrafica;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import herramientas.GestorArchivos;

import java.lang.reflect.Type;

import modelos.Localidad;
import sistema.Planificador;

public class PanelLocalidades extends JPanel {

    private Planificador planificador;
    private DefaultListModel<Localidad> modelo;
    private JList<Localidad> lista;
    private JTextField nombre, provincia, latitud, longitud;
	private JComboBox<Localidad> atajoCapitales;
	private GestorArchivos archivos = new GestorArchivos();

    public PanelLocalidades(Planificador planificador) {

        this.planificador = planificador;
        estilizarContenedor(this,"Registro de Localidades");
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 600));
        
        inicializarAtajoCapitales();

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        add(verticalStrut_1);

        JLabel l1 = new JLabel("Nombre");
        estilizarJLabel(l1);
        add(l1);

        nombre = new JTextField(10);
        estilizarEntradaDatos(nombre);
        add(nombre);

        Component verticalStrut = Box.createVerticalStrut(20);
        add(verticalStrut);

        JLabel l2 = new JLabel("Provincia");
        estilizarJLabel(l2);
        add(l2);

        provincia = new JTextField(10);
        estilizarEntradaDatos(provincia);
        add(provincia);

        Component verticalStrut_2 = Box.createVerticalStrut(20);
        add(verticalStrut_2);

        JLabel l3 = new JLabel("Latitud");
        estilizarJLabel(l3);
        add(l3);

        latitud = new JTextField(10);
        estilizarEntradaDatos(latitud);
        add(latitud);

        Component verticalStrut_3 = Box.createVerticalStrut(20);
        add(verticalStrut_3);

        JLabel l4 = new JLabel("Longitud");
        estilizarJLabel(l4);
        add(l4);

        longitud = new JTextField(10);
        estilizarEntradaDatos(longitud);
        add(longitud);

        Component verticalStrut_4 = Box.createVerticalStrut(20);
        add(verticalStrut_4);
        
        //agregar botones
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
        add(scroll);
        
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
		/*Para mandar distintos msj de error al usuario, dependemos de almacenar los datos de cada JTextField 
		para comparar. Con trim() unimos los string para comparar de forma exacta,sin los espacios*/
		
		String nombreTexto = nombre.getText().trim();
	    String provinciaTexto = provincia.getText().trim();
	    String latTexto = latitud.getText().trim();
	    String lonTexto = longitud.getText().trim();

	    // Validamos que no quede ningun campo vacio antes de agregarlo como dato
	    if (nombreTexto.isEmpty() || provinciaTexto.isEmpty() || latTexto.isEmpty() || lonTexto.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    try {
	        //convertimos las latitudes en su valor numerico
	        double latitud = Double.parseDouble(latTexto);
	        double longitud = Double.parseDouble(lonTexto);
	        
	        /*comparamos para hacer saltar excepcion cada que un rango este fuera del permitido por mundo ACTUAL 
	        (no sabemos si cuando Elon Musk domine la luna el mapa se valla a ampliar)*/
	        if (latitud < -90 || latitud > 90) {
	            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
	        }
	        if (longitud < -180 || longitud > 180) {
	            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
	        }

	        //con los datos a insertar ya verificados, agregamos finalmente la Localidad
	        Localidad l = new Localidad(nombreTexto, provinciaTexto, latitud, longitud);
	        planificador.agregarLocalidad(l);
	        modelo.addElement(l);
	        limpiarCampo();

	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "La latitud y longitud deben ser números válidos (ej: -34.51).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
	    } catch (IllegalArgumentException e) {
	        JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Rango", JOptionPane.WARNING_MESSAGE);
	    }
	}

	private void inicializarAtajoCapitales() {
		List<Localidad> capitales = archivos.cargar("capitales.json");
		
	    // Si esto imprime "DEBUG: Lista vacía", el problema es el archivo capitales.json
	    if (capitales == null || capitales.isEmpty())
	        return;
	    
	    //
	    JLabel etiquetaAtajo = new JLabel("Cargar Capitales Arg");
	    etiquetaAtajo.setForeground(Color.WHITE);
	    etiquetaAtajo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(etiquetaAtajo);

	    atajoCapitales = new JComboBox<>(capitales.toArray(new Localidad[0]));
	    atajoCapitales.setMaximumSize(new Dimension(200, 25));
	    atajoCapitales.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // Listener para rellenar los campos
	    atajoCapitales.addActionListener(e -> {
	        Localidad seleccionada = (Localidad) atajoCapitales.getSelectedItem();
	        if (seleccionada != null) {
	            nombre.setText(seleccionada.toString());
	            provincia.setText(seleccionada.getProvincia());
	            latitud.setText(String.valueOf(seleccionada.getLatitud()));
	            longitud.setText(String.valueOf(seleccionada.getLongitud()));
	        }
	    });

	    this.add(atajoCapitales);
	}
	
	/*a diferencia del que existe en planificador, como este txt no influye por la planificacion en si
	hacemos que sea parte del panel*/
	private List<Localidad> cargarCapitalesDesdeJSON() {
	    try (FileReader lector = new FileReader("capitales.json")) {
	        Type tipoLista = new TypeToken<ArrayList<Localidad>>(){}.getType();
	        return new Gson().fromJson(lector, tipoLista);
	    } catch (IOException e) {
	        return new ArrayList<>(); // Si errra devolvemos una lista vacía
	    }
	}
	
	//lo usamos para cargar los archivos almacenados del JSON
	public void actualizarModeloCarga(Localidad loc) {
	    this.modelo.addElement(loc);
	}

	private void limpiarCampo() {
	    nombre.setText("");
	    provincia.setText("");
	    latitud.setText("");
	    longitud.setText("");
	}
	
    public List<Localidad> getLocalidades() {

        List<Localidad> ListaLocalidades = new ArrayList<>();

        for (int i = 0; i < modelo.size(); i++) {
        	ListaLocalidades.add(modelo.getElementAt(i));
        }
         return ListaLocalidades;
    }
    
    
    
    //DISEÑOS
    
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
    
    public void estilizarScroll(JScrollPane scroll) {
        lista.setBackground(new Color(25, 25, 25)); 
        lista.setForeground(Color.WHITE);          
        lista.setSelectionBackground(new Color(83, 141, 78));
        lista.setSelectionForeground(Color.WHITE);
        lista.setBorder(BorderFactory.createEmptyBorder());

        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        scroll.getViewport().setBackground(new Color(25, 25, 25));
        
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(200, 200));
    }
    
    public void estilizarJLabel(JLabel label) {
    	label.setForeground(Color.WHITE);
    	label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}