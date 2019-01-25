/*This world is a lie
    and I will die
    so never mind
    and just smile
 */
package minesweeperkhu;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class MinesweeperCore implements IMinesweeperCore, Serializable {

    Print print;
    String playerName = "";
    int[][] board = new int[35][35];
    int width = 0, height = 0, mines = 0;
    boolean[][] flag = new boolean[35][35];
    boolean[][] visable = new boolean[35][35];
    public Status state = Status.STARTINGPANEL;

    public MinesweeperCore() {
        cleanBoard();
        print = new Print(this);
    }

    private void initBoard() {
        board = new int[height][width];
        flag = new boolean[height][width];
        visable = new boolean[height][width];
        state = Status.GAME;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
        initBoard();
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        initBoard();
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void setMineCount(int count) {
        mines = count;
    }

    @Override
    public int getMineCount() {
        return mines;
    }

    @Override
    public final void cleanBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
                visable[i][j] = false;
                flag[i][j] = false;
            }
        }
    }

    private void setNumbers(int x, int y) {
        if (x > 0 && board[y][x - 1] != -1) {
            board[y][x - 1]++;
        }
        if (x < width - 1 && board[y][x + 1] != -1) {
            board[y][x + 1]++;
        }
        if (y > 0 && board[y - 1][x] != -1) {
            board[y - 1][x]++;
        }
        if (y < height - 1 && board[y + 1][x] != -1) {
            board[y + 1][x]++;
        }
        if (x > 0 && y > 0 && board[y - 1][x - 1] != -1) {
            board[y - 1][x - 1]++;
        }
        if (x > 0 && y < height - 1 && board[y + 1][x - 1] != -1) {
            board[y + 1][x - 1]++;
        }
        if (x < width - 1 && y > 0 && board[y - 1][x + 1] != -1) {
            board[y - 1][x + 1]++;
        }
        if (x < width - 1 && y < height - 1 && board[y + 1][x + 1] != -1) {
            board[y + 1][x + 1]++;
        }
    }

    @Override
    public void setBoard() {
        SecureRandom bombindex = new SecureRandom();
        for (int i = 0; i < mines; i++) {
            int index = bombindex.nextInt(height * width);
            int x = index % width, y = index / width;
            if (board[y][x] != -1) {
                //visable[y][x] = true;
                board[y][x] = -1;
                setNumbers(x, y);
            } else {
                i--;
            }
        }
    }

    private void dfs(int x, int y) {
        if (board[y][x] != -1) {
            visable[y][x] = true;
        }
        if (board[y][x] == 0) {
            if (x > 0 && visable[y][x - 1] == false) {
                dfs(x - 1, y);
            }
            if (x < width - 1 && visable[y][x + 1] == false) {
                dfs(x + 1, y);
            }
            if (y > 0 && visable[y - 1][x] == false) {
                dfs(x, y - 1);
            }
            if (y < height - 1 && visable[y + 1][x] == false) {
                dfs(x, y + 1);
            }
            if (x > 0 && y > 1 && visable[y - 1][x - 1] == false) {
                dfs(x - 1, y - 1);
            }
            if (x > 0 && y < height - 1 && visable[y + 1][x - 1] == false) {
                dfs(x - 1, y + 1);
            }
            if (x < width - 1 && y > 0 && visable[y - 1][x + 1] == false) {
                dfs(x + 1, y - 1);
            }
            if (x < width - 1 && y < height - 1 && visable[y + 1][x + 1] == false) {
                dfs(x + 1, y + 1);
            }
        }
    }

    @Override
    public void updateBoard(int x, int y) {
        if (board[y][x] == -1) {
            if (calculateVisible() < 2) {
                initBoard();
                setBoard();
                dfs(x, y);
            } else {
                state = Status.LOSE;
            }
        } else {
            dfs(x, y);
        }
    }

    private int calculateVisible() {
        int result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (visable[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }
    public int calculateMines() {
        int result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == -1 && flag[i][j] == false) {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public boolean checkWin() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!visable[i][j] && board[i][j] != -1) {
                    return false;
                }
            }
        }
        state = Status.WIN;
        return true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isMine(int x, int y) {
        return board[y][x] == -1;
    }

    @Override
    public int getCellValue(int x, int y) {
        return board[y][x];
    }

    @Override
    public void setFlag(int x, int y) {
        flag[y][x] = !flag[y][x];
    }

    @Override
    public boolean isFlag(int x, int y) {
        return flag[y][x];
    }

    @Override
    public boolean isVisible(int x, int y) {
        return visable[y][x];
    }

    @Override
    public int validX(int x) {
        if (x < 0) {
            x = 0;
        } else if (x > width - 1) {
            x = width - 1;
        }
        return x;
    }

    @Override
    public int validY(int y) {
        if (y < 0) {
            y = 0;
        } else if (y > height - 1) {
            y = height - 1;
        }
        return y;
    }

    @Override
    public Status getStatus() {
        return state;
    }

    void printResultInFile(Status status, GameFrame gui) {
        print.printResultInFile(status, gui);
    }

    void printResult() {
        print.printResult();
    }

    int getTotalGames() {
        return print.getTotalGames();
    }

    int getWins() {
        return print.getWins();
    }

    int getLosses() {
        return print.getLosses();
    }

    void WritePlayerObject() {
        print.WritePlayerObject();
    }

    ArrayList<MinesweeperCore> ReadPlayerObject() {
        return print.ReadPlayerObject();
    }
}
