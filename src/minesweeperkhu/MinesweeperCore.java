/*This world is a lie
    and I will die
    so never mind
    and just smile
 */
package minesweeperkhu;

import java.security.SecureRandom;

public class MinesweeperCore implements IMinesweeperCore {

    public static Status state = Status.STARTINGPANEL;

    static int width = 0, height = 0, mines = 0;
    static int[][] board = new int[35][35];
    static boolean[][] visable = new boolean[35][35];
    static boolean[][] flag = new boolean[35][35];
    static String playerName = "player1";

    private void initBoard() {
        board = new int[width][height];
        flag = new boolean[width][height];
        visable = new boolean[width][height];
        state = Status.GAME;
    }

    @Override
    public void setWidth(int width) {
        MinesweeperCore.width = width;
        initBoard();
    }

    @Override
    public void setHeight(int height) {
        MinesweeperCore.height = height;
        initBoard();
    }

    @Override
    public void setPlayerName(String playerName) {
        MinesweeperCore.playerName = playerName;
    }

    @Override
    public void setMineCount(int count) {
        MinesweeperCore.mines = count;
    }

    @Override
    public int getMineCount() {
        return mines;
    }

    @Override
    public void cleanBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
                visable[i][j] = false;
                flag[i][j] = false;
            }
        }
    }

    private void setBomb(int x, int y) {
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
                setBomb(x, y);
            } else {
                i--;
            }
        }
    }

    @Override
    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.printf("%d", board[i][j]);
            }
            System.out.println();
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
    public void updateBoard(int y, int x) {
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (visable[j][i]) {
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
        return board[x][y] == -1;
    }

    @Override
    public int getCellValue(int x, int y) {
        return board[x][y];
    }

    @Override
    public void setFlag(int x, int y) {
        flag[x][y] = !flag[x][y];
    }

    @Override
    public boolean isFlag(int x, int y) {
        return flag[x][y];
    }

    @Override
    public boolean isVisible(int x, int y) {
        return visable[x][y];
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
}
