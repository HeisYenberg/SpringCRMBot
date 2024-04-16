# SpringCRMBot

The bot is designed to manage customer relationships, sales, and product
information within a CRM system, all through Telegram.

## Features

- Command-Driven Interaction: The bot is designed to respond to a variety of
  commands, providing a structured way for users to interact with the CRM
  system. Commands include:
- `/start`: Initiates the bot and provides an overview of available commands.
- `/authentication`: Authenticates the user to access the CRM bot
  functionalities.
- `/login`: Logs the user into the CRM system.
- `/registration`: Registers a new user in the CRM system.
- `/logged_in`: Checks if the user is logged in.
- `/add_client`: Adds a new client to the CRM system.
- `/get_client`: Retrieves information about a specific client.
- `/add_product`: Adds a new product to the CRM system.
- `/get_product`: Retrieves information about a specific product.
- `/add_sale`: Records a new sale in the CRM system.
- `/get_client_sales`: Retrieves all sales associated with a specific client.
- `/get_product_sales`: Retrieves all sales associated with a specific product.
- CSV File Support: The bot can send CSV files containing requested data,
  enhancing data accessibility and usability.
- User Authentication and Session Management: Provides secure access to the
  CRM bot functionalities through authentication and session management.

## Prerequisites

- Java 8 or later.
- Maven for building the project.
- PostgreSQL database for data storage.

## Database Setup

1. Install PostgreSQL on your local machine or server.
2. Create a new database for your application, run `schema.sql` script.
3. Configure the database connection in your `application.properties` file with
   the following settings:

```spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password