package com.banking.utility;

import java.util.concurrent.ThreadLocalRandom;

/** Generates random 10-digit account numbers. Uniqueness is enforced by the DB primary key. */
public final class AccountNumberGenerator {

    private AccountNumberGenerator() { }

    public static long generate() {
        return ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
    }
}
