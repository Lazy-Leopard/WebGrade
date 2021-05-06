package simpsanghatan.dbmsproject;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import sqlhelper.Queries;
import sqlhelper.settings;
import static utils.IOUtils.getIcon;
import utils.ImageUtil;

public class LoginPage extends JPanel {

  private JPasswordField passwordField;
  private JTextField usernameField;
  private JButton btnSignUp;
  private JLabel lblLogin;
  private JLabel lblUsername;
  private JLabel label;
  private JLabel lblPassword;
  private JButton btnGo;
  private JLabel logoLabel;

  /**
   * Create the frame.
   */
  public LoginPage() {
    Border border = BorderFactory.createTitledBorder("Login");
    setBorder(border);
    setBackground(Color.WHITE);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    createButtons();
    createLabels();
    createFields();

    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    this.add(lblLogin, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 1;
    this.add(lblUsername, gbc);
    gbc.gridx++;
    this.add(usernameField, gbc);

    gbc.gridx++;
    gbc.gridheight = 2;
    this.add(logoLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridheight = 1;
    this.add(lblPassword, gbc);
    gbc.gridx++;
    this.add(passwordField, gbc);

    gbc.gridy++;
    this.add(btnGo, gbc);

    gbc.gridy++;
    this.add(btnSignUp, gbc);
  }

  private void createButtons() {
    btnSignUp = new JButton("Sign Up");
    btnSignUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        maingui.getInstance().replacePanel(new SignUp());
      }
    });

    btnGo = new JButton("Go");
    btnGo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
          return;
        }
        int id = Queries.checkUser(usernameField.getText(), passwordField.getText());
        if (id > 0) {
          settings.userId = id;
          //TODO
          if (Queries.isAdmin(id)) {
            maingui.getInstance().replacePanel(new AdminFront());
          } else {
            maingui.getInstance().replacePanel(new UserFront());
          }
          //TODO
        } else {
          JOptionPane.showMessageDialog(null, "Unsuccessful login! \n Please try again");
        }
      }
    });
  }

  private void createLabels() {
    lblLogin = new JLabel("Login");
    lblLogin.setForeground(new Color(210, 105, 30));
    lblLogin.setFont(new Font("Agency FB", Font.BOLD, 35));

    logoLabel = new JLabel("");
    logoLabel.setIcon(ImageUtil.scaleImageIcon(getIcon("logo.png"), 150));

    lblUsername = new JLabel("Username");
    lblUsername.setForeground(new Color(139, 0, 0));
    lblUsername.setFont(new Font("Agency FB", Font.PLAIN, 30));

    lblPassword = new JLabel("Password");
    lblPassword.setFont(new Font("Agency FB", Font.PLAIN, 30));
    lblPassword.setForeground(new Color(139, 0, 0));
  }

  private void createFields() {
    usernameField = new JTextField(10);
    passwordField = new JPasswordField(10);
  }

  public static void main(String[] args) {
    settings.user = "root";
    settings.pass = "stcdalex";
    maingui.getInstance().replacePanel(new LoginPage());
  }
}
