package utilities;

public class TestLogger {
    private static final String LINE = "===================================================";

    public static void start(String testName) {
        Logs.info("");
        Logs.info(LINE);
        Logs.info("🟡 TEST STARTED: " + testName);
        Logs.info(LINE);
    }

    public static void pass(String testName) {
        Logs.info("✅ TEST PASSED: " + testName);
        Logs.info(LINE);
        Logs.info("");
    }

    public static void fail(String testName) {
        Logs.info("❌ TEST FAILED: " + testName);
        Logs.info(LINE);
        Logs.info("");
    }

}
