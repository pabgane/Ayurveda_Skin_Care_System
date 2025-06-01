DROP DATABASE ayurveda_skin_care;

CREATE DATABASE ayurveda_skin_care;

USE ayurveda_skin_care;

SHOW DATABASEs;

CREATE TABLE User_Types (
    User_Type_Id varchar(100) PRIMARY KEY,
    Type ENUM('Admin','Cashier') NOT NULL
);

CREATE TABLE Users (
    User_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    age INT,
    User_Type_Id varchar(100),
    Registration_Date DATE,
    Status ENUM('Active', 'Inactive') DEFAULT 'Active',
    FOREIGN KEY (User_Type_Id) REFERENCES User_Types(User_Type_Id)
);

CREATE TABLE Equipment (
    Equipment_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Serial_Number VARCHAR(50) UNIQUE,
    Purchase_Date DATE,
    Last_Maintenance_Date DATE,
    Status ENUM('Active', 'Inactive') DEFAULT 'Active'
);

CREATE TABLE Suppliers (
    Supplier_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Address TEXT
);

CREATE TABLE Inventory_Items (
    Inventory_Item_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description TEXT,
    Quantity INT NOT NULL,
    Unit_Price DECIMAL(10,2),
    Expiry_Date DATE,
    Supplier_Id varchar(100),
    FOREIGN KEY (Supplier_Id) REFERENCES Suppliers(Supplier_Id)
);

CREATE TABLE Customers (
    Customer_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    age INT,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(20),
    Address VARCHAR(50),
    Registration_Date DATE
);


CREATE TABLE Employees (
    Employee_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(20),
    age INT,
    Address TEXT,
    salary DECIMAL(10,2),
    emergency_contact VARCHAR(100),
    Hire_Date DATE,
    Position VARCHAR(100),
    Status VARCHAR(100)
);

CREATE TABLE Treatments (
    Treatment_Id varchar(100) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description TEXT,
    Duration INT,
    Price DECIMAL(10,2)
);

CREATE TABLE Appointments (
    Appointment_Id varchar(100) PRIMARY KEY,
    Customer_Id varchar(100),
    Employee_Id varchar(100),
    Treatment_Id varchar(100),
    Appointment_Date DATE,
    Appointment_Time TIME,
    Status ENUM('Active', 'Inactive','Canceled') DEFAULT 'Active',
    Notes TEXT,
    FOREIGN KEY (Customer_Id) REFERENCES Customers(Customer_Id),
    FOREIGN KEY (Employee_Id) REFERENCES Employees(Employee_Id),
    FOREIGN KEY (Treatment_Id) REFERENCES Treatments(Treatment_Id)
);

CREATE TABLE Payment_Methods (
    Method_Id varchar(100) PRIMARY KEY,
    Method ENUM('Cash','Card','Online Transfer') DEFAULT 'Cash'
);

CREATE TABLE Payments (
    Payment_Id varchar(100) PRIMARY KEY,
    Customer_Id varchar(100),
    Appointment_Id varchar(100),
    Amount DECIMAL(10,2) NOT NULL,
    Payment_Date DATETIME,
    Method_Id varchar(100),
    Status ENUM('Successfull', 'Faild') DEFAULT 'Successfull',
    FOREIGN KEY (Customer_Id) REFERENCES Customers(Customer_Id),
    FOREIGN KEY (Appointment_Id) REFERENCES Appointments(Appointment_Id),
    FOREIGN KEY (Method_Id) REFERENCES Payment_Methods(Method_Id)
);










-- Sample Data for Salon/Spa Management System

-- User_Types
INSERT INTO User_Types (User_Type_Id, Type) VALUES
('UT001', 'Admin'),
('UT002', 'Cashier');

-- Users
INSERT INTO Users (User_Id, Name, Phone, Username, Password, Email, age, User_Type_Id, Registration_Date, Status) VALUES
('U001', 'Sarah Johnson', '+1234567890', 'admin', 'adminpabo', 'sarah@salonxyz.com', 35, 'UT001', '2024-01-15', 'Active'),
('U002', 'Mike Chen', '+1234567891', 'cashier', 'cashierPabo', 'mike@salonxyz.com', 28, 'UT002', '2024-02-01', 'Active');

-- Equipment
INSERT INTO Equipment (Equipment_Id, Name, Serial_Number, Purchase_Date, Last_Maintenance_Date, Status) VALUES
('EQ001', 'Hair Dryer Professional 3000', 'HD3000-001', '2023-05-15', '2024-12-01', 'Active'),
('EQ002', 'Facial Steamer Deluxe', 'FS-DEL-002', '2023-08-20', '2024-11-15', 'Active'),
('EQ003', 'Massage Chair Premium', 'MC-PREM-003', '2023-03-10', '2024-10-20', 'Active'),
('EQ004', 'UV Nail Lamp', 'UV-NAIL-004', '2024-01-05', '2024-12-10', 'Active'),
('EQ005', 'Waxing Station', 'WS-PRO-005', '2023-07-12', '2024-09-30', 'Inactive');

-- Suppliers
INSERT INTO Suppliers (Supplier_Id, Name, Email, Phone, Address) VALUES
('SUP001', 'Beauty Supply Co.', 'orders@beautysupply.com', '+1555000001', '123 Beauty Street, New York, NY 10001'),
('SUP002', 'Professional Salon Products', 'sales@prosalonprod.com', '+1555000002', '456 Salon Ave, Los Angeles, CA 90210'),
('SUP003', 'Wellness Equipment Ltd.', 'info@wellnessequip.com', '+1555000003', '789 Wellness Blvd, Chicago, IL 60601'),
('SUP004', 'Organic Beauty Supplies', 'contact@organicbeauty.com', '+1555000004', '321 Natural Way, Austin, TX 78701');

-- Inventory_Items
INSERT INTO Inventory_Items (Inventory_Item_Id, Name, Description, Quantity, Unit_Price, Expiry_Date, Supplier_Id) VALUES
('INV001', 'Premium Shampoo', 'Sulfate-free premium shampoo for all hair types', 25, 24.99, '2025-12-31', 'SUP001'),
('INV002', 'Hair Conditioner', 'Deep moisturizing conditioner', 20, 28.50, '2025-11-30', 'SUP001'),
('INV003', 'Facial Cleanser', 'Gentle facial cleanser for sensitive skin', 15, 35.00, '2025-10-15', 'SUP002'),
('INV004', 'Massage Oil', 'Relaxing aromatherapy massage oil', 12, 45.00, '2026-03-20', 'SUP004'),
('INV005', 'Nail Polish Set', 'Professional nail polish collection - 12 colors', 8, 89.99, NULL, 'SUP003'),
('INV006', 'Face Mask', 'Hydrating collagen face mask', 30, 15.75, '2025-08-30', 'SUP004'),
('INV007', 'Hair Styling Gel', 'Strong hold styling gel', 18, 22.00, '2026-01-15', 'SUP002');

-- Customers
INSERT INTO Customers (Customer_Id, Name, age, Email, Phone, Address, Registration_Date) VALUES
('C001', 'Emma Thompson', 29, 'emma.thompson@email.com', '+1234567801', '123 Oak Street, City', '2024-01-20'),
('C002', 'David Wilson', 35, 'david.wilson@email.com', '+1234567802', '456 Pine Avenue, City', '2024-02-15'),
('C003', 'Maria Garcia', 42, 'maria.garcia@email.com', '+1234567803', '789 Maple Drive, City', '2024-03-01'),
('C004', 'James Brown', 28, 'james.brown@email.com', '+1234567804', '321 Elm Street, City', '2024-03-15'),
('C005', 'Anna Lee', 31, 'anna.lee@email.com', '+1234567805', '654 Birch Lane, City', '2024-04-10'),
('C006', 'Robert Davis', 45, 'robert.davis@email.com', '+1234567806', '987 Cedar Road, City', '2024-05-05'),
('C007', 'Jessica White', 26, 'jessica.white@email.com', '+1234567807', '147 Spruce Court, City', '2024-05-20');

-- Employees
INSERT INTO Employees (Employee_Id, Name, Email, Phone, age, Address, salary, emergency_contact, Hire_Date, Position, Status) VALUES
('E001', 'Sophia Martinez', 'sophia.martinez@salonxyz.com', '+1234567811', 28, '111 Beauty Lane, City', 3500.00, 'Carlos Martinez +1234567821', '2023-06-01', 'Senior Hair Stylist', 'Active'),
('E002', 'Olivia Johnson', 'olivia.johnson@salonxyz.com', '+1234567812', 25, '222 Style Street, City', 3200.00, 'Mark Johnson +1234567822', '2023-08-15', 'Nail Technician', 'Active'),
('E003', 'Isabella Brown', 'isabella.brown@salonxyz.com', '+1234567813', 32, '333 Wellness Way, City', 3800.00, 'Tom Brown +1234567823', '2023-04-10', 'Esthetician', 'Active'),
('E004', 'Mia Davis', 'mia.davis@salonxyz.com', '+1234567814', 27, '444 Spa Avenue, City', 3400.00, 'Lisa Davis +1234567824', '2023-09-01', 'Massage Therapist', 'Active'),
('E005', 'Charlotte Wilson', 'charlotte.wilson@salonxyz.com', '+1234567815', 30, '555 Relax Road, City', 3600.00, 'John Wilson +1234567825', '2023-07-20', 'Hair Colorist', 'Active');

-- Treatments
INSERT INTO Treatments (Treatment_Id, Name, Description, Duration, Price) VALUES
('T001', 'Haircut & Style', 'Professional haircut with styling', 60, 65.00),
('T002', 'Hair Color & Highlights', 'Full hair coloring service with highlights', 180, 150.00),
('T003', 'Deep Conditioning Treatment', 'Intensive hair conditioning and repair', 45, 40.00),
('T004', 'Classic Manicure', 'Basic manicure with polish application', 30, 35.00),
('T005', 'Gel Manicure', 'Long-lasting gel manicure', 45, 55.00),
('T006', 'Classic Pedicure', 'Relaxing pedicure with foot massage', 45, 45.00),
('T007', 'European Facial', 'Deep cleansing facial treatment', 75, 85.00),
('T008', 'Anti-Aging Facial', 'Advanced anti-aging facial with serums', 90, 120.00),
('T009', 'Swedish Massage', 'Full body relaxation massage', 60, 90.00),
('T010', 'Deep Tissue Massage', 'Therapeutic deep tissue massage', 75, 110.00),
('T011', 'Eyebrow Shaping', 'Professional eyebrow shaping and trimming', 20, 25.00),
('T012', 'Leg Waxing', 'Full leg hair removal waxing', 45, 70.00);

-- Appointments
INSERT INTO Appointments (Appointment_Id, Customer_Id, Employee_Id, Treatment_Id, Appointment_Date, Appointment_Time, Status, Notes) VALUES
('A001', 'C001', 'E001', 'T001', '2024-06-03', '10:00:00', 'Active', 'First time customer, prefers layered cut'),
('A002', 'C002', 'E002', 'T005', '2024-06-03', '11:30:00', 'Active', 'Regular customer, likes nude colors'),
('A003', 'C003', 'E003', 'T007', '2024-06-04', '14:00:00', 'Active', 'Has sensitive skin'),
('A004', 'C004', 'E004', 'T009', '2024-06-04', '16:00:00', 'Active', 'Prefers medium pressure'),
('A005', 'C005', 'E001', 'T002', '2024-06-05', '09:00:00', 'Active', 'Wants to go from brown to blonde'),
('A006', 'C006', 'E005', 'T002', '2024-06-05', '13:00:00', 'Active', 'Touch-up roots only'),
('A007', 'C001', 'E002', 'T004', '2024-06-06', '10:30:00', 'Canceled', 'Customer rescheduled'),
('A008', 'C007', 'E003', 'T008', '2024-06-06', '15:00:00', 'Active', 'Special event preparation'),
('A009', 'C002', 'E004', 'T010', '2024-06-07', '11:00:00', 'Active', 'Sports injury recovery'),
('A010', 'C003', 'E001', 'T011', '2024-06-07', '12:30:00', 'Active', 'Maintenance appointment');

-- Payment_Methods
INSERT INTO Payment_Methods (Method_Id, Method) VALUES
('PM001', 'Cash'),
('PM002', 'Card'),
('PM003', 'Online Transfer');

-- Payments
INSERT INTO Payments (Payment_Id, Customer_Id, Appointment_Id, Amount, Payment_Date, Method_Id, Status) VALUES
('PAY001', 'C001', 'A001', 65.00, '2024-06-03 10:45:00', 'PM002', 'Successfull'),
('PAY002', 'C002', 'A002', 55.00, '2024-06-03 12:15:00', 'PM001', 'Successfull'),
('PAY003', 'C003', 'A003', 85.00, '2024-06-04 14:45:00', 'PM002', 'Successfull'),
('PAY004', 'C004', 'A004', 90.00, '2024-06-04 17:00:00', 'PM003', 'Successfull'),
('PAY005', 'C005', 'A005', 150.00, '2024-06-05 12:30:00', 'PM002', 'Successfull'),
('PAY006', 'C006', 'A006', 150.00, '2024-06-05 16:45:00', 'PM001', 'Successfull'),
('PAY007', 'C007', 'A008', 120.00, '2024-06-06 16:30:00', 'PM002', 'Successfull'),
('PAY008', 'C002', 'A009', 110.00, '2024-06-07 12:15:00', 'PM002', 'Faild'),
('PAY009', 'C003', 'A010', 25.00, '2024-06-07 13:00:00', 'PM001', 'Successfull');