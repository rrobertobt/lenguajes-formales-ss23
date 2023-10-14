package edu.robertob.syntaxanalyzerbackend.Models.Syntaxis;

import java.util.ArrayList;
import java.util.List;

public class ErrorsTable {
    List<ErrorTableItem> errorsTableItems;

    public ErrorsTable() {
        this.errorsTableItems = new ArrayList<>();
    }

    public void addErrorTableItem(ErrorTableItem errorTableItem) {
        this.errorsTableItems.add(errorTableItem);
    }

    public void removeErrorTableItem(int line, int column) {
        System.out.println("Removing error at line: " + line + " and column: " + column);
        for (int i = 0; i < errorsTableItems.size(); i++) {
            if (errorsTableItems.get(i).getLine() == line && errorsTableItems.get(i).getColumn() == column) {
                errorsTableItems.remove(i);
            }
        }
    }
}
