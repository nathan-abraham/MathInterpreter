package src;

import src.nodes.*;
import src.errors.*;

public class Position {
    public int index;
    public int line;
    public int col;
    public String filename;
    public String filetext;

    public Position(int index, int line, int col, String filename, String filetext) {
        this.index = index;
        this.line = line;
        this.col = col;
        this.filename = filename;
        this.filetext = filetext;
    }

    public Position advance(String currentChar) {
        index++;
        col++;

        if (currentChar.equals("\n")) {
            line++;
            col = 0;
        }

        return this;
    }

    public Position copy() {
        return new Position(index, line, col, filename, filename);
    }

}
