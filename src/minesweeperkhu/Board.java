package minesweeperkhu;

public final class Board 
{
    private int numberOfMines;	
    private final Cell cells[][];

    private final int columns;
    private final int rows;
    
    public Board(int numberOfMines, int r, int c)
    {
        this.rows = r;
        this.columns = c;
        this.numberOfMines = numberOfMines;

        cells = new Cell[rows][columns];
        
        createEmptyCells();         
        setMines();
        setSurroundingMinesNumber();
    }
    
    public void createEmptyCells()
    {
        for (int x = 0; x < columns; x++)
        {
            for (int y = 0; y < rows; y++)
            {
                cells[x][y] = new Cell();
            }
        }
    }
    
    public void setMines()
    {
        int x,y;
        boolean hasMine;
        int currentMines = 0;                

        while (currentMines != numberOfMines)
        {
            x = (int)Math.floor(Math.random() * columns);
            y = (int)Math.floor(Math.random() * rows);

            hasMine = cells[x][y].getMine();

            if(!hasMine)
            {		
                cells[x][y].setMine(true);
                currentMines++;	
            }			
        }
    }
    
    public void setSurroundingMinesNumber()
    {	
        for(int x = 0 ; x < columns ; x++) 
        {
            for(int y = 0 ; y < rows ; y++) 
            {
                cells[x][y].setSurroundingMines(calculateNeighbours(x,y));                        
            }
        }
    }
    
    public int calculateNeighbours(int xCo, int yCo)
    {
        int neighbours = 0;

        for(int x=makeValidCoordinateX(xCo - 1); x<=makeValidCoordinateX(xCo + 1); x++) 
        {
            for(int y=makeValidCoordinateY(yCo - 1); y<=makeValidCoordinateY(yCo + 1); y++) 
            {
                if(x != xCo || y != yCo)
                    if(cells[x][y].getMine())
                        neighbours++;
            }
        }

        return neighbours;
    }
    
    public int makeValidCoordinateX(int i)
    {
        if (i < 0)
            i = 0;
        else if (i > columns-1)
            i = columns-1;

        return i;
    }
    
    public int makeValidCoordinateY(int i)
    {
        if (i < 0)
            i = 0;
        else if (i > rows-1)
            i = rows-1;

        return i;
    }
    
    public void setNumberOfMines(int numberOfMines)
    {
        this.numberOfMines = numberOfMines;
    }

    public int getNumberOfMines()
    {
        return numberOfMines;
    }

    public Cell[][] getCells()
    {
        return cells;
    }
    
    public int getRows()
    {
        return rows;
    }
    
    public int getColumns()
    {
        return columns;
    }
    
    public void resetBoard()
    {
        for(int x = 0 ; x < columns ; x++) 
        {
            for(int y = 0 ; y < rows ; y++) 
            {
                cells[x][y].setContent("");                        
            }
        }
    }
}
