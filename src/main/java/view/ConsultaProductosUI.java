/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import Services.ProductoService;
import Services.VentasService;
import com.formdev.flatlaf.FlatLightLaf;
import controllers.ClienteController;
import controllers.ProductsController;
import controllers.VentasController;
import entities.Estados;
import entities.EstadosProductos;
import entities.Productos;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaProductosUI extends JFrame {

   private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<Estados> cmbEstado;
    private JComboBox<String> cmbCliente;
    private final VentasController ventasController;
    private final ClienteController clienteController;
    private final ProductsController productController;

    public ConsultaProductosUI() {
        // Configurar FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        VentasService ventasService = new VentasService();
        ventasController = new VentasController(ventasService);
        clienteController = new ClienteController();
        ProductoService productService = new ProductoService();
        productController = new ProductsController(productService);
        initComponents();
        setupLayout();
        cargarDatos();
        configurarTabla();
    }
    
    private void initComponents() {
        setTitle("Consulta de Productos");
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
        
        JLabel titulo = new JLabel("Consulta de Productos");
        JLabel INFO= new JLabel("BY DENNISE G <3");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.decode("#1a202c"));
        INFO.setFont(new Font("Segoe UI", Font.BOLD, 24));
        INFO.setForeground(Color.decode("#1a202c"));
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
        txtBuscar.putClientProperty("JTextField.placeholderText", "Buscar por código o nombre de producto");
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstado= new JComboBox<>(Estados.values());
        
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
        String[] columnas = {"Nombre", "Marca", "Código", "P.Uni","Stock","Stock Min.","Descripción","Estado"};
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

    private JLabel snackbar;

    private JPanel createAccionesPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 15", "[grow]", "[]10[]"));
        panel.setBackground(Color.decode("#f8fafc"));
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true));

        JButton btnNuevo = createActionButton("NUEVO", Color.decode("#38a169"));
        JButton btnEditar = createActionButton("EDITAR", Color.decode("#3182ce"));
        JButton btnEliminar = createActionButton("ELIMINAR", Color.decode("#e53e3e"));
        JButton btnActualizar = createActionButton("ACTUALIZAR", Color.decode("#4a5568"));
        btnActualizar.addActionListener(p -> {
            cargarDatos();
            mostrarSnackbar("Productos actualizados correctamente ✅");
        });

        snackbar = new JLabel("", SwingConstants.CENTER);
        snackbar.setOpaque(true);
        snackbar.setBackground(new Color(56, 161, 105)); // verde
        snackbar.setForeground(Color.WHITE);
        snackbar.setVisible(false);

        panel.add(btnNuevo, "split 4, center");
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnActualizar, "wrap");

        panel.add(snackbar, "growx, span");
        return panel;
    }

    private void mostrarSnackbar(String mensaje) {
        snackbar.setText(mensaje);
        snackbar.setVisible(true);

        // Ocultar después de 3 segundos
        new Timer(3000, e -> snackbar.setVisible(false)).start();
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
        // Renderer personalizado centrar  columnas
        tablaVentas.getColumn("Estado").setCellRenderer(new EstadoCellRenderer());
        tablaVentas.getColumn("Marca").setCellRenderer(new EstadoCellRenderer());
        tablaVentas.getColumn("Código").setCellRenderer(new EstadoCellRenderer());
        tablaVentas.getColumn("P.Uni").setCellRenderer(new EstadoCellRenderer());
        tablaVentas.getColumn("Stock").setCellRenderer(new EstadoCellRenderer());
        tablaVentas.getColumn("Stock Min.").setCellRenderer(new EstadoCellRenderer());

        // Ajustar anchos de columnas
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(40);
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(40);
        tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(40);
        tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(40);
        tablaVentas.getColumnModel().getColumn(5).setPreferredWidth(300);
        tablaVentas.getColumnModel().getColumn(6).setPreferredWidth(120);
    }
    private void cargarDatos() {
        // Limpiar filas anteriores
        modeloTabla.setRowCount(0);
        List<Productos> listaProductos = productController.obtenerProductos();
        for (Productos product : listaProductos) {
            String nombre = product.getNombreProducto();
            String marca = product.getMarca() ;
            String codigo= product.getCode();
            String precio= "$"+product.getPrecio().toString();
            String estado= product.getEstado().getDescripcion().toUpperCase();
            int stock = product.getStock();
            int stockMIn=product.getStockMinimo();
            String descripcion= product.getDescripcion();
            modeloTabla.addRow(new Object[]{ nombre, marca,codigo, precio,stock,stockMIn,descripcion,estado});
            tablaVentas.repaint();
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

            String estado = (value != null) ? value.toString().toUpperCase() : "";
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            
            switch (estado) {
                case "DISPONIBLE":
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

    private EstadosProductos filtroCmb() {
        EstadosProductos estadoSeleccionado = (EstadosProductos) cmbEstado.getSelectedItem();
        if (estadoSeleccionado == null) return null; // seguridad
       /* switch (estadoSeleccionado) {
            case FACTURADA, PENDIENTE, CANCELADA -> productController..listarVentasPorEstado(estadoSeleccionado);

        }*/

        return estadoSeleccionado;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultaProductosUI().setVisible(true);
        });
    }
}