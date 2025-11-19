
package view;

import Services.ClienteService;
import Services.VentasService;
import controllers.ClienteController;
import controllers.VentasController;
import dao.ClientesDAO;
import dao.ClientesDAOImpl;
import dao.VentasDAO;
import dao.VentasDAOImpl;
import entities.Clientes;
import entities.Estados;
import entities.EstadosVentas;
import entities.Ventas;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ModuloActualizarVentas extends javax.swing.JInternalFrame {
    
    VentasDAO vdao = new VentasDAOImpl();
    ClientesDAO cdao = new ClientesDAOImpl();
    private final VentasController ventasController;
    private final ClienteController clienteController;
    Map<String, Long> ciudadMap = new HashMap<>();//map para guardar clave-valor
    private  Dimension tamanioBoton=null;
          private  Dimension    TamanioCajas=null;
                private  Dimension    TamanioLabels=null;

    public ModuloActualizarVentas() {
        initComponents();
        //ESTABLECER EL TAMAÑO
        tamanioBoton= new Dimension();
        TamanioCajas=new Dimension();
        TamanioLabels=new Dimension();
        TamanioCajas.setSize(225, 25);
        tamanioBoton.setSize(150, 25);
        TamanioLabels.setSize(150, 25);
        //PONER LOS CAMPOS SIN EDITAR
        txtID.setEditable(false);
        TXTESTADO.setEditable(false);
        TXTTOTAL.setEditable(false);
        TXTFACTURA.setEditable(false);
        TXTFECHA.setEditable(false);
        TXTCLIENTE.setEditable(false);
        //ESTABLECER EL TAMAÑO DE LAS CAJAS DE TEXTO
        txtID.setPreferredSize(TamanioCajas);
        TXTESTADO.setPreferredSize(TamanioCajas);
        TXTTOTAL.setPreferredSize(TamanioCajas);
         TXTFACTURA.setPreferredSize(TamanioCajas);
         TXTFECHA.setPreferredSize(TamanioCajas);
         TXTCLIENTE.setPreferredSize(TamanioCajas); 
         cmbClientes.setPreferredSize(TamanioCajas);
         //ESTABLECER EL TAMAÑO DE LOS LABELS
         jLabel1.setPreferredSize(TamanioLabels);
         jLabel5.setPreferredSize(TamanioLabels);
         jLabel7.setPreferredSize(TamanioLabels);
         jLabel2.setPreferredSize(TamanioLabels);
         jLabel6.setPreferredSize(TamanioLabels);
         jLabel4.setPreferredSize(TamanioLabels);
         jLabel3.setPreferredSize(TamanioLabels);
        //ESTABLECER EL TAMAÑO A LOS BOTONES
        btnACtualizar.setPreferredSize(tamanioBoton);
        btnMostrar.setPreferredSize(tamanioBoton);
        btnAgregarCliente.setPreferredSize(tamanioBoton);
        
        VentasService ventasService = new VentasService();
        ventasController = new VentasController(ventasService);
            ClienteService clienteService = new ClienteService();
            clienteController = new ClienteController();
        
         //Cargamos el combobox con las ciudades ya registradas en el sistema
        List<Clientes> lista = cdao.obtenerClientes();//este metodo obtiene todas las ciudades de la base de datos
        cmbClientes.removeAllItems();
        cmbClientes.addItem("Seleccione");//mensaje de combobox por defecto
        
        //Recorremos por cada ciudad y la añadimos al combo
        for (Clientes cliente : lista) {
            String nombres =cliente.getNombre().concat(" "+cliente.getApellido());
            cmbClientes.addItem(nombres);//añadimos solo el nombre 
            ciudadMap.put(nombres, cliente.getId()); // Guardar relación , el id y el nombre de la ciudad para su posterior registro

        }
        
        try {
            //Cargar Todas las ventas al iniciar el sistema

            CargarVentas();
            
        } catch (Exception ex) {
            Logger.getLogger(ModuloActualizarVentas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarVentas() throws Exception {
        DefaultTableModel modeloTabla = new DefaultTableModel ();
        List<Ventas> listaVentas = ventasController.obtenerTodasLasVentas().getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        modeloTabla.setRowCount(0);

        modeloTabla = new DefaultTableModel(new String[]{ "Factura", "Fecha de Venta", "Monto de Venta", "Cliente", "Estado"}, 0);
        tablaVentas.setModel(modeloTabla);

        for (Ventas fila : listaVentas) {
          
            String numeroFactura = fila.getNumFactura();
          
            String precio =  String.valueOf(fila.getTotal());
            String fecha= fila.getFechaHora().format(formatter);

            // Obtener solo el nombre del cliente
            long clienteId = fila.getCliente().getId();
            Clientes cliente = clienteController.obtenerClientePorId(clienteId);
            String nombreCompletoCliente = (cliente != null) ? cliente.getNombre() + " " + cliente.getApellido() : "Desconocido";

            EstadosVentas estado = fila.getEstado();

            modeloTabla.addRow(new Object[]{ numeroFactura, fecha, precio, nombreCompletoCliente, estado});
        }
    }
    
    
      private void seleccionarVentasActualizar (){

        int fila = tablaVentas.getSelectedRow();

        try {

            if (fila >= 0) {
                Long id = Long.valueOf(tablaVentas.getValueAt(fila, 0).toString());
                txtID.setText(String.valueOf(id));
                TXTFACTURA.setText(tablaVentas.getValueAt(fila, 0).toString());
                TXTFECHA.setText(tablaVentas.getValueAt(fila, 1).toString());
                TXTTOTAL.setText(tablaVentas.getValueAt(fila,2).toString());
                TXTCLIENTE.setText(tablaVentas.getValueAt(fila, 3).toString());
                TXTESTADO.setText(tablaVentas.getValueAt(fila, 4).toString());
              


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TXTESTADO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TXTTOTAL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TXTFACTURA = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TXTCLIENTE = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TXTFECHA = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        btnAgregarCliente = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        container = new javax.swing.JPanel();
        btnMostrar = new javax.swing.JButton();
        btnACtualizar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "ACtualizar Ventas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        setClosable(true);
        setIconifiable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos de la venta", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        jPanel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1088, 200));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 25));

        jLabel1.setText("Codigo de Venta:");
        jPanel1.add(jLabel1);

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });
        jPanel1.add(txtID);

        jLabel2.setText("Estado:");
        jPanel1.add(jLabel2);
        jPanel1.add(TXTESTADO);

        jLabel3.setText("Total de Venta:");
        jPanel1.add(jLabel3);
        jPanel1.add(TXTTOTAL);

        jLabel4.setText("Numero de Factura:");
        jPanel1.add(jLabel4);
        jPanel1.add(TXTFACTURA);

        jLabel5.setText("Cliente Actual:");
        jPanel1.add(jLabel5);

        TXTCLIENTE.setPreferredSize(new java.awt.Dimension(125, 25));
        jPanel1.add(TXTCLIENTE);

        jLabel6.setText("Fecha y Hora:");
        jPanel1.add(jLabel6);
        jPanel1.add(TXTFECHA);

        jLabel7.setText("Cliente Nuevo:");
        jPanel1.add(jLabel7);

        cmbClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbClientes.setPreferredSize(new java.awt.Dimension(150, 27));
        jPanel1.add(cmbClientes);

        btnAgregarCliente.setText("Agregar Cliente");
        jPanel1.add(btnAgregarCliente);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Ventas registradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18))); // NOI18N
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
                .addComponent(jScrollPane1)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );

        container.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 50, 5));

        btnMostrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnMostrar.setText("RELOAD");
        btnMostrar.setPreferredSize(new java.awt.Dimension(50, 28));
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        container.add(btnMostrar);

        btnACtualizar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnACtualizar.setText("ACTUALIZAR");
        btnACtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACtualizarActionPerformed(evt);
            }
        });
        container.add(btnACtualizar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        try {
            CargarVentas();
        } catch (Exception ex) {
            Logger.getLogger(ModuloActualizarVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseClicked
                    tablaVentas.setToolTipText(tablaVentas.getToolTipText());
                    seleccionarVentasActualizar();
                    
    }//GEN-LAST:event_tablaVentasMouseClicked

    private void tablaVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseEntered
    
    }//GEN-LAST:event_tablaVentasMouseEntered

    private void btnACtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACtualizarActionPerformed
        Ventas venta=null;
        int fila = tablaVentas.getSelectedRow();
        try {

            if (fila >= 0) {
                Long id = Long.valueOf(tablaVentas.getValueAt(fila, 0).toString());
                venta= new Ventas();
                String clienteSelect = (String) cmbClientes.getSelectedItem();
                venta.setId(Long.valueOf(txtID.getText()));
                EstadosVentas estado=  EstadosVentas.valueOf( TXTESTADO.getText());
                venta.setEstado(estado);
                venta.setNumFactura(TXTFACTURA.getText());
                venta.setTotal(new BigDecimal(TXTTOTAL.getText()));
                // Obtener los IDs  género desde el DAO
                long  idCliente = vdao.obtenerClientes().entrySet().stream()
                .filter(entry -> entry.getValue().equals(clienteSelect))
                .map(Map.Entry::getKey).findFirst().orElse(-1L);

                Clientes cliente= new Clientes (idCliente, clienteSelect);
                String fechaTexto = TXTFECHA.getText(); // Ejemplo: "04/09/2025 10:25"

                // Definir el formato exacto
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                // Parsear a LocalDateTime
                LocalDateTime fechaHora = LocalDateTime.parse(fechaTexto, formatter);
                // Asignar al DTO

                venta.setFechaHora(fechaHora);
                venta.setCliente(cliente);
                ventasController.actualizarVenta(venta);
                JOptionPane.showMessageDialog(this,"Venta con ID "+venta.getId()+" actualizada con exito");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Venta con ID "+venta.getId()+" no fue actualizada con exito"+e.getMessage());
            e.printStackTrace();

        }

    }//GEN-LAST:event_btnACtualizarActionPerformed

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped

    }//GEN-LAST:event_txtIDKeyTyped

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed

    }//GEN-LAST:event_txtIDActionPerformed
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TXTCLIENTE;
    private javax.swing.JTextField TXTESTADO;
    private javax.swing.JTextField TXTFACTURA;
    private javax.swing.JTextField TXTFECHA;
    private javax.swing.JTextField TXTTOTAL;
    private javax.swing.JButton btnACtualizar;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JPanel container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
