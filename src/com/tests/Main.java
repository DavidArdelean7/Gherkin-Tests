package com.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(JSONPlaceholderAPITests.class);

        System.out.println("Number of JSON Placeholder API tests run: " + result.getRunCount());
        System.out.println("Number of JSON Placeholder API tests failed: " + result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }

        System.out.println("All JSON Placeholder API tests passed: " + result.wasSuccessful());

        result = JUnitCore.runClasses(RestfulBookerAPITests.class);

        System.out.println("Number of Restful booker API tests run: " + result.getRunCount());
        System.out.println("Number of Restful booker API tests failed: " + result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure: " + failure.toString());
        }

        System.out.println("All Restful booker API tests passed: " + result.wasSuccessful());

    }
}