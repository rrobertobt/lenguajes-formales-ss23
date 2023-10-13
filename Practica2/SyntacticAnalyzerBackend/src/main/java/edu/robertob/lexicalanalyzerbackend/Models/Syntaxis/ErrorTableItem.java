package edu.robertob.lexicalanalyzerbackend.Models.Syntaxis;

import lombok.Data;

@Data()
public class ErrorTableItem {
    private String error;
    private int line;
    private int column;

    public ErrorTableItem(String error, int line, int column) {
        this.error = error;
        this.line = line;
        this.column = column;
    }
}
