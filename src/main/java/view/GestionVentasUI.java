/*
 * Sistema de Gestión de Ventas
 * GUI optimizada para cualquier resolución de monitor
 */
package view;

import Services.ProductoService;
import com.formdev.flatlaf.FlatLightLaf;
import controllers.ClienteController;
import controllers.ProductsController;
import controllers.VentasController;
import dao.UsuariosDAO;
import dao.UsuariosDAOImpl;
import entities.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class GestionVentasUI extends JFrame {
// Componentes principales
    private DynamicSearchField<Clientes> searchFieldCliente;
    private DynamicSearchField<Productos> searchFieldProducto;
    // Variables para almacenar selecciones actuales
        private Clientes clienteSeleccionado;
        private Productos productoSeleccionado;
        private JTextField txtFecha;
        private JSpinner spnCantidad;
        private JTextField txtPrecio;
        private JTable tablaProductos;
        private DefaultTableModel modeloTabla;
        private JLabel lblSubtotal;
        private JLabel lblImpuesto;
        private JLabel lblTotal;
        private JTextField txtNumeroFactura;
        private final ClienteController clienteController;
        private final ProductsController productsController;
        private final ProductoService productoService;
        private final VentasController ventasController;
        private final UsuariosDAO usuariosDAO;
        private  JComboBox<MetodosPago> cmbFormaPago;
        // Variables de cálculo
        private BigDecimal subtotal = BigDecimal.ZERO;
        private static final BigDecimal IMPUESTO_RATE = new BigDecimal("0.15"); //
        private DecimalFormat df = new DecimalFormat("#0.00");

        public GestionVentasUI() {
            productoService= new ProductoService();
            productsController= new ProductsController(productoService);
            clienteController= new ClienteController();
            ventasController = new VentasController();
            usuariosDAO= new UsuariosDAOImpl();
            configurarLookAndFeel();
            initComponents();
            cargarFechaHoraActual(txtFecha);
            generarNumeroFactura();
        }

        private void configurarLookAndFeel() {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void initComponents() {
            setTitle("Gestión de Ventas - Sistema POS");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            repaint();
            revalidate();

            // Panel principal responsivo
            JPanel mainPanel = new JPanel(new MigLayout("insets 20, fill"));
            mainPanel.setBackground(Color.decode("#f8fafc"));

            // Header
            JPanel headerPanel = createHeaderPanel();

            // Panel de información de venta
            JPanel infoVentaPanel = createInfoVentaPanel();

            // Panel de selección de productos
            JPanel seleccionPanel = createSeleccionProductosPanel();

            // Panel de tabla de productos
            JPanel tablaPanel = createTablaProductosPanel();

            // Panel de totales
            JPanel totalesPanel = createTotalesPanel();

            // Panel de acciones
            JPanel accionesPanel = createAccionesPanel();

            // Layout responsivo
            mainPanel.add(headerPanel, "wrap, growx");
            mainPanel.add(infoVentaPanel, "wrap, growx");
            mainPanel.add(seleccionPanel, "wrap, growx");
            mainPanel.add(tablaPanel, "wrap, grow, push");
            mainPanel.add(totalesPanel, "wrap, growx");
            mainPanel.add(accionesPanel, "growx");

            add(mainPanel);
        }

    private JPanel createSeleccionProductosPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "insets 15, fillx",   // margen interno + llenar ancho
                "[][grow][][grow][][grow]", // columnas: label-campo x3
                "[]15[]"              // dos filas con separación vertical
        ));
        panel.setBackground(Color.WHITE);
        panel.setBorder(createStyledBorder());

        // ====== Campos Fila 1 ======
        // Cliente con búsqueda dinámica
        JLabel lblCliente = createLabel("Cliente:");
        searchFieldCliente = new DynamicSearchField<>(
                "Buscar cliente...",
                this::buscarClientes,
                this::onClienteSelected,
                Clientes::getNombreCompleto,
                this::getClienteIcon
        );

        // Producto con búsqueda dinámica
        JLabel lblProducto = createLabel("Producto:");
        searchFieldProducto = new DynamicSearchField<>(
                "Buscar producto por nombre o código...",
                this::buscarProductos,
                this::onProductoSelected,
                this::getProductoDisplay,
                this::getProductoIcon
        );

        // Forma de pago
        JLabel lblFormaPago = createLabel("Forma de Pago:");
        cmbFormaPago = new JComboBox<>(MetodosPago.values());


        panel.add(lblCliente, "align label");
        panel.add(searchFieldCliente, "growx");
        panel.add(lblProducto, "align label");
        panel.add(searchFieldProducto, "growx");
        panel.add(lblFormaPago, "align label");
        panel.add(cmbFormaPago, "growx, wrap");

        // ====== Campos Fila 2 ======
        // Cantidad
        JLabel lblCantidad = createLabel("Cantidad:");
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        spnCantidad.setPreferredSize(new Dimension(80, 35));

        // Precio (auto)
        JLabel lblPrecio = createLabel("Precio:");
        txtPrecio = new JTextField("0.00");
        txtPrecio.setPreferredSize(new Dimension(100, 35));
        txtPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
        txtPrecio.setEditable(false);
        txtPrecio.setBackground(Color.decode("#f7fafc"));

        // Descuento
        JLabel lblDescuento = createLabel("Descuento:");
        JTextField txtDescuento = new JTextField("0.00");
        txtDescuento.setPreferredSize(new Dimension(100, 35));
        txtDescuento.setHorizontalAlignment(SwingConstants.RIGHT);

        // Botón agregar
        JButton btnAgregar = createStyledButton("AGREGAR", Color.decode("#38a169"));
        btnAgregar.addActionListener(this::agregarProducto);

        panel.add(lblCantidad, "align label");
        panel.add(spnCantidad, "width 80!");
        panel.add(lblPrecio, "align label");
        panel.add(txtPrecio, "width 100!");
        panel.add(lblDescuento, "align label");
        panel.add(txtDescuento, "width 100!");
        panel.add(btnAgregar, "span, split, right, width 140!");

        return panel;
    }

        private JPanel createHeaderPanel() {
            JPanel panel = new JPanel(new MigLayout("insets 20"));
            panel.setBackground(Color.WHITE);
            panel.setBorder(createStyledBorder());

            JLabel titulo = new JLabel("Nueva Venta");
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
            titulo.setForeground(Color.decode("#1a202c"));

            JLabel subtitulo = new JLabel("Sistema de Punto de Venta");
            subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            subtitulo.setForeground(Color.decode("#718096"));

            panel.add(titulo, "wrap");
            panel.add(subtitulo);

            return panel;
        }

    // Métodos de búsqueda
    private List<Clientes> buscarClientes(String criterio) {
        try {
            if (criterio.length() < 2) {
                return clienteController.obtenerClientes()
                        .stream()
                        .limit(10)
                        .collect(Collectors.toList());
            }
            return clienteController.buscarClientesNombreCedula(criterio);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private List<Productos> buscarProductos(String criterio) {
        try {
            if (criterio.length() < 2) {
                return productsController.obtenerProductos()
                        .stream()
                        .limit(15)
                        .collect(Collectors.toList());
            }
            return productsController.buscarProductosPorNombre(criterio);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Callbacks de selección
    private void onClienteSelected(Clientes cliente) {
        this.clienteSeleccionado = cliente;
        System.out.println("Cliente seleccionado: " + cliente.getNombreCompleto());
    }

    private void onProductoSelected(Productos producto) {
        this.productoSeleccionado = producto;
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        System.out.println("Producto seleccionado: " + producto.getNombreProducto());
    }

    // Funciones de display
    private String getProductoDisplay(Productos producto) {
        return String.format("%s - %s ($%.2f)",
                producto.getCode(),
                producto.getNombreProducto(),
                producto.getPrecio());
    }

    // Funciones para iconos (opcionales)
    private ImageIcon getClienteIcon(Clientes cliente) {
        return createIconForType("cliente");
    }

    private ImageIcon getProductoIcon(Productos producto) {
        return createIconForType("producto");
    }

    private ImageIcon createIconForType(String type) {
        BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = "cliente".equals(type) ? Color.decode("#4299e1") : Color.decode("#48bb78");

        GradientPaint gradient = new GradientPaint(0, 0, color, 40, 40, color.darker());
        g2d.setPaint(gradient);
        g2d.fillRoundRect(2, 2, 36, 36, 8, 8);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
        String text = "cliente".equals(type) ? "C" : "P";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (40 - fm.stringWidth(text)) / 2;
        int y = (40 + fm.getAscent()) / 2;
        g2d.drawString(text, x, y);

        g2d.dispose();
        return new ImageIcon(img);
    }

    // Modificar el método agregarProducto
    private void agregarProducto(ActionEvent e) {
        // Validaciones
        if (clienteSeleccionado == null) {
            showError("Debe seleccionar un cliente");
            return;
        }

        if (productoSeleccionado == null) {
            showError("Debe seleccionar un producto");
            return;
        }

        try {
            Long idProducto=productoSeleccionado.getId();
            String producto = productoSeleccionado.getNombreProducto();
            int stock= productoSeleccionado.getStock();
            int cantidad = (Integer) spnCantidad.getValue();
            BigDecimal precio = productoSeleccionado.getPrecio();
            BigDecimal total = precio.multiply(BigDecimal.valueOf(cantidad));

            // Validación a través del Controller (sin GUI)
            productsController.agregarProductoAVenta(idProducto, cantidad, stock,productoSeleccionado.getStockMinimo());
            // Agregar a la tabla
            int numeroItem = modeloTabla.getRowCount() + 1;
            modeloTabla.addRow(new Object[]{
                    idProducto,
                    numeroItem,
                    producto + " (" + productoSeleccionado.getCode() + ")",
                    cantidad,
                    precio,
                    total
            });

            // CORRECCIÓN: Actualizar subtotal correctamente
            // BigDecimal es inmutable, hay que asignar el resultado
            subtotal = subtotal.add(total);
            actualizarTotales();

            // Limpiar selección
            searchFieldProducto.clearSearch();
            productoSeleccionado = null;
            spnCantidad.setValue(1);
            txtPrecio.setText("0.00");

           // Suponiendo que la columna 4 = precio y columna 5 = total
            tablaProductos.getColumnModel().getColumn(4).setCellRenderer(currencyRenderer);
            tablaProductos.getColumnModel().getColumn(5).setCellRenderer(currencyRenderer);

        } catch (Exception ex) {
            showError("Error al agregar producto: " + ex.getMessage());
            ex.printStackTrace(); // Para debugging
        }
    }

    DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
        @Override
        protected void setValue(Object value) {
            if (value instanceof BigDecimal) {
                setText("$" + df.format(value)); // df es tu DecimalFormat
            } else {
                super.setValue(value);
            }
        }
    };

    // Modificar limpiarFormulario
    private void limpiarFormulario() {
        searchFieldCliente.clearSearch();
        searchFieldProducto.clearSearch();
        clienteSeleccionado = null;
        productoSeleccionado = null;
        spnCantidad.setValue(1);
        txtPrecio.setText("0.00");
        modeloTabla.setRowCount(0);
        subtotal =BigDecimal.ZERO;
        actualizarTotales();
        generarNumeroFactura();
    }

        private JPanel createInfoVentaPanel() {
            JPanel panel = new JPanel(new MigLayout(
                    "insets 15, fillx",
                    "[][grow][][grow][][grow]", // tres pares label-campo
                    "[]10[]"                    // dos filas
            ));
            panel.setBackground(Color.WHITE);
            panel.setBorder(createStyledBorder());

            // ====== Fila 1 ======
            // Número de factura
            JLabel lblFactura = createLabel("No. Factura:");
            txtNumeroFactura = new JTextField();
            txtNumeroFactura.setEditable(false);
            txtNumeroFactura.setFont(new Font("Segoe UI", Font.BOLD, 14));
            txtNumeroFactura.setBackground(Color.decode("#f7fafc"));

            // Fecha actual
            JLabel lblFecha = createLabel("Fecha:");
            txtFecha = new JTextField();
            txtFecha.setEditable(false);
            txtFecha.setBackground(Color.decode("#f7fafc"));

            // Vendedor
            JLabel lblVendedor = createLabel("Vendedor:");
            JTextField txtVendedor = new JTextField("Admin"); // Ejemplo
            txtVendedor.setEditable(false);
            txtVendedor.setBackground(Color.decode("#f7fafc"));

            panel.add(lblFactura, "align label");
            panel.add(txtNumeroFactura, "growx, width 200!");
            panel.add(lblFecha, "align label");
            panel.add(txtFecha, "growx, width 200!");
            panel.add(lblVendedor, "align label");
            panel.add(txtVendedor, "growx, width 200!, wrap");

            // ====== Fila 2 ======
            // Caja
            JLabel lblCaja = createLabel("Caja:");
            JTextField txtCaja = new JTextField("Principal"); // Ejemplo
            txtCaja.setEditable(false);
            txtCaja.setBackground(Color.decode("#f7fafc"));

            // Estado
            JLabel lblEstado = createLabel("Estado:");
            JComboBox<String> cmbEstado = new JComboBox<>(new String[]{
                    "Pendiente", "Pagada", "Anulada"
            });

            // Total ventas del día (resaltado en verde)
            JLabel lblTotalDia = new JLabel("Total del Día: $0.00");
            lblTotalDia.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblTotalDia.setForeground(new Color(56, 161, 105)); // Verde (#38a169)

            panel.add(lblCaja, "align label");
            panel.add(txtCaja, "growx, width 200!");
            panel.add(lblEstado, "align label");
            panel.add(cmbEstado, "growx, width 200!");
            panel.add(lblTotalDia, "span, right"); // ocupa las columnas sobrantes

            return panel;
        }


        private JPanel createTablaProductosPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setBorder(createStyledBorder());

            // Título de la sección
            JLabel titulo = new JLabel("Productos Agregados");
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titulo.setForeground(Color.decode("#2d3748"));
            titulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            // Crear tabla
            String[] columnas = {"ID","#", "Producto", "Cantidad", "Precio Unit.", "Total"};
            modeloTabla = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tablaProductos = new JTable(modeloTabla);
            configurarTabla();

            JScrollPane scrollPane = new JScrollPane(tablaProductos);
            scrollPane.setBorder(null);
            scrollPane.getViewport().setBackground(Color.WHITE);

            panel.add(titulo, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            return panel;
        }

        private JPanel createTotalesPanel() {
            JPanel panel = new JPanel(new MigLayout("insets 15"));
            panel.setBackground(Color.WHITE);
            panel.setBorder(createStyledBorder());

            // Labels de totales con estilo
            Font fontTotales = new Font("Segoe UI", Font.BOLD, 16);

            JLabel lblSubtotalText = new JLabel("Subtotal:");
            lblSubtotalText.setFont(fontTotales);
            lblSubtotal = new JLabel("$0.00");
            lblSubtotal.setFont(fontTotales);
            lblSubtotal.setForeground(Color.decode("#2d3748"));

            JLabel lblImpuestoText = new JLabel("IVA (15%):");
            lblImpuestoText.setFont(fontTotales);
            lblImpuesto = new JLabel("$0.00");
            lblImpuesto.setFont(fontTotales);
            lblImpuesto.setForeground(Color.decode("#2d3748"));

            JLabel lblTotalText = new JLabel("TOTAL:");
            lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblTotalText.setForeground(Color.decode("#2b6cb0"));
            lblTotal = new JLabel("$0.00");
            lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblTotal.setForeground(Color.decode("#2b6cb0"));

            // Layout con separación visual
            panel.add(lblSubtotalText, "gap push");
            panel.add(lblSubtotal, "wrap");
            panel.add(lblImpuestoText, "gap push");
            panel.add(lblImpuesto, "wrap");
            panel.add(new JSeparator(), "span 2, growx, wrap, gap 10");
            panel.add(lblTotalText, "gap push");
            panel.add(lblTotal);

            return panel;
        }

        private JPanel createAccionesPanel() {
            JPanel panel = new JPanel(new MigLayout("insets 15"));
            panel.setBackground(Color.decode("#f8fafc"));

            JButton btnNueva = createStyledButton("NUEVA VENTA", Color.decode("#4a5568"));
            JButton btnGuardar = createStyledButton("GUARDAR VENTA", Color.decode("#3182ce"));
            JButton btnImprimir = createStyledButton("IMPRIMIR", Color.decode("#38a169"));
            JButton btnCancelar = createStyledButton("CANCELAR", Color.decode("#e53e3e"));

            // Eventos
            btnNueva.addActionListener(e -> limpiarFormulario());
            btnGuardar.addActionListener(e -> guardarVenta());
            btnImprimir.addActionListener(e -> imprimirVenta());
            btnCancelar.addActionListener(e -> System.exit(0));

            panel.add(btnNueva, "width 150!");
            panel.add(btnGuardar, "width 150!, gap 10");
            panel.add(btnImprimir, "width 150!, gap 10");
            panel.add(btnCancelar, "width 150!, gap 10");

            return panel;
        }

        private void configurarTabla() {
            tablaProductos.setRowHeight(40);
            tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tablaProductos.setShowVerticalLines(false);
            tablaProductos.setGridColor(Color.decode("#e2e8f0"));
            tablaProductos.setSelectionBackground(Color.decode("#ebf8ff"));

            // Header personalizado
            tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            tablaProductos.getTableHeader().setBackground(Color.decode("#f8fafc"));
            tablaProductos.getTableHeader().setForeground(Color.decode("#4a5568"));
            tablaProductos.getTableHeader().setPreferredSize(new Dimension(0, 40));

            // Ajustar anchos de columnas
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);  // #
            tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(300); // Producto
            tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(100); // Cantidad
            tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(120); // Precio Unit.
            tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(120); // Total

            // Renderer para columnas numéricas
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            tablaProductos.getColumn("Cantidad").setCellRenderer(rightRenderer);
            tablaProductos.getColumn("Precio Unit.").setCellRenderer(rightRenderer);
            tablaProductos.getColumn("Total").setCellRenderer(rightRenderer);
        }

        private void actualizarTotales() {
            BigDecimal impuesto = subtotal.multiply(IMPUESTO_RATE);
            BigDecimal total = subtotal.add(impuesto);
            lblSubtotal.setText("$" + df.format(subtotal));
            lblImpuesto.setText("$" + df.format(impuesto));
            lblTotal.setText("$" + df.format(total));
            // Debug: imprimir valores para verificar
            System.out.println("Subtotal: " + subtotal);
            System.out.println("Impuesto: " + impuesto);
            System.out.println("Total: " + total);
        }

    private BigDecimal procesarTotal(String textoTotal) {
        try {
            // Remover símbolos de moneda y espacios
            String totalLimpio = textoTotal.trim()
                    .replace("$", "")
                    .replace("€", "")
                    .replace(",", ".") // Convertir comas decimales a puntos
                    .replaceAll("[^\\d.-]", ""); // Remover cualquier carácter que no sea dígito, punto o guión

            // Verificar que no esté vacío después de la limpieza
            if (totalLimpio.isEmpty()) {
                throw new NumberFormatException("Total vacío después de limpieza");
            }

            return new BigDecimal(totalLimpio);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de total inválido: " + textoTotal, e);
        }
    }


        private void guardarVenta() {
            if (modeloTabla.getRowCount() == 0) {
                showError("Debe agregar al menos un producto");
                return;
            }

            // Validaciones para cada fila de tablaResumen
            for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                try {
                    Long.valueOf(tablaProductos.getValueAt(i, 0).toString()); // ID del producto
                    Integer.valueOf(tablaProductos.getValueAt(i, 3).toString()); // Cantidad
                    BigDecimal precio = new BigDecimal(tablaProductos.getValueAt(i, 4).toString());

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: Datos incorrectos en la fila " + (i + 1) + " de la tabla."+e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }

            //Agregar los detalles a la venta
            List<DetalleVentas> detalles = new ArrayList<>();

            // Lógica de la GUI para actualizar la tabla
            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            Ventas venta = new Ventas();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Long id = Long.valueOf(modelo.getValueAt(i, 0).toString());
                BigDecimal precio = new BigDecimal(tablaProductos.getValueAt(i, 4).toString());
                int cantidad = (int) modelo.getValueAt(i, 3);
                Productos producto1 = productsController.buscarProductoId(id);


                DetalleVentas detalle = new DetalleVentas();
                detalle.setProductos(producto1);
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalle.calcularSubtotal();
                detalle.setSubtotal(detalle.getSubtotal());
                detalle.setVenta(venta);
                detalles.add(detalle);
            }

            try {
                BigDecimal total = procesarTotal(lblTotal.getText());
                venta.setTotal(total);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                return;
            }

            String nombreCliente= searchFieldCliente.getText().trim();
            Clientes cliente = buscarClientes(nombreCliente).stream().
                    filter(c -> c.getNombreCompleto().equalsIgnoreCase(nombreCliente))
                            .findFirst()
                                    .orElseGet(()->new Clientes("Cliente","Not found"));
            if (clienteSeleccionado != null) {
                Long idCliente = clienteSeleccionado.getId();
                System.out.println("ID del cliente: " + idCliente);
            } else {
                System.out.println("Cliente no encontrado");
            }

            venta.setCliente(cliente);
            Usuarios u1= usuariosDAO.finById(3L);
            venta.setMetodosPago((MetodosPago)cmbFormaPago.getSelectedItem());
            venta.setVendedor(u1);
            venta.setDetalles(detalles);

            // Crear el formatter con idioma español
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy, HH:mm:ss", new Locale("es"));
            LocalDateTime fechaConvertida = LocalDateTime.parse(txtFecha.getText().trim(),formatter);
            venta.setFechaHora(fechaConvertida);

            try {
                ventasController.generarVenta(venta);
            } catch (Exception ex) {
                Logger.getLogger(ModuloVentasXD.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                JOptionPane.showMessageDialog(null, "Venta Facturada ");
                JOptionPane.showMessageDialog(null, "Factura Nº:" + ventasController.obtenerUltimaFactura());

                //generarFacturaPdf(String.valueOf(ventasController.obtenerUltimaFactura()));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al generar la factura o realizar la venta.");

                ex.printStackTrace();
                return;
            }
            //Obtenemos la ultima factura y la mostramos en una caja de texto
            txtNumeroFactura.setText(String.valueOf(ventasController.obtenerUltimaFactura()));
        //GEN-LAST:event_btnGenerarVentaActionPerformed
            showSuccess("Venta guardada exitosamente");
            limpiarFormulario();
        }

        private void imprimirVenta() {
            if (modeloTabla.getRowCount() == 0) {
                showError("No hay productos para imprimir");
                return;
            }

            showInfo("Enviando a impresora...");
        }

    public static void cargarFechaHoraActual(JTextField FECHA) {
        // Formato para la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd-MMM-yyyy, HH:mm:ss", new Locale("es", "ES"));

        // Timer para actualizar la hora cada segundo
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Obtener la fecha y hora actual
                LocalDateTime ahora = LocalDateTime.now();

                // Formatear la fecha y hora
                String fechaHoraFormateada = ahora.format(formatter);

                // Actualizar el JLabel con la fecha y hora formateada
                FECHA.setText(fechaHoraFormateada);
            }
        }, 0, 1000); // Iniciar timer

    }
        private void generarNumeroFactura() {
            String numero = String.format("%08d", System.currentTimeMillis() % 100000000);
            txtNumeroFactura.setText(numero);
        }

        // Métodos de utilidad
        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(Color.decode("#4a5568"));
            return label;
        }

        private JButton createStyledButton(String text, Color color) {
            JButton button = new JButton(text);
            button.setBackground(color);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(120, 40));

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

        private javax.swing.border.Border createStyledBorder() {
            return BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.decode("#e2e8f0"), 1, true),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            );
        }

        private void showError(String message) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        private void showSuccess(String message) {
            JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

        private void showInfo(String message) {
            JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new GestionVentasUI().setVisible(true);
            });
        }
}