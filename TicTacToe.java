package T3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerXTurn = true;
    private String playerXName, playerOName;
    private String playerXChar = "X", playerOChar = "O";
    private int playerXWins = 0, playerOWins = 0;
    
    // Labels for displaying the leaderboard
    private JLabel playerXLabel;
    private JLabel playerOLabel;
    
    // Start Over button
    private JButton startOverButton;

    public TicTacToe() {
        // Set up the JFrame
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 550);  // Adjusted height to fit Start Over button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Prompt players for names
        playerXName = JOptionPane.showInputDialog("Enter name for Player 1 (X):");
        playerOName = JOptionPane.showInputDialog("Enter name for Player 2 (O):");

        // Prompt players to choose characters
        playerXChar = JOptionPane.showInputDialog(playerXName + ", choose your character (default is 'X'):");
        if (playerXChar == null || playerXChar.trim().isEmpty()) {
            playerXChar = "X";  // Default to X if nothing is entered
        }

        playerOChar = JOptionPane.showInputDialog(playerOName + ", choose your character (default is 'O'):");
        if (playerOChar == null || playerOChar.trim().isEmpty()) {
            playerOChar = "O";  // Default to O if nothing is entered
        }

        // Initialize the main game panel (3x3 grid)
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");  // Empty buttons
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);  // Attach listener
                gamePanel.add(buttons[row][col]);  // Add button to the grid
            }
        }

        // Initialize the leaderboard
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new GridLayout(3, 1));  // Adjusted for 3 rows
        playerXLabel = new JLabel(playerXName + " (" + playerXChar + "): " + playerXWins + " Wins");
        playerOLabel = new JLabel(playerOName + " (" + playerOChar + "): " + playerOWins + " Wins");
        leaderboardPanel.add(playerXLabel);
        leaderboardPanel.add(playerOLabel);

        // Add "Start Over" button to reset the game
        startOverButton = new JButton("Start Over");
        startOverButton.setFont(new Font("Arial", Font.BOLD, 20));
        startOverButton.addActionListener(e -> resetGame());
        leaderboardPanel.add(startOverButton);  // Add the button to the leaderboard panel

        // Add the game panel and leaderboard panel to the frame
        add(gamePanel, BorderLayout.CENTER);
        add(leaderboardPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // If button is already clicked, do nothing
        if (!clickedButton.getText().equals("")) {
            return;
        }

        // Mark the button with the current player's character
        if (playerXTurn) {
            clickedButton.setText(playerXChar);
        } else {
            clickedButton.setText(playerOChar);
        }

        // Check for a win or draw
        if (checkForWin()) {
            if (playerXTurn) {
                JOptionPane.showMessageDialog(this, playerXName + " wins!");
                playerXWins++;
                playerXLabel.setText(playerXName + " (" + playerXChar + "): " + playerXWins + " Wins");
            } else {
                JOptionPane.showMessageDialog(this, playerOName + " wins!");
                playerOWins++;
                playerOLabel.setText(playerOName + " (" + playerOChar + "): " + playerOWins + " Wins");
            }
            resetBoard();
        } else if (checkForDraw()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
        }

        // Switch turn
        playerXTurn = !playerXTurn;
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }

            // Check columns
            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }

        // Check diagonals
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) {
            return true;
        }

        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) {
            return true;
        }

        return false;
    }

    private boolean checkForDraw() {
        // Check if all buttons are clicked and no win
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        // Reset all buttons to empty for a new game
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
         // Player X starts the next game
    }

    private void resetGame() {
        // Resets the game completely, including scores and the board
        resetBoard();
        playerXWins = 0;
        playerOWins = 0;
        playerXLabel.setText(playerXName + " (" + playerXChar + "): " + playerXWins + " Wins");
        playerOLabel.setText(playerOName + " (" + playerOChar + "): " + playerOWins + " Wins");
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.setVisible(true);  // Make the game window visible
    }
}
