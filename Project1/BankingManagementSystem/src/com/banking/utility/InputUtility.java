package com.banking.utility;

import java.math.BigDecimal;
import java.util.Scanner;

/** Console input helpers. Re-prompts on malformed input instead of crashing. */
public class InputUtility {

    private final Scanner scanner = new Scanner(System.in);

    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /** Returns the raw line (may be empty). */
    public String readOptional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public BigDecimal readAmount(String prompt) {
        while (true) {
            try {
                return new BigDecimal(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount (for example 2500.50).");
            }
        }
    }
}
