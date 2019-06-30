package lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Lox {
    static boolean hasError;

    public static void main(String args[]) throws IOException {
        hasError = false;
        if (args.length > 1){
            System.out.println("USAGE: Lox <script>");
            System.exit(1);
        }
        if(args.length == 1){
            runFile(args[0]);
        }else{
            runPrompt();
        }

    }

    /**
     * Interpret the file
     * @param filePath Path to the file
     * @throws IOException
     */
    private static void runFile(String filePath) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        run(new String(bytes, Charset.defaultCharset()));

        if(hasError){
            System.exit(2);
        }

    }

    private static void run(String src){
        Scanner scanner = new Scanner(src);
        scanner.tokens().forEach(System.out::println);
    }

    static void error(int line, String msg){
        report(line, "unknown", msg);
    }

    private static void report(int line, String filename, String msg){
        System.err.println("[line " + line + "]  Error: " + filename + ": " + msg);
        hasError = true;
    }

    /**
     * Run REPL
     */
    private static void runPrompt() throws IOException{
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        for(;;){
            System.out.print("> ");
            run(reader.readLine());
            hasError = false;

        }

    }
}
