/*This world is a lie
    and I will die
    so never mind
    and just smile
*/
package minesweeperkhu;

public interface IMinesweeperCore {
    public void setBoard();
    public void cleanBoard();
    public void setWidth(int width);
    public void setHeight(int height);
    public void setFlag(int x, int y);
    public void setMineCount(int count);
    public void updateBoard(int x, int y);
    public void setPlayerName(String playerName);
    public int getWidth();
    public int getHeight();
    public int validX(int x);
    public int validY(int y);
    public int getMineCount();
    public int getCellValue(int x, int y);
    public boolean checkWin();
    public boolean isMine(int x, int y);
    public boolean isFlag(int x, int y);
    public boolean isVisible(int x, int y);
    public Status getStatus();
}
