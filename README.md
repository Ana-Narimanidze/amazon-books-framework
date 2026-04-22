# Amazon Books Automation Framework

## Overview

This project is an automated UI testing framework for Amazon.de, built using Java, Selenium WebDriver, TestNG, and Allure Reporting.

The framework follows the Page Object Model (POM) design pattern to ensure clean architecture, reusability, and maintainability.

---

## Test Scenario

The automated test covers an end-to-end user flow:

1. Open Amazon.de
2. Open the hamburger (All) menu
3. Navigate to Books
4. Open the Books subcategory
5. Validate that the Best Sellers section is visible
6. Select the first two books
7. Add both books to the cart
8. Navigate to the Cart page
9. Capture product prices dynamically
10. Validate that Cart Subtotal equals the sum of selected books
11. Click Proceed to Checkout
12. Validate that the Login page is displayed
13. Enter email
14. Enter invalid password
15. Validate:
- Error message text: Your password is incorrect
- Error message color (red)

---

## Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Allure Report
- Page Object Model (POM)

---

## Project Structure

src
├── main
│   └── java/com/example
│       ├── core
│       │   ├── BasePage.java
│       │   └── DriverFactory.java
│       ├── pages
│       │   ├── HomePage.java
│       │   ├── HamburgerMenuPage.java
│       │   ├── BooksLandingPage.java
│       │   ├── ProductPage.java
│       │   ├── CartPage.java
│       │   └── SignInPage.java
│       └── utils
│           ├── AllureUtils.java
│           └── TestListener.java
└── test
├── java/com/example/tests
│   ├── BaseTest.java
│   └── AmazonBooksTest.java
└── resources
└── testng.xml

---

## How to Run Tests

Run with Maven:

mvn clean test

Run in headless mode:

mvn clean test -Dheadless=true

---

## Allure Reporting

Generate and open report:

allure serve target/allure-results

Generate static report:

allure generate target/allure-results --clean -o allure-report
allure open allure-report

---

## Git Workflow Recommendation

- main — stable branch
- develop — integration branch
- feature/* — feature branches

Example branches:
- feature/home-page
- feature/books-page
- feature/cart-checkout
- feature/allure-reporting

Merge strategy:
- Feature → Develop: Pull Request + Code Review
- Develop → Main: approved merges only
- Use squash merge for small tasks

---

## Notes

- Amazon frequently changes DOM structure and localization
- Locators may require updates over time
- Changes are isolated within Page classes
- Price validation is dynamic (no hardcoded values)

---

## Author

QA Automation project built for practice and portfolio purposes.