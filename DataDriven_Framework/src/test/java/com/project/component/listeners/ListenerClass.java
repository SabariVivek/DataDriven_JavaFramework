package com.project.component.listeners;

import com.project.component.miscellaneous.JiraOperations;
import com.project.utils.ConfigLoader;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ListenerClass extends TestListenerAdapter {

    JiraOperations jiraOps = new JiraOperations();

    /**
     * Jira issue creation code
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String automaticIssueCreationInJira = ConfigLoader.getInstance().getAutomaticIssueCreation_ONorOFF();
        if (automaticIssueCreationInJira.trim().equalsIgnoreCase("ON")) {
            String issueSummary = "Automation TestClass Failed - " + result.getMethod().getMethodName();
            String issueDescription = "TestClass data to be passed here...";
            String issueNumber = null;
            try {
                issueNumber = jiraOps.createJiraIssue("10000", "10005", issueSummary, issueDescription, "3", "Spotify", "60a54c315d67f2006960fc83");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println("Jira - Defect ID : " + issueNumber);
        }
    }
}