package view;


import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;


public class PanelActualizarVenta extends JPanel {
    public PanelActualizarVenta() {
        setLayout(new MigLayout(
                "insets 16, wrap 2",        // márgenes y 2 columnas
                "[right]16[grow, fill]",    // 1ª col para etiquetas, 2ª col expandible
                "[]"                        // filas automáticas
        ));

        // Campos
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Disponible", "Agotado"});
        JTextArea txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        // Imagen
        JLabel lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(120, 120));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JButton btnCargarImg = new JButton("Cargar Imagen");

        // === Agregar al formulario ===
        add(new JLabel("Código:"));
        add(txtCodigo, "growx");
        add(new JLabel("Nombre:"));
        add(txtNombre, "growx");
        add(new JLabel("Marca:"));
        add(txtMarca, "growx");
        add(new JLabel("Precio:"));
        add(txtPrecio, "growx");
        add(new JLabel("Stock:"));
        add(txtStock, "growx");
        add(new JLabel("Estado:"));
        add(cbEstado, "growx");

        add(new JLabel("Descripción:"), "top");
        add(new JScrollPane(txtDescripcion), "grow, h 80!");

        add(new JLabel("Imagen:"), "top");
        JPanel pnlImg = new JPanel(new MigLayout("wrap 1, align center"));
        pnlImg.add(lblImagen, "center");
        pnlImg.add(btnCargarImg);
        add(pnlImg, "growx, span");

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnGuardar, "span, split 2, center");
        add(btnCancelar);
    }
}




