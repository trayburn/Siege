package org.improving.siege.io;

public interface InputOutput {
    String getInput();
    void displayText(String text);
    void displayPrompt(String prompt);

    void displayIndent(String text);
    void displayAlert(String text);
}
