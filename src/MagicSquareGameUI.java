import javax.swing.*;
import java.awt.*;

public class MagicSquareGameUI extends JFrame {
    private int n;
    private int[][] magicArray;
    private MatrixFrame matrixFrame;
    private long startTime;

    public MagicSquareGameUI() {
        setTitle("Magic Square Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        showInputFrame();
    }

    private void showInputFrame() {
        InputFrame inputFrame = new InputFrame(this);
        inputFrame.setVisible(true);
    }

    public void startGame(int n) {
        this.n = n;
        magicArray = MagicSquareGenerator.generateMagicSquare(n);
        MagicSquareGenerator.shuffleMagicSquare(magicArray);
        startTime = System.currentTimeMillis();
        showMatrixFrame();
    }

    private void showMatrixFrame() {
        matrixFrame = new MatrixFrame(this, magicArray);
        matrixFrame.setVisible(true);
    }

    public void resetGame() {
        // Notify the user that the difficulty is being increased
        JOptionPane.showMessageDialog(this, "The difficulty is being increased!");

        // Increment the size of the matrix by 2
        n += 2;
        if (n % 2 == 0) {
            n += 1; // Ensure `n` remains odd
        }

        // Generate the new magic square with the updated size
        magicArray = MagicSquareGenerator.generateMagicSquare(n);
        MagicSquareGenerator.shuffleMagicSquare(magicArray);
        matrixFrame.updateMatrixPanel(magicArray);
    }

    public void onGameCompleted() {
        long endTime = System.currentTimeMillis();
        int solvingTime = (int) ((endTime - startTime) / 1000);
        String playerName = JOptionPane.showInputDialog(this, "You've completed the magic square! Enter your name:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            // Store solving time and player name
            // Display fastest time logic here
            JOptionPane.showMessageDialog(this, "Completed in " + solvingTime + " seconds");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MagicSquareGameUI();
            }
        });
    }
}