package edu.robertob.syntaxanalyzerbackend.Utils;

import java.util.Map;
import java.util.Stack;

// Clase que contiene métodos útiles para el análisis léxico, especificamente para el procesamiento de tokens
public class TokenUtils {
    public static boolean isValidIdentifier(String lexeme, Map<String, TokenType> keywords) {
        // Revisando regla por regla
        // Rule 1: It must start with a letter or an underscore
        char firstChar = lexeme.charAt(0);
        if (!Character.isLetter(firstChar) && firstChar != '_') {
            return false;
        }

        // Rule 2: It can only contain letters, numbers, and underscores
        for (int i = 1; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);
            if (!Character.isLetterOrDigit(currentChar) && currentChar != '_') {
                return false;
            }
        }

        // Rule 3: It can't be a keyword
        if (keywords.containsKey(lexeme)) {
            return false;
        }

        return true;
    }

    public static boolean containsLettersOrUnderscores(String lexeme) {
        for (int i = 0; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);
            if (Character.isLetter(currentChar) || currentChar == '_') {
                return true;
            }
        }
        return false;
    }
    // Metodo para verificar si los caracteres de un lexema son validos, es decir dentro del alfabeto
    public static boolean isValidLexeme(String lexeme) {
        for (int i = 0; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);
            if (!isInAlphabet(currentChar)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isInAlphabet(char c) {
        return DefinitionsUtils.alphabet.contains(String.valueOf(c));
    }

    public static boolean isLogicOperator(String lexeme) {
        return lexeme.equals("and") || lexeme.equals("or") || lexeme.equals("not");
    }

    public static boolean isParenthesesBalanced(String input) {
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false; // Unmatched closing parenthesis
                } else {
                    stack.pop(); // Matched closing parenthesis
                }
            }
        }

        return stack.isEmpty(); // If the stack is empty, all parentheses are balanced
    }
}
