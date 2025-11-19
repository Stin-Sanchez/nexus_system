package view;
import Services.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import controllers.*;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.*;


public class ModuloVentasXD extends javax.swing.JInternalFrame {
    private final ClienteController clienteController;
    private final ProductsController productoController;
    private final VentasController ventasController;

    // Esquema de colores moderno con gradientes
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);  // Indigo moderno
    private static final Color PRIMARY_HOVER = new Color(79, 70, 229);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);   // Verde esmeralda
    private static final Color WARNING_COLOR = new Color(251, 146, 60);  // Naranja suave
    private static final Color DANGER_COLOR = new Color(239, 68, 68);    // Rojo coral
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    // Fuentes modernas
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 11);

    // Dimensiones
    private static final Dimension LABEL_LENGHT = new Dimension(100, 30);
    private static final Dimension TEXT_SIZE = new Dimension(200, 35);
    private static final int CORNER_RADIUS = 12;


    public ModuloVentasXD() {

        initComponents();
        cargarFechaHoraActual(lblFecha);

        //Inicializar la capa de controladores
        clienteController = new ClienteController();
        ProductoService productoService = new ProductoService();
        productoController = new ProductsController(productoService);
        ventasController = new VentasController();


        //Se cargan todos los productos y clientes al iniciar el sistema
        cargarProductos();
        cargarClientes();

        //Obtenemos el ultimo numero de factura
        //vdao.obtenerUltimoNumeroFactura();
        txtNumeroSerie.setText(String.valueOf(ventasController.obtenerUltimaFactura()));
        btnAgregar.setBackground(SUCCESS_COLOR);
                btnEliminar.setBackground(DANGER_COLOR);
                btnGenerarVenta.setBackground(SUCCESS_COLOR);
                btnDeshabilitar.setBackground(WARNING_COLOR);
                btnFiltro.setBackground(WARNING_COLOR);
                btnFiltrarClientes.setBackground(WARNING_COLOR);
                btnHabilitar.setBackground(PRIMARY_COLOR);
                labelTotal.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
                labelTotal.setForeground(PRIMARY_COLOR);
                
                //Le damos tamaño a los labels y cajas de texto
                lblClienteId.setPreferredSize(LABEL_LENGHT);
                        lblNombreCliente.setPreferredSize(LABEL_LENGHT);
                        lblApellido.setPreferredSize(LABEL_LENGHT);
                        lblCedula.setPreferredSize(LABEL_LENGHT);
                        lblCelular.setPreferredSize(LABEL_LENGHT);
                        lblCorreo.setPreferredSize(LABEL_LENGHT);
                        lblDireccion.setPreferredSize(LABEL_LENGHT);
                       txtIDCliente.setPreferredSize(TEXT_SIZE);
                       txtNombreCliente.setPreferredSize(TEXT_SIZE);
                       txtApellidoCliente.setPreferredSize(TEXT_SIZE);
                       txtCI.setPreferredSize(TEXT_SIZE);
                       txtCorreo.setPreferredSize(TEXT_SIZE);
                       txtCelular.setPreferredSize(TEXT_SIZE);
                       txtDireccion.setPreferredSize(TEXT_SIZE);
                       panelDatosCliente.setMaximumSize(new Dimension(290,250));
                       panelDatosCliente.setPreferredSize(new Dimension(290,240));

    }
    private void cargarProductos() {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Descripción");

        List<Productos> listaProductos = productoController.obtenerProductos();

        for (Productos producto : listaProductos) {
            modeloTabla.addRow(new Object[]{
                producto.getId(),
                producto.getNombreProducto(),
                producto.getCode(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getDescripcion()
            });
        }

        tablaProductos.setModel(modeloTabla);
    }

    private void cargarClientes() {

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("COD.Cliente");
        modeloTabla.addColumn("Nombres");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("CI-DNI");
        modeloTabla.addColumn("Celular");
        modeloTabla.addColumn("Correo");

        List<Clientes> listaClientes = clienteController.obtenerActivos();

        for (Clientes cliente : listaClientes) {
            modeloTabla.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getCedula(),
                cliente.getTelefono(),
                cliente.getEmail()

            });
        }

        tablaClientes.setModel(modeloTabla);

    }

    public static void cargarFechaHoraActual(JLabel labelFechaHora) {
        // Formato para la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'del' yyyy, HH:mm:ss", new Locale("es", "ES"));

        // Timer para actualizar la hora cada segundo
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Obtener la fecha y hora actual
                LocalDateTime ahora = LocalDateTime.now();

                // Formatear la fecha y hora
                String fechaHoraFormateada = ahora.format(formatter);

                // Actualizar el JLabel con la fecha y hora formateada
                labelFechaHora.setText(fechaHoraFormateada);
            }
        }, 0, 1000); // Iniciar timer

    }

    private void actualizarTablaProductos() {
        String nombre = txtNombreP.getText();
        List<Productos> productos = productoController.buscarProductosPorNombre(nombre);
        // Suponiendo que tienes una instancia de JTable llamada 'miTabla'
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0); //

        for (Productos producto : productos) {
            modelo.addRow(new Object[]{
                producto.getId(),
                producto.getNombreProducto(),
                producto.getCode(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getDescripcion()
            });
        }

    }

    private void actualizarTablaClientes() {

        String criterio = txtBuscarClientes.getText();
        List<Clientes> clientes = clienteController.buscarClientesNombreCedula(criterio);
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0);

        for (Clientes cliente : clientes) {
            modelo.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getCedula(),
                cliente.getTelefono(),
                cliente.getEmail()
            });
        }

        for (int columnas = 0; columnas < tablaProductos.getColumnCount(); columnas++) {
            Class<?> colunClass = tablaClientes.getColumnClass(columnas);
            tablaProductos.setDefaultEditor(colunClass, null);
        }
    }

    private void selectClienteByVenta() {

        int fila = tablaClientes.getSelectedRow();

        try {

            if (fila >= 0) {
                Long id = Long.valueOf(tablaClientes.getValueAt(fila, 0).toString());
                txtIDCliente.setText(String.valueOf(id));
                txtNombreCliente.setText(tablaClientes.getValueAt(fila, 1).toString());
                txtApellidoCliente.setText(tablaClientes.getValueAt(fila, 2).toString());
                txtCI.setText(tablaClientes.getValueAt(fila, 3).toString());
                txtCelular.setText(tablaClientes.getValueAt(fila, 4).toString());
                txtCorreo.setText(tablaClientes.getValueAt(fila, 5).toString());
                //Obtener las direcciones de los clientes mediante su id

                String direccionClientes = clienteController.obtenerDireccionCliente(id);
                txtDireccion.setText(direccionClientes);

            }

        } catch (NumberFormatException e) {
            System.out.println("Error al seleccionar el cliente de la tabla"+e.getMessage());
        }
    }

    private void selectProductByVenta() {

        int fila = tablaProductos.getSelectedRow();

        try {

            if (fila >= 0) {
                Long id = Long.valueOf(tablaProductos.getValueAt(fila, 0).toString());
                txtIDProducto.setText(String.valueOf(id));
                txtNombreProductos.setText(tablaProductos.getValueAt(fila, 1).toString());
                txtCodigo.setText(tablaProductos.getValueAt(fila, 2).toString());
                txtPrecioProducto.setText(tablaProductos.getValueAt(fila, 3).toString());
                txtStock.setText(tablaProductos.getValueAt(fila, 4).toString());
                txtDescripcion.setText(tablaProductos.getValueAt(fila, 5).toString());
                txtPrecioVenta.setText(tablaProductos.getValueAt(fila, 3).toString());

                EstadosProductos estado = productoController.obtenerEstadoProducto(id);
                txtEstado.setText(estado.getDescripcion());

                String rutaImagen = productoController.obtenerImagenProducto(id);

                try {
                    ImageIcon icono = new ImageIcon(rutaImagen);
                    Image imagen = icono.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(imagen));
                } catch (Exception e) {
                    label.setIcon(null);
                    System.out.println("No se pudo cargar la imagen: " + rutaImagen);
                }

            }

        } catch (NumberFormatException e) {
         System.out.println("Error al seleccionar el producto de la tabla"+e.getMessage());

        }
    }

    private void eliminarProductosTabla() {

        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

            int indiceSeleccionado = tablaResumen.getSelectedRow();

            if (indiceSeleccionado != -1) {

                modelo.removeRow(indiceSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione la fila para eliminar el producto");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto\n" + e.getMessage());

        }

    }

    private void agregarProducto() {
        try {

            // Lógica de la GUI para actualizar la tabla
            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
            Long id = Long.valueOf(txtIDProducto.getText());
            
            if(tablaProductos.isCellSelected(0, 0)){
                   JOptionPane.showMessageDialog(null, "Selecciona un producto para agregar a la venta");
            }
            int cantidad = (int) spnCantidad.getValue();
            int stock = Integer.parseInt(txtStock.getText());

            for (int i = 0; i < modelo.getRowCount(); i++) {
                String idExistente = (String) modelo.getValueAt(i, 0);

                if (idExistente.equals(id)) {
                    JOptionPane.showMessageDialog(null, "El producto ya fue agregado");
                    return;
                }
            }

            String nombrePro = txtNombreProductos.getText();
            double precioUnitario = Double.parseDouble(txtPrecioVenta.getText());
            int cantidadPro = Integer.parseInt(spnCantidad.getValue().toString());

            if (cantidadPro <= 0) {
                JOptionPane.showMessageDialog(null, "Seleccione la cantidad del producto");
                return;
            }

            double subTotal = precioUnitario * cantidadPro;

            // Validación a través del Controller (sin GUI)
            productoController.agregarProductoAVenta(id, cantidad, stock,10);

            // Lógica de la GUI para actualizar la tabla
            modelo.addRow(new Object[]{id, nombrePro, precioUnitario, cantidad, subTotal});

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error: Por favor seleccione el producto de la tabla para agregarlo a la venta"+e.getMessage()); // La GUI maneja el error
        }
    }

    /*public void generarFacturaPdf(String numFac) {
        Document document = new Document(PageSize.A4, 50, 50, 115, 30); // Márgenes
        try {
            // Nombre de archivo único con timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nombreArchivo = "Factura_Venta_" + numFac + "_" + timestamp + ".pdf";
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHora = formatoFecha.format(new Date());

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            // Asignar el event handler para encabezado/pie de página
            writer.setPageEvent(new HeaderFooter());
            document.open();

            // ----- ESTILOS -----
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font infoNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

            BaseColor color = new BaseColor(0, 86, 163);

            // ----- ENCABEZADO DE FACTURA -----
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{50, 50});

            PdfPCell cellLeft = new PdfPCell();
            PdfPCell cellRight = new PdfPCell();
            cellLeft.setBorder(Rectangle.NO_BORDER);
            cellRight.setBorder(Rectangle.NO_BORDER);
            cellLeft.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

            Paragraph leftContent = new Paragraph();
            leftContent.add(new Phrase("Factura Electrónica\n", infoNegrita));
            leftContent.add(new Phrase("Nº" + numFac, infoFont));
            cellLeft.addElement(leftContent);

            Paragraph rightContent = new Paragraph();
            rightContent.add(new Phrase("Autorización: ", infoNegrita));
            rightContent.add(new Phrase("123-456-789000\n", infoFont));
            rightContent.add(new Phrase("Fecha: ", infoNegrita));
            rightContent.add(new Phrase(fechaHora, infoFont));
            rightContent.setAlignment(Element.ALIGN_RIGHT);
            cellRight.addElement(rightContent);

            headerTable.addCell(cellLeft);
            headerTable.addCell(cellRight);
            headerTable.setSpacingBefore(5);
            headerTable.setSpacingAfter(5);
            document.add(headerTable);

            // Línea separadora después del encabezado de factura
            PdfContentByte canvas = writer.getDirectContent();
            canvas.setLineWidth(1f);
            canvas.moveTo(document.leftMargin(), writer.getVerticalPosition(false) - 0);
            canvas.lineTo(document.getPageSize().getWidth() - document.rightMargin(), writer.getVerticalPosition(false) - 0);
            canvas.setColorStroke(color);
            canvas.stroke();

            // ----- DATOS CLIENTE -----
            Paragraph cliente = new Paragraph();
            cliente.add(new Phrase("Cliente: ", infoNegrita));
            cliente.add(new Phrase(txtNombreCliente.getText() + " " + txtApellidoCliente.getText(), infoFont));
            cliente.add(new Phrase("\nRUC/CI: ", infoNegrita));
            cliente.add(new Phrase(txtCI.getText(), infoFont));
            cliente.add(new Phrase("\nDireccion: ", infoNegrita));
            cliente.add(new Phrase(txtDireccion.getText(), infoFont));
            cliente.setAlignment(Element.ALIGN_LEFT);
            cliente.setSpacingAfter(10);
            cliente.setSpacingBefore(5);
            document.add(cliente);

            // ----- TABLA PRODUCTOS -----
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10);
            tabla.setSpacingAfter(10);
            tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER); // Eliminar bordes de todas las celdas

            // Configurar celdas de encabezado
            PdfPCell headerCell;
            BaseColor colorCelda = new BaseColor(80, 213, 226);
            headerCell = new PdfPCell(new Phrase("Producto", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setBackgroundColor(colorCelda);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            tabla.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Cantidad", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setBackgroundColor(colorCelda);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            tabla.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Precio Unitario", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setBackgroundColor(colorCelda);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            tabla.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Subtotal", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setBackgroundColor(colorCelda);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            tabla.addCell(headerCell);

            // Agregar filas de productos
            for (int i = 0; i < tablaResumen.getRowCount(); i++) {
                String producto = tablaResumen.getValueAt(i, 1).toString();
                String precio = tablaResumen.getValueAt(i, 2).toString();
                String cantidad = tablaResumen.getValueAt(i, 3).toString();
                String subtotal = tablaResumen.getValueAt(i, 4).toString();

                PdfPCell cell = new PdfPCell(new Phrase(producto, new Font(Font.FontFamily.HELVETICA, 11)));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPadding(5);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase(cantidad, new Font(Font.FontFamily.HELVETICA, 11)));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPadding(5);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase("$" + String.format("%.2f", Double.valueOf(precio)), new Font(Font.FontFamily.HELVETICA, 11)));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPadding(5);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase("$" + String.format("%.2f", Double.valueOf(subtotal)), new Font(Font.FontFamily.HELVETICA, 11)));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPadding(5);
                tabla.addCell(cell);
            }

            document.add(tabla);

            // Línea separadora después de la tabla
            canvas.setLineWidth(1f);
            canvas.moveTo(document.leftMargin(), writer.getVerticalPosition(false) - 5);
            canvas.lineTo(document.getPageSize().getWidth() - document.rightMargin(), writer.getVerticalPosition(false) - 5);
            canvas.setColorStroke(colorCelda);
            canvas.stroke();

            // ----- TOTAL -----
            String totalStr = labelTotal.getText().replace("$", "").replace(",", ".").trim();
            BigDecimal totalBD = new BigDecimal(totalStr);
            Paragraph total = new Paragraph("Total: $" + totalBD.setScale(2, RoundingMode.HALF_UP), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(5);
            total.setSpacingAfter(5);
            document.add(total);

            JOptionPane.showMessageDialog(null, "PDF generado correctamente:\n" + nombreArchivo);

        } catch (DocumentException | HeadlessException | FileNotFoundException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF"+e.getMessage());
          
        } finally {
            document.close();
        }
    }

    public class HeaderFooter extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {

                float headerHeight = 100f;

                PdfPTable header = new PdfPTable(1);
                header.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
                header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                header.getDefaultCell().setPadding(5);

                BaseColor color = new BaseColor(0, 86, 163); // Color azul corporativo

                // Nombre de la empresa con color y tamaño
                header.addCell(new Phrase("IMPORTADORA NEXUS, S&M",
                        new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, color)));

                // Resto de información de la empresa
                header.addCell(new Phrase("RUC: 123456789001",
                        new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                header.addCell(new Phrase("Dirección: Av. Mons. Rogelio Beauger",
                        new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                header.addCell(new Phrase("Tel: 0969561832 | Email: infonexus@gmail.com",
                        new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));

                // Posicionar el encabezado
                header.writeSelectedRows(0, -1, document.leftMargin(),
                        document.getPageSize().getHeight() - document.topMargin() + headerHeight,
                        writer.getDirectContent());

                // Línea separadora debajo del encabezado
                PdfContentByte canvas = writer.getDirectContent();
                canvas.moveTo(document.leftMargin(), document.getPageSize().getHeight() - document.topMargin() - 0);
                canvas.lineTo(document.getPageSize().getWidth() - document.rightMargin(),
                        document.getPageSize().getHeight() - document.topMargin() - 0);
                canvas.setColorStroke(color);
                canvas.stroke();

                // Configuración del pie de página
                float footerHeight = 40; // Altura total que ocupará el pie
                float textBase = document.bottomMargin() - 5; // Posición base ajustada

                // Línea separadora (15px desde abajo)
                canvas.setLineWidth(1.0f);
                canvas.setColorStroke(color);
                canvas.moveTo(
                        document.leftMargin(),
                        textBase + footerHeight - 15
                );
                canvas.lineTo(
                        document.getPageSize().getWidth() - document.rightMargin(),
                        textBase + footerHeight - 15
                );
                canvas.stroke();

                // Mensaje "Gracias por su compra" centrado (10px desde abajo)
                ColumnText.showTextAligned(
                        canvas,
                        Element.ALIGN_CENTER,
                        new Phrase("Gracias por su compra",
                                new Font(Font.FontFamily.HELVETICA, 10)),
                        document.getPageSize().getWidth() / 2, // Posición X en el centro
                        textBase + 10, // 10px sobre el borde inferior
                        0
                );

                // Número de página al lado derecho (10px desde abajo)
                ColumnText.showTextAligned(
                        canvas,
                        Element.ALIGN_RIGHT,
                        new Phrase("Página " + writer.getPageNumber(),
                                new Font(Font.FontFamily.HELVETICA, 8)),
                        document.getPageSize().getWidth() - document.rightMargin(),
                        textBase + 10, // 10px sobre el borde inferior
                        0
                );

            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, "Error al procesar PDF"+e.getMessage());
            }

        }
    }*/

    public void actualizarTotalVenta() {
        try {
        System.out.println("=== INICIANDO CALCULO DE TOTAL ===");
        
        // Verificar que el controlador esté inicializado
        if (ventasController == null) {
            System.err.println("ERROR: ventasController es null");
            labelTotal.setText("ERROR: Controller null");
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        List<Double> subtotales = new ArrayList<>();
        
        System.out.println("Filas en tabla: " + modelo.getRowCount());
        
        // Extraer subtotales de la tabla
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object subtotalObj = modelo.getValueAt(i, 4); // Columna 4 = SUBTOTAL
            System.out.println("Fila " + i + " - Valor crudo: " + subtotalObj);
            
            if (subtotalObj != null) {
                String subtotalStr = subtotalObj.toString().trim();
                System.out.println("Fila " + i + " - Valor string: '" + subtotalStr + "'");
                
                if (!subtotalStr.isEmpty()) {
                    try {
                        Double subtotal = Double.valueOf(subtotalStr);
                        subtotales.add(subtotal);
                        System.out.println("Fila " + i + " - Subtotal agregado: " + subtotal);
                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir subtotal en fila " + (i + 1) + ": " + subtotalStr);
                        subtotales.add(0.0);
                    }
                }
            } else {
                System.out.println("Fila " + i + " - Valor es null");
            }
        }
        
        System.out.println("Lista de subtotales: " + subtotales);
        System.out.println("Total de subtotales encontrados: " + subtotales.size());
        
        // Usar el controlador para calcular el total
        BigDecimal total = ventasController.calcularTotalVenta(subtotales);
        System.out.println("Total calculado por controller: " + total);
        
        // Verificar que el label no sea null
        if (labelTotal == null) {
            System.err.println("ERROR: labelTotal es null");
            return;
        }
        
        // Actualizar la etiqueta con el total formateado
        if (total != null) {
            String totalFormateado = String.format("%.2f", total.doubleValue());
            String textoFinal = "$" + totalFormateado;
            System.out.println("Texto a mostrar en label: '" + textoFinal + "'");
            labelTotal.setText(textoFinal);
            System.out.println("Texto actual del label después de setear: '" + labelTotal.getText() + "'");
        } else {
            System.out.println("Total es null, seteando $0.00");
            labelTotal.setText("$0.00");
        }
        
        // Forzar repaint por si acaso
        labelTotal.repaint();
        
        System.out.println("=== FIN CALCULO DE TOTAL ===");
        
    } catch (Exception e) {
        System.err.println("ERROR GENERAL en actualizarTotalVenta: " + e.getMessage());
        
        if (labelTotal != null) {
            labelTotal.setText("ERROR");
        }
    }
    }

    public void limpíarCamposPostVenta() {
        // Limpiar JTextFields
        txtApellidoCliente.setText("");
        txtBuscarClientes.setText("");
        txtCI.setText("");
        txtCelular.setText("");
        txtCodigo.setText("");
        txtCorreo.setText("");
        txtEstado.setText("");
        txtIDCliente.setText("");
        txtIDProducto.setText("");
        txtNombreCliente.setText("");
        txtNombreP.setText("");
        txtDireccion.setText("");
        txtPrecioProducto.setText("");
        txtNombreProductos.setText("");
        txtStock.setText("");
        txtPrecioVenta.setText("");

        // Limpiar JTextPane
        txtDescripcion.setText("");

        // Limpiar JSpinner
        spnCantidad.setValue(0);

        // Limpiar JTable (tablaResumen)
        DefaultTableModel modeloResumen = (DefaultTableModel) tablaResumen.getModel();
        modeloResumen.setRowCount(0);

        // Limpiar JLabel (totalVenta)
        labelTotal.setText("0.00");
        label.setIcon(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelClientes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarClientes = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnFiltrarClientes = new javax.swing.JButton();
        panelProductos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnFiltro = new javax.swing.JButton();
        panelResumenSeleccion = new javax.swing.JPanel();
        panelDatosCliente = new javax.swing.JPanel();
        lblClienteId = new javax.swing.JLabel();
        txtIDCliente = new javax.swing.JTextField();
        lblNombreCliente = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellidoCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblCelular = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        txtNombreProductos = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        btnHabilitar = new javax.swing.JButton();
        btnDeshabilitar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        spnCantidad = new javax.swing.JSpinner();
        btnEliminar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();
        label = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtNumeroSerie = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaResumen = new javax.swing.JTable();
        lblFecha = new javax.swing.JLabel();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modulo de Ventas");
        setFrameIcon(null);
        setPreferredSize(new java.awt.Dimension(getWidth(), getHeight()));

        panelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Clientes Disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        panelClientes.setSize(new java.awt.Dimension(300, 200));

        jLabel1.setText("Buscar:");


        txtBuscarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtBuscarClientesMouseEntered(evt);
            }
        });
        txtBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClientesActionPerformed(evt);
            }
        });
        txtBuscarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClientesKeyReleased(evt);
            }
        });

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaClientes);

        btnFiltrarClientes.setBackground(null);
        btnFiltrarClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnFiltrarClientes.setText("Filtrar");
        btnFiltrarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscarClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiltrarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1)))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrarClientes))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelProductos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Productos Disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        panelProductos.setSize(new java.awt.Dimension(400, 200));

        jLabel2.setText("Buscar:");

        txtNombreP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtNombrePMouseEntered(evt);
            }
        });
        txtNombreP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePActionPerformed(evt);
            }
        });
        txtNombreP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombrePKeyReleased(evt);
            }
        });

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProductos);

        btnFiltro.setBackground(null);
        btnFiltro.setForeground(java.awt.Color.white);
        btnFiltro.setText("Filtrar");
        btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreP)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(307, 307, 307)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelResumenSeleccion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Resumen de Selección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        panelDatosCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos de Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        panelDatosCliente.setSize(new java.awt.Dimension(panelClientes.getWidth(), panelClientes.getHeight()));
        panelDatosCliente.setEnabled(true);
        panelDatosCliente.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 20));

        lblClienteId.setText("ID:");
        lblClienteId.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblClienteId);
        txtIDCliente.setEditable(false);
        txtIDCliente.setFocusable(false);
        txtIDCliente.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtIDCliente);

        lblNombreCliente.setText("Nombres");
        lblNombreCliente.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblNombreCliente);

        txtNombreCliente.setEditable(false);
        txtNombreCliente.setFocusable(false);
        txtNombreCliente.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtNombreCliente);

        lblApellido.setText("Apellidos");
        lblApellido.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblApellido);

        txtApellidoCliente.setEditable(false);
        txtApellidoCliente.setFocusable(false);
        txtApellidoCliente.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtApellidoCliente);
        panelDatosCliente.add(jLabel6);

        lblCedula.setText("Nº Cedula:");
        lblCedula.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblCedula);

        txtCI.setEditable(false);
        txtCI.setFocusable(false);
        txtCI.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtCI);

        lblCorreo.setText("Correo:");
        lblCorreo.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblCorreo);

        txtCorreo.setEditable(false);
        txtCorreo.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtCorreo);

        lblCelular.setText("Nº Celular:");
        lblCelular.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblCelular);

        txtCelular.setEditable(false);
        txtCelular.setPreferredSize(TEXT_SIZE);
        panelDatosCliente.add(txtCelular);

        lblDireccion.setText("Direccion:");
        lblDireccion.setPreferredSize(LABEL_LENGHT);
        panelDatosCliente.add(lblDireccion);

        txtDireccion.setEditable(false);
        txtDireccion.setPreferredSize(new Dimension(150,50));
        panelDatosCliente.add(txtDireccion);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel5.setSize(new java.awt.Dimension(panelProductos.getWidth(), panelProductos.getHeight()));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 20));

        jLabel8.setText("iD:");
        jPanel5.add(jLabel8);

        jLabel9.setText("Nombre:");
        jPanel5.add(jLabel9);

        jLabel10.setText("Precio:");
        jPanel5.add(jLabel10);

        jLabel11.setText("Stock:");
        jPanel5.add(jLabel11);

        txtIDProducto.setEditable(false);
        txtIDProducto.setFocusable(false);
        jPanel5.add(txtIDProducto);

        txtNombreProductos.setEditable(false);
        txtNombreProductos.setFocusable(false);
        txtNombreProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductosActionPerformed(evt);
            }
        });
        jPanel5.add(txtNombreProductos);

        txtPrecioProducto.setEditable(false);
        txtPrecioProducto.setFocusable(false);
        jPanel5.add(txtPrecioProducto);

        txtStock.setEditable(false);
        txtStock.setFocusable(false);
        jPanel5.add(txtStock);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Precio y Cantidad de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jLabel12.setText("Precio de Venta:");
        jPanel6.add(jLabel12);

        txtPrecioVenta.setEnabled(false);
        jPanel6.add(txtPrecioVenta);

        btnHabilitar.setText("Habilitar");
        btnHabilitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHabilitarMouseEntered(evt);
            }
        });
        btnHabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabilitarActionPerformed(evt);
            }
        });
        jPanel6.add(btnHabilitar);

        btnDeshabilitar.setText("Deshabilitar");
        btnDeshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshabilitarActionPerformed(evt);
            }
        });
        jPanel6.add(btnDeshabilitar);

        jLabel13.setText("Cantidad a vender:");
        jPanel6.add(jLabel13);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-carrito-de-compras-20.png"))); // NOI18N
        btnAgregar.setText("Agregar Producto");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel6.add(btnAgregar);

        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel6.add(spnCantidad);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-eliminar-20.png"))); // NOI18N
        btnEliminar.setText("Eliminar Producto");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel6.add(btnEliminar);

        jPanel5.add(jPanel6);

        jLabel17.setText("Codigo:");
        jPanel5.add(jLabel17);

        txtCodigo.setEditable(false);
        txtCodigo.setFocusable(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel5.add(txtCodigo);

        txtDescripcion.setEditable(false);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Descripcion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        txtDescripcion.setFocusable(false);
        txtDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtDescripcionMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(txtDescripcion);

        jPanel5.add(jScrollPane4);
        jPanel5.add(label);

        jLabel19.setText("Estado:");
        jPanel5.add(jLabel19);

        txtEstado.setEditable(false);
        jPanel5.add(txtEstado);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(panelResumenSeleccion);
        panelResumenSeleccion.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(panelDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Resumen de Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel14.setText("Ultima Factura Nº:");

        txtNumeroSerie.setEditable(false);
        txtNumeroSerie.setFocusable(false);
        txtNumeroSerie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtNumeroSerieMouseEntered(evt);
            }
        });

        tablaResumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID.PRODUCTO", "PRODUCTO", "PRECIO UNITARIO", "CANTIDAD", "SUBTOTAL"
            }
        ));
        tablaResumen.setPreferredSize(null);
        jScrollPane3.setViewportView(tablaResumen);

        lblFecha.setText("--------------");

        btnGenerarVenta.setText("Generar Venta");
        btnGenerarVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarVentaMouseClicked(evt);
            }
        });
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Total de Venta:");

        labelTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTotal.setText("-------");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(257, 257, 257)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1308, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(293, 293, 293)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(labelTotal))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGenerarVenta)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
                    .addComponent(panelResumenSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(panelResumenSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        setBounds(0, 0, 1480, 808);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductosActionPerformed

    private void txtNombrePKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombrePKeyReleased

        actualizarTablaProductos();
    }//GEN-LAST:event_txtNombrePKeyReleased

    private void txtBuscarClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClientesKeyReleased
        actualizarTablaClientes();
    }//GEN-LAST:event_txtBuscarClientesKeyReleased

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked

        selectProductByVenta();
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        // vdao.agregarProducto(tablaResumen, txtIDProducto, txtNombreProductos, txtPrecioVenta, spnCantidad, txtStock);
        agregarProducto();
        actualizarTotalVenta();
        //vdao.calcularTotal(tablaResumen, labelTotal);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        eliminarProductosTabla();
        actualizarTotalVenta();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClientesMouseClicked

        selectClienteByVenta();
    }//GEN-LAST:event_tablaClientesMouseClicked

    private void btnGenerarVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarVentaMouseClicked

    }//GEN-LAST:event_btnGenerarVentaMouseClicked

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

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed

        // Validaciones para txtIDCliente
        if (txtIDCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente no puede estar vacío.");
            return;
        }
        try {
            Long.valueOf(txtIDCliente.getText()); // Intentar convertir a número
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente debe ser un número.");
            return;
        }
        // Validaciones para txtCodigo (asumiendo que es el estado)
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: El estado no puede estar vacío.");
            return;
        }

        // Validaciones para labelTotal (asumiendo que es el precio total)
        if (labelTotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: El precio total no puede estar vacío.");
            return;
        }

        // Validaciones para tablaResumen
        if (tablaResumen.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Error: La tabla de resumen no puede estar vacía.");
            return;
        }

        // Validaciones para cada fila de tablaResumen
        for (int i = 0; i < tablaResumen.getRowCount(); i++) {
            try {
                Long.valueOf(tablaResumen.getValueAt(i, 0).toString()); // ID del producto
                Integer.valueOf(tablaResumen.getValueAt(i, 3).toString()); // Cantidad
                BigDecimal precio = new BigDecimal(tablaResumen.getValueAt(i, 2).toString());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Datos incorrectos en la fila " + (i + 1) + " de la tabla.");
                return;
            }
        }

        // Convertir el texto del JLabel a LocalDateTime
        String fechaTexto = lblFecha.getText(); // "lunes 07 de abril del 2025, 21:10:58"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'del' yyyy, HH:mm:ss", new Locale("es", "ES"));
        LocalDateTime fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);

        //Agregar los detalles a la venta
        List<DetalleVentas> detalles = new ArrayList<>();

        // Lógica de la GUI para actualizar la tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        Ventas venta = new Ventas();
        for (int i = 0; i < modelo.getRowCount(); i++) {

            Long id = Long.valueOf(modelo.getValueAt(i, 0).toString());
            String nombre = (String) modelo.getValueAt(i, 1);
            BigDecimal precio = new BigDecimal(tablaResumen.getValueAt(i, 2).toString());
            int cantidad = (int) modelo.getValueAt(i, 3);

            Productos producto1 = productoController.buscarProductoId(id);

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
            BigDecimal total = procesarTotal(labelTotal.getText());
            venta.setTotal(total);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return;
        }
        Clientes cliente = clienteController.obtenerClientePorId(Long.valueOf(txtIDCliente.getText()));
        venta.setCliente(cliente);
        venta.setDetalles(detalles);
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

        //Al realizar la venta limpiamos todos los campos
        limpíarCamposPostVenta();

        //LLamamos al metodo para cargar todos los productos y los clientes en la tabla
        cargarProductos();

        cargarClientes();

        //Obtenemos la ultima factura y la mostramos en una caja de texto
        txtNumeroSerie.setText(String.valueOf(ventasController.obtenerUltimaFactura()));
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnHabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabilitarActionPerformed
        txtPrecioVenta.setEnabled(true);
    }//GEN-LAST:event_btnHabilitarActionPerformed

    private void btnDeshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshabilitarActionPerformed
        txtPrecioVenta.setEnabled(false);
    }//GEN-LAST:event_btnDeshabilitarActionPerformed

    private void txtBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClientesActionPerformed

    }//GEN-LAST:event_txtBuscarClientesActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setToolTipText("Haz clic en una fila de la tabla para seleccionar el producto que deseas eliminar.");
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void txtBuscarClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarClientesMouseEntered
        txtBuscarClientes.setToolTipText("Ingresa el nombre del cliente para buscarlo");
    }//GEN-LAST:event_txtBuscarClientesMouseEntered

    private void txtNombrePMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombrePMouseEntered
        txtNombreP.setToolTipText("Ingresa el nombre o codigo del producto a buscar");
    }//GEN-LAST:event_txtNombrePMouseEntered

    private void btnHabilitarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHabilitarMouseEntered
        btnHabilitar.setToolTipText("Habilita este boton solo si deseas cambiar el precio de venta\nEl precio del producto sera el mismo");
    }//GEN-LAST:event_btnHabilitarMouseEntered

    private void txtNumeroSerieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroSerieMouseEntered
        txtNumeroSerie.setToolTipText("Ultima Factura Nº" + txtNumeroSerie.getText());
    }//GEN-LAST:event_txtNumeroSerieMouseEntered

    private void txtDescripcionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescripcionMouseEntered

        String textoOriginal = txtDescripcion.getText();
        StringBuilder textoConSaltos = new StringBuilder("<html>"); // Iniciamos con HTML para saltos de línea

        for (int i = 0; i < textoOriginal.length(); i += 71) {
            int fin = Math.min(i + 71, textoOriginal.length());
            textoConSaltos.append(textoOriginal.substring(i, fin));
            if (fin < textoOriginal.length()) {
                textoConSaltos.append("<br>"); // Insertamos el  salto de línea
            }
        }

        textoConSaltos.append("</html>"); // Cerrar etiqueta HTML

        txtDescripcion.setToolTipText(textoConSaltos.toString());
    }//GEN-LAST:event_txtDescripcionMouseEntered

    private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroActionPerformed
        actualizarTablaProductos();
    }//GEN-LAST:event_btnFiltroActionPerformed

    private void btnFiltrarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarClientesActionPerformed
        actualizarTablaClientes();
    }//GEN-LAST:event_btnFiltrarClientesActionPerformed

    private void txtNombrePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnDeshabilitar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFiltrarClientes;
    private javax.swing.JButton btnFiltro;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnHabilitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JPanel panelResumenSeleccion;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel label;
    public javax.swing.JLabel labelTotal;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblClienteId;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JPanel panelDatosCliente;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaResumen;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtBuscarClientes;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextPane txtDescripcion;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtIDCliente;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables

}
