import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MatrixFrame extends JFrame {
    private MagicSquareGameUI mainApp;
    private int[][] magicArray;
    private JPanel matrixPanel;
    private JLabel selectedCell = null;

    public MatrixFrame(MagicSquareGameUI mainApp, int[][] magicArray) {
        this.mainApp = mainApp;
        this.magicArray = magicArray;

        setTitle("Magic Square");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        matrixPanel = new JPanel(new GridLayout(magicArray.length, magicArray.length));
        matrixPanel.setBackground(Color.BLACK);
        updateMatrixPanel(magicArray);

        add(matrixPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void highlightCells(JLabel cell) {
        int borderThickness = 3; // Set the desired border thickness
        for (Component comp : matrixPanel.getComponents()) {
            JLabel label = (JLabel) comp;
            label.setBorder(new LineBorder(Color.WHITE, borderThickness));
            label.putClientProperty("borderColor", Color.WHITE);
        }

        cell.setBorder(new LineBorder(Color.GREEN, borderThickness));
        cell.putClientProperty("borderColor", Color.GREEN);

        int i = -1, j = -1;
        for (int x = 0; x < magicArray.length; x++) {
            for (int y = 0; y < magicArray.length; y++) {
                if (matrixPanel.getComponent(magicArray.length * x + y) == cell) {
                    i = x;
                    j = y;
                    break;
                }
            }
            if (i != -1 && j != -1) break;
        }

        if (i > 0) {
            JLabel adjacentCell = (JLabel) matrixPanel.getComponent(magicArray.length * (i - 1) + j);
            adjacentCell.setBorder(new LineBorder(Color.RED, borderThickness));
            adjacentCell.putClientProperty("borderColor", Color.RED);
        }
        if (i < magicArray.length - 1) {
            JLabel adjacentCell = (JLabel) matrixPanel.getComponent(magicArray.length * (i + 1) + j);
            adjacentCell.setBorder(new LineBorder(Color.RED, borderThickness));
            adjacentCell.putClientProperty("borderColor", Color.RED);
        }
        if (j > 0) {
            JLabel adjacentCell = (JLabel) matrixPanel.getComponent(magicArray.length * i + (j - 1));
            adjacentCell.setBorder(new LineBorder(Color.RED, borderThickness));
            adjacentCell.putClientProperty("borderColor", Color.RED);
        }
        if (j < magicArray.length - 1) {
            JLabel adjacentCell = (JLabel) matrixPanel.getComponent(magicArray.length * i + (j + 1));
            adjacentCell.setBorder(new LineBorder(Color.RED, borderThickness));
            adjacentCell.putClientProperty("borderColor", Color.RED);
        }
    }

    private void swapCells(JLabel cell1, JLabel cell2) {
        int i1 = -1, j1 = -1, i2 = -1, j2 = -1;

        for (int x = 0; x < magicArray.length; x++) {
            for (int y = 0; y < magicArray.length; y++) {
                if (matrixPanel.getComponent(magicArray.length * x + y) == cell1) {
                    i1 = x;
                    j1 = y;
                }
                if (matrixPanel.getComponent(magicArray.length * x + y) == cell2) {
                    i2 = x;
                    j2 = y;
                }
                if (i1 != -1 && j1 != -1 && i2 != -1 && j2 != -1) break;
            }
            if (i1 != -1 && j1 != -1 && i2 != -1 && j2 != -1) break;
        }

        MagicSquareGenerator.swap(magicArray, i1, j1, i2, j2);
        updateMatrixPanel(magicArray);
        checkIfCompleted();
    }

    public void updateMatrixPanel(int[][] magicArray) {
        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(magicArray.length, magicArray.length));
        for (int i = 0; i < magicArray.length; i++) {
            for (int j = 0; j < magicArray.length; j++) {
                JLabel cell = new JLabel(String.valueOf(magicArray[i][j]), SwingConstants.CENTER);
                cell.setFont(new Font("Monospaced", Font.PLAIN, 24));
                cell.setForeground(Color.WHITE);
                cell.setBackground(Color.BLACK);
                cell.setOpaque(true);
                cell.setBorder(new LineBorder(Color.WHITE, 3)); // Set the default border thickness
                cell.putClientProperty("borderColor", Color.WHITE);
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedCell == null) {
                            selectedCell = cell;
                            highlightCells(cell);
                        } else if (selectedCell != cell) {
                            if (Color.RED.equals(cell.getClientProperty("borderColor"))) {
                                swapCells(selectedCell, cell);
                                selectedCell = null;
                            } else {
                                selectedCell = cell;
                                highlightCells(cell);
                            }
                        }
                    }
                });
                matrixPanel.add(cell);
            }
        }
        matrixPanel.revalidate();
        matrixPanel.repaint();
    }

    private void checkIfCompleted() {
        if (MagicSquareGenerator.isValidMagicSquare(magicArray)) {
            int option = JOptionPane.showConfirmDialog(this, "You've completed the magic square! Would you like to try again?", "Completed!", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                mainApp.resetGame();
            }
        }
    }
}
