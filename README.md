# Swag Labs UI Test Automation

UI test automation framework for [Swag Labs (Sauce Demo)](https://www.saucedemo.com/). The project uses Java, Selenium WebDriver, TestNG, Page Object Model (POM), JSON test data, and Allure reporting.

## Highlights

- Page Objects separate page interactions from test assertions.
- Login scenarios are data-driven from a JSON file.
- Explicit waits are used instead of fixed delays.
- A clean Chrome session starts for every test method.
- Allure annotations and results provide readable test reporting.

## Tech Stack

| Tool | Version | Purpose |
|---|---:|---|
| Java | 21 | Programming language |
| Selenium WebDriver | 4.49.0 | Browser automation |
| TestNG | 7.12.0 | Test execution and assertions |
| Jackson Databind | 2.17.2 | JSON test-data parsing |
| Allure TestNG | 3.0.0 | Test-result reporting |
| Maven | 3.8+ | Build and dependency management |

## Test Coverage

| Area | Scenarios covered |
|---|---|
| Authentication | Valid login, locked user, invalid credentials, and required-password validation |
| Inventory | Page URL/title/cart validation, product count, social links, and product subtotal |
| Cart | Empty cart, add/remove products, and cart state after logout/login |
| Checkout | Required customer-information validation |

The login test reads four credential cases from `testData.json`. Together with the remaining scenarios, the suite currently executes 11 test cases.

## Project Structure

```text
Swag Labs Test Automation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Pages/          # Page Objects and reusable page utilities
│   │   │   └── utils/          # JSON test-data reader
│   │   └── resources/
│   │       └── testdata/       # testData.json
│   └── test/
│       └── java/
│           ├── base/           # WebDriver setup and teardown
│           ├── liseteners/     # TestNG/Allure listener
│           └── Tests/          # Test classes
├── runtest.xml                 # TestNG suite definition
└── pom.xml                     # Maven configuration
```

## Prerequisites

- JDK 21
- Maven 3.8 or later
- Google Chrome
- Allure Commandline (optional, to generate or serve Allure reports locally)

Selenium Manager resolves the compatible ChromeDriver automatically.

## Run the Tests

```bash
git clone https://github.com/ahmedfathyhakeem6690-test/Swag-Labs--automation-test.git
cd "Swag-Labs--automation-test/Swag Labs Test Automation"

# Run all tests
mvn test

# Or run the TestNG suite explicitly
mvn test -Dsurefire.suiteXmlFiles=runtest.xml
```

Run commands from the directory containing `pom.xml` because the current JSON data reader uses a project-relative path.

## Allure Reports

Test results are written to `target/allure-results`. With Allure Commandline installed, open a local report with:

```bash
allure serve target/allure-results
```

The TestNG listener can also generate a single-file report at `reports/index.html` after a run. New `target/` and `reports/` output is ignored by Git. Existing generated files already tracked by the repository should be removed from the Git index in a separate cleanup commit.

## Planned Improvements

- Add screenshot attachments on test failure.
- Make the browser, headless mode, and base URL configurable.
- Read test data from the classpath rather than a project-relative file path.
- Add a Maven Wrapper and GitHub Actions CI workflow.
- Standardize package and method names using Java conventions.

## Author

**Ahmed Fathy** — Software Quality Control Engineer
[LinkedIn](https://www.linkedin.com/in/ahmed-fathy-a6966a405)
