import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputFrame extends JFrame {
        private MagicSquareGameUI mainApp;

        public InputFrame(MagicSquareGameUI mainApp) {
            this.mainApp = mainApp;
            setTitle("Enter Dimensions");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(Color.BLACK);

            JTextField inputField = new JTextField();
            inputField.setFont(new Font("Monospaced", Font.PLAIN, 24));
            inputField.setHorizontalAlignment(JTextField.CENTER);
            inputField.setBackground(Color.BLACK);
            inputField.setForeground(Color.WHITE);
            inputField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JButton submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Monospaced", Font.PLAIN, 24));
            submitButton.setBackground(Color.BLACK);
            submitButton.setForeground(Color.WHITE);
            submitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int n = Integer.parseInt(inputField.getText());
                        if (n % 2 != 0 && n > 1) {
                            mainApp.startGame(n);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(InputFrame.this, "Please enter an odd positive number.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(InputFrame.this, "Invalid input. Please enter a valid integer.");
                    }
                }
            });

            JLabel promptLabel = new JLabel("What dimensions do you want the matrix to be?");
            promptLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
            promptLabel.setForeground(Color.WHITE);
            promptLabel.setHorizontalAlignment(JLabel.CENTER);

            add(promptLabel, BorderLayout.NORTH);
            add(inputField, BorderLayout.CENTER);
            add(submitButton, BorderLayout.SOUTH);

            setLocationRelativeTo(null);
        }
    }

