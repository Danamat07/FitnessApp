# FitnessApp 💪
Welcome to Fitness App! This application is designed to streamline your fitness journey by making it easy to handle class registrations and manage your gym schedule. 🏆
> ![Languages](https://img.shields.io/badge/Languages-Java%20%7C%20SQL-blue)

# Features and Key Functions 🏋️‍♀️
User Roles:
  - Trainers: Create and manage fitness classes, including handling organizational aspects
  - Members: Enroll in classes and receive personalized recommendations.

CRUD Operations:
  - Add, update, delete, and retrieve users, fitness classes, and other entities.

Advanced Algorithms:
  - Sorting: Organize fitness classes by date. 📅
  - Filtering: Filter classes by trainer and needed equipment. 🧰
  - Recommendations: Suggest fitness classes based on user previous enrollments. 💡

# Layers
  - Model Layer -> contains the core entities of the application.
  - Repository Layer -> includes an abstract class IRepository that defines CRUD operations, implemented by InMemoryRepository, FileRepository, and DatabaseRepository. This structure provides flexible storage options while ensuring consistent data handling across different storage methods. 💾
  - Service Layer -> handles business logic, including sorting, filtering, and generating recommendations, acting as an intermediary between the Repository and Controller layers. 🔧
  - Controller layer -> handles communication between the UI and Service layers, managing data flow and ensuring proper exception handling for smooth application operation.
  - UI Layer -> provides a user interface with a menu for navigation, storage option switching, and displaying key data.
  - Test Layer -> contains unit and integration tests that verify the functionality and reliability of the application, ensuring all components work as expected. 🧪
  - Helpers Layer -> provides utility methods. ⚙️

# UML Diagram 📊
![image](https://github.com/user-attachments/assets/7285d30d-bd0a-45ae-a741-0f32a8307b10)

![image](https://github.com/user-attachments/assets/da0dfe59-6647-4a5d-90b7-eb776c1a53b0)
