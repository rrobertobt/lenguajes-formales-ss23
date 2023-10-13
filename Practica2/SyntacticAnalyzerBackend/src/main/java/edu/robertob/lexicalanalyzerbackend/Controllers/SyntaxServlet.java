package edu.robertob.lexicalanalyzerbackend.Controllers;

import edu.robertob.lexicalanalyzerbackend.Models.LexicalAnalyzer;
import edu.robertob.lexicalanalyzerbackend.Models.SyntaxAnalyzer;
import edu.robertob.lexicalanalyzerbackend.Models.Token;
import edu.robertob.lexicalanalyzerbackend.Utils.GsonWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/syntax")
public class SyntaxServlet extends HttpServlet {
    // Define later
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
        this.syntaxAnalyzer.analyzeTokens(this.tokensToAnalyze);
        this.gsonWrapper.sendAsJson(resp, this.tokensToAnalyze);
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
