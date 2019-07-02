package lox;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal; // For the actual numbers/strings.  Convert to actual type later
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
