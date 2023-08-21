package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Utils.DefinitionsUtils;
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
        DefinitionsUtils.fillSymbolsTableAsStringType(this.symbolsTable);
//        fillSymbolsTable();
    }

    public void findCodeTokens(String code) {
        System.out.println("[ANALYZER] Finding tokens in code: " + code);
        // Token temporal
        Token token;
        // Limpiando la lista de tokens encontrados
        this.foundTokens.clear();
        // Reseteando el contador de líneas y columnas
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
                    // Se detecta el inicio de un comentario
                    int commentEnd = i + 1;
                    while (commentEnd < codeChars.length && codeChars[commentEnd] != '\n') {
                        commentEnd++;
                    }
                    token = new Token(code.substring(i, commentEnd), TokenType.COMMENT, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
                    this.currentLine++;
                    this.currentColumn = 0;
                    // Saltar el comentario y resetear el buffer
                    i = commentEnd;
                    buffer = "";
                    break;
                case '\"':
                case '\'':
                    int stringEnd = i + 1;
                    boolean foundClosingQuote = false;
                    while (stringEnd < codeChars.length) {
                        if (codeChars[stringEnd] == '"') {
                            foundClosingQuote = true;
                            break;
                        } else if (codeChars[stringEnd] == '\'') {
                            foundClosingQuote = true;
                            break;
                        }
                        stringEnd++;
                    }

                    if (!foundClosingQuote) {
                        // Si no se encontró el cierre de la cadena, se crea un token de error
                        System.out.println("[ERROR] Unclosed string at index: " + i);
                        token = new Token(code.substring(i, stringEnd), TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                        // Skip the string
                    } else {
                        token = new Token(code.substring(i, stringEnd + 1), TokenType.STRING, this.currentLine, this.currentColumn);
                        // Se salta la cadena
                    }
                    this.foundTokens.add(token);
                    i = stringEnd;
                    buffer = "";
                    break;
                // Delimitadores y signos de puntuación
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
                    // Operadores
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
                    // Revisa si el siguiente caracter es un /, si es así, cuenta como división también
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '/') {
                        // Revisar de nuevo si el siguiente caracter es un /, si es así, es invalido
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
                    } else {
                        this.createToken(buffer);
                        this.createToken("/");
                        buffer = "";
                    }
                    break;
                case '%':
                    this.createToken(buffer);
                    this.createToken("%");
                    buffer = "";
                    break;
                case '<':
                    // Revisa si el siguiente caracter es un =, si es así, cuenta como comparación pero menor o igual que
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
                    // Revisa si el siguiente caracter es un =, si es así, cuenta como comparación pero mayor o igual que
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
                    // Revisa si el siguiente caracter es un =, si es así, cuenta como comparación en vez de asignación
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
                    // Revisa si el siguiente caracter es un =, si es así, cuenta como comparación, si no, es invalido
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("!=");
                        i++;
                    } else {
                        token = new Token("!", TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                        this.foundTokens.add(token);
                    }
                    buffer = "";
                    break;
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
                    boolean hasDecimalPoint = false;
                    TokenType numericConstantType;

                    // Se encuantra el final del número
                    while (numberEnd < codeChars.length) {
                        char currentNumChar = codeChars[numberEnd];
                        if (currentNumChar >= '0' && currentNumChar <= '9') {
                            // Si es un dígito, sigue siendo un número
                            numberEnd++;
                        } else if (currentNumChar == '.' && !hasDecimalPoint) {
                            // Se encuentra un punto decimal, si no se ha encontrado uno antes, sigue siendo un número
                            hasDecimalPoint = true;
                            numberEnd++;
                        } else {
                            // Si no es un dígito o tampoco un punto decimal valido, se termina el número
                            break;
                        }
                    }

                    // Revisa si el número tiene más de un punto decimal para definir el tipo de token y crearlo
                    boolean hasMoreOneDot = (numberEnd < codeChars.length) &&
                            codeChars[numberEnd - 1] == '.' &&
                            codeChars[numberEnd] == '.';


                    if (hasMoreOneDot) {
                        numericConstantType = TokenType.INVALID_UNIDENTIFIED;
                    } else if (hasDecimalPoint) {
                        numericConstantType = TokenType.NUMERIC_CONSTANT_DECIMAL;
                    } else {
                        numericConstantType = TokenType.NUMERIC_CONSTANT_WHOLE;
                    }

                    token = new Token(code.substring(i, numberEnd), numericConstantType, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
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
        // Si el lexema está vacío, no se crea un token
        if (lexeme.isEmpty()) {
            return;
        }

        // Verificar si el lexema es un token válido de identificador
        /* Check if the lexeme is an identifier
        Rules:
        1. It must start with a letter or an underscore
        2. It can only contain letters, numbers and underscores
        3. It can't be a keyword
        4. Case-sensitive
        Using regex is not allowed
        */

        var tokenType = this.symbolsTable.get(lexeme);
        if (tokenType == null) {
            tokenType = TokenType.INVALID_UNIDENTIFIED;
        }
        Token token = new Token(lexeme, tokenType, this.currentLine, this.currentColumn);
        this.foundTokens.add(token);
    }
}