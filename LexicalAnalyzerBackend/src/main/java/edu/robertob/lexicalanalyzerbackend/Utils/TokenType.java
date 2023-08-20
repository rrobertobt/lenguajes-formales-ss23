package edu.robertob.lexicalanalyzerbackend.Utils;

public enum TokenType {
    IDENTIFIER,
    KEYWORD,
    NEWLINE,
    OPERATOR_ARITHMETIC,
    OPERATOR_COMPARISON,
    OPERATOR_ASSIGNMENT,
    OPERATOR_LOGIC,
    PUNCTUATION_PARENTHESIS_OPEN,
    PUNCTUATION_PARENTHESIS_CLOSE,
    PUNCTUATION_BRACKET_OPEN,
    PUNCTUATION_BRACKET_CLOSE,
    PUNCTUATION_BRACE_OPEN,
    PUNCTUATION_BRACE_CLOSE,
    PUNCTUATION_COMMA,
    PUNCTUATION_COLON,
    PUNCTUATION_SEMICOLON,
    PUNCTUATION_DOT,
    LITERAL,
    STRING,
    CONSTANT,
    COMMENT,
    LOGIC,
    INVALID_UNIDENTIFIED,
    EOF
}
