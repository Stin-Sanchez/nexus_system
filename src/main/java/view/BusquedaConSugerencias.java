package view;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusquedaConSugerencias extends JFrame {
    private JTextField searchField;
    private JPopupMenu popup;
    private JList<Product> productList;
    private DefaultListModel<Product> listModel;
    private List<Product> allProducts;

    public BusquedaConSugerencias() {
        initializeProducts();
        setupUI();
        setupListeners();
        setVisible(true);
    }

    private void initializeProducts() {
        allProducts = new ArrayList<>();

        // Crear productos de ejemplo con rutas de imágenes reales
        // Opción 1: Imágenes desde archivos locales
        allProducts.add(new Product("Laptop Dell XPS", "Computadora portátil de alto rendimiento", "https://m.media-amazon.com/images/I/91MXLpouhoL._AC_SL1500_.jpg"));
        allProducts.add(new Product("iPhone 15", "Smartphone de última generación", "https://mobilestore.ec/wp-content/uploads/2023/09/iPhone-15-Pro-Black-Mobile-Store-Ecuador.png"));
        allProducts.add(new Product("Samsung Galaxy S24", "Teléfono Android premium", "https://mobilestore.ec/wp-content/uploads/2024/01/Samsung-Galaxy-S24-Ultra-Titanium-Black-Mobile-Store-Ecuador.jpg"));
        allProducts.add(new Product("MacBook Pro", "Laptop profesional para creativos", "https://onvia.com.ec/media/2024/08/WhatsApp-Image-2024-08-03-at-11.03.28-AM.jpeg"));
        allProducts.add(new Product("iPad Air", "Tablet versátil y potente", "https://m.media-amazon.com/images/I/61JREs56sTL.jpg"));
        allProducts.add(new Product("AirPods Pro", "Audífonos inalámbricos con cancelación de ruido", "https://think-ecuador.com/wp-content/uploads/2024/02/0160300827.jpeg"));
        allProducts.add(new Product("Dell Monitor 27", "Monitor 4K para oficina", "https://m.media-amazon.com/images/I/81tLVRGg2EL._AC_SL1500_.jpg"));
        allProducts.add(new Product("Logitech Mouse MX", "Mouse ergonómico inalámbrico", "https://nanotech-market.com/wp-content/uploads/2022/09/Lime-Green-and-Black-Active-Hype-Personal-Fitness-Coach-Instagram-Post-2022-09-28T160203.626.png"));
        allProducts.add(new Product("Teclado Mecánico", "Teclado gaming RGB", "https://tecnomall.ec/wp-content/uploads/2021/09/41gOoRZwsnL._SL500_.jpg"));
        allProducts.add(new Product("Webcam HD", "Cámara web para videoconferencias", "https://www.tenveo-video-conference-es.com/uploads/12429/page/usb-4k-webcam-with-built-in-microphone75454.jpg"));

        // Opción 2: También puedes añadir productos con URLs de imágenes
        // allProducts.add(new Product("Producto Web", "Descripción", "https://ejemplo.com/imagen.jpg"));
    }

    private ImageIcon loadImageIcon(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = null;

            // Verificar si es una URL o un archivo local
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                // Cargar imagen desde URL
                URL url = new URL(imagePath);
                originalImage = ImageIO.read(url);
            } else {
                // Cargar imagen desde archivo local
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    originalImage = ImageIO.read(imageFile);
                } else {
                    // Si no existe el archivo, crear icono por defecto
                    return createDefaultIcon(width, height);
                }
            }

            if (originalImage != null) {
                // Redimensionar la imagen manteniendo la proporción
                Image scaledImage = getScaledImage(originalImage, width, height);
                return new ImageIcon(scaledImage);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar imagen: " + imagePath + " - " + e.getMessage());
        }

        // Si hay error, devolver icono por defecto
        return createDefaultIcon(width, height);
    }

    private Image getScaledImage(BufferedImage originalImage, int maxWidth, int maxHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calcular las dimensiones manteniendo la proporción
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    private ImageIcon createDefaultIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear un icono por defecto con gradiente
        GradientPaint gradient = new GradientPaint(0, 0, Color.LIGHT_GRAY, width, height, Color.GRAY);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(2, 2, width-4, height-4, 10, 10);

        // Añadir borde
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(2, 2, width-4, height-4, 10, 10);

        // Añadir texto "Sin imagen"
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 8));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "Sin imagen";
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = (height + fm.getAscent()) / 2;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
        return new ImageIcon(img);
    }

    private void setupUI() {
        setTitle("Búsqueda Dinámica de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Panel superior con campo de búsqueda
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Buscar producto:"));

        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(searchField);

        add(topPanel, BorderLayout.NORTH);

        // Crear el popup menu
        popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Crear el modelo y lista para el popup
        listModel = new DefaultListModel<>();
        productList = new JList<>(listModel);
        productList.setCellRenderer(new ProductListCellRenderer());
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurar el popup
        JScrollPane scrollPane = new JScrollPane(productList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // IMPORTANTE: Evitar que el JList tome el foco automáticamente
        productList.setFocusable(false);
        scrollPane.setFocusable(false);

        popup.add(scrollPane);
        popup.setFocusable(false); // El popup tampoco debe tomar foco

        // Panel central con información
        JTextArea infoArea = new JTextArea("Escribe en el campo de búsqueda para ver los resultados en tiempo real.\n\n" +
                "Características:\n" +
                "• Búsqueda dinámica mientras escribes\n" +
                "• Muestra imagen y nombre del producto\n" +
                "• Popup que aparece automáticamente\n" +
                "• Selección con mouse o teclado");
        infoArea.setEditable(false);
        infoArea.setBackground(getBackground());
        infoArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JScrollPane(infoArea), BorderLayout.CENTER);
    }

    private void setupListeners() {
        // Listener para cambios en el texto
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });

        // Listener para teclas especiales (solo cuando el popup está visible)
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (popup.isVisible()) {
                    int selectedIndex = productList.getSelectedIndex();

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            if (selectedIndex < listModel.getSize() - 1) {
                                productList.setSelectedIndex(selectedIndex + 1);
                            }
                            e.consume(); // Solo consumir el evento para teclas de navegación
                            break;

                        case KeyEvent.VK_UP:
                            if (selectedIndex > 0) {
                                productList.setSelectedIndex(selectedIndex - 1);
                            }
                            e.consume(); // Solo consumir el evento para teclas de navegación
                            break;

                        case KeyEvent.VK_ENTER:
                            if (selectedIndex >= 0) {
                                selectProduct(listModel.getElementAt(selectedIndex));
                            }
                            e.consume(); // Solo consumir el evento para Enter
                            break;

                        case KeyEvent.VK_ESCAPE:
                            popup.setVisible(false);
                            e.consume(); // Solo consumir el evento para Escape
                            break;

                        // NO consumir otros eventos para permitir escritura normal
                    }
                }
                // Si el popup no está visible, no hacer nada (permitir escritura normal)
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No hacer nada aquí para evitar interferencias
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // No hacer nada aquí para evitar interferencias
            }
        });

        // Listener para selección con mouse (sin cambiar foco)
        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product selected = productList.getSelectedValue();
                if (selected != null) {
                    // Opcional: mostrar información adicional
                    // Mantener el foco en el campo de texto
                    SwingUtilities.invokeLater(() -> {
                        searchField.requestFocus();
                    });
                }
            }
        });

        // Listener para doble click (sin cambiar foco hasta seleccionar)
        productList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Product selected = productList.getSelectedValue();
                    if (selected != null) {
                        selectProduct(selected);
                    }
                } else {
                    // En click simple, mantener foco en el campo de texto
                    SwingUtilities.invokeLater(() -> {
                        searchField.requestFocus();
                    });
                }
            }
        });
    }

    private void performSearch() {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            popup.setVisible(false);
            return;
        }

        // Filtrar productos
        listModel.clear();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchText) ||
                    product.getDescription().toLowerCase().contains(searchText)) {
                listModel.addElement(product);
            }
        }

        // Mostrar u ocultar popup según resultados
        if (listModel.getSize() > 0) {
            if (!popup.isVisible()) {
                Point location = searchField.getLocationOnScreen();
                popup.show(searchField, 0, searchField.getHeight());
            }
            // Seleccionar el primer elemento por defecto
            productList.setSelectedIndex(0);

            // IMPORTANTE: Mantener el foco en el campo de texto
            SwingUtilities.invokeLater(() -> {
                searchField.requestFocus();
            });
        } else {
            popup.setVisible(false);
        }
    }

    private void selectProduct(Product product) {
        searchField.setText(product.getName());
        popup.setVisible(false);

        // Asegurar que el foco regrese al campo de texto después de la selección
        SwingUtilities.invokeLater(() -> {
            searchField.requestFocus();
            searchField.setCaretPosition(searchField.getText().length());
        });

        // Mostrar información del producto seleccionado
        JOptionPane.showMessageDialog(this,
                "Producto seleccionado:\n" +
                        "Nombre: " + product.getName() + "\n" +
                        "Descripción: " + product.getDescription() + "\n" +
                        "Imagen: " + product.getImagePath(),
                "Producto Seleccionado",
                JOptionPane.INFORMATION_MESSAGE,
                product.getLargeIcon());
    }

    // Clase interna para representar un producto
    private class Product {
        private String name;
        private String description;
        private String imagePath;
        private ImageIcon icon;

        public Product(String name, String description, String imagePath) {
            this.name = name;
            this.description = description;
            this.imagePath = imagePath;
            // Cargar la imagen de forma lazy (cuando se necesite)
            this.icon = null;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getImagePath() { return imagePath; }

        public ImageIcon getIcon() {
            // Cargar la imagen solo cuando se necesite (lazy loading)
            if (icon == null) {
                icon = loadImageIcon(imagePath, 50, 50);
            }
            return icon;
        }

        // Método para obtener imagen más grande para mostrar detalles
        public ImageIcon getLargeIcon() {
            return loadImageIcon(imagePath, 120, 120);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Renderer personalizado para mostrar imagen y texto
    private static class ProductListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Product) {
                Product product = (Product) value;
                setText("<html><b>" + product.getName() + "</b><br/>" +
                        "<small style='color: gray;'>" + product.getDescription() + "</small></html>");
                setIcon(product.getIcon());
                setVerticalTextPosition(CENTER);
                setHorizontalTextPosition(RIGHT);
            }

            // Configurar colores para selección
            if (isSelected) {
                setBackground(new Color(184, 207, 229));
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BusquedaConSugerencias();
        });
    }
}
