🏪 Stockify Platform
====================

Overview
--------

Stockify is a robust command-line inventory management system built with Java. It provides a comprehensive solution for managing product inventory with features like adding products, updating quantities, tracking prices, and generating inventory reports.

✨ Features
----------

-   **Product Management**
    -   Add new products with unique IDs
    -   Update product quantities and prices
    -   Remove products from inventory
    -   View detailed product information
-   **Inventory Operations**
    -   List all products in inventory
    -   Sort products by various attributes (ID, name, quantity, price)
    -   Calculate the total inventory value
    -   Track product count
-   **Data Validation**
    -   Unique product ID validation
    -   Input format verification
    -   Positive number validation for quantities and prices
    -   Product name format validation

🚀 Getting Started
------------------

### Prerequisites

-   Java JDK 17 or higher
-   Gradle 8.5 or higher

### Installation

1.  Clone the repository:

`git clone https://github.com/yourusername/stockify-platform.git
cd stockify-platform`

1.  Build the project:

`./gradlew build`

1.  Run the application:

`./gradlew run`

💻 Usage
--------

### Command Format

The system accepts the following commands:

1.  **Add Product**

`ADD_PRODUCT <product_id> <product_name> <quantity> <price>`

1.  **Update Quantity**

`UPDATE_QUANTITY <product_id> <quantity>`

1.  **Update Price**

`UPDATE_PRICE <product_id> <price>`

1.  **View Product**

`VIEW_PRODUCT <product_id>`

1.  **Remove Product**

`REMOVE_PRODUCT <product_id>`

1.  **List Products**

`LIST_PRODUCTS`

1.  **Sort Products**

`SORT_PRODUCTS <field>
# field can be: id, name, quantity, or price`

1.  **Exit Application**

`EXIT`

### Example Usage

`> ADD_PRODUCT 101 Apple 10 50
SUCCESS

> UPDATE_QUANTITY 101 20
SUCCESS

> VIEW_PRODUCT 101
Product ID: 101
Name: Apple
Quantity: 20
Price: 50

> LIST_PRODUCTS
101:Apple:20:50

> EXIT
Product Count: 1
Total Inventory Value: 1000
Goodbye!`

🏗️ Architecture
----------------

The project follows clean architecture principles and is organized into the following packages:
    
    `com.stockify.platform/
    ├── App.java                 # Main application entry point
    ├── model/
    │   └── Product.java        # Product entity
    ├── service/
    │   └── InventoryService.java  # Business logic
    ├── controller/
    │   └── CommandController.java # Command handling
    └── util/
        └── ValidationUtil.java    # Input validation`

🧪 Testing
----------

Run the test suite using:

`./gradlew test`

The test suite includes:

-   Basic functionality tests
-   Edge cases
-   Input validation
-   State management
-   Integration tests

📝 Constraints
--------------

-   Product IDs must be unique
-   Product names cannot contain spaces or semicolons
-   Quantities and prices must be positive integers
-   All inputs must follow the specified format

🤝 Contributing
---------------

1.  Fork the repository
2.  Create a feature branch
3.  Commit your changes
4.  Push to the branch
5.  Open a Pull Request

📄 License
----------

This project is licensed under the MIT License - see the LICENSE file for details.

👥 Authors
----------

-   Your Name - *Initial work* - [YourGithub](https://github.com/yourusername)

🙏 Acknowledgments
------------------

-   Thanks to all contributors who helped shape this project
-   Inspired by real-world inventory management systems
-   Built with modern Java practices and clean architecture principles
