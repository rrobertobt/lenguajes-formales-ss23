package edu.robertob.lexicalanalyzerbackend.Models.Syntaxis;

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
}
