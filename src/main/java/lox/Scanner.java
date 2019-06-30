package lox;

import java.util.ArrayList;
import java.util.List;

import static lox.TokenType.EOF;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int line = 0;
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
     */
    private void scanToken() {

    }


}
