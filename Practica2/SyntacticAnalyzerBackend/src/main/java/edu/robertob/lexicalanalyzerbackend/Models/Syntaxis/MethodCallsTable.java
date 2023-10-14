package edu.robertob.lexicalanalyzerbackend.Models.Syntaxis;

import java.util.ArrayList;
import java.util.List;

public class MethodCallsTable {
    List<MethodCallTableItem> items;
    int totalMethodsCalled = 0;

    public MethodCallsTable() {
        this.items = new ArrayList<>();
    }

    public void addMethodCallTableItem(MethodCallTableItem methodCallTableItem) {
        this.items.add(methodCallTableItem);
    }

    public void registerItem(String methodName, int lastCalledAtLine) {
        // Let's make this method reusable, so we can use it to register a method call or to increment the amount of times a method is called
        boolean itemAlreadyExists = false;
        for (MethodCallTableItem methodCallTableItem : items) {
            if (methodCallTableItem.getMethodName().equals(methodName)) {
                itemAlreadyExists = true;
            }
        }
        if (!itemAlreadyExists) {
            MethodCallTableItem methodCallTableItem = new MethodCallTableItem(methodName, lastCalledAtLine);
            this.addMethodCallTableItem(methodCallTableItem);
            this.totalMethodsCalled++;
        } else {
            this.incrementMethodCall(methodName, lastCalledAtLine);
        }
    }

    public void incrementMethodCall(String methodName, int lastCalledAtLine) {
        for (MethodCallTableItem methodCallTableItem : items) {
            if (methodCallTableItem.getMethodName().equals(methodName)) {
                methodCallTableItem.incrementTimesCalled(lastCalledAtLine);
            }
        }
    }
}
