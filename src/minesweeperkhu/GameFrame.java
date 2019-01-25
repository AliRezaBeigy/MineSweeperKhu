package minesweeperkhu;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EtchedBorder;

public class GameFrame extends JFrame {

    MinesweeperCore minesweeperCore;

    private int windows_width;
    private int windows_height;

    private Icon mine;
    private Icon flag;
    private JMenuItem exit;
    private JMenu gameMenu;
    private JPanel mineBoard;
    private Icon placeholder;
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private JMenuItem newGame;
    private JLabel background;
    private JPanel footerPanel;
    private JButton[][] buttons;
    private JLabel mineIconLabel;
    private JLabel timeLeftLabel;
    private JPanel timeLeftPanel;
    private JLabel clockIconLabel;
    private JPanel minesCountPanel;
    private JLabel minesCountLabel;

    private final int columns;
    private final int rows;
    private int mines;

    private Thread timer;
    private int timeLeft;
    private boolean timerStop;

    private void InitializeForm() {
        mainPanel = new JPanel();
        menuBar = new JMenuBar();
        mineBoard = new JPanel();
        footerPanel = new JPanel();
        exit = new JMenuItem("Exit");
        gameMenu = new JMenu("Game");
        timeLeftPanel = new JPanel();
        minesCountPanel = new JPanel();
        newGame = new JMenuItem("New Game");
        buttons = new JButton[columns][rows];
        mineIconLabel = new JLabel("", SwingConstants.CENTER);
        clockIconLabel = new JLabel("", SwingConstants.CENTER);
        timeLeftLabel = new JLabel("0", SwingConstants.CENTER);
        minesCountLabel = new JLabel("0", SwingConstants.CENTER);
        background = new JLabel(new ImageIcon(getClass().getResource("/resources/background.jpg")));

        setTitle("Mine Sweeper");
        windows_width = minesweeperCore.width * 50;
        windows_height = minesweeperCore.height * 50 + 100;
        setSize(windows_width, windows_height);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/mine.png")));
    }

    public GameFrame(MinesweeperCore minesweeperCore) {
        this.minesweeperCore = minesweeperCore;
        rows = minesweeperCore.height;
        mines = minesweeperCore.mines;
        columns = minesweeperCore.width;
        timeLeft = 0;
        timerStop = true;
        InitializeForm();

        mineBoard.setLayout(new GridLayout(rows, columns, 0, 0));
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                buttons[x][y] = new JButton("");
                buttons[x][y].setName(Integer.toString(x) + ":" + Integer.toString(y));
                buttons[x][y].setFont(new Font("Serif", Font.BOLD, 24));
                buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
                mineBoard.add(buttons[x][y]);
            }
        }

        timeLeftPanel.setLayout(new BorderLayout(5, 0));
        timeLeftLabel.setFont(new Font("Serif", Font.BOLD, 20));
        timeLeftLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        timeLeftLabel.setBackground(new Color(204, 223, 255));
        timeLeftLabel.setForeground(Color.BLACK);
        timeLeftLabel.setOpaque(true);
        clockIconLabel.setIcon(new ImageIcon(getClass().getResource("/resources/clock.png")));
        timeLeftPanel.add(clockIconLabel, BorderLayout.WEST);
        timeLeftPanel.add(timeLeftLabel, BorderLayout.CENTER);
        timeLeftPanel.setOpaque(false);

        minesCountLabel.setFont(new Font("Serif", Font.BOLD, 20));
        minesCountLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        minesCountLabel.setBackground(new Color(204, 223, 255));
        minesCountLabel.setForeground(Color.BLACK);
        minesCountLabel.setOpaque(true);
        minesCountLabel.setText(Integer.toString(minesweeperCore.calculateMines()));
        mineIconLabel.setIcon(new ImageIcon(getClass().getResource("/resources/mine.png")));
        minesCountPanel.setLayout(new BorderLayout(5, 0));
        minesCountPanel.add(minesCountLabel, BorderLayout.CENTER);
        minesCountPanel.add(mineIconLabel, BorderLayout.EAST);
        minesCountPanel.setOpaque(false);

        footerPanel.setLayout(new BorderLayout(0, 5));
        footerPanel.add(timeLeftPanel, BorderLayout.NORTH);
        footerPanel.add(minesCountPanel, BorderLayout.SOUTH);
        footerPanel.setOpaque(false);

        mainPanel.setLayout(new BorderLayout(0, 10));
        mainPanel.add(mineBoard, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        exit.setName("Exit");
        newGame.setName("New Game");

        gameMenu.add(newGame);
        gameMenu.add(exit);
        menuBar.add(gameMenu);

        setLayout(new BorderLayout());
        background.setLayout(new BorderLayout(0, 0));
        background.add(menuBar, BorderLayout.NORTH);
        background.add(mainPanel, BorderLayout.CENTER);
        add(background);
    }

    public void updateColor(JButton b) {
        switch (b.getText()) {
            case "1":
                b.setForeground(Color.blue);
                break;
            case "2":
                b.setForeground(new Color(76, 153, 0));
                break;
            case "3":
                b.setForeground(Color.red);
                break;
            case "4":
                b.setForeground(new Color(153, 0, 0));
                break;
            case "5":
                b.setForeground(new Color(153, 0, 153));
                break;
            case "6":
                b.setForeground(new Color(96, 96, 96));
                break;
            case "7":
                b.setForeground(new Color(0, 0, 102));
                break;
            case "8":
                b.setForeground(new Color(153, 0, 76));
                break;
            default:
                break;
        }
    }

    public void startTimer() {
        timerStop = false;
        timer = new Thread() {
            @Override
            public void run() {
                while (!timerStop) {
                    timeLeft++;
                    timeLeftLabel.setText("  " + timeLeft + "  ");
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        timer.start();
    }

    public void interruptTimer() {
        timerStop = true;
        try {
            if (timer != null) {
                timer.join();
            }
        } catch (InterruptedException ex) {
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void resetButton() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                buttons[x][y].setText("");
                buttons[x][y].setBackground(new Color(0, 103, 200));
                buttons[x][y].setIcon(placeholder);
            }
        }
    }

    public void setButtonListeners(Game game) {
        addWindowListener(game);

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                buttons[x][y].addMouseListener(game);
            }
        }

        newGame.addActionListener(game);
        exit.addActionListener(game);
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public void setMines(int m) {
    }

    public void increaseMines() {
        mines++;
        minesCountLabel.setText(Integer.toString(mines));
    }

    public void decreaseMines() {
        mines--;
        minesCountLabel.setText(Integer.toString(mines));
    }

    private static Icon Scaled(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }

    public void setIcons() {
        int bOffset = buttons[0][1].getInsets().left;
        int bWidth = buttons[0][1].getWidth();
        int bHeight = buttons[0][1].getHeight();
        mine = Scaled(new ImageIcon(getClass().getResource("/resources/mine.png")), bWidth - bOffset, bHeight - bOffset);
        flag = Scaled(new ImageIcon(getClass().getResource("/resources/flag.png")), bWidth - bOffset, bHeight - bOffset);
        placeholder = Scaled(new ImageIcon(getClass().getResource("/resources/placeholder.png")), bWidth - bOffset, bHeight - bOffset);
    }

    public Icon getIconMine() {
        return mine;
    }

    public Icon getIconFlag() {
        return flag;
    }

    public Icon getIconTile() {
        return placeholder;
    }
}
