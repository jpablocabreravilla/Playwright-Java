## 🧪 Jpcv_Playwright_Java

Automated UI testing project using 
**Java**, **Playwright**, **Page Object Model (POM)**.

## 🚀 Technologies Used

- Java 17
- [Playwright for Java](https://playwright.dev/java/)
- JUnit 5
- Maven
- Allure Report
- SLF4J + Log4j2 (custom logger)
- Page Object Model (POM) design pattern

## ✅ How to Run the Tests

```bash
 mvn clean test
 mvn clean test -Dheadless=true/false
 mvn clean test -Dgroups=Tag
 mvn allure:report
 mvn allure:serve

mvn clean test -Dheadless=true -Dgroups=E2E && mvn allure:serve
mvn clean test -Dheadless=false -Dgroups=E2E && mvn allure:serve
