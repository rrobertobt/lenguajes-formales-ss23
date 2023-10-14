package edu.robertob.syntaxanalyzerbackend.Models;

import edu.robertob.syntaxanalyzerbackend.Utils.DefinitionsUtils;
import edu.robertob.syntaxanalyzerbackend.Utils.TokenType;
import lombok.Getter;

import java.util.*;

import static edu.robertob.syntaxanalyzerbackend.Utils.TokenUtils.*;

// Clase base que contiene la lógica para el análisis léxico
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
        String buffer = "";
        char [] codeChars = code.toCharArray();
        for (int i = 0; i < codeChars.length; i++) {
            char currentChar = codeChars[i];
            switch (currentChar){
                case ' ':
                    this.createToken(buffer);
                    buffer = "";
                    token = new Token(" ", TokenType.SPACE, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
                    break;
                case '\n':
                    this.createToken(buffer);
                    buffer = "";
                    this.currentLine++;
                    this.currentColumn = 0;
                    token = new Token("\n", TokenType.NEW_LINE, this.currentLine - 1, this.currentColumn);
                    this.foundTokens.add(token);
                    break;
                case '#':
                    // Se detecta el inicio de un comentario
                    int commentEnd = i + 1;
                    while (commentEnd < codeChars.length && codeChars[commentEnd] != '\n') {
                        commentEnd++;
                    }
                    token = new Token(code.substring(i, commentEnd), TokenType.COMMENT, this.currentLine, this.currentColumn);
                    this.foundTokens.add(token);
//                    this.currentLine++;
                    this.currentColumn = 0;
                    // Saltar el comentario y resetear el buffer
                    i = commentEnd - 1;
                    buffer = "";
                    break;
                case '\"':
                case '\'':
                    int stringEnd = i + 1;
                    boolean foundClosingQuote = false;
                    while (stringEnd < codeChars.length) {
                        if (codeChars[stringEnd] == '\"') {
                            foundClosingQuote = true;
                            break;
                        } else if (codeChars[stringEnd] == '\'') {
                            foundClosingQuote = true;
                            break;
                        } else {
                            stringEnd++;
                        }
                    }

                    if (!foundClosingQuote) {
                        // Si no se encontró el cierre de la cadena, se crea un token de error
                        token = new Token(code.substring(i, stringEnd), TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn);
                    } else {
                        // Ajustar el contador de columnas al valor correcto
                        if(this.currentLine == 1){
                            this.currentColumn = stringEnd + 2;
                        } else {
                            this.currentColumn = (stringEnd - i) + 2;
                        }
                        token = new Token(code.substring(i, stringEnd + 1), TokenType.STRING, this.currentLine, this.currentColumn);
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
                    // Revisar si el siguiente caracter es un =, si es asi cuenta como asignación de suma
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("+=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("+");
                        buffer = "";
                        break;
                    }

                case '-':
                    // Revisar si el siguiente caracter es un =, si es asi cuenta como asignación de resta
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("-=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("-");
                        buffer = "";
                        break;
                    }
                case '*':
                    // Revisar si el siguiente caracter es un =, si es asi cuenta como asignación de multiplicación
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("*=");
                        buffer = "";
                        i++;
                        break;
                    // También revisar, si el siguiente caracter es un *, si es así, cuenta como potencia
                    } else if (i + 1 < codeChars.length && codeChars[i + 1] == '*') {
                        // Y luego revisar si el siguiente caracter es un =, si es así, cuenta como asignación de potencia
                        if (i + 2 < codeChars.length && codeChars[i + 2] == '=') {
                            this.createToken(buffer);
                            this.createToken("**=");
                            buffer = "";
                            i += 2;
                            break;
                        } else {
                            this.createToken(buffer);
                            this.createToken("**");
                            buffer = "";
                            i++;
                            break;
                        }

                    } else {
                        this.createToken(buffer);
                        this.createToken("*");
                        buffer = "";
                        break;
                    }
                case '/':
                    // Revisar si el siguiente caracter es un =, si es asi cuenta como asignación de división
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("/=");
                        buffer = "";
                        i++;
                        break;
                    }
                    // Revisa si el siguiente caracter es un /, si es así, cuenta como división también
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '/') {
                        // Revisar de nuevo si el siguiente caracter es un /, si es así, es invalido
                        if (i + 2 < codeChars.length && codeChars[i + 2] == '/') {
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
                    // Revisa si el siguiente caracter es un =, si es así, cuenta como asignación de módulo
                    if (i + 1 < codeChars.length && codeChars[i + 1] == '=') {
                        this.createToken(buffer);
                        this.createToken("%=");
                        buffer = "";
                        i++;
                        break;
                    } else {
                        this.createToken(buffer);
                        this.createToken("%");
                        buffer = "";
                        break;
                    }
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
                    /* Primero revisamos el buffer para ver si hay algo que no sea un número, si es el caso, continuamos el bucle
                    porque puede ser un identificador */
                    if (containsLettersOrUnderscores(buffer)) {
                        buffer += currentChar;
                        break;
                    }

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
                    i = numberEnd - 1;
                    buffer = "";
                    break;
                default:
                    buffer = buffer + currentChar;
                    break;
            }
            this.currentColumn++;
        }
    }

    private void createToken(String lexeme) {
        // Si el lexema está vacío, no se crea un token
        if (lexeme.isEmpty()) return;
        // Verificar si el lexema es un token válido de identificador
        if (isValidIdentifier(lexeme, this.symbolsTable)) {
            // También verificar si contiene letras del alfabeto permitido, si no, es invalido
            if (!isValidLexeme(lexeme)) {
                this.foundTokens.add(new Token(lexeme, TokenType.INVALID_UNIDENTIFIED, this.currentLine, this.currentColumn));
                return;
            }
            this.foundTokens.add(new Token(lexeme, TokenType.IDENTIFIER, this.currentLine, this.currentColumn));
        } else {
            // Si no es un identificador, se busca en la tabla de símbolos, si no está, es inválido
            // Pero también debemos verificar si es un operador logic (and, or, not)
            if (isLogicOperator(lexeme)) {
                this.foundTokens.add(new Token(lexeme, TokenType.OPERATOR_LOGIC, this.currentLine, this.currentColumn));
                return;
            }
            var tokenType = this.symbolsTable.get(lexeme);
            if (tokenType == null) {
                tokenType = TokenType.INVALID_UNIDENTIFIED;
            }
            this.foundTokens.add(new Token(lexeme, tokenType, this.currentLine, this.currentColumn));
        }
    }
}