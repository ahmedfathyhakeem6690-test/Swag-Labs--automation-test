# Swag Labs – UI Test Automation

UI test automation project for the [Swag Labs (Sauce Demo)](https://www.saucedemo.com/) web application, built with **Java**, **Selenium WebDriver** and **TestNG**, using the **Page Object Model** and **data-driven testing** with JSON.

## Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21 | Programming language |
| Selenium WebDriver | 4.49.0 | Browser automation |
| TestNG | 7.12.0 | Test runner and assertions |
| Jackson Databind | 2.17.2 | Reading JSON test data |
| Maven | – | Build and dependency management |

## What Is Covered

### Login (`LoginTest`)

Each row in `testData.json` is executed as a separate test:

| Username | Password | Expected result |
|----------|----------|-----------------|
| `standard_user` | `secret_sauce` | Redirected to the inventory page |
| `locked_out_user` | `secret_sauce` | Error: *Sorry, this user has been locked out.* |
| `wrong_user` | `123` | Error: *Username and password do not match any user in this service* + error icon displayed |
| `standard_user` | *(empty)* | Error: *Password is required* |

### Inventory (`InventoryTest`)

After a successful login:

- URL contains `inventory.html`
- Page header contains "Swag Labs"
- Cart icon is displayed
- Exactly 6 products are listed

## Project Structure

```
src
├── main
│   ├── java
│   │   ├── Pages          # Page Objects (LoginPage, InventoryPage)
│   │   └── utils          # DataDriven – JSON data reader
│   └── resources
│       └── testdata       # testData.json
└── test
    └── java
        ├── base           # BaseTest – browser setup and teardown
        └── Tests          # LoginTest, InventoryTest
runtest.xml                # TestNG suite file
pom.xml
```

## Design Notes

- **Page Object Model:** locators and page actions live in `Pages`, tests contain only scenarios and assertions.
- **Explicit waits:** `WebDriverWait` with `ExpectedConditions` instead of fixed sleeps.
- **Data-driven:** login scenarios are defined in JSON and fed to tests through a TestNG `@DataProvider`.
- **Soft assertions:** `SoftAssert` is used so one test reports all of its failed checks together.
- **Clean browser per test:** a new browser is started in `@BeforeMethod` and always closed in `@AfterMethod`.

## Prerequisites

- JDK 21
- Maven 3.8+
- Google Chrome (Selenium Manager downloads the matching driver automatically)

## How to Run

```bash
# clone the repository
git clone https://github.com/ahmedfathyhakeem6690-test/Swag-Labs--automation-test.git
cd "Swag-Labs--automation-test/Swag Labs Test Automation/untitled"

# run all tests
mvn test

# or run through the TestNG suite file
mvn test -Dsurefire.suiteXmlFiles=runtest.xml
```

Tests can also be run from IntelliJ IDEA by right-clicking `runtest.xml` and choosing **Run**.

> The test data file is read using a relative path, so run the tests from the project root (the folder that contains `pom.xml`).

## Planned Improvements

- Cart and checkout scenarios
- Logout and product sorting scenarios
- Screenshot on failure
- Allure reporting
- Run tests automatically with GitHub Actions

## Author

**Ahmed Fathy** – Software Quality Control Engineer
LinkedIn: *https://www.linkedin.com/in/ahmed-fathy-a6966a405*
