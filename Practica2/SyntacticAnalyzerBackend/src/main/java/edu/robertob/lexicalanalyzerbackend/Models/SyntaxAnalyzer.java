package edu.robertob.lexicalanalyzerbackend.Models;

import edu.robertob.lexicalanalyzerbackend.Utils.TokenType;

import java.util.List;

public class SyntaxAnalyzer {

    private List<Token> tokens;
    private int currentIndex;
    public void analyzeTokens(List<Token> tokensToAnalyze) {
        this.tokens = tokensToAnalyze;
        this.currentIndex = 0;
        parse();
    }

    public SyntaxAnalyzer() {
        this.currentIndex = 0;
    }

    public void parse() {
        while (currentIndex < tokens.size()) {
            assignment();
        }
//        assignment();
//        if (this.currentIndex < tokens.size()) {
//            // Si quedan tokens después de la asignación, hay un error.
//            System.out.println("Error de sintaxis, tokens inesperados o hay comentarios");
//        } else {
//            System.out.println("Análisis sintáctico (parece) correcto");
//        }
    }

    private boolean assignment() {
        String id;
        String value = "";
        int indexFromAssToken = 0;
        if (match(TokenType.IDENTIFIER)) {
            id = tokens.get(currentIndex - 1).getLexeme();
            if (match(TokenType.OPERATOR_ASSIGNMENT) || match(TokenType.OPERATOR_ASSIGNMENT_SUM) || match(TokenType.OPERATOR_ASSIGNMENT_SUB) || match(TokenType.OPERATOR_ASSIGNMENT_MUL) || match(TokenType.OPERATOR_ASSIGNMENT_DIV)) {
                indexFromAssToken = currentIndex;
                if (expression()) {
                    // Get the value of the expression by going from the token agter the assignment token to the token before the current index
                    for (int i = indexFromAssToken; i < currentIndex - 1; i++) {
                        value += tokens.get(i).getLexeme();
                    }

                    System.out.println("[SYNTAX] 😁 = Asignación correcta => id: " + id + " | valor: " + value + "\n");
                    return true;
                }
            } else {
                System.out.println("Se esperaba un '=' después del identificador.");
                return false;
            }
        } else {
            if (!tokens.get(currentIndex).getType().equals(TokenType.COMMENT) || !tokens.get(currentIndex).getType().equals(TokenType.NEW_LINE)) {
                System.out.println("Se esperaba un identificador.");
                currentIndex++;
            }
        }
        return false;
    }

//    private void expression() {
//        term();
//        while (match(TokenType.OPERATOR_ARITHMETIC)) {
//            term();
//        }
//    }

    private boolean expression() {
        if (term()) {
            while (match(TokenType.OPERATOR_ARITHMETIC)) {
                System.out.println("Se encontró un operador aritmético.");
                if (!term()) {
                    System.out.println("Se esperaba un término después del operador aritmético.");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean term() {
        if (match(TokenType.IDENTIFIER) || match(TokenType.STRING) || match(TokenType.NUMERIC_CONSTANT_WHOLE) || match(TokenType.NUMERIC_CONSTANT_DECIMAL) || match(TokenType.LOGIC_TRUE) || match(TokenType.LOGIC_FALSE)) {
            return true;
        } else if (match(TokenType.PUNCTUATION_PARENTHESIS_OPEN)) {
            if (expression()) {
                return true;
            }

            if (match(TokenType.OPERATOR_ARITHMETIC)) {
                if (expression()) {
                    return true;
                }
            } else {
                System.out.println("Se esperaba un operador después de la expresión dentro de paréntesis.");
                return false;
            }

            if (!match(TokenType.PUNCTUATION_PARENTHESIS_CLOSE)) {
                System.out.println("Falta un paréntesis cerrado.");
                return false;
            }
        } else {
            System.out.println("Error de sintaxis en el término.");
            return false;
        }
        return false;
    }


//    private void term() {
//        if (match(TokenType.IDENTIFIER) || match(TokenType.STRING) || match(TokenType.NUMERIC_CONSTANT_WHOLE) || match(TokenType.NUMERIC_CONSTANT_DECIMAL) || match(TokenType.LOGIC_TRUE) || match(TokenType.LOGIC_FALSE)) {
////            return true;
//        } else if (match(TokenType.PUNCTUATION_PARENTHESIS_OPEN)) {
//            expression();
//            if (match(TokenType.OPERATOR_ARITHMETIC)) {
//                expression();
//            } else {
//                System.out.println("Se esperaba un operador después de la expresión dentro de paréntesis.");
//            }
//            if (!match(TokenType.PUNCTUATION_PARENTHESIS_CLOSE)) {
//                System.out.println("Falta un paréntesis cerrado.");
//            }
//        } else {
//            System.out.println("Error de sintaxis en el término.");
////            return false;
//        }
//    }
    private boolean match(TokenType expectedType) {
        // Ignorar tokens de nueva línea
        while (currentIndex < tokens.size() && tokens.get(currentIndex).getType() == TokenType.NEW_LINE) {
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
}
