/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import Services.VentasService;
import com.formdev.flatlaf.FlatLightLaf;
import controllers.ClienteController;
import controllers.VentasController;
import entities.Clientes;
import entities.*;
import entities.Ventas;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaVentasUI extends JFrame {
   
   private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<EstadosVentas> cmbEstado;
    private JComboBox<String> cmbCliente;
    private final VentasController ventasController;
    private final ClienteController clienteController;

    public ConsultaVentasUI() {
        // Configurar FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        VentasService ventasService = new VentasService();
        ventasController = new VentasController(ventasService);
        clienteController = new ClienteController();
        initComponents();
        setupLayout();
        cargarDatos();
        configurarTabla();
    }
    
    private void initComponents() {
        setTitle("Consulta de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Panel principal con padding
        JPanel mainPanel = new JPanel(new MigLayout("insets 20, gap 15"));
        mainPanel.setBackground(Color.decode("#f8fafc"));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Panel de filtros
        JPanel filtrosPanel = createFiltrosPanel();
        
        
        
        // Tabla
        JPanel tablaPanel = createTablaPanel();
        
        // Panel de acciones
        JPanel accionesPanel = createAccionesPanel();
        
        // Agregar componentes
        mainPanel.add(headerPanel, "wrap, growx");
        mainPanel.add(filtrosPanel, "wrap, growx");
      
        mainPanel.add(tablaPanel, "wrap, grow, push");
        mainPanel.add(accionesPanel, "growx");
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20"));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titulo = new JLabel("Consulta de Ventas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.decode("#1a202c"));
        
        panel.add(titulo, "wrap");
        return panel;
    }
    
    private JPanel createFiltrosPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 15"));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Búsqueda
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscar = new JTextField(20);
        txtBuscar.putClientProperty("JTextField.placeholderText", "Buscar por factura o cliente...");
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstado= new JComboBox<>(EstadosVentas.values());
        
        // Cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbCliente = new JComboBox<>(new String[]{"TODOS", "joss Saenz", "Sin Josue Sanchez Mosquera", "Maria De la Vega"});
        
        // Botón filtrar
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(Color.decode("#3182ce"));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        
        // Layout
        panel.add(lblBuscar, "");
        panel.add(txtBuscar, "width 250!, gap 10");
        panel.add(lblEstado, "gap 20");
        panel.add(cmbEstado, "width 120!, gap 10");
        panel.add(lblCliente, "gap 20");
        panel.add(cmbCliente, "width 200!, gap 10");
        panel.add(btnFiltrar, "gap 20");
        
        return panel;
    }
    
   
    
    private void createStatLabel(String value, String label) {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.setBackground(Color.decode("#667eea"));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblValue.setForeground(Color.WHITE);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblLabel.setForeground(Color.decode("#e2e8f0"));
        lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(lblValue, "wrap, center");
        panel.add(lblLabel, "center");
        
    }
    
    private JPanel createTablaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true));
        
        // Crear modelo de tabla
        String[] columnas = {"Factura", "Fecha de Venta", "Monto", "Cliente", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.setRowHeight(40);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVentas.setShowVerticalLines(false);
        tablaVentas.setGridColor(Color.decode("#f7fafc"));

        // Header personalizado
        tablaVentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaVentas.getTableHeader().setBackground(Color.decode("#f8fafc"));
        tablaVentas.getTableHeader().setForeground(Color.decode("#4a5568"));
        tablaVentas.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        scrollPane.setBorder(null);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAccionesPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 15"));
        panel.setBackground(Color.decode("#f8fafc"));
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true));
        
        JButton btnNuevo = createActionButton("NUEVO", Color.decode("#38a169"));
        btnNuevo.addActionListener(nv->new GestionVentasUI().setVisible(true));
        JButton btnEditar = createActionButton("EDITAR", Color.decode("#3182ce"));
        JButton btnEliminar = createActionButton("ELIMINAR", Color.decode("#e53e3e"));
        JButton btnActualizar = createActionButton("ACTUALIZAR", Color.decode("#4a5568"));
        btnActualizar.addActionListener(e->cargarDatos());
        
        panel.add(btnNuevo, "gap push");
        panel.add(btnEditar, "gap 10");
        panel.add(btnEliminar, "gap 10");
        panel.add(btnActualizar, "gap 10");
        
        return panel;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void configurarTabla() {
        // Renderer personalizado para la columna de estado
        tablaVentas.getColumn("Estado").setCellRenderer(new EstadoCellRenderer());
        
        // Ajustar anchos de columnas
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(100); // Factura
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(150); // Fecha
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(100); // Monto
        tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(250); // Cliente
        tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(120); // Estado
    }

    private void cargarDatos() {

        List<Ventas> listaVentas = ventasController.obtenerTodasLasVentas().getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Ventas fila : listaVentas) {

            String numeroFactura = fila.getNumFactura();
            String precio = "$".concat(String.valueOf(fila.getTotal()));
            String fecha = fila.getFechaHora().format(formatter);

            // Obtener solo el nombre del cliente
            long clienteId = fila.getCliente().getId();
            Clientes cliente = clienteController.obtenerClientePorId(clienteId);
            String nombreCompletoCliente = (cliente != null) ? cliente.getNombre() + " " + cliente.getApellido() : "Desconocido";
            String estado = fila.getEstado().getDescripcion().toUpperCase();
            modeloTabla.addRow(new Object[]{numeroFactura, fecha, precio, nombreCompletoCliente, estado});
        }
    }
    
    private void setupLayout() {
        // Configuración adicional si es necesaria
    }
    
    // Renderer personalizado para estados
    class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String estado = (String) value;
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            
            switch (estado) {
                case "FACTURADA":
                    setBackground(Color.decode("#c6f6d5"));
                    setForeground(Color.decode("#22543d"));
                    break;
                case "PENDIENTE":
                    setBackground(Color.decode("#fef5e7"));
                    setForeground(Color.decode("#c05621"));
                    break;
                case "CANCELADA":
                    setBackground(Color.decode("#fed7d7"));
                    setForeground(Color.decode("#742a2a"));
                    break;
                default:
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
            }
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            
            return this;
        }
    }

    private EstadosVentas filtroCmb() {
        EstadosVentas estadoSeleccionado = (EstadosVentas) cmbEstado.getSelectedItem();
        if (estadoSeleccionado == null) return null; // seguridad
        switch (estadoSeleccionado) {
            case FACTURADA, PENDIENTE, CANCELADA -> ventasController.listarVentasPorEstado(estadoSeleccionado);

        }

        return estadoSeleccionado;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultaVentasUI().setVisible(true);
        });
    }
}