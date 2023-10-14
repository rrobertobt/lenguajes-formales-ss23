package edu.robertob.lexicalanalyzerbackend.Models.Syntaxis;

import edu.robertob.lexicalanalyzerbackend.Models.Token;
import lombok.Data;

import java.util.List;

@Data()
public class SyntaxEndpointResponse {
    private List<Token> tokensFound;
    private SyntaxSymbolTable syntaxSymbolTable;
    private ErrorsTable errorsTable;
    private MethodCallsTable methodCallsTable;
    public SyntaxEndpointResponse (
        List<Token> tokensFound,
        SyntaxSymbolTable syntaxSymbolTable,
        ErrorsTable errorsTable,
        MethodCallsTable methodCallsTable
    ) {
        this.tokensFound = tokensFound;
        this.syntaxSymbolTable = syntaxSymbolTable;
        this.errorsTable = errorsTable;
        this.methodCallsTable = methodCallsTable;
    }
}
