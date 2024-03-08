package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ui
{

    private Board board;
    private JButton exitButton;
    private JButton pauseButton;
    private JTextArea pointsText;

    public Ui(Board board) {
	this.board = board;
	// Add exit button
	exitButton = new JButton("Exit");
	exitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		board.setIsPaused(true);


		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
							   "Exit", JOptionPane.YES_NO_OPTION);

		if (choice == JOptionPane.YES_OPTION) {

		    // Ask for name if points are in top 3
		    if (board.getPoints() > board.getHighscoreList().getLowestScore()) {
			String name = JOptionPane.showInputDialog("You got a new highscore!\nEnter your name");
			if (name.isEmpty()) {
			    name = "No Name";
			}
			board.getHighscoreList().addScore(new ScoreEntry(board.getPoints(), name));
		    }

		    System.exit(0);
		}
		board.setIsPaused(false);
	    }
	});
	exitButton.setPreferredSize(new Dimension(90, 25));

	// Add pause button
	pauseButton = new JButton("Pause");
	pauseButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		if (board.getIsPaused()) {
		    board.setIsPaused(false);
		    pauseButton.setText("Pause");
		} else {
		    board.setIsPaused(true);
		    pauseButton.setText("Start");
		}
	    }
	});
	pauseButton.setPreferredSize(new Dimension(90, 25));

	// Add points display as JTextArea
	pointsText = new JTextArea();
	pointsText.setEditable(false);
	pointsText.setText("Points: " + board.getPoints());
	pointsText.setFont(new Font("Monospaced", Font.PLAIN, 15));




    }




    public JButton getExitButton() {
	return exitButton;
    }

    public JButton getPauseButton() {
	return pauseButton;
    }
    public JTextArea getPointsText() {
	return pointsText;
    }

    public void updatePointsText() {
	pointsText.setText("Points: " + board.getPoints());
    }
}
