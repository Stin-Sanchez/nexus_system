
package view;

import Services.DetalleVentasService;
import Services.ProductoService;
import Services.VentasService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import controllers.DetalleVentasController;
import controllers.ProductsController;
import controllers.VentasController;
import dao.DetalleVentasDAO;
import dao.DetalleVentasImpl;
import dao.ProductosDAO;
import dao.ProductosDAOImpl;
import dao.VentasDAO;
import dao.VentasDAOImpl;
import entities.DetalleVentas;
import entities.Productos;
import entities.Ventas;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Comprobante extends javax.swing.JInternalFrame {

    VentasDAO vdao = new VentasDAOImpl();
    DetalleVentasDAO ddao = new DetalleVentasImpl();
    ProductosDAO pdao = new ProductosDAOImpl();

    private final VentasController ventasController;
    private final DetalleVentasController detalleController;
    private final ProductsController productoController;

    public Comprobante() {
        initComponents();

        VentasService ventasService = new VentasService();
        ventasController = new VentasController(ventasService);

        DetalleVentasService detalleServices = new DetalleVentasService(ddao);
        detalleController = new DetalleVentasController(detalleServices);

        ProductoService productoService = new ProductoService();
        productoController = new ProductsController(productoService);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarFactura = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFacturaEncontrada = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblFechaVenta = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Modulo de Facturas");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Ingresar Numero de Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtBuscarFactura.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarFacturaActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos de Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setText("Factura  Nº:");

        lblFacturaEncontrada.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFacturaEncontrada.setText("----------");

        jLabel2.setText("Fecha de Venta:");

        lblFechaVenta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFechaVenta.setText("------------------------------");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFacturaEncontrada, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblFacturaEncontrada)
                    .addComponent(jLabel2)
                    .addComponent(lblFechaVenta))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Cliente"));

        jLabel3.setText("Cliente");

        lblApellido.setText("--------------------------------------");

        jLabel5.setText("Nº Cedula:");

        lblNombreCliente.setText("--------------------------------------");

        lblDireccion.setText("Direccion:");

        lblCedula.setText("--------------------------------------");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblNombreCliente)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblApellido)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(lblCedula))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tablaProductos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaProductos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Total: $");

        labelTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelTotal.setText("---");

        btnGenerar.setText("Generar Reporte");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelTotal)
                    .addComponent(btnGenerar))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        mostrarDatosFactura();
        //mostrarProductosFactura();
        // vdao.buscarFacturaMostrarProductos(txtBuscarFactura, tablaProductos, labelTotal);

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarFacturaActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
          generarReporteFactura(lblFacturaEncontrada.getText());
    }//GEN-LAST:event_btnGenerarActionPerformed

    public void mostrarDatosFactura() {
        Ventas venta =
                ventasController.obtenerDetallesFactura(txtBuscarFactura.getText()).getData();
         // Validación previa
           String numeroFactura = txtBuscarFactura.getText();
        if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese un número de factura válido", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
        if (venta != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblFacturaEncontrada.setText(String.valueOf(venta.getNumFactura()));
            lblFechaVenta.setText(venta.getFechaHora().format(formatter));
            lblNombreCliente.setText(venta.getCliente().getNombre()+" "+venta.getCliente().getApellido());
            lblApellido.setText(venta.getCliente().getCedula());
            lblCedula.setText(venta.getCliente().getDireccion());
            
            
                DefaultTableModel modeloTabla = new DefaultTableModel();
               List<DetalleVentas> detalles = venta.getDetalles();
            BigDecimal totalFactura =BigDecimal.ZERO;

            modeloTabla.setRowCount(0);

            modeloTabla = new DefaultTableModel(new String[]{" Producto", "Codigo", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
            tablaProductos.setModel(modeloTabla);

            DecimalFormat formato = new DecimalFormat("#.##");

            for (DetalleVentas detalle : detalles) {
                // Obtener solo el nombre del cliente
                long productoId = detalle.getProductos().getId();
                Productos producto = productoController.buscarProductoId(productoId);
                String nombreProducto = (producto != null) ? producto.getNombreProducto() : "Desconocido";
                String codigoProducto = (producto != null) ? producto.getCode() : "Desconocido";
                int cantidad = detalle.getCantidad();
                BigDecimal cantidadBD = BigDecimal.valueOf(cantidad);
                BigDecimal precioUni = detalle.getPrecio();
                formato.format(precioUni);

                BigDecimal subtotal = cantidadBD.multiply(precioUni);
                formato.format(subtotal);

                totalFactura=totalFactura.add(subtotal);

                modeloTabla.addRow(new Object[]{nombreProducto, codigoProducto, cantidad, precioUni, subtotal});

            }

            labelTotal.setText(formato.format(totalFactura));
            
        } else {
           JOptionPane.showMessageDialog(this, 
                "No se encontró la factura con el número especificado", 
                "Factura no encontrada", 
                JOptionPane.INFORMATION_MESSAGE);
          
            lblFacturaEncontrada.setText(" ");
            lblFechaVenta.setText("");
            lblNombreCliente.setText("");
            lblApellido.setText("");
            lblCedula.setText("");
            return;
        }
        } catch (RuntimeException e) {
          JOptionPane.showMessageDialog(this, 
            "Ocurrió un error al buscar la factura. Por favor, inténtelo nuevamente.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        System.err.println("Error en mostrarDatosFactura: " + e.getMessage());
        e.printStackTrace();

    }
    }

    public void mostrarProductosFactura() {

        try {
            DefaultTableModel modeloTabla = new DefaultTableModel();

            List<DetalleVentas> detalles = detalleController.obtenerDetallesDeFactura(txtBuscarFactura.getText());
            BigDecimal totalFactura = BigDecimal.ZERO;

            modeloTabla.setRowCount(0);

            modeloTabla = new DefaultTableModel(new String[]{" Producto", "Codigo", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
            tablaProductos.setModel(modeloTabla);

            DecimalFormat formato = new DecimalFormat("#.##");

            for (DetalleVentas detalle : detalles) {
                // Obtener solo el nombre del cliente
                long productoId = detalle.getProductos().getId();
                Productos producto = productoController.buscarProductoId(productoId);
                String nombreProducto = (producto != null) ? producto.getNombreProducto(): "Desconocido";
                String codigoProducto = (producto != null) ? producto.getCode() : "Desconocido";
                int cantidad =  detalle.getCantidad();
                BigDecimal cantidadBD = BigDecimal.valueOf(cantidad);
                BigDecimal precioUni = detalle.getPrecio();
                formato.format(precioUni);

                BigDecimal subtotal = (cantidadBD.multiply(precioUni));
                formato.format(subtotal);

                totalFactura= totalFactura.add(subtotal);

                modeloTabla.addRow(new Object[]{nombreProducto, codigoProducto, cantidad, precioUni, subtotal});

            }

            labelTotal.setText(formato.format(totalFactura));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los productos " + e.getMessage());
        }
    }
    
    private void generarReporteFactura(String numFac){
         Document document = new Document(PageSize.A4, 50, 50, 110, 30); // Márgenes
        try {
            // Nombre de archivo único con timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nombreArchivo = "Reporte_Factura_Venta_" + numFac + "_" + timestamp + ".pdf";
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHora = formatoFecha.format(new Date());

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        // Asignar el event handler para encabezado/pie de página
        writer.setPageEvent(new Comprobante.HeaderFooter());
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
        leftContent.add(new Phrase("Nº"+numFac, infoFont));
        cellLeft.addElement(leftContent);

        Paragraph rightContent = new Paragraph();
        rightContent.add(new Phrase("Autorización: ", infoNegrita));
        rightContent.add(new Phrase("123-456-789000\n", infoFont));
        rightContent.add(new Phrase("Fecha: ", infoNegrita));
        rightContent.add(new Phrase(lblFechaVenta.getText(), infoFont));
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
        cliente.add(new Phrase(lblNombreCliente.getText(), infoFont));
        cliente.add(new Phrase("\nRUC/CI: ", infoNegrita));
        cliente.add(new Phrase(lblApellido.getText(), infoFont));
        cliente.add(new Phrase("\nDireccion: ", infoNegrita));
        cliente.add(new Phrase(lblCedula.getText(), infoFont));
        cliente.setAlignment(Element.ALIGN_LEFT);
        cliente.setSpacingAfter(10);
        cliente.setSpacingBefore(5);
        document.add(cliente);
        
      

        // ----- TABLA PRODUCTOS -----
        PdfPTable tabla = new PdfPTable(5);
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
        
        headerCell = new PdfPCell(new Phrase("Codigo", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
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
        for (int i = 0; i < tablaProductos.getRowCount(); i++) {
            String producto = tablaProductos.getValueAt(i, 0).toString();
            String codigo=tablaProductos.getValueAt(i, 1).toString();
            String cantidad = tablaProductos.getValueAt(i, 2).toString();
            String precio = tablaProductos.getValueAt(i, 3).toString();
            String subtotal = tablaProductos.getValueAt(i, 4).toString();

            PdfPCell cell = new PdfPCell(new Phrase(producto, new Font(Font.FontFamily.HELVETICA, 11)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            tabla.addCell(cell);
            
            cell = new PdfPCell(new Phrase(codigo, new Font(Font.FontFamily.HELVETICA, 11)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            tabla.addCell(cell);
            
            cell = new PdfPCell(new Phrase(cantidad, new Font(Font.FontFamily.HELVETICA, 11)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            tabla.addCell(cell);
            
            cell = new PdfPCell(new Phrase("$" + String.format("%.2f", Double.parseDouble(precio)), new Font(Font.FontFamily.HELVETICA, 11)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            tabla.addCell(cell);
            
            cell = new PdfPCell(new Phrase("$" + String.format("%.2f", Double.parseDouble(subtotal)), new Font(Font.FontFamily.HELVETICA, 11)));
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

            //Obtener el texto del label
            String totalText = labelTotal.getText();

// Reemplazar la coma por un punto si existe
            String totalFormatted = totalText.replace(',', '.');

// Convertir la cadena formateada a Double
            double totalValue = Double.valueOf(totalFormatted);

// Usar el valor para generar el PDF
            Paragraph total = new Paragraph("Total: $" + String.format("%.2f", totalValue), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(5);
            total.setSpacingAfter(5);
            document.add(total);

            JOptionPane.showMessageDialog(null, "PDF generado correctamente:\n" + nombreArchivo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al generar PDF"+e.getMessage());
        e.printStackTrace();
    } finally {
        document.close();
    }
    }
    
      public class HeaderFooter extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try{

              float headerHeight = 90f;

            PdfPTable header = new PdfPTable(1);
            header.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            header.getDefaultCell().setPadding(5);

            BaseColor color = new BaseColor(0, 86, 163); // Color azul corporativo

            // Nombre de la empresa con color y tamaño
            header.addCell(new Phrase("IMPORTADORA NEXUS S&M",
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, color)));
            
            // Resto de información de la empresa
            header.addCell(new Phrase("Reporte de Venta",
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD,color)));
            header.setSpacingBefore(10f);
            header.setSpacingAfter(10f);
          

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
            
            //Pie de pagina

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

             // Número de factura al lado izquierdo (10px desde abajo)
             ColumnText.showTextAligned(
                     canvas,
                     Element.ALIGN_LEFT,
                     new Phrase("Reporte de Factura Nº " + lblFacturaEncontrada.getText(),
                             new Font(Font.FontFamily.HELVETICA, 10)),
                     document.leftMargin(),
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
            e.printStackTrace();
        }
    
    }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFacturaEncontrada;
    private javax.swing.JLabel lblFechaVenta;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscarFactura;
    // End of variables declaration//GEN-END:variables
}
