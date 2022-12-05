package advent.util;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
public class TestUtil {

    static final String TESTS_LOCATION_PREFIX = "src/test/java/input/files/";

    public static Scanner openFile(Class<?> caller, String fileName) {
        String dayFolder = caller.getSimpleName()
                .replace("Test", "")
                .toLowerCase() + "/";

        String fileLocation = TESTS_LOCATION_PREFIX + dayFolder + fileName;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileLocation));
        } catch (Exception ex) {
            Assertions.fail(String.format("Error opening file: %s. Error: %s", fileLocation, ex.getMessage()));
        }
        return scanner;
    }
}
