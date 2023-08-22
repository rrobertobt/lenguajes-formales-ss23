package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;
import lombok.Getter;
import lombok.Setter;

import static edu.robertob.lexicalanalyzerbackend.Utils.DefinitionsUtils.keywordsPatternsMap;
import static edu.robertob.lexicalanalyzerbackend.Utils.DefinitionsUtils.patternsMap;

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
        if (type != TokenType.KEYWORD){
            this.pattern = patternsMap.get(type);
        } else {
            this.pattern = keywordsPatternsMap.get(lexeme);
        }
    }

    public Token() {
    }
}
