package fr.uge.info2.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void should_not_get_error() {
        var args = new String[]{"File"};
        assertDoesNotThrow(() -> Main.main(args));
    }
}