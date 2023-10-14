package edu.robertob.syntaxanalyzerbackend.Models.Syntaxis;

import lombok.Data;

// This class is responsible for storing the amount of times a method is called
@Data()
public class MethodCallTableItem {
    private String methodName;
    private int timesCalled;
    private int lastCalledAtLine;

    public MethodCallTableItem(String methodName, int lastCalledAtLine) {
        this.methodName = methodName;
        this.lastCalledAtLine = lastCalledAtLine;
        this.timesCalled = 1;
    }

    public void incrementTimesCalled(int lastCalledAtLine) {
        this.lastCalledAtLine = lastCalledAtLine;
        this.timesCalled++;
    }
}
