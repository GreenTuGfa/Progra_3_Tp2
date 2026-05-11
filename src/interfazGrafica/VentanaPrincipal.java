package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import herramientas.CalculadorCostos;
import modelos.Conexion;
import modelos.Localidad;
import modelos.ParametrosCostos;
import sistema.Planificador;


public class VentanaPrincipal extends JFrame {

    private Planificador planificador;

    private PanelLocalidades panelLocalidades;
    
    private PanelParametros panelParametros;
    
    private PanelResultado panelResultado;
    
    private PanelMapa panelMapa;
    
    public VentanaPrincipal() {
        //Inicializar la lógica y CARGAR los datos antes que la interfaz
        planificador = new Planificador(new CalculadorCostos());
        planificador.cargarDatos("localidades.json");
      
        //Configuración básica de la ventana
        setTitle("Conectando localidades a la velocidad de la luz");
        setSize(1000, 700);
        
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 	//Evita que el frame se cierre sin antes guardar el archivo
        getContentPane().setLayout(new BorderLayout());

        //Inicializar Paneles
        panelLocalidades = new PanelLocalidades(planificador);
        panelParametros = new PanelParametros();
        panelResultado = new PanelResultado();
        panelMapa = new PanelMapa();
        
        planificador.cargarDatos("localidades.json");
        //Recorremos las localidades cargadas del archivo y las metemos en el modelo del panel
        for (Localidad localidad : planificador.localidades()) {
            panelLocalidades.actualizarModeloCarga(localidad);
        }

        panelLocalidades.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelLocalidades.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panelParametros.setAlignmentX(Component.RIGHT_ALIGNMENT);
        															
        JPanel panelIzquierdo = new JPanel();
        estilizarPanel(panelIzquierdo);
        
        JButton btnPlanificar = new JButton("Planificar");
        estilizarBoton(btnPlanificar);
        btnPlanificar.addActionListener(excepcion -> ejecutarPlanificacion());

        panelIzquierdo.add(btnPlanificar);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelMapa, panelResultado);
        estilizarSplit(split);

        getContentPane().add(panelIzquierdo, BorderLayout.WEST);
        getContentPane().add(split, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);

        //Cierra y guarda el archivo
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                planificador.guardarDatos("localidades.json");
                System.exit(0);
            }
        });

        setVisible(true);
    }
    
    private void ejecutarPlanificacion() {
        try {
            ParametrosCostos parametro = panelParametros.getParametros();

            List<Conexion> red = planificador.planificar(parametro);

            panelResultado.mostrar(red, planificador.costoTotal(red));

            PanelMapa.mostrar(panelLocalidades.getLocalidades(), red);

        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this, "Asegurate de que todos los parametros de costo de los numeros sean válidos.", "Error en Parámetros", JOptionPane.ERROR_MESSAGE);
        } catch (Exception excepcion) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error al planificar :(");
        }
    }
    
    //aplicar buen diseño
    public void estilizarPanel(JPanel panel) {
    	panelLocalidades.estilizarContenedor(panel, "");
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 700));

        panel.add(panelParametros);
        panel.add(Box.createVerticalStrut(40));
        panel.add(panelLocalidades);
        panel.add(Box.createVerticalGlue());
    }
    
    public void estilizarBoton(JButton btnPlanificar) {
    	btnPlanificar.setMaximumSize(new Dimension(300, 40));
        btnPlanificar.setPreferredSize(new Dimension(200, 40));
        btnPlanificar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnPlanificar.setBackground(new Color(83, 141, 78));
        btnPlanificar.setForeground(Color.WHITE);
        btnPlanificar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPlanificar.setFocusPainted(false);
        btnPlanificar.setBorder(BorderFactory.createLineBorder(new Color(60, 100, 55), 2));
        btnPlanificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
	public void estilizarSplit(JSplitPane split) {
		split.setResizeWeight(0.5);
        split.setDividerLocation(0.4);
        split.setOneTouchExpandable(true);
        split.setDividerSize(5);
	}
}