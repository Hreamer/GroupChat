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
import java.net.Socket;
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

        //server class declaration test
        @Test(timeout = 1000)
        public void ServerClassDecTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;

            clazz = ServerThread.class;
            className = "Server";

            // Perform tests

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));


        }

        //server port number decleration test
        @Test(timeout = 1000)
        public void portNumberDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "PORT";
            Class<?> expectedType = int.class;

            clazz = Server.class;

            try {

                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();
            modifiers = testField.getModifiers();
            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
            Assert.assertTrue("Ensure that `" + className
                    + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertTrue("Ensure that `" + className
                    + "`'s `" + fieldName + "` field is `final`!", Modifier.isFinal(modifiers));

        }

        //client.java method tests
        @Test(timeout = 1000)
        public void createAccDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "createAccount";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        "has 2 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        @Test(timeout = 1000)
        public void getValidAccDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "getValidAccount";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        "has 2 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        @Test(timeout = 1000)
        public void createCo() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "createCo";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 0 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void connectUsersDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "connectUsers";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void checkDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "check";
            clazz = Client.class;
            Class<?> expectedReturnType = boolean.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void serverClientConnectionDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;


            String methodName = "serverClient";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 0 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void sendNewMessageDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "sendNewMessage";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void updateJListDec() {
            Class<?> clazz;
            String className = "Client.java";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "updateJList";
            clazz = Client.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //userAccount method tests
        @Test(timeout = 1000)
        public void getUserName() {
            Class<?> clazz;
            String className = "UserAccount";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "getUserName";
            clazz = UserAccount.class;
            Class<?> expectedReturnType = String.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //serverThread constructor check
        @Test(timeout = 1_000)
        public void serverThreadConstructorDecTest() {

            Class<?> clazz = ServerThread.class;
            String className = "ServerThread";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 0;

            try {
                constructor = clazz.getDeclaredConstructor(Socket.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has one parameter with type Socket!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            exceptions = constructor.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is `public`!", Modifier.isPublic(modifiers));

        }

        //serverThread method tests
        @Test(timeout = 1000)
        public void serverRunMethodRun() {
            Class<?> clazz;
            String className = "ServerThread";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "run";
            clazz = ServerThread.class;
            Class<?> expectedReturnType = void.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }

        //serverThread method test
        @Test(timeout = 1000)
        public void isValidLoginDec() {
            Class<?> clazz;
            String className = "ServerThread";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;


            String methodName = "isValidLogin";
            clazz = ServerThread.class;
            Class<?> expectedReturnType = String.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName +
                    "` method throws 1 exception", expectedLength, exceptions.length);

        }

        //ServerThread method test
        @Test(timeout = 1000)
        public void addUserDec() {
            Class<?> clazz;
            String className = "ServerThread";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;


            String methodName = "addUser";
            clazz = ServerThread.class;
            Class<?> expectedReturnType = boolean.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 parameters!");
                return;
            } //end try catch

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();


            Assert.assertTrue("Ensure that `" + className + "`'s `"
                    + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" +
                    methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);

        }
        //ServerThread method test
        /**
         @Test(timeout = 1000)
         public void writeToFileDec() {
         Class<?> clazz;
         String className = "ServerThread";
         Method method;
         int modifiers;
         Class<?> actualReturnType;
         int expectedLength = 0;
         Class<?>[] exceptions;


         String methodName = "writeToFile";
         clazz = ServerThread.class;
         Class<?> expectedReturnType = void.class;

         // Attempt to access the class method
         try {
         method = clazz.getDeclaredMethod(methodName, String.class, ArrayList.class);
         } catch (NoSuchMethodException e) {
         Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
         " has 2 parameters!");
         return;
         } //end try catch

         modifiers = method.getModifiers();

         actualReturnType = method.getReturnType();


         Assert.assertTrue("Ensure that `" + className + "`'s `"
         + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
         Assert.assertEquals("Ensure that `" + className + "`'s `" +
         methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
         }
         **/
        //Server thread method test declaration test
        /**
         @Test(timeout = 1000)
         public void getMembersDec() {
         Class<?> clazz;
         String className = "ServerThread";
         Method method;
         int modifiers;
         Class<?> actualReturnType;
         int expectedLength = 0;
         Class<?>[] exceptions;


         String methodName = "getMembers";
         clazz = ServerThread.class;
         Class<?> expectedReturnType = ArrayList.class;

         // Attempt to access the class method
         try {
         method = clazz.getDeclaredMethod(methodName, String.class);
         } catch (NoSuchMethodException e) {
         Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
         " has 1 parameters!");
         return;
         } //end try catch

         modifiers = method.getModifiers();

         actualReturnType = method.getReturnType();

         Assert.assertTrue("Ensure that `" + className + "`'s `"
         + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
         Assert.assertEquals("Ensure that `" + className + "`'s `" +
         methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
         }
         **/
        //ServerThread method declaration test
        /**
         @Test(timeout = 1000)
         public void deleteConvoDec() {
         Class<?> clazz;
         String className = "ServerThread";
         Method method;
         int modifiers;
         Class<?> actualReturnType;
         int expectedLength = 0;
         Class<?>[] exceptions;


         String methodName = "deleteConvo";
         clazz = ServerThread.class;
         Class<?> expectedReturnType = void.class;

         // Attempt to access the class method
         try {
         method = clazz.getDeclaredMethod(methodName, String.class, String.class);
         } catch (NoSuchMethodException e) {
         Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
         " has 2 parameters!");
         return;
         } //end try catch

         modifiers = method.getModifiers();

         actualReturnType = method.getReturnType();


         Assert.assertTrue("Ensure that `" + className + "`'s `"
         + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
         Assert.assertEquals("Ensure that `" + className + "`'s `" +
         methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
         }
         **/
        //SeverThread method declaration test
        /**
         @Test(timeout = 1000)

         public void createConvoDec() {
         Class<?> clazz;
         String className = "ServerThread";
         Method method;
         int modifiers;
         Class<?> actualReturnType;
         int expectedLength = 1;
         Class<?>[] exceptions;


         String methodName = "createConvo";
         clazz = ServerThread.class;
         Class<?> expectedReturnType = void.class;

         // Attempt to access the class method
         try {
         method = clazz.getDeclaredMethod(methodName, ArrayList.class);
         } catch (NoSuchMethodException e) {
         Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
         " has 1 parameters!");
         return;
         } //end try catch

         modifiers = method.getModifiers();

         actualReturnType = method.getReturnType();

         Assert.assertTrue("Ensure that `" + className + "`'s `"
         + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
         Assert.assertEquals("Ensure that `" + className + "`'s `" +
         methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
         }
         **/
        //ServerThread method declaration test

        /**
         * @Test(timeout = 1000)
         * public void sendMessageDec() {
         * Class<?> clazz;
         * String className = "ServerThread";
         * Method method;
         * int modifiers;
         * Class<?> actualReturnType;
         * int expectedLength = 0;
         * Class<?>[] exceptions;
         * <p>
         * <p>
         * String methodName = "sendMessage";
         * clazz = ServerThread.class;
         * Class<?> expectedReturnType = void.class;
         * <p>
         * // Attempt to access the class method
         * try {
         * method = clazz.getDeclaredMethod(methodName, String.class, String.class);
         * } catch (NoSuchMethodException e) {
         * Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
         * " has 2 parameters!");
         * return;
         * } //end try catch
         * <p>
         * modifiers = method.getModifiers();
         * <p>
         * actualReturnType = method.getReturnType();
         * <p>
         * <p>
         * Assert.assertTrue("Ensure that `" + className + "`'s `"
         * + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
         * Assert.assertEquals("Ensure that `" + className + "`'s `" +
         * methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
         * }
         **/
        //class tests
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
            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `" + className + "` extends `Object`!", Object.class, superclass);

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
            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `" + className + "` extends `JFrame`!", JFrame.class, superclass);

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

            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));

            Assert.assertEquals("Ensure that `" + className + "` extends `Thread`!", Thread.class, superclass);

        }

        //field declaration tests
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
            } catch (NoSuchFieldException e) {
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
            } catch (NoSuchFieldException e) {
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
            } catch (NoSuchFieldException e) {
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
            } catch (NoSuchFieldException e) {
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
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);

        }

        @Test(timeout = 1000)
        public void testfileNameDeclaration() {
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
            } catch (NoSuchFieldException e) {
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
        public void testConversationsFileDeclaration() {
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
            } catch (NoSuchFieldException e) {
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
        public void testSocketDeclaration() {
            String className = "ServerThread";
            Class<?> clazz;
            Field testField;
            int modifiers;
            Class<?> type;

            String fieldName = "socket";
            Class<?> expectedType = Socket.class;

            clazz = ServerThread.class;

            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            }

            type = testField.getType();
            modifiers = testField.getModifiers();

            Assert.assertEquals("Ensure that `" +
                    className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName
                    + "` field is `private`!", Modifier.isPrivate(modifiers));

        }

    }

}
