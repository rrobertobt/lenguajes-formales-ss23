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
//        if (type != TokenType.KEYWORD_AND && type != TokenType.KEYWORD_AS && type != TokenType.KEYWORD_ASSERT && type != TokenType.KEYWORD_BREAK && type != TokenType.KEYWORD_CLASS && type != TokenType.KEYWORD_CATCH && type != TokenType.KEYWORD_CONTINUE && type != TokenType.KEYWORD_DEF && type != TokenType.KEYWORD_DEL && type != TokenType.KEYWORD_ELIF && type != TokenType.KEYWORD_ELSE && type != TokenType.KEYWORD_EXCEPT && type != TokenType.KEYWORD_FINALLY && type != TokenType.KEYWORD_FOR && type != TokenType.KEYWORD_FROM && type != TokenType.KEYWORD_GLOBAL && type != TokenType.KEYWORD_IF && type != TokenType.KEYWORD_IMPORT && type != TokenType.KEYWORD_IN && type != TokenType.KEYWORD_IS && type != TokenType.KEYWORD_LAMBDA && type != TokenType.KEYWORD_NONE && type != TokenType.KEYWORD_NONLOCAL && type != TokenType.KEYWORD_NOT && type != TokenType.KEYWORD_OR && type != TokenType.KEYWORD_PASS && type != TokenType.KEYWORD_RAISE && type != TokenType.KEYWORD_RETURN && type != TokenType.KEYWORD_TRY && type != TokenType.KEYWORD_WHILE && type != TokenType.KEYWORD_WITH && type != TokenType.KEYWORD_YIELD){
//            this.pattern = patternsMap.get(type);
//        } else {
//            this.pattern = keywordsPatternsMap.get(lexeme);
//        }
    }

    public Token() {
    }
}
