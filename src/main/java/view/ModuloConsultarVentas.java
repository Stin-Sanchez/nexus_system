package view;

import Services.*;
import controllers.ClienteController;
import controllers.VentasController;
import entities.Clientes;
import entities.Estados;
import entities.EstadosVentas;
import entities.Ventas;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

public class ModuloConsultarVentas extends javax.swing.JInternalFrame {
    private final VentasController ventasController;
    private final ClienteController clienteController;
    private Dimension tamanioBoton = null;
    private      JComboBox<Estados> comboEstados;
       // Colores y estilo
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);

    public ModuloConsultarVentas() {
        initComponents();
        this.setMaximizable(true);
        tamanioBoton = new Dimension();
        tamanioBoton.setSize(125, 25);

        VentasService ventasService = new VentasService();
        ventasController = new VentasController(ventasService);

        clienteController = new ClienteController();
        btnActualizar.setPreferredSize(tamanioBoton);
        btnEliminar.setPreferredSize(tamanioBoton);
        btnNuevo.setPreferredSize(tamanioBoton);
        
        //COLORES
           btnActualizar.setBackground(WARNING_COLOR);
        btnEliminar.setBackground(DANGER_COLOR);
        btnNuevo.setBackground(SUCCESS_COLOR);
        // Llenar el combo directamente con todos los valores del enum
        comboEstados = new JComboBox<>(Estados.values());
        containerFiltros.add(comboEstados);
        comboEstados.addActionListener(e ->actualizarTablaVentas());
        
        
        try {
            //Cargar Todas las ventas al iniciar el sistema

            CargarVentas();
        } catch (Exception ex) {
            Logger.getLogger(ModuloConsultarVentas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final void CargarVentas() throws Exception {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        List<Ventas> listaVentas = ventasController.obtenerTodasLasVentas().getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        modeloTabla.setRowCount(0);

        modeloTabla = new DefaultTableModel(new String[]{"Factura", "Fecha de Venta", "Monto de Venta", "Cliente", "Estado"}, 0);
        tablaVentas.setModel(modeloTabla);

        for (Ventas fila : listaVentas) {

            String numeroFactura = fila.getNumFactura();
            String precio = String.valueOf(fila.getTotal());
            String fecha = fila.getFechaHora().format(formatter);

            // Obtener solo el nombre del cliente
            long clienteId = fila.getCliente().getId();
            Clientes cliente = clienteController.obtenerClientePorId(clienteId);
            String nombreCompletoCliente = (cliente != null) ? cliente.getNombre() + " " + cliente.getApellido() : "Desconocido";

            EstadosVentas estado = fila.getEstado();

            modeloTabla.addRow(new Object[]{numeroFactura, fecha, precio, nombreCompletoCliente, estado});
        }
    }

    private void actualizarTablaVentas() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        modelo.setRowCount(0); //

        VentasController.OperationResult<List<Ventas>> result = ventasController.listarVentasPorEstado(filtroCmb());

        if (result != null && result.getData() != null && !result.getData().isEmpty()) {
            // result.getData() devuelve List<Ventas>
            result.getData().stream().forEach(venta -> {
                modelo.addRow(new Object[]{
                    venta.getNumFactura(),
                    venta.getFechaHora().format(formatter),
                    venta.getTotal(),
                    venta.getCliente().getNombre() + " " + venta.getCliente().getApellido(),
                    venta.getEstado()
                });
            });
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron facturas con el filtro seleccionado",
                    "Error de búsqueda",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerMian = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        containerFiltros = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        containerVentas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Consulta de Ventas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18), PRIMARY_COLOR)); // NOI18N
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        container.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 50, 5));

        btnNuevo.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-añadir-20.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setPreferredSize(new java.awt.Dimension(50, 28));
        btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoMouseEntered(evt);
            }
        });
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        container.add(btnNuevo);

        btnActualizar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-20.png"))); // NOI18N
        btnActualizar.setText("EDITAR");
        btnActualizar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        container.add(btnActualizar);

        btnEliminar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-eliminar-20.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        container.add(btnEliminar);

        btnRefrescar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-repetir-20.png"))); // NOI18N
        btnRefrescar.setText("ACTUALIZAR");
        btnRefrescar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefrescarMouseEntered(evt);
            }
        });
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        container.add(btnRefrescar);

        containerFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Seleccione el tipo de filtro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), PRIMARY_COLOR)); // NOI18N
        containerFiltros.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        containerFiltros.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        txtFiltro.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtFiltro.setPreferredSize(new java.awt.Dimension(700, 25));
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
        containerFiltros.add(txtFiltro);

        containerVentas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Ventas ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), PRIMARY_COLOR)); // NOI18N
        containerVentas.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

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

        javax.swing.GroupLayout containerVentasLayout = new javax.swing.GroupLayout(containerVentas);
        containerVentas.setLayout(containerVentasLayout);
        containerVentasLayout.setHorizontalGroup(
            containerVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerVentasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1)
                .addGap(15, 15, 15))
        );
        containerVentasLayout.setVerticalGroup(
            containerVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerVentasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout containerMianLayout = new javax.swing.GroupLayout(containerMian);
        containerMian.setLayout(containerMianLayout);
        containerMianLayout.setHorizontalGroup(
            containerMianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerMianLayout.createSequentialGroup()
                .addGroup(containerMianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(container, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, containerMianLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(containerMianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(containerVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(containerFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(15, 15, 15))
        );
        containerMianLayout.setVerticalGroup(
            containerMianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerMianLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(containerFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerMian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerMian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
                        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked

    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        try {
            // Crear nuevo módulo
            ModuloVentasXD mv = new ModuloVentasXD();

            // Configurar módulo
            mv.setClosable(true);
            mv.setMaximizable(true);
            mv.setResizable(true);

            // Obtener referencia al panel actual para restaurar después
            final JInternalFrame panelActual = this;

            // Agregar listener para manejar el cierre
            mv.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosing(InternalFrameEvent e) {
                    // Restaurar panel actual cuando se cierre el módulo de ventas
                    SwingUtilities.invokeLater(() -> {
                        panelActual.setVisible(true);
                        try {
                            panelActual.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            // Ignorar error de selección
                        }
                    });
                }
            });

            // Transición: ocultar actual, mostrar nuevo
            this.setVisible(false);

            JDesktopPane desktop = this.getDesktopPane();
            desktop.add(mv);
            mv.setVisible(true);

            try {
                mv.setSelected(true);
                if (mv.isMaximizable()) {
                    mv.setMaximum(true);
                }
            } catch (PropertyVetoException ex) {
                System.out.println("No se pudo maximizar el módulo");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir módulo de ventas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseClicked
        tablaVentas.setToolTipText(tablaVentas.getToolTipText());
    }//GEN-LAST:event_tablaVentasMouseClicked

    private void tablaVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseEntered

    }//GEN-LAST:event_tablaVentasMouseEntered

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped

    }//GEN-LAST:event_txtFiltroKeyTyped

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            // Crear nuevo módulo
            ModuloActualizarVentas mv = new ModuloActualizarVentas();

            // Configurar módulo
            mv.setClosable(true);
            mv.setMaximizable(false);
            mv.setResizable(true);

            // Obtener referencia al panel actual para restaurar después
            final JInternalFrame panelActual = this;

            // Agregar listener para manejar el cierre
            mv.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosing(InternalFrameEvent e) {
                    // Restaurar panel actual cuando se cierre el módulo de ventas
                    SwingUtilities.invokeLater(() -> {
                        panelActual.setVisible(true);
                        try {
                            panelActual.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            // Ignorar error de selección
                        }
                    });
                }
            });

            // Transición: ocultar actual, mostrar nuevo
            this.setVisible(false);

            JDesktopPane desktop = this.getDesktopPane();
            desktop.add(mv);
            mv.setVisible(true);

            try {
                mv.setSelected(true);
                if (mv.isMaximizable()) {
                    mv.setMaximum(true);
                }
            } catch (PropertyVetoException ex) {
                System.out.println("No se pudo maximizar el módulo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al abrir módulo de ventas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered
        btnNuevo.setToolTipText("Agregar nueva venta");
    }//GEN-LAST:event_btnNuevoMouseEntered

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        btnActualizar.setToolTipText("Actualizar ventas");
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setToolTipText("Selecciona la fila de la venta a eliminar en la tabla");
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnRefrescarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrescarMouseEntered
        btnRefrescar.setToolTipText("Refrescar Sistema");
    }//GEN-LAST:event_btnRefrescarMouseEntered

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        try {
            CargarVentas();
        } catch (Exception ex) {
            System.getLogger(ModuloConsultarVentas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private EstadosVentas filtroCmb() {
        EstadosVentas estadoSeleccionado = (EstadosVentas) comboEstados.getSelectedItem();
        if (estadoSeleccionado == null) return null; // seguridad
        switch (estadoSeleccionado) {
            case FACTURADA, PENDIENTE, CANCELADA -> ventasController.listarVentasPorEstado(estadoSeleccionado);

        }

return estadoSeleccionado;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JPanel container;
    private javax.swing.JPanel containerFiltros;
    private javax.swing.JPanel containerMian;
    private javax.swing.JPanel containerVentas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
