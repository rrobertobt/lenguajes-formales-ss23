package edu.robertob.lexicalanalyzerbackend.Utils;

import java.util.Map;

public class DefinitionsUtils {
    public static void fillSymbolsTableAsStringType(Map<String, TokenType> map){
        // Keywords
        map.put("and", TokenType.KEYWORD);
        map.put("as", TokenType.KEYWORD);
        map.put("assert", TokenType.KEYWORD);
        map.put("break", TokenType.KEYWORD);
        map.put("class", TokenType.KEYWORD);
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
        map.put("True", TokenType.LOGIC);
        map.put("False", TokenType.LOGIC);
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
}
