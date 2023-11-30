package aoc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.junit.jupiter.api.Assertions;

public class TestUtil {

    public static final String INPUT_FILE = "input.txt";

    public static final String SAMPLE_INPUT_FILE = "sample_input.txt";

    public static final String RESULT_MESSAGE = "Result for task %s is:\n%s \n";

    private static final String AOC_URL_BASE = "https://adventofcode.com/2022/day/%s/";
    private static final String AOC_URL_INPUT = AOC_URL_BASE + "input";
    private static final String AOC_URL_ANSWER = AOC_URL_BASE + "answer";

    private static final String COOKIE_KEY = "cookie";
    private static final String COOKIE_VALUE = "session=" + System.getProperty("session", "no_session");
    private static final String NO_COOKIE = "session=no_session";

    private static final String USER_AGENT_KEY = "user-agent";
    private static final String USER_AGENT_VALUE = "mihail-m's aoc java lib https://github.com/mihail-m/AdventOfCode2022";

    private static final String ANSWER_KEY = "answer";
    private static final String LEVEL_KEY = "level";

    private static final String CORRECT_ANSWER = "one gold star";

    private static final String ALREADY_SOLVED = "You don't seem to be solving the right level";

    private static final String TESTS_LOCATION_PREFIX = "src/test/java/input/files/";

    public static Scanner openFile(Class<?> caller, String inputFile) {
        return createScanner(TESTS_LOCATION_PREFIX + getDayFolder(caller) + inputFile, caller, true);
    }

    public static void postAndValidateResult(Class<?> caller, String result, String level) {
        if (noCookie()) {
            return;
        }

        System.out.println("Posting result for validation...");

        String day = getDay(caller);
        if (day.charAt(0) == '0') {
            day = day.substring(1);
        }

        HttpPost request = new HttpPost(String.format(AOC_URL_ANSWER, day));
        addHeaders(request);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(ANSWER_KEY, result));
        params.add(new BasicNameValuePair(LEVEL_KEY, level));

        try {
            request.setEntity(new UrlEncodedFormEntity(params));
        } catch (Exception ex) {
            Assertions.fail("Error setting request body.", ex);
        }

        String response = getRequestResponse(request);
        Assertions.assertTrue(response.contains(CORRECT_ANSWER) || response.contains(ALREADY_SOLVED));
        System.out.println("Answer is correct!");
    }

    private static Scanner createScanner(String fileLocation, Class<?> caller, boolean retry) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileLocation));
        } catch (Exception ex) {
            if (ex instanceof FileNotFoundException && retry) {
                getInputFile(caller);
                return createScanner(fileLocation, caller, false);
            } else {
                Assertions.fail(String.format("Error opening file: %s.", fileLocation), ex);
            }
        }
        return scanner;
    }

    private static void getInputFile(Class<?> caller) {
        if (noCookie()) {
            return;
        }

        System.out.println("Retrieving input information...");

        String day = getDay(caller);
        if (day.charAt(0) == '0') {
            day = day.substring(1);
        }

        HttpGet request = new HttpGet(String.format(AOC_URL_INPUT, day));
        addHeaders(request);

        writeFile(getDay(caller), getRequestResponse(request));
    }

    private static void writeFile(String day, String input) {
        String dir = TESTS_LOCATION_PREFIX + "day" + day;
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                System.out.println("Input file directory NOT created.");
            }
        }

        String fileLocation = dir + "/" + INPUT_FILE;
        try {
            Files.write(Paths.get(fileLocation), input.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Input file saved at: " + fileLocation);
        } catch (IOException ex) {
            System.out.println("Error creating input file." + ex.getMessage());
        }
    }

    private static String getRequestResponse(HttpUriRequest request) {
        String response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpEntity entity = httpClient.execute(request).getEntity();

            if (entity == null) {
                Assertions.fail("Response entity is null.");
            }

            response = EntityUtils.toString(entity);
        } catch (IOException ex) {
            Assertions.fail("Error creating Http client.", ex);
        }
        return response;
    }

    private static String getDayFolder(Class<?> caller) {
        return caller.getSimpleName()
                .replace("Test", "")
                .toLowerCase() + "/";
    }

    private static String getDay(Class<?> caller) {
        return getDayFolder(caller)
                .replace("day", "")
                .replace("/", "");
    }

    private static boolean noCookie() {
        if (COOKIE_VALUE.equals(NO_COOKIE)) {
            System.out.println("No session provided.");
            System.out.println("To create input file and/or post result run test with: -Dsession=... option provided.");
            return true;
        }
        return false;
    }

    private static void addHeaders(HttpRequest request) {
        request.addHeader(COOKIE_KEY, COOKIE_VALUE);
        request.addHeader(USER_AGENT_KEY, USER_AGENT_VALUE);
    }
}
