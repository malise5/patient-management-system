-- =============================================
-- Table: patient
-- =============================================
CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    registration_date DATE NOT NULL
);

-- =============================================
-- Dummy Data
-- =============================================
INSERT INTO patient (name, email, address, date_of_birth, registration_date)
VALUES
('John Doe', 'john.doe@example.com', '123 Main St, New York, NY', '1985-06-15', '2024-01-10'),
('Jane Smith', 'jane.smith@example.com', '456 Oak Ave, Los Angeles, CA', '1990-09-22', '2024-02-14'),
('Michael Brown', 'michael.brown@example.com', '789 Pine Rd, Chicago, IL', '1978-12-03', '2024-03-01'),
('Emily Johnson', 'emily.johnson@example.com', '321 Cedar St, Houston, TX', '1992-03-19', '2024-03-25'),
('David Wilson', 'david.wilson@example.com', '654 Maple Dr, Phoenix, AZ', '1988-11-05', '2024-04-05'),
('Sophia Davis', 'sophia.davis@example.com', '987 Elm Blvd, Philadelphia, PA', '1995-07-28', '2024-05-10'),
('James Miller', 'james.miller@example.com', '159 Birch Ln, San Antonio, TX', '1983-04-12', '2024-06-20'),
('Olivia Martinez', 'olivia.martinez@example.com', '753 Spruce Way, Dallas, TX', '1998-10-08', '2024-07-18'),
('William Anderson', 'william.anderson@example.com', '852 Walnut Cir, San Diego, CA', '1975-01-25', '2024-08-03'),
('Ava Thomas', 'ava.thomas@example.com', '951 Chestnut Ct, Austin, TX', '2000-05-30', '2024-09-12');
