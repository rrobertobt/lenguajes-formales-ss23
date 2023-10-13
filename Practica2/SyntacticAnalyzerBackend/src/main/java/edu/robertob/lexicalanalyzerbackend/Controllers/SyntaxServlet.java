package edu.robertob.lexicalanalyzerbackend.Controllers;

import edu.robertob.lexicalanalyzerbackend.Models.LexicalAnalyzer;
import edu.robertob.lexicalanalyzerbackend.Models.SyntaxAnalyzer;
import edu.robertob.lexicalanalyzerbackend.Models.Syntaxis.SyntaxEndpointResponse;
import edu.robertob.lexicalanalyzerbackend.Models.Token;
import edu.robertob.lexicalanalyzerbackend.Utils.GsonWrapper;
import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/syntax")
public class SyntaxServlet extends HttpServlet {
    SyntaxEndpointResponse syntaxEndpointResponse;
    SyntaxAnalyzer syntaxAnalyzer;
    LexicalAnalyzer lexicalAnalyzer;
    List<Token> tokensToAnalyze;
    private final GsonWrapper gsonWrapper;

    public SyntaxServlet() {
        this.syntaxAnalyzer = new SyntaxAnalyzer();
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.gsonWrapper = new GsonWrapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = getRequestBody(req);
        this.lexicalAnalyzer.findCodeTokens(requestBody);
        this.tokensToAnalyze = this.lexicalAnalyzer.getFoundTokens();
        List<Token> filteredTokens = new ArrayList<>(this.tokensToAnalyze);
        filteredTokens.removeIf(token -> token.getType().equals(TokenType.SPACE));
        this.syntaxAnalyzer.analyzeTokens(filteredTokens);

        this.syntaxEndpointResponse = new SyntaxEndpointResponse(
            this.tokensToAnalyze,
            this.syntaxAnalyzer.getSymbolTable(),
            this.syntaxAnalyzer.getErrorsTable()
        );
        this.gsonWrapper.sendAsJson(resp, this.syntaxEndpointResponse);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n"); // Append newline character
            }
        }
        return sb.toString();
    }
}
