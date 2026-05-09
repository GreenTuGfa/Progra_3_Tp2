package interfazGrafica;

import javax.swing.*;
import java.awt.*;
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
        planificador.cargarLocalidadesDesdeArchivo("localidades.txt"); 

        //Configuración básica de la ventana
        setTitle("Planificador de Fibra Óptica");
        setSize(1000, 700);
        
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 	//Evita que el frame se cierre sin antes guardar el archivo
        getContentPane().setLayout(new BorderLayout());

        //Inicializar Paneles
        panelLocalidades = new PanelLocalidades(planificador);
        panelParametros = new PanelParametros();
        panelResultado = new PanelResultado();
        panelMapa = new PanelMapa();

        //Recorremos las localidades cargadas del archivo y las metemos en el modelo del panel
        for (Localidad loc : planificador.localidades()) {
            panelLocalidades.actualizarModeloCarga(loc);
        }

        panelLocalidades.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelLocalidades.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panelParametros.setAlignmentX(Component.RIGHT_ALIGNMENT);
        															
        JPanel panelIzquierdo = new JPanel();
        darFormatoPanel(panelIzquierdo);
        
        JButton btnPlanificar = new JButton("Planificar");
        darFormatoBtnPlanificar(btnPlanificar);
        
        panelIzquierdo.add(btnPlanificar);
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelMapa, panelResultado);
        darFormatoSplit(split);

        getContentPane().add(panelIzquierdo, BorderLayout.WEST);
        getContentPane().add(split, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);

        //Cierra y guarda el archivo
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                planificador.guardarLocalidadesEnArchivo("localidades.txt");
                System.exit(0); 
            }
        });

        setVisible(true);
    }
    
    public void darFormatoPanel(JPanel panel) {
    	panelLocalidades.estilizarContenedor(panel, "");
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 700));

        panel.add(panelParametros);
        panel.add(Box.createVerticalStrut(40));
        panel.add(panelLocalidades);
        panel.add(Box.createVerticalGlue());
    }
    
    public void darFormatoBtnPlanificar(JButton btnPlanificar) {
    	btnPlanificar.setMaximumSize(new Dimension(300, 40));
        btnPlanificar.setPreferredSize(new Dimension(200, 40));
        btnPlanificar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnPlanificar.setBackground(new Color(83, 141, 78));
        btnPlanificar.setForeground(Color.WHITE);
        btnPlanificar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPlanificar.setFocusPainted(false);
        btnPlanificar.setBorder(BorderFactory.createLineBorder(new Color(60, 100, 55), 2));
        btnPlanificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlanificar.addActionListener(e -> ejecutarPlanificacion());
    }
    
	public void darFormatoSplit(JSplitPane split) {
		split.setResizeWeight(0.5);
        split.setDividerLocation(0.4);
        split.setOneTouchExpandable(true);
        split.setDividerSize(5);
	}

    private void ejecutarPlanificacion() {
        try {
            ParametrosCostos param = panelParametros.getParametros();

            List<Conexion> red = planificador.planificar(param);

            panelResultado.mostrar(red, planificador.costoTotal(red));

            PanelMapa.mostrar(panelLocalidades.getLocalidades(), red);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegurate de que todos los parametros de costo de los numeros sean válidos.", "Error en Parámetros", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error al planificar :(");
        }
    }
}