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
    List<Token> scanTokens() {
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
            default:
                Lox.error(line, "Unrecognized character");
        }
    }

    /**
     * Increment the current "pointer" and return the next character
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


}
