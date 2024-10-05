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
    private JLabel playerXLabel;
    private JLabel playerOLabel;
    private JButton startOverButton;
    public TicTacToe() {a
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 550); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        playerXName = JOptionPane.showInputDialog("Enter name for Player 1 (X):");
        playerOName = JOptionPane.showInputDialog("Enter name for Player 2 (O):");
        playerXChar = JOptionPane.showInputDialog(playerXName + ", choose your character (default is 'X'):");
        if (playerXChar == null || playerXChar.trim().isEmpty()) {
            playerXChar = "X";  
        }

        playerOChar = JOptionPane.showInputDialog(playerOName + ", choose your character (default is 'O'):");
        if (playerOChar == null || playerOChar.trim().isEmpty()) {
            playerOChar = "O";  
        }
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(""); 
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);  
                gamePanel.add(buttons[row][col]); 
            }
        }

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new GridLayout(3, 1)); 
        playerXLabel = new JLabel(playerXName + " (" + playerXChar + "): " + playerXWins + " Wins");
        playerOLabel = new JLabel(playerOName + " (" + playerOChar + "): " + playerOWins + " Wins");
        leaderboardPanel.add(playerXLabel);
        leaderboardPanel.add(playerOLabel);
        startOverButton = new JButton("Start Over");
        startOverButton.setFont(new Font("Arial", Font.BOLD, 20));
        startOverButton.addActionListener(e -> resetGame());
        leaderboardPanel.add(startOverButton);
        add(gamePanel, BorderLayout.CENTER);
        add(leaderboardPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (!clickedButton.getText().equals("")) {
            return;
        }
        if (playerXTurn) {
            clickedButton.setText(playerXChar);
        } else {
            clickedButton.setText(playerOChar);
        }
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
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }
            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }
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
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
    }

    private void resetGame() {
        resetBoard();
        playerXWins = 0;
        playerOWins = 0;
        playerXLabel.setText(playerXName + " (" + playerXChar + "): " + playerXWins + " Wins");
        playerOLabel.setText(playerOName + " (" + playerOChar + "): " + playerOWins + " Wins");
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.setVisible(true); 
    }
}
