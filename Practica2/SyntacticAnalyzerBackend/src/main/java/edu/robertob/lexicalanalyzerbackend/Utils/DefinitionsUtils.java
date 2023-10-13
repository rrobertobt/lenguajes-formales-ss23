package edu.robertob.lexicalanalyzerbackend.Utils;

import java.util.Map;

// Clase que contiene y define el alfabeto y las palabras reservadas del lenguaje
public class DefinitionsUtils {
    public static final String alphabet = " _abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#(){}[],:;.+-*/%<>=!\"'\n";
    public static void fillSymbolsTableAsStringType(Map<String, TokenType> map){
        // Keywords
        map.put("and", TokenType.KEYWORD_AND);
        map.put("as", TokenType.KEYWORD_AS);
        map.put("assert", TokenType.KEYWORD_ASSERT);
        map.put("break", TokenType.KEYWORD_BREAK);
        map.put("class", TokenType.KEYWORD_CLASS);
        map.put("catch", TokenType.KEYWORD_CATCH);
        map.put("continue", TokenType.KEYWORD_CONTINUE);
        map.put("def", TokenType.KEYWORD_DEF);
        map.put("del", TokenType.KEYWORD_DEL);
        map.put("elif", TokenType.KEYWORD_ELIF);
        map.put("else", TokenType.KEYWORD_ELSE);
        map.put("except", TokenType.KEYWORD_EXCEPT);
        map.put("finally", TokenType.KEYWORD_FINALLY);
        map.put("for", TokenType.KEYWORD_FOR);
        map.put("from", TokenType.KEYWORD_FROM);
        map.put("global", TokenType.KEYWORD_GLOBAL);
        map.put("if", TokenType.KEYWORD_IF);
        map.put("import", TokenType.KEYWORD_IMPORT);
        map.put("in", TokenType.KEYWORD_IN);
        map.put("is", TokenType.KEYWORD_IS);
        map.put("lambda", TokenType.KEYWORD_LAMBDA);
        map.put("None", TokenType.KEYWORD_NONE);
        map.put("nonlocal", TokenType.KEYWORD_NONLOCAL);
        map.put("not", TokenType.KEYWORD_NOT);
        map.put("or", TokenType.KEYWORD_OR);
        map.put("pass", TokenType.KEYWORD_PASS);
        map.put("raise", TokenType.KEYWORD_RAISE);
        map.put("return", TokenType.KEYWORD_RETURN);
        map.put("try", TokenType.KEYWORD_TRY);
        map.put("while", TokenType.KEYWORD_WHILE);
        map.put("with", TokenType.KEYWORD_WITH);
        map.put("yield", TokenType.KEYWORD_YIELD);
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
        map.put("+=", TokenType.OPERATOR_ASSIGNMENT_SUM);
        map.put("-=", TokenType.OPERATOR_ASSIGNMENT_SUB);
        map.put("*=", TokenType.OPERATOR_ASSIGNMENT_MUL);
        map.put("/=", TokenType.OPERATOR_ASSIGNMENT_DIV);
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
            Map.entry(TokenType.IDENTIFIER, "^[a-zA-Z_][a-zA-Z0-9_]*"),
            Map.entry(TokenType.NUMERIC_CONSTANT_WHOLE, "[0-9]+"),
            Map.entry(TokenType.NUMERIC_CONSTANT_DECIMAL, "[0-9]+.[0-9]+"),
            Map.entry(TokenType.STRING, "\"*\""),
            Map.entry(TokenType.COMMENT, "#*"),
            Map.entry(TokenType.NEW_LINE, "'\\n'"),
            Map.entry(TokenType.INVALID_UNIDENTIFIED, "^.*"),
            Map.entry(TokenType.LOGIC_FALSE, "^False"),
            Map.entry(TokenType.LOGIC_TRUE, "^True"),
            Map.entry(TokenType.PUNCTUATION_COLON, "^:"),
            Map.entry(TokenType.PUNCTUATION_COMMA, "^,"),
            Map.entry(TokenType.PUNCTUATION_DOT, "^\\."),
            Map.entry(TokenType.PUNCTUATION_PARENTHESIS_OPEN, "("),
            Map.entry(TokenType.PUNCTUATION_PARENTHESIS_CLOSE, ")"),
            Map.entry(TokenType.PUNCTUATION_BRACKET_OPEN, "["),
            Map.entry(TokenType.PUNCTUATION_BRACKET_CLOSE, "]"),
            Map.entry(TokenType.PUNCTUATION_BRACE_OPEN, "{"),
            Map.entry(TokenType.PUNCTUATION_BRACE_CLOSE, "}"),
            Map.entry(TokenType.PUNCTUATION_SEMICOLON, ";"),
            Map.entry(TokenType.OPERATOR_ARITHMETIC, "\\+|-|\\*|/|//|%|\\*\\*"),
            Map.entry(TokenType.OPERATOR_ASSIGNMENT, "="),
            Map.entry(TokenType.OPERATOR_COMPARISON, "==|!=|<|>|<=|>=")

    );

    public static final Map<String, String> keywordsPatternsMap = Map.ofEntries(
            Map.entry("and", "and"),
            Map.entry("as", "as"),
            Map.entry("assert", "assert"),
            Map.entry("break", "break"),
            Map.entry("class", "class"),
            Map.entry("catch", "catch"),
            Map.entry("continue", "continue"),
            Map.entry("def", "def"),
            Map.entry("del", "del"),
            Map.entry("elif", "elif"),
            Map.entry("else", "else"),
            Map.entry("except", "except"),
            Map.entry("finally", "finally"),
            Map.entry("for", "for"),
            Map.entry("from", "from"),
            Map.entry("global", "global"),
            Map.entry("if", "if"),
            Map.entry("import", "import"),
            Map.entry("in", "in"),
            Map.entry("is", "is"),
            Map.entry("lambda", "lambda"),
            Map.entry("None", "None"),
            Map.entry("nonlocal", "nonlocal"),
            Map.entry("not", "not"),
            Map.entry("or", "or"),
            Map.entry("pass", "pass"),
            Map.entry("raise", "raise"),
            Map.entry("return", "return"),
            Map.entry("try", "try"),
            Map.entry("while", "while"),
            Map.entry("with", "with"),
            Map.entry("yield", "yield")
    );
}
