package minesweeperkhu;

import java.io.* ;
import java.util.Date;
import static minesweeperkhu.MinesweeperCore.height;

public class Print extends MinesweeperCore {
    
    static String str = "" ;
    static int TotalGames ;
    static int Wins ;
    static int Losses ;
    static File file = new File(playerName + "_game_log.txt");
    static File InCompleteFile = new File("_incomplete_game_log.txt");
    static String time ;
    static String SaveGame ;
    static String previousSave = "" ;
    static String SaveForPrint = "" ;
    
    //Called In End of Each Game
    static void printResultInFile(Status state , GameFrame gui){    
        SetTime(gui.getTimeLeft());
        ReadSaveFile();
        SetSaveGame();      
        if(state == Status.WIN){
            str += "\n" + playerName + " is Win ..." ;
            //for summary
            printResultInFile2();
            Wins ++ ;
        }
        else if (state == Status.LOSE){
            str += "\n" + playerName + " is Lost ...";
            printResultInFile2();
            Losses ++ ;
        }
        TotalGames ++ ;       
        SetSaveGame();
        printInFile();
        SaveForPrint = str + SaveForPrint ;
        str = "" ;
        previousSave = "" ;
    }
    
    static void printInFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter( file , false))){         
            writer.write(SaveGame + str + previousSave);
        } 
        catch (IOException ex) {
        }
    }
    
    static void printResult(){      
        System.out.print(SaveGame + SaveForPrint);
    }
    
    static void printResultInFile2 () {
        str += "\r\n\n" ;
        for(int Line = 3 ; Line < height*2 + 4 ; Line++){
            printBoardInFile(Line) ;
            str += "\r\n" ;                                // Next Line
        }
        str += "\r\n\n" ;
        str += "Boombs = " + mines + "     |     " ;
        str += "Time = " + time + "     |     " ;
        str += "End on Date = " + new Date(System.currentTimeMillis()) ;
        str += "\r\n" ;
        str += SeparatorLine() ;
        str += "\r\n\n" ;                                 // Empty Line
    }

    static void printBoardInFile(int Line){
        for(int Column = 1 ; Column < width*6 + 2 ; Column++){
            if(Line%2 == 1) str += "~" ;
            else { 
                if (Column%6==1) str += "|" ;
                if (Column%6==3) {
                    if(board[Line/2-2][Column/6]>0)
                        str += "  " + board[Line/2-2][Column/6] + "  " ;
                    else if (board[Line/2-2][Column/6]==0)
                        str += "     ";
                    else if (board[Line/2-2][Column/6] == -1)
                        str += "  @  " ;
                }
            }
        }
    }
    
    static String SeparatorLine(){
        String separatorLine = "" ;
        for(int i = 0 ; i<100 ; i++){
            separatorLine += "_" ;
        }
        return separatorLine ;
    }
   
    static void ReadSaveFile(){ 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){ 
            
            // Frst Line
            reader.readLine();
            // Second Line
            String [] sa = reader.readLine().split(" ");
            
            TotalGames = Integer.parseInt(sa[3]) ;
            Wins= Integer.parseInt(sa[15]) ;
            Losses = Integer.parseInt(sa[27]) ;
            
            // Cross 2 Lines
            reader.readLine();
            reader.readLine();
            
            String pS ;
            while((pS = reader.readLine()) != null){
                previousSave += pS ;
                previousSave += "\n";
            }
        } 
        catch (IOException | java.lang.NumberFormatException | java.lang.NullPointerException ex) {
        }
    }
    
    static int getTotalGames(){
        ReadSaveFile();
        return TotalGames ;
    }
    static int getWins(){
        ReadSaveFile();
       return Wins ;
    }
    static int getLosses(){
        ReadSaveFile();
        return Losses ;
    }
    
    static void SetTime(int timeLeft){
        time = String.format("%02d : %02d : %02d",timeLeft/3600 , (timeLeft-timeLeft/3600)/60 , timeLeft-timeLeft/3600-timeLeft/60);
    }
    
    static void SetSaveGame(){
        SaveGame = "Player Name = " + playerName + "\n" ; 
        SaveGame += "Total Games = " + TotalGames + "     |     " ;
        SaveGame += "Wins = " + Wins + "     |     " ;
        SaveGame += "Losses = " + Losses ;
        SaveGame += "\n" + SeparatorLine() + "\n" + SeparatorLine() + "\n\n" ;
    }
    
    static void WritePlayerObject (MinesweeperCore minsweeperCore) {
        try(ObjectOutputStream write_Obj = new ObjectOutputStream(new FileOutputStream( InCompleteFile , true))){           
            write_Obj.writeObject(minsweeperCore);
        } 
        catch (IOException ex) {
        }
    }
    
    static MinesweeperCore ReadPlayerObject (String playerName){
        MinesweeperCore minesweeperCore = new MinesweeperCore();
        try(ObjectInputStream read_Obj = new ObjectInputStream(new FileInputStream(InCompleteFile))){
            while(true){
                MinesweeperCore minesweeperCore2 = (MinesweeperCore)read_Obj.readObject() ; 
                if (minesweeperCore2.playerName.equalsIgnoreCase(playerName)){
                    minesweeperCore = minesweeperCore2 ;
                }
            }
        }
        catch (IOException | ClassNotFoundException ex){         
        }
 
        return minesweeperCore ;
    }
}
