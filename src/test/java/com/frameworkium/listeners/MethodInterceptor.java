package com.frameworkium.listeners;

import static com.frameworkium.config.SystemProperty.JIRA_URL;
import static com.frameworkium.config.SystemProperty.JQL_QUERY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frameworkium.annotations.Issue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import com.frameworkium.jira.api.SearchIssues;

public class MethodInterceptor implements IMethodInterceptor {

    private static final Logger logger = LogManager.getLogger(MethodInterceptor.class);

    @Override
    public List<IMethodInstance> intercept(
            List<IMethodInstance> methods, ITestContext context) {
        return filterTestsToRunBasedOnJQL(methods);
    }

    private List<IMethodInstance> filterTestsToRunBasedOnJQL(List<IMethodInstance> methods) {
        List<IMethodInstance> methodsToRun = new ArrayList<>();

        // gets list of tests to run from JIRA based on the JQL
        if (JQL_QUERY.isSpecified() && JIRA_URL.isSpecified()) {
            logger.info("Overriding specified tests to run with JQL query results");

            Map<String, IMethodInstance> testMethods = new HashMap<>();
            for (IMethodInstance instance : methods) {
                Issue issue =
                        instance.getMethod().getConstructorOrMethod()
                                .getMethod().getAnnotation(Issue.class);
                if (null != issue) {
                    String issueKey = issue.value();
                    testMethods.put(issueKey, instance);
                }
            }

            List<String> issueKeysBasedOnJQL =
                    new SearchIssues(JQL_QUERY.getValue()).getKeys();
            for (String issueKey : issueKeysBasedOnJQL) {
                IMethodInstance method = testMethods.get(issueKey);
                if (null != method) {
                    methodsToRun.add(method);
                    logger.debug(String.format("Adding %s", issueKey));
                } else {
                    logger.error("Key cannot find test for issueKey " + issueKey);
                }
            }

            logger.info(String.format("Running %d tests specified by query...", methodsToRun.size()));
        } else {
            methodsToRun.addAll(methods);
        }
        return methodsToRun;
    }
}
