# SYSC 4806 Bookstore Web App
Mock bookstore web application for the SYSC 4806 group project

This application simulates an online bookstore where the bookstore owner can manage books and authors, and users can browse the books available. 

The current project is viewable at the link here: https://bookstore-sysc4806-a6g3aygdasaeg2hc.canadacentral-01.azurewebsites.net/

### Development Process

The development process is organized in GitHub through Issues, Pull Requests, and a Kanban board.
- Issues: Used to track tasks, bug fixes, and feature requests.
- Kanban Board: Shows the current status of tasks (Backlog, Ready, In Progress, In Review, Done).
- Code Reviews: All pull requests undergo code review to ensure quality and consistency.
- Testing: JUnit tests are written for key functionalities. There are JUnit tests for Author Info (AuthorInfoTest), Book Info (BookInfoTest), Bookstore Controller (BookstoreControllerTest), Listing Info (ListingInfoTest), Order Info (OrderInfoTest), Order Item (OrderItemTest), User Info (UserInfoTest), User Service (UserServiceTest), Shopping Cart Controller (ShoppingCartControllerTest), User Controller (UserControllerTest), Modulatity Test (ModularityTest), and health tests for all pages. 

### Contributions
All team members participate in every aspect of the project, including:
- Coding and implementing features
- Writing and reviewing tests
- Managing issues and updating the Kanban board
- Conducting and participating in code reviews

## Milestone 1

The  prototype demonstrates foundational functionality, focusing on data collection from the back end, displaying books and authors, and adding new entries.
This prototype provides a functional early prototype demonstrating a key use case, integrated CI, and deployment on Azure.

## Milestone 2 - Alpha Release

The second milestone added several new features and improved usability. Key highlights include:

New Additions:
Author and Book Management:

- Bookstore owners can add new authors and books.
- Books can be associated with multiple authors.

Login and User specific functionality:
- Users can now login to unique sessions. There are currenty 3 types of users; default, publishers, and admin
- Users can update account information such as Username, Password, and email address. 

Shopping Cart and Checkout System: 
- Users can now add books to their shopping carts. The quantity of items available in inventory is updated in real time.
- Users can place orders for items with a checkout system.
- Users can view the status of orders placed, as well as the detail of each order. 

Browse Books:
- Users can view the catalog of available books, see details for each book, and filter by various criteria.
  
Navigation and Basic UI:
- Improved navigation and layout, cleaning up the UI that guides users through the available features.

### Class Diagram
![bookstore_class_diagram](https://github.com/user-attachments/assets/55cc1858-c4e2-4acd-b283-74411aabb844)

### Milestone 3 Goals : Final Demo

### Current State

For Milestone 3, the focus shifted to refining the application and ensuring it delivers a seamless user experience.

## Spring Modulith Implementation:
- Modularized the codebase using Spring Modulith to enforce module boundaries and dependencies.
- Enhanced code organization and testability.
- 
## Advanced Filtering and Display:
- Added filters for browsing books, improving the user experience.
- Updated the book display to a grid format for a modern and intuitive design.
- Provide advanced search and filtering options, including filtering by genre.
- Added book recommendations for users.
- 
## Health Testing:
- Added health tests for all pages to ensure consistent functionality and detect potential issues proactively.
- 
## Role-Based Permissions:
- Implemented granular access control with clear role-based restrictions:
- Default Users: Can browse, add items to the cart, and place orders.
- Publishers: Can manage books and authors.
- Admins: Can manage users and view all orders.

## Usability and User Experience Enhancements:

- Order Management:
- Provide users with a more detailed view of their order history.
- Allow admins to view and manage all orders in the system, including filtering by order status.

## Feature Enhancements

Role-Based Permissions:
- Restrict access to sensitive pages based on user roles, with clear redirects to a “No Permissions” page if access is denied.

Admin Features:
- Admins can manage users, view orders, view account information.





