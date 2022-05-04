package Paurus;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class Main {
    public static WebDriver driver = new ChromeDriver();
    static final ArrayList<Employee> listOfEmployees = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        //Login to site, go to the Employees URL.
        loginToSite();

        //Get data from URL
        //Put data from JSON array to ArrayList object.
        jsonDataToArrayList();

        //Add employees to the site.
        addEmployees();
    }
    public static void loginToSite() {
        try {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); //Path to chromedriver
            driver.get("https://trial-xz3pqp.trial.operations.dynamics.com/"); //Which site(link) to open

            //We make sure that chrome is 100% zoomed
            driver.manage().window().maximize();

            //Getting textField for username and password from html
            WebElement username = driver.findElement(By.cssSelector("#i0116"));
            WebElement password = driver.findElement(By.cssSelector("#i0118"));

            //Send information to textFields
            username.sendKeys("dejan@paurus.si");
            password.sendKeys("L3tsT3st");

            // We wait that element is ready to be clickable, otherwise it won't be clicked.
            waitUntilClickable("#idSIButton9");
            waitUntilClickable("#idSIButton9");
            waitUntilClickable("#idSIButton9");

            driver.get("https://trial-xz3pqp.trial.operations.dynamics.com/?cmp=DAT&mi=HcmWorkerListPage_Employees");
            waitUntilClickable("#hcmworkerlistpage_employees_1_SystemDefinedViewEditButton_label");

        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
        }
    }
    public static void waitUntilClickable(String cssSelector) {
        //This method waits for a duration until element is ready to be clickable
        //Input is selector path of a button
        WebDriverWait waitDuration = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement elementReady = waitDuration.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementReady);
    }
    public static void jsonDataToArrayList() {
        //Get json data from JSON array
        //Then add new object and add it to ArrayList
        JSONArray jArray = GetData.getObjectData();

        for (int i = 0; i < jArray.size(); i++) {
            JSONObject jObject = (JSONObject) jArray.get(i);
            String id = (String.valueOf(jObject.get("id")));
            String FirstName = (String) jObject.get("first_name");
            String LastName = (String) jObject.get("last_name");
            String email = (String) jObject.get("email");

            Employee empl = new Employee(id, FirstName, LastName, email);
            listOfEmployees.add(empl);
        }
    }
    public static void addEmployees() throws InterruptedException {
        //Add Employees from ArrayList<Employees> to the site.
        String employmentDate = "5/28/2022 12:40:00 AM";
        for (int i = 0; i < listOfEmployees.size(); i++) {
            waitUntilClickable("#hcmworkerlistpage_employees_1_HRNew_Worker_label");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            WebElement element_FirstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("FirstName")));
            WebElement element_LastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LastName")));
            WebElement element_EmploymentStartDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("EmploymentStartDate")));

            element_FirstName.sendKeys(listOfEmployees.get(i).getFirstName());
            element_LastName.sendKeys(listOfEmployees.get(i).getLastName());
            element_EmploymentStartDate.sendKeys(employmentDate);

            int ChangingIndex = 5 + i;

            waitUntilClickable("#HcmWorkerNewWorker_" + ChangingIndex + "_PositionDetailsTab_caption");
            //Thread.sleep(3000);
            waitUntilClickable("#HcmWorkerNewWorker_" + ChangingIndex + "_OkNoRedirect");
            //Thread.sleep(3000);
        }
    }
}
