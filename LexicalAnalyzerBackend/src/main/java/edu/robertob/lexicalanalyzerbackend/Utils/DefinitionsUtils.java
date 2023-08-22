package edu.robertob.lexicalanalyzerbackend.Utils;

import java.util.Map;

// Clase que contiene y define el alfabeto y las palabras reservadas del lenguaje
public class DefinitionsUtils {
    public static final String alphabet = " _abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#(){}[],:;.+-*/%<>=!\"'\n";
    public static void fillSymbolsTableAsStringType(Map<String, TokenType> map){
        // Keywords
        map.put("and", TokenType.KEYWORD);
        map.put("as", TokenType.KEYWORD);
        map.put("assert", TokenType.KEYWORD);
        map.put("break", TokenType.KEYWORD);
        map.put("class", TokenType.KEYWORD);
        map.put("catch", TokenType.KEYWORD);
        map.put("continue", TokenType.KEYWORD);
        map.put("def", TokenType.KEYWORD);
        map.put("del", TokenType.KEYWORD);
        map.put("elif", TokenType.KEYWORD);
        map.put("else", TokenType.KEYWORD);
        map.put("except", TokenType.KEYWORD);
        map.put("finally", TokenType.KEYWORD);
        map.put("for", TokenType.KEYWORD);
        map.put("from", TokenType.KEYWORD);
        map.put("global", TokenType.KEYWORD);
        map.put("if", TokenType.KEYWORD);
        map.put("import", TokenType.KEYWORD);
        map.put("in", TokenType.KEYWORD);
        map.put("is", TokenType.KEYWORD);
        map.put("lambda", TokenType.KEYWORD);
        map.put("None", TokenType.KEYWORD);
        map.put("nonlocal", TokenType.KEYWORD);
        map.put("not", TokenType.KEYWORD);
        map.put("or", TokenType.KEYWORD);
        map.put("pass", TokenType.KEYWORD);
        map.put("raise", TokenType.KEYWORD);
        map.put("return", TokenType.KEYWORD);
        map.put("try", TokenType.KEYWORD);
        map.put("while", TokenType.KEYWORD);
        map.put("with", TokenType.KEYWORD);
        map.put("yield", TokenType.KEYWORD);
        // Logic (true and false)
        map.put("True", TokenType.LOGIC_TRUE);
        map.put("False", TokenType.LOGIC_FALSE);
        // Operators (arithmetic)
        map.put("+", TokenType.OPERATOR_ARITHMETIC);
        map.put("-", TokenType.OPERATOR_ARITHMETIC);
        map.put("*", TokenType.OPERATOR_ARITHMETIC);
        map.put("/", TokenType.OPERATOR_ARITHMETIC);
        map.put("//", TokenType.OPERATOR_ARITHMETIC);
        map.put("%", TokenType.OPERATOR_ARITHMETIC);
        map.put("**", TokenType.OPERATOR_ARITHMETIC);
        // Operators (assignment)
        map.put("=", TokenType.OPERATOR_ASSIGNMENT);
        // Operators (comparison)
        map.put("==", TokenType.OPERATOR_COMPARISON);
        map.put("!=", TokenType.OPERATOR_COMPARISON);
        map.put("<", TokenType.OPERATOR_COMPARISON);
        map.put(">", TokenType.OPERATOR_COMPARISON);
        map.put("<=", TokenType.OPERATOR_COMPARISON);
        map.put(">=", TokenType.OPERATOR_COMPARISON);
        // Punctuation
        map.put("(", TokenType.PUNCTUATION_PARENTHESIS_OPEN);
        map.put(")", TokenType.PUNCTUATION_PARENTHESIS_CLOSE);
        map.put("[", TokenType.PUNCTUATION_BRACKET_OPEN);
        map.put("]", TokenType.PUNCTUATION_BRACKET_CLOSE);
        map.put("{", TokenType.PUNCTUATION_BRACE_OPEN);
        map.put("}", TokenType.PUNCTUATION_BRACE_CLOSE);
        map.put(",", TokenType.PUNCTUATION_COMMA);
        map.put(":", TokenType.PUNCTUATION_COLON);
        map.put(";", TokenType.PUNCTUATION_SEMICOLON);
        map.put(".", TokenType.PUNCTUATION_DOT);
    }
    public static final Map<TokenType, String> patternsMap = Map.ofEntries(
            Map.entry(TokenType.IDENTIFIER, "^[a-zA-Z_][a-zA-Z0-9_]*$"),
            Map.entry(TokenType.NUMERIC_CONSTANT_WHOLE, "^\\d+$"),
            Map.entry(TokenType.NUMERIC_CONSTANT_DECIMAL, "^\\d+\\.\\d+$"),
            Map.entry(TokenType.STRING, "^\".*\"$"),
            Map.entry(TokenType.COMMENT, "^#.*$"),
            Map.entry(TokenType.NEWLINE, "^\\n+$"),
            Map.entry(TokenType.INVALID_UNIDENTIFIED, "^.*$"),
            Map.entry(TokenType.LOGIC_FALSE, "^False$"),
            Map.entry(TokenType.LOGIC_TRUE, "^True$"),
            Map.entry(TokenType.PUNCTUATION_COLON, "^:$"),
            Map.entry(TokenType.PUNCTUATION_COMMA, "^,$"),
            Map.entry(TokenType.PUNCTUATION_DOT, "^\\.$"),
            Map.entry(TokenType.PUNCTUATION_PARENTHESIS_OPEN, "^\\($"),
            Map.entry(TokenType.PUNCTUATION_PARENTHESIS_CLOSE, "^\\)$"),
            Map.entry(TokenType.PUNCTUATION_BRACKET_OPEN, "^\\[$"),
            Map.entry(TokenType.PUNCTUATION_BRACKET_CLOSE, "^\\]$"),
            Map.entry(TokenType.PUNCTUATION_BRACE_OPEN, "^\\{$"),
            Map.entry(TokenType.PUNCTUATION_BRACE_CLOSE, "^\\}$"),
            Map.entry(TokenType.PUNCTUATION_SEMICOLON, "^;$"),
            Map.entry(TokenType.OPERATOR_ARITHMETIC, "^\\+|-|\\*|/|//|%|\\*\\*$"),
            Map.entry(TokenType.OPERATOR_ASSIGNMENT, "^=$"),
            Map.entry(TokenType.OPERATOR_COMPARISON, "^==|!=|<|>|<=|>=$")

    );

    public static final Map<String, String> keywordsPatternsMap = Map.ofEntries(
            Map.entry("and", "^and$"),
            Map.entry("as", "^as$"),
            Map.entry("assert", "^assert$"),
            Map.entry("break", "^break$"),
            Map.entry("class", "^class$"),
            Map.entry("continue", "^continue$"),
            Map.entry("def", "^def$"),
            Map.entry("del", "^del$"),
            Map.entry("elif", "^elif$"),
            Map.entry("else", "^else$"),
            Map.entry("except", "^except$"),
            Map.entry("finally", "^finally$"),
            Map.entry("for", "^for$"),
            Map.entry("from", "^from$"),
            Map.entry("global", "^global$"),
            Map.entry("if", "^if$"),
            Map.entry("import", "^import$"),
            Map.entry("in", "^in$"),
            Map.entry("is", "^is$"),
            Map.entry("lambda", "^lambda$"),
            Map.entry("None", "^None$"),
            Map.entry("nonlocal", "^nonlocal$"),
            Map.entry("not", "^not$"),
            Map.entry("or", "^or$"),
            Map.entry("pass", "^pass$"),
            Map.entry("raise", "^raise$"),
            Map.entry("return", "^return$"),
            Map.entry("try", "^try$"),
            Map.entry("while", "^while$"),
            Map.entry("with", "^with$"),
            Map.entry("yield", "^yield$")
    );
}
