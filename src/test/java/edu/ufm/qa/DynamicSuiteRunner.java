package edu.ufm.qa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import edu.ufm.qa.tests.CartTest;
import edu.ufm.qa.tests.LoginTest;

/** testng.xml built in code. -Drun.groups=smoke (default) or regression. */
public class DynamicSuiteRunner {
    public static void main(String[] args) throws IOException {
        String input = System.getProperty("run.groups", "smoke");
        List<String> groups = List.of(input.split(","));

        XmlSuite suite = new XmlSuite();
        suite.setName("dynamic-" + String.join("-", groups));
        suite.setParallel(XmlSuite.ParallelMode.CLASSES);
        suite.setThreadCount(2);
        suite.setParameters(Map.of("browser", "chrome"));

        XmlTest test = new XmlTest(suite);
        test.setName("web");
        test.setIncludedGroups(groups);
        test.setXmlClasses(List.of(
                new XmlClass(LoginTest.class), new XmlClass(CartTest.class)));

        Path out = Path.of("build", "testng-dynamic.xml");
        Files.createDirectories(out.getParent());
        Files.writeString(out, suite.toXml());

        TestNG testng = new TestNG();
        testng.setXmlSuites(List.of(suite));
        testng.run();
        if (testng.getStatus() != 0) {
            throw new IllegalStateException("suite failed: " + out);
        }
    }
}
