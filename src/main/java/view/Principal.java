package view;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class Principal extends javax.swing.JFrame {

    private Comprobante v;
    private NuevoProducto p;
    private NuevCliente c;
    private ModuloConsultarVentas cventas;
    private ModuloConsultarProductos produ;
    private ModuloActualizarVentas acV;

    public Principal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        panelContenedor = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuPrincipal = new javax.swing.JMenu();
        Usuarios1 = new javax.swing.JMenu();
        menuNuevaVenta = new javax.swing.JMenuItem();
        menuConsultarVentas = new javax.swing.JMenuItem();
        SUBMENUACTUALIZARVENTAS = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        productos = new javax.swing.JMenu();
        menuProductos = new javax.swing.JMenuItem();
        menuConsultarProductos = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        Reportes = new javax.swing.JMenu();
        menuFacturas = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        Clientes = new javax.swing.JMenu();
        menuClientes = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        Usuarios = new javax.swing.JMenu();
        menuUsuarios = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("File");
        jMenuBar3.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("File");
        jMenuBar4.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar4.add(jMenu7);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelContenedor.setPreferredSize(new java.awt.Dimension(1600, 900));

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1800, Short.MAX_VALUE)
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        MenuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/menu.png"))); // NOI18N

        Usuarios1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-ventas-48.png"))); // NOI18N
        Usuarios1.setText("Ventas");

        menuNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-añadir-32.png"))); // NOI18N
        menuNuevaVenta.setText("Nuevo Venta");
        menuNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevaVentaActionPerformed(evt);
            }
        });
        Usuarios1.add(menuNuevaVenta);

        menuConsultarVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-casilla-de-verificación-2-32.png"))); // NOI18N
        menuConsultarVentas.setText("Consultar Ventas");
        menuConsultarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsultarVentasActionPerformed(evt);
            }
        });
        Usuarios1.add(menuConsultarVentas);

        SUBMENUACTUALIZARVENTAS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-archivo-de-texto-32.png"))); // NOI18N
        SUBMENUACTUALIZARVENTAS.setText("Editar Ventas");
        SUBMENUACTUALIZARVENTAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SUBMENUACTUALIZARVENTASActionPerformed(evt);
            }
        });
        Usuarios1.add(SUBMENUACTUALIZARVENTAS);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-basura-32.png"))); // NOI18N
        jMenuItem3.setText("Eliminar Ventas");
        Usuarios1.add(jMenuItem3);

        MenuPrincipal.add(Usuarios1);

        productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-productos-48.png"))); // NOI18N
        productos.setText("Productos");

        menuProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-añadir-32.png"))); // NOI18N
        menuProductos.setText("Nuevo Producto");
        menuProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProductosActionPerformed(evt);
            }
        });
        productos.add(menuProductos);

        menuConsultarProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-casilla-de-verificación-2-32.png"))); // NOI18N
        menuConsultarProductos.setText("Consultar Productos");
        menuConsultarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsultarProductosActionPerformed(evt);
            }
        });
        productos.add(menuConsultarProductos);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-archivo-de-texto-32.png"))); // NOI18N
        jMenuItem7.setText("Editar Productos");
        productos.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-basura-32.png"))); // NOI18N
        jMenuItem8.setText("Eliminar Productos");
        productos.add(jMenuItem8);

        MenuPrincipal.add(productos);

        Reportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/reporte.png"))); // NOI18N
        Reportes.setText("Reportes");

        menuFacturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-casilla-de-verificación-2-32.png"))); // NOI18N
        menuFacturas.setText("Consultar Facturas");
        menuFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFacturasActionPerformed(evt);
            }
        });
        Reportes.add(menuFacturas);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-archivo-de-texto-32.png"))); // NOI18N
        jMenuItem4.setText("Editar Facturas");
        Reportes.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-basura-32.png"))); // NOI18N
        jMenuItem5.setText("Eliminar Factura");
        Reportes.add(jMenuItem5);

        MenuPrincipal.add(Reportes);

        Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-clientes-48.png"))); // NOI18N
        Clientes.setText("Clientes");

        menuClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-añadir-32.png"))); // NOI18N
        menuClientes.setText("Nuevo Cliente");
        menuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClientesActionPerformed(evt);
            }
        });
        Clientes.add(menuClientes);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-casilla-de-verificación-2-32.png"))); // NOI18N
        jMenuItem10.setText("Consultar Clientes");
        Clientes.add(jMenuItem10);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-archivo-de-texto-32.png"))); // NOI18N
        jMenuItem9.setText("Editar Clientes");
        Clientes.add(jMenuItem9);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-basura-32.png"))); // NOI18N
        jMenuItem11.setText("Eliminar Clientes");
        Clientes.add(jMenuItem11);

        MenuPrincipal.add(Clientes);

        Usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/users.png"))); // NOI18N
        Usuarios.setText("Usuarios");

        menuUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-añadir-32.png"))); // NOI18N
        menuUsuarios.setText("Nuevo Usuario");
        menuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUsuariosActionPerformed(evt);
            }
        });
        Usuarios.add(menuUsuarios);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-casilla-de-verificación-2-32.png"))); // NOI18N
        jMenuItem13.setText("Consultar Usuarios");
        Usuarios.add(jMenuItem13);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-editar-archivo-de-texto-32.png"))); // NOI18N
        jMenuItem12.setText("Editar Usuarios");
        Usuarios.add(jMenuItem12);

        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icons8-basura-32.png"))); // NOI18N
        jMenuItem14.setText("Eliminar Usuarios");
        Usuarios.add(jMenuItem14);

        MenuPrincipal.add(Usuarios);

        jMenuBar1.add(MenuPrincipal);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 1800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFacturasActionPerformed
        v = new Comprobante();
        panelContenedor.add(v);
        v.setVisible(true);

        v.moveToFront();

        try {
            v.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_menuFacturasActionPerformed

    private void menuProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProductosActionPerformed
        p = new NuevoProducto();
        panelContenedor.add(p);
        p.setVisible(true);
        panelContenedor.repaint();

    }//GEN-LAST:event_menuProductosActionPerformed

    private void menuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClientesActionPerformed
        c = new NuevCliente();
        panelContenedor.add(c);
        c.setVisible(true);
        panelContenedor.repaint();
    }//GEN-LAST:event_menuClientesActionPerformed

    private void menuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUsuariosActionPerformed
  
    }//GEN-LAST:event_menuUsuariosActionPerformed

    private void menuNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevaVentaActionPerformed
        ModuloVentasXD ventas = new ModuloVentasXD();

        try {
            ventas.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }

        panelContenedor.add(ventas);
        ventas.setVisible(true);
        ventas.moveToFront();
        panelContenedor.repaint();

    }//GEN-LAST:event_menuNuevaVentaActionPerformed

    private void menuConsultarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsultarVentasActionPerformed
        cventas = new ModuloConsultarVentas();
        panelContenedor.add(cventas);
        cventas.setVisible(true);
        panelContenedor.repaint();


    }//GEN-LAST:event_menuConsultarVentasActionPerformed

    private void menuConsultarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsultarProductosActionPerformed
        produ = new ModuloConsultarProductos();
        panelContenedor.add(produ);
        produ.setVisible(true);

    }//GEN-LAST:event_menuConsultarProductosActionPerformed

    private void SUBMENUACTUALIZARVENTASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SUBMENUACTUALIZARVENTASActionPerformed
        acV = new ModuloActualizarVentas();
        panelContenedor.add(acV);
        acV.setVisible(true);
    }//GEN-LAST:event_SUBMENUACTUALIZARVENTASActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Clientes;
    private javax.swing.JMenu MenuPrincipal;
    private javax.swing.JMenu Reportes;
    private javax.swing.JMenuItem SUBMENUACTUALIZARVENTAS;
    private javax.swing.JMenu Usuarios;
    private javax.swing.JMenu Usuarios1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem menuClientes;
    private javax.swing.JMenuItem menuConsultarProductos;
    private javax.swing.JMenuItem menuConsultarVentas;
    private javax.swing.JMenuItem menuFacturas;
    private javax.swing.JMenuItem menuNuevaVenta;
    private javax.swing.JMenuItem menuProductos;
    private javax.swing.JMenuItem menuUsuarios;
    private javax.swing.JDesktopPane panelContenedor;
    private javax.swing.JMenu productos;
    // End of variables declaration//GEN-END:variables
}
