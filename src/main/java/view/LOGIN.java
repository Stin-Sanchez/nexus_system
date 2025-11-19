/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author stin-josue
 */
public class LOGIN extends JFrame {

    private JPanel panelMain;
    private JPanel panelLogin;
    private JTextField txtUser, txtPassword;
    private JLabel lblUser, lblPassword, lblLogin;
    private Dimension labelTxtSize;
    private JButton btnLogin;

    public LOGIN(String title) throws HeadlessException {
        super(title);
    }

    public LOGIN() {
        setSize(new Dimension(600, 500));
        setResizable(false);
        setLocationRelativeTo(null);
        this.repaint();


        this.
                labelTxtSize = new Dimension(200, 30);
        setup();
        configurarPanelLOgin();
    }

    private void setup() {
        panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setPreferredSize(this.getPreferredSize());
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(labelTxtSize);
        btnLogin.setHorizontalAlignment(JButton.CENTER);


    }

    private void configurarPanelLOgin() {
        panelLogin = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelLogin.setBorder(new TitledBorder("Ingrese sus credenciales"));
        panelLogin.setPreferredSize(new Dimension(250, 200));
        panelLogin.setBackground(Color.red);
        lblLogin = new JLabel("LOGIN");
        lblLogin.setPreferredSize(labelTxtSize);
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogin.setHorizontalAlignment(JLabel.CENTER);
        lblUser = new JLabel("USER:");
        lblUser.setPreferredSize(labelTxtSize);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 22));
        txtUser = new JTextField();
        txtUser.putClientProperty("JTextField.placeholderText", "Ingrese su usuario");
        txtUser.setPreferredSize(labelTxtSize);
        lblPassword = new JLabel("Password:");
        lblPassword.setPreferredSize(labelTxtSize);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 22));
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(labelTxtSize);
        txtPassword.putClientProperty("JTextField.placeholderText", "Ingrese su usuario");

        panelLogin.add(lblUser);
        panelLogin.add(txtUser);
        panelLogin.add(lblPassword);
        panelLogin.add(txtPassword);
        panelMain.add(lblLogin);
        panelMain.add(panelLogin);
        panelMain.add(btnLogin);
        add(panelMain);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                    new LOGIN().setVisible(true);
                }
        );
    }


}
