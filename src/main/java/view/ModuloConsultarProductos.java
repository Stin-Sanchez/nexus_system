
package view;

import Services.*;
import controllers.ProductsController;
import dao.ProductosDAO;
import dao.ProductosDAOImpl;
import entities.EstadosProductos;
import entities.Productos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ModuloConsultarProductos extends javax.swing.JInternalFrame {

    ProductosDAO pdao= new ProductosDAOImpl();
   
    private final ProductsController productController;
  

    public ModuloConsultarProductos() {
        initComponents();

        ProductoService productService = new ProductoService();
        productController = new ProductsController(productService);

       
        
        try {
            //Cargar Todas las ventas al iniciar el sistema

            CargarProductos();
        } catch (Exception ex) {
            Logger.getLogger(ModuloConsultarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarProductos() throws Exception {
        DefaultTableModel modeloTabla = new DefaultTableModel ();
        List<Productos> listaProductos = productController.obtenerProductos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        modeloTabla.setRowCount(0);

        modeloTabla = new DefaultTableModel(new String[]{ "Nombre", "Marca", "Codigo", "P.Uni", "Estado","Stock","Descripcion"}, 0);
        tablaVentas.setModel(modeloTabla);

        for (Productos product : listaProductos) {
          
            String nombre = product.getNombreProducto();
          
            String marca = product.getMarca() ;
            String codigo= product.getCode();
            BigDecimal precio= product.getPrecio();
            EstadosProductos estado= product.getEstado();
            int stock = product.getStock();
            String descripcion= product.getDescripcion();
                   

            modeloTabla.addRow(new Object[]{ nombre, marca,codigo, precio,estado,stock,descripcion});
        }
    }
    
    
    
    private void actualizarTablaProductos() {
        String name = txtFiltro.getText();
        List<Productos> productList = productController.buscarProductosPorNombre(name);
     
        
        
        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        modelo.setRowCount(0); //

        for ( Productos produc: productList) {
            modelo.addRow(new Object[]{
                produc.getNombreProducto(),
                produc.getMarca(),
                produc.getCode(),
                produc.getPrecio(),
                produc.getEstado(),
                produc.getStock(),
                produc.getDescripcion()
            });
      
        
    }
        
         for (int columnas = 0; columnas < tablaVentas.getColumnCount(); columnas++) {
            Class<?> colunClass = tablaVentas.getColumnClass(columnas);
            tablaVentas.setDefaultEditor(colunClass, null);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        btnFiltro = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        container = new javax.swing.JPanel();
        btnMostrar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Consulta de Productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        setClosable(true);
        setIconifiable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingrese el tipo de filtro"));
        jPanel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        btnFiltro.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-busca-mas-20.png"))); // NOI18N
        btnFiltro.setText("Buscar");
        btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFiltro)
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));
        jPanel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        tablaVentas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablaVentasMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tablaVentas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );

        container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMostrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnMostrar.setText("NUEVO");
        btnMostrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMostrar.setPreferredSize(new java.awt.Dimension(50, 28));
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        container.add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 140, -1));

        jButton3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton3.setText("ACTUALIZAR");
        container.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 150, -1));

        btnSalir.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSalir.setText("ELIMINAR");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        container.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 0, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(container, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
                
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
          
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        try {
         
            NuevoProducto np= new NuevoProducto();
            np.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ModuloConsultarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseClicked
                    tablaVentas.setToolTipText(tablaVentas.getToolTipText());
    }//GEN-LAST:event_tablaVentasMouseClicked

    private void tablaVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseEntered
    
    }//GEN-LAST:event_tablaVentasMouseEntered

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
   
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
           
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroActionPerformed
      productController.buscarProductosPorNombre(txtFiltro.getText());
    actualizarTablaProductos();
    }//GEN-LAST:event_btnFiltroActionPerformed
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltro;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel container;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
