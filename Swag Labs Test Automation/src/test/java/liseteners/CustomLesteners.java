package liseteners;

import org.testng.IExecutionListener;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;

public class CustomLesteners implements ITestListener, IExecutionListener , IInvokedMethodListener {
    String deleteJsonCommand = "cmd /c cd target/allure-results && del *.json";
    String allureReportGenerate = "cmd /c allure generate target/allure-results -o reports/ --clean --single-file";
    public void onExecutionStart() {
        // not implemented
        executeCommand(deleteJsonCommand);
    }


    /** Invoked once all the suites have been run. */
    public void onExecutionFinish() {
        // not implemented
        executeCommand(allureReportGenerate);
    }
    private static void executeCommand(String CMD) {

        try {
            Process process = Runtime.getRuntime().exec(CMD);
            int exitcode = process.waitFor();
            if (exitcode == 0) {
                System.out.println("Process has been executed successfully");
            }else {
                System.out.println("Process has been failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
