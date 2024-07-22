# Blog-Backend
  Overview
This Spring Boot application serves as the backend for a blog platform, providing robust functionalities for user management, content creation, and interaction.

Features

Blog Backend Application
This project is a backend application for a blog platform built with Spring Boot, Java, Hibernate, JPA, JWT Security, and MySQL. 
It features user roles (admin and user), post and category management, commenting, search functionalities, and pagination,sorting .

Features --> 

 * User Roles
 - Admin: Can manage users like only admin can delete the users , users cannot delete themselves.
 - User: Can create posts,category, comment on posts, and browse content,can perform crud operations on post,comment,category and more.

 * Category Management
 - Admins can add categories.
 - Multiple users can add multiple posts within a category.

* Post and Comment Management
 - Users can create, read, update, and delete posts.
 - Users can comment on posts.

* Search Functionalities
- Search posts by title.
- Get posts by category.
- Get posts by a particular user.

* Pagination
 - Implemented pagination for displaying posts Efficiently.
- user can sort post asc,desc

 * Exposed More than 20 + Rest Endpoints.... 
 
Technologies ---
Spring Boot: Rapid application development framework.
Java: Core programming language.
Spring Security: Robust security for user authentication and authorization.
JPA: Object-relational mapping for database interactions.
Hibernate: (ORM) Object Mappeer Framework .
JWT: Secure token-based authentication.
MySQL: Relational database for data storage.
Swagger.

Clone the repository:

git clone https://github.com/Vignesh282004/blog-backend.git
cd blog-backend

Run Application :
mvn spring-boot:run 






