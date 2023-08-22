package edu.robertob.lexicalanalyzerbackend.Controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.Main;
import edu.robertob.lexicalanalyzerbackend.Models.LexicalAnalyzer;
import edu.robertob.lexicalanalyzerbackend.Models.Token;
import edu.robertob.lexicalanalyzerbackend.Utils.GsonWrapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/analyze")
public class MainServlet extends HttpServlet {

    LexicalAnalyzer lexicalAnalyzer;
    List<String> tokens;
    private final GsonWrapper gsonWrapper;
    public MainServlet() {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.tokens = new ArrayList<>();
        this.gsonWrapper = new GsonWrapper();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = getRequestBody(request);
        this.lexicalAnalyzer.findCodeTokens(requestBody);
        var foundTokens = this.lexicalAnalyzer.getFoundTokens();
        this.gsonWrapper.sendAsJson(response, foundTokens);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[SERVLET] Reading line: " + line);
//                sb.append(line).append(" "); // Append space character
                sb.append(line).append("\n"); // Append newline character
            }
        }
        return sb.toString();
    }

    public void destroy() {
    }
}