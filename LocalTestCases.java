import junit.framework.TestCase;
import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.Scanner;
import java.util.Random;
import static org.junit.Assert.*;

public class LocalTestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }
        @Test(timeout = 1000)
        public void UserAccountClassDecTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;

            clazz = UserAccount.class;
            className = "UserAccount";

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();
            Assert.assertTrue("Ensure that `"+ className +"` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `"+ className +"` extends `Object`!", Object.class, superclass);

        }
        @Test(timeout = 1000)
        public void ClientClassDecTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;

            clazz = Client.class;
            className = "Client";

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();
            Assert.assertTrue("Ensure that `"+ className +"` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `"+ className +"` extends `JFrame`!", JFrame.class, superclass);

        }

        @Test(timeout = 1000)
        public void ServerThreadClassDecTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;

            clazz = ServerThread.class;
            className = "ServerThread";

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            Assert.assertTrue("Ensure that `"+ className +"` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `"+ className +"` extends `Thread`!", Thread.class, superclass);

        }
        @Test(timeout = 1000)
        public void testUserNameDeclaration() {
            String className = "UserAccount";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "userName";
            Class<?> expectedType = String.class;

            clazz = UserAccount.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }
            modifiers = testField.getModifiers();

            type = testField.getType();
            Assert.assertTrue("Ensure that `" + className
                    + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        @Test(timeout = 1000)
        public void testPasswordDeclaration() {
            String className = "UserAccount";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "password";
            Class<?> expectedType = String.class;

            clazz = UserAccount.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }
            modifiers = testField.getModifiers();

            type = testField.getType();
            Assert.assertTrue("Ensure that `" + className
                    + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertEquals("Ensure that `"
                    + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);

        }
        @Test(timeout = 1000)
        public void testCoDeclaration() {
            String className = "UserAccount";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "co";
            Class<?> expectedType = ArrayList.class;

            clazz = UserAccount.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }
            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className
                    + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);

        }
        @Test(timeout = 1000)
        public void testUsersArrDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "usernames";
            Class<?> expectedType = ArrayList.class;

            clazz = ServerThread.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);

        }
        @Test(timeout = 1000)
        public void testPassArrDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "passwords";
            Class<?> expectedType = ArrayList.class;

            clazz = ServerThread.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);

        }
        @Test(timeout = 1000)
        public void testfilenameDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "userFile";
            Class<?> expectedType = String.class;

            clazz = ServerThread.class;

            try {
                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();
            modifiers = testField.getModifiers();
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName +
                    "` field is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName
                    + "` field is `private`!", Modifier.isFinal(modifiers));

        }
        @Test(timeout = 1000)
        public void testconversationsFileDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "conversationsFile";
            Class<?> expectedType = String.class;

            clazz = ServerThread.class;

            try {
                testField = clazz.getDeclaredField(fieldName);
            }  catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();
            modifiers = testField.getModifiers();
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName +
                    "` field is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName
                    + "` field is `private`!", Modifier.isFinal(modifiers));

        }

    }

}
