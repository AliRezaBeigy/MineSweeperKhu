package minesweeperkhu;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Print implements Serializable {

    MinesweeperCore core;
    String str = "";
    int TotalGames;
    int Wins;
    int Losses;
    File InCompleteFile = new File("_incomplete_game_log.txt");
    String time;
    String SaveGame;
    String previousSave = "";
    String SaveForPrint = "";

    Print(MinesweeperCore minesweeperCore) {
        core = minesweeperCore;
    }

    //Called In End of Each Game
    void printResultInFile(Status state, GameFrame gui) {
        previousSave = "";
        SetTime(gui.getTimeLeft());
        ReadSaveFile();
        SetSaveGame();
        if (state == Status.WIN) {
            str += "\r\n" + core.playerName + " Win ...";
            //for summary
            printResultInFile2();
            Wins++;
        } else if (state == Status.LOSE) {
            str += "\r\n" + core.playerName + " Lost ...";
            printResultInFile2();
            Losses++;
        }
        TotalGames++;
        SetSaveGame();
        printInFile();
        SaveForPrint = str + SaveForPrint;
        str = "";
    }

    void printInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(core.playerName + "_game_log.txt"), false))) {
            writer.write(SaveGame + str + previousSave);
        } catch (IOException ex) {
        }
    }

    void printResult() {
        System.out.print(SaveGame + SaveForPrint);
    }

    void printResultInFile2() {
        str += "\r\n\r\n";
        for (int Line = 3; Line < core.height * 2 + 4; Line++) {
            printBoardInFile(Line);
            str += "\r\n";                                // Next Line
        }
        str += "\r\n\r\n";
        str += "Boombs = " + core.mines + "     |     ";
        str += "Time = " + time + "     |     ";
        str += "End on Date = " + new Date(System.currentTimeMillis());
        str += "\r\n";
        str += SeparatorLine();
        str += "\r\n\r\n";                                 // Empty Line
    }

    void printBoardInFile(int Line) {
        for (int Column = 1; Column < core.width * 6 + 2; Column++) {
            if (Line % 2 == 1) {
                str += "~";
            } else {
                if (Column % 6 == 1) {
                    str += "|";
                }
                if (Column % 6 == 3) {
                    if (core.board[Line / 2 - 2][Column / 6] > 0) {
                        str += "  " + core.board[Line / 2 - 2][Column / 6] + "  ";
                    } else if (core.board[Line / 2 - 2][Column / 6] == 0) {
                        str += "     ";
                    } else if (core.board[Line / 2 - 2][Column / 6] == -1) {
                        str += "  @  ";
                    }
                }
            }
        }
    }

    String SeparatorLine() {
        String separatorLine = "";
        for (int i = 0; i < 100; i++) {
            separatorLine += "_";
        }
        return separatorLine;
    }

    void ReadSaveFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(core.playerName + "_game_log.txt")))) {

            // Frst Line
            reader.readLine();
            // Second Line
            String[] sa = reader.readLine().split(" ");

            TotalGames = Integer.parseInt(sa[3]);
            Wins = Integer.parseInt(sa[15]);
            Losses = Integer.parseInt(sa[27]);

            // Cross 2 Lines
            reader.readLine();
            reader.readLine();

            String pS;
            while ((pS = reader.readLine()) != null) {
                previousSave += pS;
                previousSave += "\r\n";
            }
        } catch (IOException | java.lang.NumberFormatException | java.lang.NullPointerException ex) {
        }
    }

    int getTotalGames() {
        ReadSaveFile();
        return TotalGames;
    }

    int getWins() {
        ReadSaveFile();
        return Wins;
    }

    int getLosses() {
        ReadSaveFile();
        return Losses;
    }

    void SetTime(int timeLeft) {
        time = String.format("%02d : %02d : %02d", timeLeft / 3600, (timeLeft - timeLeft / 3600) / 60, timeLeft - timeLeft / 3600 - timeLeft / 60);
    }

    void SetSaveGame() {
        SaveGame = "Player Name = " + core.playerName + "\r\n";
        SaveGame += "Total Games = " + TotalGames + "     |     ";
        SaveGame += "Wins = " + Wins + "     |     ";
        SaveGame += "Losses = " + Losses;
        SaveGame += "\r\n" + SeparatorLine() + "\r\n" + SeparatorLine() + "\r\n\r\n";
    }

    void WritePlayerObject() {
        ArrayList<MinesweeperCore> saves = ReadPlayerObject();
        InCompleteFile.delete();
        try (ObjectOutputStream write_Obj = new ObjectOutputStream(new FileOutputStream(InCompleteFile, true))) {
            saves.add(core);
            write_Obj.writeObject(saves);
        } catch (IOException ex) {
            ArrayList<MinesweeperCore> minafesweeperCore = new ArrayList<>();
        }
    }

    ArrayList<MinesweeperCore> ReadPlayerObject() {
        ArrayList<MinesweeperCore> minesweeperCore = new ArrayList<>();
        try (ObjectInputStream read_Obj = new ObjectInputStream(new FileInputStream(InCompleteFile))) {
            while (true) {
                minesweeperCore = (ArrayList<MinesweeperCore>) read_Obj.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ArrayList<MinesweeperCore> minafesweeperCore = new ArrayList<>();
        }

        return minesweeperCore;
    }
}
