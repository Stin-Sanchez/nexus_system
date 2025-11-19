package view;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  ModernDashboard extends JFrame {

    // Colores para pequeños negocios - más cálidos y amigables
    private static final Color PRIMARY_COLOR = new Color(34, 197, 94);  // Verde éxito
    private static final Color SECONDARY_COLOR = new Color(59, 130, 246); // Azul información
    private static final Color WARNING_COLOR = new Color(249, 115, 22);   // Naranja advertencia
    private static final Color DANGER_COLOR = new Color(239, 68, 68);     // Rojo peligro
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color SIDEBAR_COLOR = new Color(55, 65, 81);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(31, 41, 55);

    // Componentes principales
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    private JPanel dashboardContent;
    private boolean sidebarVisible = true;
    private int sidebarWidth = 220; // Más estrecho para pantallas pequeñas

    public ModernDashboard() {
        initializeFrame();
        createComponents();
        setupLayout();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Mi Negocio POS - Panel de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Tamaño más modesto
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));

        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createComponents() {
        setLayout(new BorderLayout());
        createSidebar();
        createMainContent();
    }

    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, getHeight()));
        sidebarPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Logo simple del negocio
        JLabel logoLabel = createStyledLabel("🏪 MI NEGOCIO", 18, Color.WHITE, Font.BOLD);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(logoLabel);

        sidebarPanel.add(Box.createVerticalStrut(25));

        // Menú simplificado para pequeños negocios
        String[] menuItems = {"Inicio", "Vender", "Productos", "Clientes", "Reportes"};
        String[] menuIcons = {"🏠", "🛒", "📦", "👥", "📊"};

        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createMenuButton(menuIcons[i] + "  " + menuItems[i], i == 0);
            final String itemName = menuItems[i];
            menuButton.addActionListener(e -> switchToModule(itemName));
            sidebarPanel.add(menuButton);
            sidebarPanel.add(Box.createVerticalStrut(8));
        }

        sidebarPanel.add(Box.createVerticalGlue());

        // Información del usuario actual
        JPanel userPanel = createUserPanel();
        sidebarPanel.add(userPanel);

        sidebarPanel.add(Box.createVerticalStrut(10));

        // Botón de cerrar sesión
        JButton logoutButton = createLogoutButton();
        logoutButton.addActionListener(e -> logout());
        sidebarPanel.add(logoutButton);
    }

    private void createMainContent() {
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(BACKGROUND_COLOR);

        // Header simplificado
        JPanel headerPanel = createHeader();
        mainContentPanel.add(headerPanel, BorderLayout.NORTH);

        // Contenido principal
        dashboardContent = createDashboardContent();

        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        header.setPreferredSize(new Dimension(0, 60));

        // Botón hamburguesa
        JButton toggleButton = new JButton("☰");
        toggleButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        toggleButton.setBackground(PRIMARY_COLOR);
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setPreferredSize(new Dimension(35, 35));
        toggleButton.addActionListener(e -> toggleSidebar());

        // Panel de información del día
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = createStyledLabel("Buen día! ", 16, TEXT_COLOR, Font.PLAIN);
        JLabel dateLabel = createStyledLabel(getCurrentDate(), 14, new Color(107, 114, 128), Font.PLAIN);

        infoPanel.add(welcomeLabel);
        infoPanel.add(dateLabel);

        // Botón de venta rápida
        JButton quickSaleButton = new JButton("💳 Venta Rápida");
        quickSaleButton.setBackground(PRIMARY_COLOR);
        quickSaleButton.setForeground(Color.WHITE);
        quickSaleButton.setBorderPainted(false);
        quickSaleButton.setFocusPainted(false);
        quickSaleButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        quickSaleButton.addActionListener(e -> quickSale());

        header.add(toggleButton, BorderLayout.WEST);
        header.add(infoPanel, BorderLayout.CENTER);
        header.add(quickSaleButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createDashboardContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND_COLOR);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Resumen del día (más simple)
        JPanel todayPanel = createTodayPanel();
        content.add(todayPanel);

        content.add(Box.createVerticalStrut(20));

        // Panel de alertas importantes
        JPanel alertsPanel = createAlertsPanel();
        content.add(alertsPanel);

        content.add(Box.createVerticalStrut(20));

        // Panel de dos columnas para aprovechar mejor el espacio
        JPanel twoColumnPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        twoColumnPanel.setBackground(BACKGROUND_COLOR);
        twoColumnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Columna izquierda: Productos populares
        JPanel popularProductsPanel = createPopularProductsPanel();
        twoColumnPanel.add(popularProductsPanel);

        // Columna derecha: Actividad reciente
        JPanel activityPanel = createSimpleActivityPanel();
        twoColumnPanel.add(activityPanel);

        content.add(twoColumnPanel);
        content.add(Box.createVerticalStrut(20));

        // Panel de gráfico de ventas del día
        JPanel salesChartPanel = createSalesChartPanel();
        content.add(salesChartPanel);

        content.add(Box.createVerticalStrut(20));

        // Panel inferior con más información
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        // Clima del negocio (estado general)
        JPanel businessWeatherPanel = createBusinessWeatherPanel();
        bottomPanel.add(businessWeatherPanel);

        // Métodos de pago
        JPanel paymentMethodsPanel = createPaymentMethodsPanel();
        bottomPanel.add(paymentMethodsPanel);

        // Próximos vencimientos
        JPanel expirationPanel = createExpirationPanel();
        bottomPanel.add(expirationPanel);

        content.add(bottomPanel);

        return content;
    }

    private JPanel createTodayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 15, 15)); // 2x2 para mejor visualización
        panel.setBackground(BACKGROUND_COLOR);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Cards esenciales para pequeños negocios
        panel.add(createSimpleCard("Ventas Hoy", "$1,250", "💰", PRIMARY_COLOR, "▲ +15% vs ayer"));
        panel.add(createSimpleCard("Productos Vendidos", "47 unidades", "📦", SECONDARY_COLOR, "12 productos diferentes"));
        panel.add(createSimpleCard("Clientes Atendidos", "23 personas", "👥", new Color(147, 51, 234), "3 clientes nuevos"));
        panel.add(createSimpleCard("Stock Bajo", "5 productos", "⚠️", WARNING_COLOR, "Necesitan reposición"));

        return panel;
    }

    private JPanel createSimpleCard(String title, String value, String icon, Color color, String subtitle) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Header con ícono
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        JLabel titleLabel = createStyledLabel(title, 11, new Color(107, 114, 128), Font.PLAIN);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Valor principal más grande
        JLabel valueLabel = createStyledLabel(value, 22, TEXT_COLOR, Font.BOLD);

        // Subtítulo informativo
        JLabel subtitleLabel = createStyledLabel(subtitle, 10, color, Font.PLAIN);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(subtitleLabel, BorderLayout.SOUTH);

        // Efecto hover suave
        addHoverEffect(card);

        return card;
    }

    private JPanel createAlertsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel titleLabel = createStyledLabel("⚡ Alertas Importantes", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Lista de alertas críticas
        JPanel alertsList = new JPanel();
        alertsList.setLayout(new BoxLayout(alertsList, BoxLayout.Y_AXIS));
        alertsList.setBackground(CARD_COLOR);

        String[] alerts = {
                "🔴 Coca-Cola 500ml - Solo quedan 3 unidades",
                "🟡 Factura #1247 - Cliente debe $150.00",
                "🟢 Backup realizado exitosamente a las 02:00 AM"
        };

        Color[] alertColors = {DANGER_COLOR, WARNING_COLOR, PRIMARY_COLOR};

        for (int i = 0; i < alerts.length; i++) {
            JLabel alertLabel = createStyledLabel(alerts[i], 11, alertColors[i], Font.PLAIN);
            alertLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
            alertsList.add(alertLabel);
        }

        panel.add(alertsList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPopularProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = createStyledLabel("🔥 Productos Populares Hoy", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Lista de productos con mini-gráficos
        JPanel productsList = new JPanel();
        productsList.setLayout(new BoxLayout(productsList, BoxLayout.Y_AXIS));
        productsList.setBackground(CARD_COLOR);

        String[] products = {"Coca-Cola 500ml", "Pan Integral", "Leche Entera", "Galletas Oreo", "Agua Mineral"};
        String[] quantities = {"24", "18", "15", "12", "10"};
        Color[] barColors = {PRIMARY_COLOR, SECONDARY_COLOR, new Color(147, 51, 234), WARNING_COLOR, new Color(236, 72, 153)};

        for (int i = 0; i < products.length; i++) {
            JPanel productPanel = createProductRow(products[i], quantities[i], barColors[i], (24 - i * 3));
            productsList.add(productPanel);
            productsList.add(Box.createVerticalStrut(5));
        }

        panel.add(productsList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProductRow(String product, String quantity, Color color, int percentage) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CARD_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel productLabel = createStyledLabel(product, 11, TEXT_COLOR, Font.PLAIN);
        JLabel quantityLabel = createStyledLabel(quantity + " und.", 10, color, Font.BOLD);

        // Mini barra de progreso
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.X_AXIS));
        progressPanel.setBackground(CARD_COLOR);

        JPanel progressBar = new JPanel();
        progressBar.setBackground(color);
        progressBar.setPreferredSize(new Dimension(percentage, 4));
        progressBar.setMaximumSize(new Dimension(percentage, 4));

        JPanel emptyBar = new JPanel();
        emptyBar.setBackground(new Color(243, 244, 246));

        progressPanel.add(progressBar);
        progressPanel.add(emptyBar);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(CARD_COLOR);
        infoPanel.add(productLabel, BorderLayout.WEST);
        infoPanel.add(quantityLabel, BorderLayout.EAST);

        row.add(infoPanel, BorderLayout.NORTH);
        row.add(progressPanel, BorderLayout.SOUTH);

        return row;
    }

    private JPanel createSimpleActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = createStyledLabel("📋 Últimas Actividades", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Actividades con timestamps
        JPanel activitiesList = new JPanel();
        activitiesList.setLayout(new BoxLayout(activitiesList, BoxLayout.Y_AXIS));
        activitiesList.setBackground(CARD_COLOR);

        String[] activities = {
                "💰 Venta #1254 - $45.50 - María González",
                "📦 Stock actualizado: Coca-Cola +50 und.",
                "👤 Cliente nuevo: Juan Pérez registrado",
                "🔄 Producto agregado: Galletas Príncipe",
                "💳 Pago con tarjeta: $123.75"
        };

        String[] times = {"Hace 5 min", "Hace 12 min", "Hace 18 min", "Hace 25 min", "Hace 32 min"};

        for (int i = 0; i < activities.length; i++) {
            JPanel actPanel = new JPanel(new BorderLayout());
            actPanel.setBackground(CARD_COLOR);
            actPanel.setBorder(new EmptyBorder(3, 0, 3, 0));

            JLabel actLabel = createStyledLabel(activities[i], 10, TEXT_COLOR, Font.PLAIN);
            JLabel timeLabel = createStyledLabel(times[i], 9, new Color(107, 114, 128), Font.PLAIN);

            actPanel.add(actLabel, BorderLayout.CENTER);
            actPanel.add(timeLabel, BorderLayout.EAST);

            activitiesList.add(actPanel);
        }

        panel.add(activitiesList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSalesChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);

        JLabel titleLabel = createStyledLabel("📈 Ventas del Día", 14, TEXT_COLOR, Font.BOLD);
        JLabel totalLabel = createStyledLabel("Total: $1,250.00", 12, PRIMARY_COLOR, Font.BOLD);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(totalLabel, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Gráfico de barras simple
        JPanel chartPanel = createSimpleBarChart();
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSimpleBarChart() {
        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight() - 20;
                int barWidth = width / 12;

                // Datos de ejemplo por horas
                int[] sales = {50, 30, 45, 80, 120, 200, 180, 250, 180, 160, 90, 60};
                String[] hours = {"8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h"};

                int maxSale = 250;

                for (int i = 0; i < sales.length; i++) {
                    int barHeight = (sales[i] * height) / maxSale;
                    int x = i * barWidth + 10;
                    int y = height - barHeight + 10;

                    // Gradiente de color
                    Color barColor = new Color(34, 197, 94, 150 + (sales[i] * 105 / maxSale));
                    g2d.setColor(barColor);
                    g2d.fillRect(x, y, barWidth - 5, barHeight);

                    // Hora debajo de cada barra
                    g2d.setColor(new Color(107, 114, 128));
                    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
                    g2d.drawString(hours[i], x + 2, height + 25);
                }
            }
        };

        chart.setBackground(CARD_COLOR);
        chart.setPreferredSize(new Dimension(0, 100));

        return chart;
    }

    private JPanel createBusinessWeatherPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = createStyledLabel("🌤️ Estado del Negocio", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Estado general con emoji grande
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(CARD_COLOR);

        JLabel emojiLabel = new JLabel("😊", SwingConstants.CENTER);
        emojiLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        emojiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = createStyledLabel("Excelente Día", 14, PRIMARY_COLOR, Font.BOLD);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = createStyledLabel("Ventas por encima", 10, new Color(107, 114, 128), Font.PLAIN);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel2 = createStyledLabel("del promedio diario", 10, new Color(107, 114, 128), Font.PLAIN);
        descLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(emojiLabel);
        statusPanel.add(Box.createVerticalStrut(5));
        statusPanel.add(statusLabel);
        statusPanel.add(descLabel);
        statusPanel.add(descLabel2);

        panel.add(statusPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPaymentMethodsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = createStyledLabel("💳 Métodos de Pago", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel methodsList = new JPanel();
        methodsList.setLayout(new BoxLayout(methodsList, BoxLayout.Y_AXIS));
        methodsList.setBackground(CARD_COLOR);

        // Datos de métodos de pago
        String[] methods = {"💵 Efectivo", "💳 Tarjeta", "📱 Transferencia"};
        String[] amounts = {"$780.00", "$350.00", "$120.00"};
        String[] percentages = {"62%", "28%", "10%"};
        Color[] colors = {PRIMARY_COLOR, SECONDARY_COLOR, new Color(147, 51, 234)};

        for (int i = 0; i < methods.length; i++) {
            JPanel methodPanel = new JPanel(new BorderLayout());
            methodPanel.setBackground(CARD_COLOR);
            methodPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

            JLabel methodLabel = createStyledLabel(methods[i], 11, TEXT_COLOR, Font.PLAIN);

            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            rightPanel.setBackground(CARD_COLOR);

            JLabel amountLabel = createStyledLabel(amounts[i], 10, colors[i], Font.BOLD);
            JLabel percentLabel = createStyledLabel("(" + percentages[i] + ")", 9, new Color(107, 114, 128), Font.PLAIN);

            rightPanel.add(amountLabel);
            rightPanel.add(percentLabel);

            methodPanel.add(methodLabel, BorderLayout.WEST);
            methodPanel.add(rightPanel, BorderLayout.EAST);

            methodsList.add(methodPanel);
        }

        panel.add(methodsList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createExpirationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = createStyledLabel("⏰ Próximos Vencimientos", 14, TEXT_COLOR, Font.BOLD);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel expirationList = new JPanel();
        expirationList.setLayout(new BoxLayout(expirationList, BoxLayout.Y_AXIS));
        expirationList.setBackground(CARD_COLOR);

        String[] products = {"🥛 Leche Entera", "🍞 Pan de Molde", "🧀 Queso Fresco", "🥤 Yogurt Natural"};
        String[] dates = {"2 días", "5 días", "1 semana", "10 días"};
        Color[] urgencyColors = {DANGER_COLOR, WARNING_COLOR, SECONDARY_COLOR, PRIMARY_COLOR};

        for (int i = 0; i < products.length; i++) {
            JPanel prodPanel = new JPanel(new BorderLayout());
            prodPanel.setBackground(CARD_COLOR);
            prodPanel.setBorder(new EmptyBorder(3, 0, 3, 0));

            JLabel prodLabel = createStyledLabel(products[i], 10, TEXT_COLOR, Font.PLAIN);
            JLabel dateLabel = createStyledLabel(dates[i], 10, urgencyColors[i], Font.BOLD);

            prodPanel.add(prodLabel, BorderLayout.WEST);
            prodPanel.add(dateLabel, BorderLayout.EAST);

            expirationList.add(prodPanel);
        }

        panel.add(expirationList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(75, 85, 99));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setMaximumSize(new Dimension(sidebarWidth - 10, 50));

        JLabel userLabel = createStyledLabel("👤 Admin", 12, Color.WHITE, Font.BOLD);
        JLabel storeLabel = createStyledLabel("Tienda Principal", 10, new Color(209, 213, 219), Font.PLAIN);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(75, 85, 99));
        textPanel.add(userLabel);
        textPanel.add(storeLabel);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createMenuButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(sidebarWidth - 15, 38));
        button.setPreferredSize(new Dimension(sidebarWidth - 15, 38));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (selected) {
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(SIDEBAR_COLOR);
            button.setForeground(new Color(209, 213, 219));
        }

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selected) {
                    button.setBackground(new Color(75, 85, 99));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!selected) {
                    button.setBackground(SIDEBAR_COLOR);
                }
            }
        });

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("🚪 Cerrar Sesión");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(sidebarWidth - 15, 35));
        button.setPreferredSize(new Dimension(sidebarWidth - 15, 35));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        button.setBackground(DANGER_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private JLabel createStyledLabel(String text, int fontSize, Color color, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, style, fontSize));
        label.setForeground(color);
        return label;
    }

    private void addHoverEffect(JPanel card) {
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }
        });
    }

    private void setupLayout() {
        add(sidebarPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });
    }

    private void handleResize() {
        int width = getWidth();

        // Auto-ocultar sidebar en pantallas muy pequeñas
        if (width < 850 && sidebarVisible) {
            toggleSidebar();
        } else if (width >= 850 && !sidebarVisible) {
            toggleSidebar();
        }
    }

    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebarPanel.setVisible(sidebarVisible);
        revalidate();
        repaint();
    }

    private void switchToModule(String moduleName) {
        JOptionPane.showMessageDialog(this,
                "Abriendo módulo: " + moduleName,
                "POS - Navegación",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void quickSale() {
        JOptionPane.showMessageDialog(this,
                "Abriendo ventana de venta rápida...",
                "Venta Rápida",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Cerrar sesión y salir del sistema?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.setProperty("awt.useSystemAAFontSettings", "on");
                System.setProperty("swing.aatext", "true");

                new ModernDashboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
