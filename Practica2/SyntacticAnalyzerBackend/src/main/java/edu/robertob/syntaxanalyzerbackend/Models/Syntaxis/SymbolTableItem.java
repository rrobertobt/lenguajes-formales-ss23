package edu.robertob.syntaxanalyzerbackend.Models.Syntaxis;

import lombok.Data;

import java.util.Objects;

@Data()
public class SymbolTableItem {
    private String symbol;
    private SymbolType type;
    private String value;
    private int line;
    private int column;

    public SymbolTableItem(String symbol, SymbolType type, String value, int line, int column) {
        this.symbol = symbol;
        this.type = type;
        // Value can be optional
        this.value = Objects.requireNonNullElse(value, "");

        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "SymbolTableItem{" +
                "symbol='" + symbol + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
