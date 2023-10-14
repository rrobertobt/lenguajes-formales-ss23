package edu.robertob.syntaxanalyzerbackend.Models.Syntaxis;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data()
public class SyntaxSymbolTable {
    List<SymbolTableItem> symbolTableItems;

    public SyntaxSymbolTable() {
        this.symbolTableItems = new ArrayList<>();
    }

    public void addSymbolTableItem(SymbolTableItem symbolTableItem) {
        this.symbolTableItems.add(symbolTableItem);
    }
}
