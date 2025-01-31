# 📸 RentWise DB – Photo/Video Equipment Rental System  

📅 **September – November 2024**  
🛠️ **Technologies Used:** MySQL | Java | Bash | SQL | Shell Scripting  


## 🚀 Overview  
RentWise DB is a **SQL-based rental system** designed to streamline **photo and video equipment rentals** efficiently. It ensures **optimized data organization, secure transactions, and automated processes** through:  

✅ **A structured MySQL database** for handling customers, rentals, and payments.  
✅ **A Bash automation suite** for query execution, reporting, and system monitoring.  
✅ **A Java-based GUI** for an intuitive interface to manage rentals and returns.  


## 🔑 Key Features  

### 🎯 **Database Management**  
- **Designed in 3NF (Third Normal Form)** for optimized storage and retrieval.  
- **Implements triggers and stored procedures** to enforce business rules.  
- **Ensures data integrity and security** with constraints and validation mechanisms.  

### ⚡ **Bash-Based Automation**  
- **Automates rental transactions** with batch processing.  
- **Tracks overdue rentals, equipment availability, and payments.**  
- **Generates reports** using shell scripting for system insights.  

### 🖥️ **Java GUI for User Interaction**  
- **User-friendly graphical interface** for managing customers, equipment, and rentals.  
- **Form-based data entry** to streamline user interactions.  
- **Built-in error handling and validation** for seamless operations.  


## 📂 Database Schema  

### **Main Tables**  
📌 **Customers** – Stores user details and contact information.  
📌 **Equipment** – Tracks camera gear, video tools, and availability status.  
📌 **Rentals** – Manages active and past rental transactions.  
📌 **Payments** – Logs customer payments, overdue fees, and methods.  
📌 **Maintenance** – Maintains service history of equipment.  
📌 **Reservations** – Handles advance booking of equipment.  
📌 **Overdue** – Tracks late rentals and penalties.  
📌 **Staff** – Manages employees handling rental operations.  

### **Relations & Normalization (3NF & BCNF)**  
✔️ **Prevents redundancy** while maintaining efficient data retrieval.  
✔️ **Indexing and keys** for faster lookup performance.  
✔️ **Overdue and payment history tracking** for rental accountability.  


## 🛠️ Installation & Setup  

### **1️⃣ Compiling and Running the Java GUI**  
```
javac MainMenu.java
java RentWiseGUI
```  

### **2️⃣ Setting Up the Database (If Needed)**  
```
mysql -u root -p < rentwise_schema.sql
mysql -u root -p rentwise_db < sample_data.sql
```
