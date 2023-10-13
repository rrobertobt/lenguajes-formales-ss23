package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Models.Syntaxis.*;
import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;
import edu.robertob.lexicalanalyzerbackend.Utils.TokenUtils;
import lombok.Getter;

import java.util.List;

public class SyntaxAnalyzer {

    private List<Token> tokens;
    @Getter
    private SyntaxSymbolTable symbolTable;
    @Getter()
    private ErrorsTable errorsTable;
    private int currentIndex;
    public void analyzeTokens(List<Token> tokensToAnalyze) {
        this.tokens = tokensToAnalyze;
        this.symbolTable = new SyntaxSymbolTable();
        this.errorsTable = new ErrorsTable();
        this.currentIndex = 0;
        parse();
    }

    public SyntaxAnalyzer() {
        this.currentIndex = 0;
    }

    public void parse() {
        while (currentIndex < tokens.size()) {
            if (!methodCall()) {
                assignment();
            }
        }
    }

    private boolean assignment() {
        Token id;
        String name = "";
        String value = "";
        int indexFromAssToken;
        System.out.println(" = [SYNTAX] Trying to match assignment");
        if (match(TokenType.IDENTIFIER)) {
            id = tokens.get(currentIndex - 1);
            name = id.getLexeme();
            if (match(TokenType.OPERATOR_ASSIGNMENT) || match(TokenType.OPERATOR_ASSIGNMENT_SUM) || match(TokenType.OPERATOR_ASSIGNMENT_SUB) || match(TokenType.OPERATOR_ASSIGNMENT_MUL) || match(TokenType.OPERATOR_ASSIGNMENT_DIV)) {
                indexFromAssToken = currentIndex;
                if (expression()) {
                    // Get the value of the expression by going from the token agter the assignment token to the token before the current index
                    for (int i = indexFromAssToken; i < currentIndex; i++) {
                        if (tokenIsNotCommentOrNewLine(i)) {
                            value += tokens.get(i).getLexeme();
                        }
                    }
                    symbolTable.addSymbolTableItem(new SymbolTableItem(name, SymbolType.VARIABLE, value, id.getLine(), id.getColumn()));
                    System.out.println("[SYNTAX] 😁😁😁 = Asignación correcta => id: " + id.getLexeme() + " | valor: " + value + "\n");
                    return true;
                } else {
                    this.errorsTable.addErrorTableItem(new ErrorTableItem("Error de sintaxis en la expresión", id.getLine(), id.getColumn()));
                    System.out.println("Error de sintaxis en la expresión");
                    return false;
                }
            } else {
                this.errorsTable.addErrorTableItem(new ErrorTableItem("Se esperaba un '=' después del identificador", id.getLine(), id.getColumn()));
                System.out.println("Se esperaba un '=' después del identificador.");
                return false;
            }
        } else {
            if (currentIndex < tokens.size()) {
                if (!tokens.get(currentIndex).getType().equals(TokenType.NEW_LINE)){
                System.out.println("Se esperaba un identificador en: " + tokens.get(currentIndex).getLine() + ":" + tokens.get(currentIndex).getColumn());
                this.errorsTable.addErrorTableItem(new ErrorTableItem("Se esperaba un identificador", tokens.get(currentIndex).getLine(), tokens.get(currentIndex).getColumn()));
                currentIndex++;
                }
            }
        }
        return false;
    }

    private boolean methodCall(){
        System.out.println("m() [SYNTAX] Trying to match method call");
        int checkpoint = currentIndex;
        if (match(TokenType.IDENTIFIER)) {
            if (match(TokenType.PUNCTUATION_PARENTHESIS_OPEN)) {
                if (arguments()) {
                    if (match(TokenType.PUNCTUATION_PARENTHESIS_CLOSE)) {
                        // Agregar a la tabla de símbolos
                        System.out.println("[SYNTAX] 😁😁😁 = Llamada a método correcta => metodo: " + tokens.get(currentIndex - 3).getLexeme() + "\n");
                        return true;
                    } else {
                        System.out.println("Falta un paréntesis cerrado.");
                        return false;
                    }
                } else {
                    System.out.println("Se esperaba un argumento(s) dentro de paréntesis.");
                    return false;
                }
            } else {
                System.out.println("Se esperaba un paréntesis abierto para la llamada a método.");
                currentIndex = checkpoint;
                return false;
            }
        }
        // Si no se cumple la regla, regresar el índice para que se pueda intentar con otra regla
        currentIndex = checkpoint;
        return false;
    }

    private boolean arguments(){
        if (expression()) {
            while (match(TokenType.PUNCTUATION_COMMA)) {
                if (expression()) {
                    // La expresión sigue siendo válida
                } else {
                    return false;
                }
            }
            return true;
        }
        // Si no hay argumentos, la llamada a método es válida también
        return true;
    }


    private boolean expression() {
        System.out.println("[SYNTAX] Trying to match expression");
        if (term()) {
            while (match(TokenType.OPERATOR_ARITHMETIC)) {
                if (term()) {
                    // La expresión sigue siendo válida
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean term() {
        if (
                // Si el termino por alguna razón contiene una nueva línea, no es un término válido
                !match(TokenType.NEW_LINE) &&
                (match(TokenType.IDENTIFIER) || match(TokenType.STRING) || match(TokenType.NUMERIC_CONSTANT_WHOLE) || match(TokenType.NUMERIC_CONSTANT_DECIMAL) || match(TokenType.LOGIC_TRUE) || match(TokenType.LOGIC_FALSE))) {
            return true;
        } else if (match(TokenType.PUNCTUATION_PARENTHESIS_OPEN)) {
            if (expression()) {
                if (match(TokenType.PUNCTUATION_PARENTHESIS_CLOSE)) {
                    return true;
                } else {
                    System.out.println("Falta un paréntesis cerrado.");
                    return false;
                }
            } else {
                System.out.println("Se esperaba un término dentro de paréntesis.");
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean match(TokenType expectedType) {
        // Ignorar tokens de nueva línea a menos que se espere uno (expectedType argument)
        while (currentIndex < tokens.size() && tokens.get(currentIndex).getType() == TokenType.NEW_LINE && tokens.get(currentIndex).getType() != expectedType) {
            currentIndex++;
        }
        // Ignorar comentarios
        while (currentIndex < tokens.size() && tokens.get(currentIndex).getType() == TokenType.COMMENT) {
            currentIndex++;
        }
        if (currentIndex < tokens.size()) {
            System.out.println("[SYNTAX] Trying to match " + expectedType + " with " + tokens.get(currentIndex).getType() + " at index " + currentIndex + " -> " + tokens.get(currentIndex).getLexeme());
        }

        if (currentIndex < tokens.size() && tokens.get(currentIndex).getType() == expectedType) {
            System.out.println("[SYNTAX] ✅ Matched " + expectedType + " with " + tokens.get(currentIndex).getType() + " at index " + currentIndex + " -> " + tokens.get(currentIndex).getLexeme());
            currentIndex++;
            return true;
        }
        return false;
    }

    private boolean tokenIsNotCommentOrNewLine(int index) {
        return tokens.get(index).getType() != TokenType.COMMENT && tokens.get(index).getType() != TokenType.NEW_LINE;
    }

}
