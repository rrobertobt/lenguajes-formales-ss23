package edu.robertob.lexicalanalyzerbackend.Controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.Main;
import edu.robertob.lexicalanalyzerbackend.Models.LexicalAnalyzer;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/analyze")
public class MainServlet extends HttpServlet {

    LexicalAnalyzer lexicalAnalyzer;
    List<String> tokens;
    public MainServlet() {
        this.lexicalAnalyzer = new LexicalAnalyzer();
        this.tokens = new ArrayList<>();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response content type to plain text
        // Testing the lexical analyzer with fixed code
//        String code = "def main():\n" +
//                "    print(\"Hello world\")\n" +
//                "    return 0\n" +
//                "\n" +
//                "if __name__ == \"__main__\":\n" +
//                "    main()";
        // Getting the entire code from the request body
        String requestBody = getRequestBody(request);
//        String code = "def class if int(int)";
        this.lexicalAnalyzer.findCodeTokens(requestBody);
        var foundTokens = this.lexicalAnalyzer.getFoundTokens();
        PrintWriter out = response.getWriter();
        out.println("Reading code:\n\n" + requestBody);
        out.println("Found tokens:" + "\n");
        for (var token : foundTokens) {
            out.println(token.getLexeme() + " " + token.getType() + " " + token.getLine() + " " + token.getColumn());
        }

        response.setContentType("text/plain");
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