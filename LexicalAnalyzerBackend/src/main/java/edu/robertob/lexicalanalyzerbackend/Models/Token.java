package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Token {
    private String lexeme;
    private String pattern;
    private TokenType type;
    private int line;
    private int column;

    public Token(String lexeme, TokenType type, int line, int column) {
        this.lexeme = lexeme;
        this.type = type;
        this.line = line;
        this.column = column;
//        this.pattern = pattern;

        System.out.println("[CREATE TOKEN] Detected token: " + lexeme + " of type: " + type + " at line: " + line + " and column: " + column);
    }

    public Token() {
    }
}
