package lox;

import java.util.ArrayList;
import java.util.List;

import static lox.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int line = 0;   // line number
    private int current = 0;

    Scanner(String src) {
        this.source = src;
    }

    /**
     * End of the scan means the current character is at the end of the source
     *
     * @return
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }

    /**
     * Scan all the tokens of the source.
     * Add an EOF token at the end
     *
     * @return A list of tokens
     */
    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    /**
     * Identify a token given a TokenType
     * For now, lets scan one character at a time
     */
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;

            // For Comments
            // Note that comments are not added as tokens
            case '/':
                if (match('=')) while (peek() != '\n' && !isAtEnd()) advance();
                else addToken(SLASH);
                break;

            // Ignored characters
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            default:
                Lox.error(line, "Unrecognized character: " + c);
        }
    }

    /**
     * Increment the current "pointer" and return the character that was just passed over
     *
     * @return The next character in the source
     */
    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    /**
     * Add a token given the type and possible literal
     *
     * @param type    The Token Type defined in the TokenType enum
     * @param literal The possible literal value (number/string)
     */
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * Add token with no literal (number/string)
     *
     * @param type The Token Type defined in the TokenType enum
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    /**
     * If the current character matches the expected, move on to the next character and return true
     * else return false
     *
     * @param expected The expected character
     * @return True if current character matches expected character
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;

    }

    /**
     * Return the current character without "consuming it" (advance())
     *
     * @return
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }


}
