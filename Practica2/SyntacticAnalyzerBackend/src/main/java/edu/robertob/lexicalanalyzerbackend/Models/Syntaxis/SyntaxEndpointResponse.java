package edu.robertob.lexicalanalyzerbackend.Models.Syntaxis;

import edu.robertob.lexicalanalyzerbackend.Models.Token;
import lombok.Data;

import java.util.List;

@Data()
public class SyntaxEndpointResponse {
    private List<Token> tokensFound;
    private SyntaxSymbolTable syntaxSymbolTable;
    private ErrorsTable errorsTable;
    public SyntaxEndpointResponse (List<Token> tokensFound, SyntaxSymbolTable syntaxSymbolTable, ErrorsTable errorsTable) {
        this.tokensFound = tokensFound;
        this.syntaxSymbolTable = syntaxSymbolTable;
        this.errorsTable = errorsTable;
    }
}
