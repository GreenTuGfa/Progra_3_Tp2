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

        planificador = new Planificador(new CalculadorCostos());
        
        setTitle("Planificador de Fibra Óptica");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        panelLocalidades = new PanelLocalidades(planificador);
        panelLocalidades.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelLocalidades.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        panelParametros = new PanelParametros();
        panelParametros.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelResultado = new PanelResultado();
        panelMapa = new PanelMapa();
        
        JPanel panelIzquierdo = new JPanel();
        panelLocalidades.estilizarContenedor(panelIzquierdo, "");
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(300, 700));

        panelIzquierdo.add(panelParametros);
        panelIzquierdo.add(Box.createVerticalStrut(40));
        panelIzquierdo.add(panelLocalidades);
        panelIzquierdo.add(Box.createVerticalGlue());
        
        JButton btnPlanificar = new JButton("Planificar");
        
        btnPlanificar.setMaximumSize(new Dimension(300, 40));
        btnPlanificar.setPreferredSize(new Dimension(200, 40));
        btnPlanificar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnPlanificar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        btnPlanificar.setBackground(new Color(83, 141, 78));
        btnPlanificar.setForeground(Color.WHITE);
        btnPlanificar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPlanificar.setFocusPainted(false);
        btnPlanificar.setBorder(BorderFactory.createLineBorder(new Color(60, 100, 55), 2));
        btnPlanificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlanificar.setContentAreaFilled(true); //saca la forma feita de windows
        btnPlanificar.addActionListener(e -> ejecutarPlanificacion());
        
        
        panelIzquierdo.add(btnPlanificar);
        
        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                panelMapa,
                panelResultado
        );

        split.setResizeWeight(0.5);
        split.setDividerLocation(0.4);
        split.setOneTouchExpandable(true);
        split.setDividerSize(5);

        getContentPane().add(panelIzquierdo, BorderLayout.WEST);
        getContentPane().add(split, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ejecutarPlanificacion() {

        try {
            ParametrosCostos param = panelParametros.getParametros();

            List<Conexion> red = planificador.planificar(param);

            panelResultado.mostrar(red, planificador.costoTotal(red));

            PanelMapa.mostrar(panelLocalidades.getLocalidades(), red);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en datos"); //da error cuando no estan completos los datos pedidos
        }
    }
}