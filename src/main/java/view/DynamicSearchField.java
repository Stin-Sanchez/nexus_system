package view;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;
import java.util.function.Consumer;
public class DynamicSearchField<T> extends JPanel {
    private JTextField searchField;
    private JPopupMenu popup;
    private JList<T> itemList;
    private DefaultListModel<T> listModel;
    private Function<String, List<T>> searchFunction;
    private Consumer<T> onItemSelected;
    private Function<T, String> displayFunction;
    private Function<T, ImageIcon> iconFunction;
    private List<T> allItems;

    public DynamicSearchField(String placeholder,
                              Function<String, List<T>> searchFunction,
                              Consumer<T> onItemSelected,
                              Function<T, String> displayFunction,
                              Function<T, ImageIcon> iconFunction) {

        this.searchFunction = searchFunction;
        this.onItemSelected = onItemSelected;
        this.displayFunction = displayFunction;
        this.iconFunction = iconFunction;

        initComponents(placeholder);
        setupListeners();
    }

    private void initComponents(String placeholder) {
        setLayout(new BorderLayout());

        // Campo de búsqueda
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 35));

        // Placeholder
        searchField.putClientProperty("JTextField.placeholderText", placeholder);

        // Crear popup
        popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        popup.setFocusable(false);

        // Lista para resultados
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setCellRenderer(new SearchResultRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setFocusable(false);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setFocusable(false);
        popup.add(scrollPane);

        add(searchField, BorderLayout.CENTER);
    }

    private void setupListeners() {
        // Document listener para búsqueda en tiempo real
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { performSearch(); }
            @Override
            public void removeUpdate(DocumentEvent e) { performSearch(); }
            @Override
            public void changedUpdate(DocumentEvent e) { performSearch(); }
        });

        // Key listener para navegación
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (popup.isVisible()) {
                    int selectedIndex = itemList.getSelectedIndex();

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            if (selectedIndex < listModel.getSize() - 1) {
                                itemList.setSelectedIndex(selectedIndex + 1);
                            }
                            e.consume();
                            break;

                        case KeyEvent.VK_UP:
                            if (selectedIndex > 0) {
                                itemList.setSelectedIndex(selectedIndex - 1);
                            }
                            e.consume();
                            break;

                        case KeyEvent.VK_ENTER:
                            if (selectedIndex >= 0) {
                                selectItem(listModel.getElementAt(selectedIndex));
                            }
                            e.consume();
                            break;

                        case KeyEvent.VK_ESCAPE:
                            popup.setVisible(false);
                            e.consume();
                            break;
                    }
                }
            }

            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyTyped(KeyEvent e) {}
        });

        // Mouse listener
        itemList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    T selected = itemList.getSelectedValue();
                    if (selected != null) {
                        selectItem(selected);
                    }
                } else {
                    SwingUtilities.invokeLater(() -> searchField.requestFocus());
                }
            }
        });
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();

        if (searchText.isEmpty()) {
            popup.setVisible(false);
            return;
        }

        // Búsqueda asíncrona para no bloquear la UI
        SwingUtilities.invokeLater(() -> {
            List<T> results = searchFunction.apply(searchText);

            listModel.clear();
            for (T item : results) {
                listModel.addElement(item);
            }

            if (listModel.getSize() > 0) {
                if (!popup.isVisible()) {
                    popup.show(searchField, 0, searchField.getHeight());
                }
                itemList.setSelectedIndex(0);

                SwingUtilities.invokeLater(() -> searchField.requestFocus());
            } else {
                popup.setVisible(false);
            }
        });
    }

    private void selectItem(T item) {
        searchField.setText(displayFunction.apply(item));
        popup.setVisible(false);

        SwingUtilities.invokeLater(() -> {
            searchField.requestFocus();
            searchField.setCaretPosition(searchField.getText().length());
        });

        if (onItemSelected != null) {
            onItemSelected.accept(item);
        }
    }

    public void clearSearch() {
        searchField.setText("");
        popup.setVisible(false);
    }

    public String getText() {
        return searchField.getText();
    }

    public void setText(String text) {
        searchField.setText(text);
    }

    public JTextField getTextField() {
        return searchField;
    }

    // Renderer personalizado
    private class SearchResultRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value != null) {
                @SuppressWarnings("unchecked")
                T item = (T) value;

                setText(displayFunction.apply(item));

                if (iconFunction != null) {
                    setIcon(iconFunction.apply(item));
                } else {
                    setIcon(createDefaultIcon());
                }

                setVerticalTextPosition(CENTER);
                setHorizontalTextPosition(RIGHT);
            }

            if (isSelected) {
                setBackground(new Color(184, 207, 229));
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            return this;
        }
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(2, 2, 28, 28, 6, 6);
        g2d.setColor(Color.GRAY);
        g2d.drawRoundRect(2, 2, 28, 28, 6, 6);

        g2d.dispose();
        return new ImageIcon(img);
    }
}
