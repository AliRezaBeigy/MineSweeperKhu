package minesweeperkhu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Game implements MouseListener, ActionListener, WindowListener {

    private MinesweeperCore minesweeperCore;
    public static boolean playing;
    private final GameFrame gui;

    public Game(MinesweeperCore minesweeperCore) {
        this.minesweeperCore = minesweeperCore;
        minesweeperCore.state = Status.GAME;
        gui = new GameFrame(minesweeperCore);
        gui.setButtonListeners(this);
        playing = false;
        gui.setVisible(true);
        gui.setIcons();
        gui.resetButton();
        updateUIBoard();
    }

    private void endGame() {
        minesweeperCore.printResultInFile(minesweeperCore.getStatus(), gui);
        playing = false;
        showAll();
    }

    public void gameWon() {
        gui.interruptTimer();
        endGame();
        JDialog dialog = new JDialog(gui, Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("You won the game!", SwingConstants.CENTER);
        JPanel statistics = new JPanel();
        statistics.setLayout(new GridLayout(5, 1, 0, 10));
        JLabel time = new JLabel("  Time:  " + Integer.toString(gui.getTimeLeft()) + " seconds");
        JLabel TotalGames = new JLabel("  Total games = " + minesweeperCore.getTotalGames());
        JLabel Wins = new JLabel("  Wins = " + minesweeperCore.getWins());
        JLabel Losses = new JLabel("  Losses = " + minesweeperCore.getLosses());
        JLabel JplayerName = new JLabel("  Player name = " + minesweeperCore.playerName);
        statistics.add(JplayerName);
        statistics.add(time);
        statistics.add(TotalGames);
        statistics.add(Wins);
        statistics.add(Losses);
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        statistics.setBorder(loweredetched);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 10, 0));
        JButton exit = new JButton("Exit");
        JButton playAgain = new JButton("Play Again");
        exit.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            minesweeperCore.printResult();
            System.exit(0);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            minesweeperCore.cleanBoard();
            minesweeperCore.setBoard();
            Game game = new Game(minesweeperCore);
            gui.dispose();
        });
        buttons.add(exit);
        buttons.add(playAgain);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(statistics, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);
        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dialog.dispose();
                minesweeperCore.cleanBoard();
                minesweeperCore.setBoard();
                Game game = new Game(minesweeperCore);
                gui.dispose();
            }
        });
        dialog.setTitle("Game Won");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(gui);
        dialog.setVisible(true);
    }

    public void gameLost() {
        gui.interruptTimer();
        endGame();
        JDialog dialog = new JDialog(gui, Dialog.ModalityType.DOCUMENT_MODAL);
        JLabel message = new JLabel("You lost this game.", SwingConstants.CENTER);
        JPanel statistics = new JPanel();
        statistics.setLayout(new GridLayout(5, 1, 0, 10));
        JLabel time = new JLabel("  Time:  " + Integer.toString(gui.getTimeLeft()) + " seconds");
        JLabel TotalGames = new JLabel("  Total games = " + minesweeperCore.getTotalGames());
        JLabel Wins = new JLabel("  Wins = " + minesweeperCore.getWins());
        JLabel Losses = new JLabel("  Losses = " + minesweeperCore.getLosses());
        JLabel JplayerName = new JLabel("  Player name = " + minesweeperCore.playerName);
        statistics.add(JplayerName);
        statistics.add(time);
        statistics.add(TotalGames);
        statistics.add(Wins);
        statistics.add(Losses);
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        statistics.setBorder(loweredetched);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 2, 0));
        JButton exit = new JButton("Exit");
        JButton playAgain = new JButton("Play Again");
        exit.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            minesweeperCore.printResult();
            System.exit(0);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            minesweeperCore.cleanBoard();
            minesweeperCore.setBoard();
            Game game = new Game(minesweeperCore);
            gui.dispose();
        });
        buttons.add(exit);
        buttons.add(playAgain);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(statistics, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);
        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dialog.dispose();
                minesweeperCore.cleanBoard();
                minesweeperCore.setBoard();
                Game game = new Game(minesweeperCore);
                gui.dispose();
            }
        });

        dialog.setTitle("Game Lost");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(gui);
        dialog.setVisible(true);
    }

    public void showScore() {
        JDialog dialog = new JDialog(gui, Dialog.ModalityType.DOCUMENT_MODAL);
        JPanel bestTimes = new JPanel();
        bestTimes.setLayout(new GridLayout(5, 1));

        TitledBorder b = BorderFactory.createTitledBorder("Best Times");
        b.setTitleJustification(TitledBorder.LEFT);
        bestTimes.setBorder(b);
        JPanel statistics = new JPanel();

        statistics.setLayout(new GridLayout(6, 1, 0, 10));

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        statistics.setBorder(loweredetched);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 10, 0));

        JButton close = new JButton("Close");
        JButton reset = new JButton("Reset");

        close.addActionListener((ActionEvent e) -> {
            dialog.dispose();
        });
        reset.addActionListener((ActionEvent e) -> {
            ImageIcon question = new ImageIcon(getClass().getResource("/resources/question.png"));

            int option = JOptionPane.showOptionDialog(null, "Do you want to reset all your statistics to zero?",
                    "Reset Statistics", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, question, null, null);

            switch (option) {
                case JOptionPane.YES_OPTION:
                    dialog.dispose();
                    showScore();
                    break;

                case JOptionPane.NO_OPTION:
                    break;
            }
        });

        buttons.add(close);
        buttons.add(reset);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(bestTimes, BorderLayout.WEST);
        c.add(statistics, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setTitle("Minesweeper Statistics - Haris Muneer");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(gui);
        dialog.setVisible(true);
    }

    private void showAll() {
        JButton buttons[][] = gui.getButtons();
        for (int x = 0; x < minesweeperCore.width; x++) {
            for (int y = 0; y < minesweeperCore.height; y++) {
                int cellSolution = minesweeperCore.getCellValue(x, y);
                if (!minesweeperCore.isVisible(x, y)) {
                    buttons[x][y].setIcon(null);
                    if (minesweeperCore.isMine(x, y)) {
                        buttons[x][y].setIcon(gui.getIconMine());
                    } else {
                        if (cellSolution == 0) {
                            buttons[x][y].setText("");
                        } else {
                            buttons[x][y].setText(Integer.toString(cellSolution));
                            gui.updateColor(buttons[x][y]);
                        }
                    }
                    buttons[x][y].setBackground(Color.GRAY);
                } else if (minesweeperCore.isFlag(x, y)) {
                    if (!minesweeperCore.isMine(x, y)) {
                        buttons[x][y].setBackground(Color.orange);
                    } else {
                        buttons[x][y].setBackground(Color.green);
                    }
                } else if (minesweeperCore.isMine(x, y)) {
                    buttons[x][y].setIcon(gui.getIconMine());
                    buttons[x][y].setBackground(Color.orange);
                }
            }
        }
    }

    private void updateUIBoard() {
        JButton buttons[][] = gui.getButtons();
        for (int x = 0; x < minesweeperCore.getWidth(); x++) {
            for (int y = 0; y < minesweeperCore.getHeight(); y++) {
                int cellSolution = minesweeperCore.getCellValue(x, y);
                if (minesweeperCore.isVisible(x, y) && !minesweeperCore.isMine(x, y)) {
                    buttons[x][y].setIcon(null);
                    if (cellSolution == 0) {
                        buttons[x][y].setText("");
                    } else {
                        buttons[x][y].setText(Integer.toString(cellSolution));
                        gui.updateColor(buttons[x][y]);
                    }
                    buttons[x][y].setBackground(Color.lightGray);
                } else if (minesweeperCore.isFlag(x, y)) {
                    if (!minesweeperCore.isMine(x, y)) {
                        buttons[x][y].setBackground(Color.orange);
                    } else {
                    buttons[x][y].setIcon(gui.getIconFlag());
                        buttons[x][y].setBackground(Color.green);
                    }
                    buttons[x][y].setBackground(Color.lightGray);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        switch (menuItem.getName()) {
            case "New Game":
                minesweeperCore.cleanBoard();
                minesweeperCore.setBoard();
                Game game = new Game(minesweeperCore);
                gui.dispose();
                break;
            case "Exit":
                System.exit(0);
            default:
                showScore();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!playing) {
            gui.startTimer();
            playing = true;
        }

        if (playing) {
            JButton button = (JButton) e.getSource();
            String[] co = button.getName().split(":");

            int x = Integer.parseInt(co[0]);
            int y = Integer.parseInt(co[1]);

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (minesweeperCore.isFlag(x, y)) {
                    return;
                }
            } else if (SwingUtilities.isRightMouseButton(e) && !minesweeperCore.isVisible(x, y)) {
                if (minesweeperCore.isFlag(x, y)) {
                    button.setText("");
                    button.setBackground(new Color(0, 110, 140));
                    button.setIcon(gui.getIconTile());
                    gui.increaseMines();
                } else {
                    button.setBackground(Color.lightGray);
                    button.setIcon(gui.getIconFlag());
                    gui.decreaseMines();
                }
                minesweeperCore.setFlag(x, y);
                return;
            }
            minesweeperCore.updateBoard(x, y);
            updateUIBoard();
            if (minesweeperCore.checkWin()) {
                gameWon();
            }
            if (minesweeperCore.getStatus() == Status.LOSE) {
                gameLost();
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        minesweeperCore.WritePlayerObject();
        System.exit(0);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
