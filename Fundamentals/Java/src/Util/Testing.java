package Util;

public class Testing<Item> {
    /**
     * If the test is failed
     * @param expected Expected output
     * @param result Actual output
     * @param methodName Name of the method with the failed test.
     */
    private void testFailed(Item expected, Item result, String methodName, String errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Test failed.\n" +
                "Expected: \"" + expected + "\"\n" +
                "Got     : \"" + result + "\"\n" +
                "Method: " + methodName);
        if (errorMessage.length() > 0) {
            sb.append("\nErrorMessage: " + errorMessage);
        }
        System.out.println(sb.toString());
        System.exit(0);
    }

    /**
     * Checks if the test is successful
     * @param expected Expected output
     * @param result Actual output
     * @param methodName Name of the method being tested
     */
    public void testResult (Item expected, Item result, String methodName, String errorMessage) {
        if (!(result.equals(expected))) {
            testFailed(expected, result, methodName, errorMessage);
        }
    }
}
