-- Crear las tablas
CREATE TABLE company (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_by UUID
);

CREATE TABLE department (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    company_id UUID REFERENCES company(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_by UUID
);

CREATE TABLE employee (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    department_id UUID REFERENCES department(id),
    role VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_by UUID
);

CREATE TABLE vacation_request (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES employee(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason TEXT,
    status VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_by UUID
);


-- Insertar datos de prueba
INSERT INTO company (id, name, country, created_at, created_by, updated_at, updated_by)
VALUES
    ('00000000-0000-0000-0000-000000000001', 'Google', 'USA', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001'),
    ('00000000-0000-0000-0000-000000000002', 'Microsoft', 'USA', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001');

INSERT INTO department (id, name, company_id, created_at, created_by, updated_at, updated_by)
VALUES
    ('00000000-0000-0000-0000-000000000003',  'Ingeniería', '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001'),
    ('00000000-0000-0000-0000-000000000004', 'Marketing', '00000000-0000-0000-0000-000000000002', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001');

INSERT INTO employee (id, name, surname, email, department_id, role, created_at, created_by, updated_at, updated_by)
VALUES
    ('00000000-0000-0000-0000-000000000005', 'John', 'Doe', 'johndoe@example.com', '00000000-0000-0000-0000-000000000003', 'Ingeniero', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001'),
    ('00000000-0000-0000-0000-000000000006', 'Jane', 'Smith', 'janesmith@example.com', '00000000-0000-0000-0000-000000000004', 'Marketeador', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001');

INSERT INTO vacation_request (id, employee_id, start_date, end_date, reason, status, created_at, created_by, updated_at, updated_by)
VALUES
    ('00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000005', '2024-12-21', '2024-12-28', 'Vacaciones de Navidad', 'Pendiente', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001'),
    ('00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000006', '2024-12-25', '2024-12-26', 'Festivo', 'Aprobada', NOW(), '00000000-0000-0000-0000-000000000001', NOW(), '00000000-0000-0000-0000-000000000001');