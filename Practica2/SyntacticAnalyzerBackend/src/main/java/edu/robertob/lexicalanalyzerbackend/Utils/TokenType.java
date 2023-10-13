package edu.robertob.lexicalanalyzerbackend.Utils;

public enum TokenType {
    IDENTIFIER,
    KEYWORD_AND,
    KEYWORD_AS,
    KEYWORD_ASSERT,
    KEYWORD_BREAK,
    KEYWORD_CLASS,
    KEYWORD_CATCH,
    KEYWORD_CONTINUE,
    KEYWORD_DEF,
    KEYWORD_DEL,
    KEYWORD_ELIF,
    KEYWORD_ELSE,
    KEYWORD_EXCEPT,
    KEYWORD_FINALLY,
    KEYWORD_FOR,
    KEYWORD_FROM,
    KEYWORD_GLOBAL,
    KEYWORD_IF,
    KEYWORD_IMPORT,
    KEYWORD_IN,
    KEYWORD_IS,
    KEYWORD_LAMBDA,
    KEYWORD_NONE,
    KEYWORD_NONLOCAL,
    KEYWORD_NOT,
    KEYWORD_OR,
    KEYWORD_PASS,
    KEYWORD_RAISE,
    KEYWORD_RETURN,
    KEYWORD_TRY,
    KEYWORD_WHILE,
    KEYWORD_WITH,
    KEYWORD_YIELD,
    NEWLINE,
    OPERATOR_ARITHMETIC,
    OPERATOR_COMPARISON,
    OPERATOR_ASSIGNMENT,
    OPERATOR_ASSIGNMENT_SUM,
    OPERATOR_ASSIGNMENT_SUB,
    OPERATOR_ASSIGNMENT_MUL,
    OPERATOR_ASSIGNMENT_DIV,
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
    NUMERIC_CONSTANT_WHOLE,
    NUMERIC_CONSTANT_DECIMAL,
    COMMENT,
    LOGIC_TRUE,
    LOGIC_FALSE,
    INVALID_UNIDENTIFIED,
    NEW_LINE, SPACE, EOF
}
