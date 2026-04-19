# 9K Car Care Service API Endpoints

This document lists all available API endpoints for the 9K Car Care Service application, categorized by user type (Admin and Customer). It includes examples for testing with Postman.

## Base URL
- **Local Development**: `http://localhost:8080`

## Authentication
The API uses HTTP Basic Authentication for admin endpoints.
- **Username**: `admin`
- **Password**: `password`

Customer endpoints (`/api/customer/**`) are open without authentication.
Admin endpoints require authentication.

For Postman, use Basic Auth with the credentials above, or add header:
```
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

---

## Testing Sequence Flow
To test the APIs properly, follow this order:

1. **Create a Car Service Request** (Admin creates new service request)
2. **Update Car Status** (Admin updates status by car number)
3. **Get All Cars** (Admin view of all cars)
4. **Get Car Status** (Customer checks status by car number)

---

## Admin Endpoints

### 1. Create a New Car Service Request
- **Method**: POST
- **Endpoint**: `/api/admin/cars`
- **Description**: Creates a new car service request with customer and worker details.

#### Request Body Example:
```json
{
  "carNumber": "ABC123",
  "customerName": "John Doe",
  "customerMobile": "9876543210",
  "workerName": "Jane Smith",
  "workerMobile": "9876543211",
  "expectedDeliveryTime": "2026-04-20T10:00:00",
  "serviceNames": ["WASHING", "POLISHING"]
}
```

#### Postman Setup:
- **Method**: POST
- **URL**: `http://localhost:8080/api/admin/cars`
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK with created car details

---

### 2. Update Car Status
- **Method**: PUT
- **Endpoint**: `/api/admin/cars/number/{carNumber}/status`
- **Description**: Updates the status of a car by its car number.

#### Request Body Example:
```json
{
  "status": "IN_PROGRESS"
}
```

#### Postman Setup:
- **Method**: PUT
- **URL**: `http://localhost:8080/api/admin/cars/number/ABC123/status` (replace ABC123 with actual car number)
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK

---

### 3. Get All Cars
- **Method**: GET
- **Endpoint**: `/api/admin/cars`
- **Description**: Retrieves a list of all cars.

#### Postman Setup:
- **Method**: GET
- **URL**: `http://localhost:8080/api/admin/cars`
- **Headers**:
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Expected Response**: 200 OK with array of car objects

---

## Customer Endpoints

### 4. Get Car Status by Car Number
- **Method**: GET
- **Endpoint**: `/api/customer/cars/{carNumber}/status`
- **Description**: Retrieves the status and details of a specific car by its number for customers.

#### Postman Setup:
- **Method**: GET
- **URL**: `http://localhost:8080/api/customer/cars/ABC123` (replace ABC123 with actual car number)
- **Headers**: None required
- **Expected Response**: 200 OK with car status details including customer info, worker info, service types, and current status

---

## Notes
- **Service Types**: Supported service names are: "WASHING", "POLISHING", "PPF", "DENTING"
- **Status Values**: Valid statuses are: "RECEIVED", "IN_PROGRESS", "COMPLETED"
- **Error Handling**: If a car is not found, the API returns a 404 or appropriate error response.
- **Data Types**: Ensure carNumber is a String, mobile numbers are valid phone numbers.
- **Testing Tips**:
  - Create a car service request first with admin credentials.
  - Use the car number for all subsequent operations.
  - Test admin endpoints with authentication, customer endpoints without.
  - Check the admin "Get All Cars" to verify changes.

This covers all operations available in the current API implementation with the correct testing sequence.