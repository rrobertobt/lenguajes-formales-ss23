package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;
import lombok.Getter;

import java.util.*;

public class LexicalAnalyzer {
    Map<String, TokenType> symbolsTable;
    @Getter
    List<Token> foundTokens;

    int currentLine = 1;
    int currentColumn = 1;


    public LexicalAnalyzer() {
        this.symbolsTable = new HashMap<>();
        this.foundTokens = new ArrayList<>();
        fillSymbolsTable();
    }

    public void findCodeTokens(String code) {
        System.out.println("[ANALYZER] Finding tokens in code: " + code);
        // Clearing the found tokens list
        this.foundTokens.clear();
        // Resetting the line and column counters
        this.currentLine = 1;
        this.currentColumn = 1;
        int index = 0;
        String buffer = "";
        char [] codeChars = code.toCharArray();
        for (int i = 0; i < codeChars.length; i++) {
//        for (char currentChar : codeChars) {
            char currentChar = codeChars[i];
            System.out.println("[ANALYZER] Current char: " + currentChar + " at index: " + i);
            switch (currentChar){
                case ' ':
                    System.out.println("[ANALYZER] Found space at index: " + index);
                    this.createToken(buffer);
                    buffer = "";
                    break;
                case '\n':
                    System.out.println("[ANALYZER] Found new line at index: " + index + "BUFFER: " + buffer);
                    this.createToken(buffer);
                    buffer = "";
                    this.currentLine++;
                    this.currentColumn = 0;
                    break;
                case '#':
                    // This is the start of a comment
                    int commentEnd = i + 1;
                    while (commentEnd < codeChars.length && codeChars[commentEnd] != '\n') {
                        commentEnd++;
                    }
                    // Create a token for the comment
                    Token token = new Token(code.substring(i, commentEnd), TokenType.COMMENT, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
                    this.currentLine++;
                    this.currentColumn = 0;
                    // Skip the comment and continue
                    i = commentEnd;
                    // Reset the buffer
                    buffer = "";
                    break;
                case '\"':
                    int stringEnd = i + 1;
                    boolean foundClosingQuote = false;
                    while (stringEnd < codeChars.length) {
                        if (codeChars[stringEnd] == '"') {
                            foundClosingQuote = true;
                            break;
                        }
                        stringEnd++;
                    }

                    if (!foundClosingQuote) {
                        // The string does not have a closing quote
                        System.out.println("[ERROR] Unclosed string at index: " + i);
                        token = new Token(code.substring(i, stringEnd), TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                        this.foundTokens.add(token);
                        // Skip the string
                        i = stringEnd;
                    } else {
                        // Create a token for the string
                        token = new Token(code.substring(i, stringEnd + 1), TokenType.STRING, this.currentLine, this.currentColumn);
                        this.foundTokens.add(token);
                        // Skip the string
                        i = stringEnd;
                    }
                    buffer = "";
                    break;
                case '\'':
                    stringEnd = i + 1;
                    foundClosingQuote = false;
                    while (stringEnd < codeChars.length) {
                        if (codeChars[stringEnd] == '\'') {
                            foundClosingQuote = true;
                            break;
                        }
                        stringEnd++;
                    }

                    if (!foundClosingQuote) {
                        // The string does not have a closing quote
                        System.out.println("[ERROR] Unclosed string at index: " + i);
                        token = new Token(code.substring(i, stringEnd), TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                        this.foundTokens.add(token);
                        // Skip the string
                        i = stringEnd;
                    } else {
                        // Create a token for the string
                        token = new Token(code.substring(i, stringEnd + 1), TokenType.STRING, this.currentLine, this.currentColumn);
                        this.foundTokens.add(token);
                        // Skip the string
                        i = stringEnd;
                    }
                    buffer = "";
                    break;
                case '(':
                    this.createToken(buffer);
                    this.createToken("(");
                    buffer = "";
                    break;
                case ')':
                    this.createToken(buffer);
                    this.createToken(")");
                    buffer = "";
                    break;
                case '[':
                    this.createToken(buffer);
                    this.createToken("[");
                    buffer = "";
                    break;
                case ']':
                    this.createToken(buffer);
                    this.createToken("]");
                    buffer = "";
                    break;
                case '{':
                    this.createToken(buffer);
                    this.createToken("{");
                    buffer = "";
                    break;
                case '}':
                    this.createToken(buffer);
                    this.createToken("}");
                    buffer = "";
                    break;
                case ',':
                    this.createToken(buffer);
                    this.createToken(",");
                    buffer = "";
                    break;
                case ':':
                    this.createToken(buffer);
                    this.createToken(":");
                    buffer = "";
                    break;
                case '.':
                    this.createToken(buffer);
                    this.createToken(".");
                    buffer = "";
                    break;
                case ';':
                    this.createToken(buffer);
                    this.createToken(";");
                    buffer = "";
                    break;
                    // Operators
                case '+':
                    this.createToken(buffer);
                    this.createToken("+");
                    buffer = "";
                    break;
                case '-':
                    this.createToken(buffer);
                    this.createToken("-");
                    buffer = "";
                    break;
                case '*':
                    this.createToken(buffer);
                    this.createToken("*");
                    buffer = "";
                    break;
                case '/':
                    // Check if the next character is also a /, if so, it counts as division too
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '/') {
                        // Check again if the next character is also a /, if so, it is invalid
                        if (i + 2 < codeChars.length && codeChars[i + 2] == '/') {
                            System.out.println("[ERROR] Invalid token at index: " + i);
                            token = new Token(code.substring(i, i + 3), TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                            this.foundTokens.add(token);
                            i += 2;
                            buffer = "";
                            break;
                        } else {
                            this.createToken(buffer);
                            this.createToken("//");
                            buffer = "";
                            i++;
                        }
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("/");
                        buffer = "";
                        break;
                    }
                case '%':
                    this.createToken(buffer);
                    this.createToken("%");
                    buffer = "";
                    break;
                case '<':
                    // Check if the next character is a =, if so, it counts as comparison but less than or equal to
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("<=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("<");
                        buffer = "";
                        break;
                    }
                case '>':
                    // Check if the next character is a =, if so, it counts as comparison but greater than or equal to
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken(">=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken(">");
                        buffer = "";
                        break;
                    }
                case '=':
                    // Check if the next character is also a =, if so, it counts as comparison instead of assignment
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("==");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("=");
                        buffer = "";
                        break;
                    }
                case '!':
                    // Check if the next character is also a =, if so, it counts as comparison instead of assignment
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("!=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        token = new Token("!", TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                        // Invalid_unidentified token
                        this.foundTokens.add(token);
                        buffer = "";
                        break;
                    }
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    int numberEnd = i + 1;
                    while (numberEnd < codeChars.length && (codeChars[numberEnd] >= '0' && codeChars[numberEnd] <= '9')) {
                        numberEnd++;
                    }

                    // Create a token for the number
                    token = new Token(code.substring(i, numberEnd), TokenType.NUMERIC_CONSTANT, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
                    // Skip the number
                    i = numberEnd;
                    buffer = "";
                    break;
                default:
                    buffer = buffer + currentChar;
                    break;
            }
            this.currentColumn++;
            index++;
        }
    }

    private void createToken(String lexeme) {
        if (lexeme.isEmpty()) {
            return;
        }

        /* Check if the lexeme is an identifier
        Rules:
        1. It must start with a letter or an underscore
        2. It can only contain letters, numbers and underscores
        3. It can't be a keyword
        4. Case sensitive
        Using regex is not allowed
        */

        var tokenType = this.symbolsTable.get(lexeme);
        if (tokenType == null) {
            tokenType = TokenType.INVALID_UNIDENTIFIED;
        }
        Token token = new Token(lexeme, tokenType, this.currentLine, this.currentColumn);
        this.foundTokens.add(token);
    }
    private void fillSymbolsTable() {
        // Keywords
        this.symbolsTable.put("and", TokenType.KEYWORD);
        this.symbolsTable.put("as", TokenType.KEYWORD);
        this.symbolsTable.put("assert", TokenType.KEYWORD);
        this.symbolsTable.put("break", TokenType.KEYWORD);
        this.symbolsTable.put("class", TokenType.KEYWORD);
        this.symbolsTable.put("continue", TokenType.KEYWORD);
        this.symbolsTable.put("def", TokenType.KEYWORD);
        this.symbolsTable.put("del", TokenType.KEYWORD);
        this.symbolsTable.put("elif", TokenType.KEYWORD);
        this.symbolsTable.put("else", TokenType.KEYWORD);
        this.symbolsTable.put("except", TokenType.KEYWORD);
        this.symbolsTable.put("finally", TokenType.KEYWORD);
        this.symbolsTable.put("for", TokenType.KEYWORD);
        this.symbolsTable.put("from", TokenType.KEYWORD);
        this.symbolsTable.put("global", TokenType.KEYWORD);
        this.symbolsTable.put("if", TokenType.KEYWORD);
        this.symbolsTable.put("import", TokenType.KEYWORD);
        this.symbolsTable.put("in", TokenType.KEYWORD);
        this.symbolsTable.put("is", TokenType.KEYWORD);
        this.symbolsTable.put("lambda", TokenType.KEYWORD);
        this.symbolsTable.put("None", TokenType.KEYWORD);
        this.symbolsTable.put("nonlocal", TokenType.KEYWORD);
        this.symbolsTable.put("not", TokenType.KEYWORD);
        this.symbolsTable.put("or", TokenType.KEYWORD);
        this.symbolsTable.put("pass", TokenType.KEYWORD);
        this.symbolsTable.put("raise", TokenType.KEYWORD);
        this.symbolsTable.put("return", TokenType.KEYWORD);
        this.symbolsTable.put("try", TokenType.KEYWORD);
        this.symbolsTable.put("while", TokenType.KEYWORD);
        this.symbolsTable.put("with", TokenType.KEYWORD);
        this.symbolsTable.put("yield", TokenType.KEYWORD);
        // Logic (true and false)
        this.symbolsTable.put("True", TokenType.LOGIC);
        this.symbolsTable.put("False", TokenType.LOGIC);
        // Operators (arithmetic)
        this.symbolsTable.put("+", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("-", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("*", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("/", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("//", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("%", TokenType.OPERATOR_ARITHMETIC);
        this.symbolsTable.put("**", TokenType.OPERATOR_ARITHMETIC);
        // Operators (assignment)
        this.symbolsTable.put("=", TokenType.OPERATOR_ASSIGNMENT);
        // Operators (comparison)
        this.symbolsTable.put("==", TokenType.OPERATOR_COMPARISON);
        this.symbolsTable.put("!=", TokenType.OPERATOR_COMPARISON);
        this.symbolsTable.put("<", TokenType.OPERATOR_COMPARISON);
        this.symbolsTable.put(">", TokenType.OPERATOR_COMPARISON);
        this.symbolsTable.put("<=", TokenType.OPERATOR_COMPARISON);
        this.symbolsTable.put(">=", TokenType.OPERATOR_COMPARISON);

        // Punctuation
        this.symbolsTable.put("(", TokenType.PUNCTUATION_PARENTHESIS_OPEN);
        this.symbolsTable.put(")", TokenType.PUNCTUATION_PARENTHESIS_CLOSE);
        this.symbolsTable.put("[", TokenType.PUNCTUATION_BRACKET_OPEN);
        this.symbolsTable.put("]", TokenType.PUNCTUATION_BRACKET_CLOSE);
        this.symbolsTable.put("{", TokenType.PUNCTUATION_BRACE_OPEN);
        this.symbolsTable.put("}", TokenType.PUNCTUATION_BRACE_CLOSE);
        this.symbolsTable.put(",", TokenType.PUNCTUATION_COMMA);
        this.symbolsTable.put(":", TokenType.PUNCTUATION_COLON);
        this.symbolsTable.put(";", TokenType.PUNCTUATION_SEMICOLON);
        this.symbolsTable.put(".", TokenType.PUNCTUATION_DOT);
    }

}
