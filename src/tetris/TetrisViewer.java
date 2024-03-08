package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisViewer {

    private Board board;

    public TetrisViewer(Board board) {
        this.board = board;
    }

    public void show() {
        StartScreen startScreen = new StartScreen();


        // Create and set up frame
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setTitle("Tetris");
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Show startup image for a bit
        frame.add(startScreen);
        frame.setVisible(true);

        // Wait 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now remove the image from the frame
        frame.remove(startScreen);

        int height = board.getHeight();
        int width = board.getWidth();

        TetrisComponent tetris = new TetrisComponent(board);
        board.addBoardListener(tetris);

        // Handle the UI (buttons, score etc)
        Ui ui = new Ui(board);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(ui.getPauseButton());
        buttonPanel.add(ui.getExitButton());

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scorePanel.add(ui.getPointsText());

        // Add and pack everything to the frame
        frame.add(tetris, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        // Create input and action maps
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();

        // Define key bindings for x movement
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        // Define rotation key bindings
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "rotateLeft");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "rotateRight");

        // Add key bindings to ActionMap
        actionMap.put("moveLeft", new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                board.move(Direction.LEFT);
                if (board.hasCollision()) {
                    board.move(Direction.RIGHT);
                }
            }
        });

        actionMap.put("moveRight", new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                board.move(Direction.RIGHT);
                if (board.hasCollision()) {
                    board.move(Direction.LEFT);
                }
            }
        });

        actionMap.put("rotateLeft", new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                board.move(Direction.ROTATE_LEFT);
            }
        });

        actionMap.put("rotateRight", new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                board.move(Direction.ROTATE_RIGHT);
            }
        });


        final Action updateBoard = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                if (!board.getIsGameOver() && !board.getIsPaused()) {

                    board.tick();
                    ui.updatePointsText();
                }
            }
        };

        Timer timer = new Timer(500, updateBoard);
        timer.setCoalesce(true);
        timer.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}