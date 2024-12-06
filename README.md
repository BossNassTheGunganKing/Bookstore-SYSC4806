# SYSC 4806 Bookstore Web App
Mock bookstore web application for the SYSC 4806 group project

This application simulates an online bookstore where the bookstore owner can manage books and authors, and users can browse the books available. 

The current project is viewable at the link here: https://bookstore-sysc4806-a6g3aygdasaeg2hc.canadacentral-01.azurewebsites.net/

### Development Process

The development process is organized in GitHub through Issues, Pull Requests, and a Kanban board.
- Issues: Used to track tasks, bug fixes, and feature requests.
- Kanban Board: Shows the current status of tasks (Backlog, Ready, In Progress, In Review, Done).
- Code Reviews: All pull requests undergo code review to ensure quality and consistency.
- Testing: JUnit tests are written for key functionalities. There are JUnit tests for Author Info (AuthorInfoTest), Book Info (BookInfoTest), Bookstore Controller (BookstoreControllerTest), and Listing Info (ListingInfoTest).

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

### Current State

The current prototype builds upon the foundational functionality from Milestone 1. The application provides a functional core that demonstrates its main purpose, allowing users to interact with book and author data. While some features are not yet complete, users can get a feel for the intended functionality. 

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

For the final milestone, we aim to focus on refining the application and ensuring its readiness for a seamless user experience. Below are the key updates planned:

## Finalizing the Scope of the Product
- Review and finalize the set of features to ensure the application is usable and useful for its intended purpose.
- Remove any placeholders, dangling links, or buttons pointing to non-implemented features in the UI.
- Polish the interface for a consistent and intuitive design.

## Usability and User Experience Enhancements:

- Order Management:
- Provide users with a more detailed view of their order history.
- Add an order cancellation feature for users to cancel pending orders (if applicable).
- Allow admins to view and manage all orders in the system, including filtering by order status.

Shopping Cart:
- Improve real-time feedback for inventory availability during cart operations (e.g., notify users if stock becomes unavailable).
- Ensure seamless transition from cart to checkout with accurate pricing and stock checks.

Error Handling:
- Add user-friendly error messages and validation feedback across all forms (e.g., login, registration, adding books/authors, updating account details).
- Ensure robust error pages (e.g., 404, 500) with appropriate redirection links.

## Feature Enhancements

Role-Based Permissions:
- Restrict access to sensitive pages based on user roles, with clear redirects to a “No Permissions” page if access is denied.
- Remove links that users should not have axccess to. 

Admin Features:
- Admins can manage users, including the ability to deactivate accounts or reset user information.

Search and Filtering:
- Enhance the catalog browsing experience with advanced search and filtering options, including filtering by genre, author, and availability.





