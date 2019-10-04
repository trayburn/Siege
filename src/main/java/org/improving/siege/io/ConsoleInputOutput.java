package org.improving.siege.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInputOutput implements InputOutput {
    private final Scanner scanner;

    public ConsoleInputOutput(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void displayText(String text) {
        System.out.println(text);
    }

    @Override
    public void displayPrompt(String prompt) {
        System.out.print(prompt);
    }

    @Override
    public void displayIndent(String text) {
        this.displayText("  " + text);
    }

    @Override
    public void displayAlert(String text) {
        this.displayText("> " + text);
    }
}
