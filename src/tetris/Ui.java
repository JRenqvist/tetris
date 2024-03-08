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
	exitButton = new JButton("Avsluta");
	exitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		board.setIsPaused(true);


		int choice = JOptionPane.showConfirmDialog(null, "Vill du avsluta?",
							   "Avsluta", JOptionPane.YES_NO_OPTION);

		if (choice == JOptionPane.YES_OPTION) {

		    // Ask for name if points are in top 3
		    if (board.getPoints() > board.getHighscoreList().getLowestScore()) {
			String name = JOptionPane.showInputDialog("Du fick ett nytt highscore!\nAnge ditt namn");
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
	pauseButton = new JButton("Pausa");
	pauseButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		if (board.getIsPaused()) {
		    board.setIsPaused(false);
		    pauseButton.setText("Pausa");
		} else {
		    board.setIsPaused(true);
		    pauseButton.setText("Börja");
		}
	    }
	});
	pauseButton.setPreferredSize(new Dimension(90, 25));

	// Add points display as JTextArea
	pointsText = new JTextArea();
	pointsText.setEditable(false);
	pointsText.setText("Poäng: " + board.getPoints());
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
	pointsText.setText("Poäng: " + board.getPoints());
    }
}
