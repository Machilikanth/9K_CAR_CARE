# 9K Car Care Service API Endpoints

This document lists all available API endpoints for the 9K Car Care Service application, categorized by user type (Admin and User). It includes examples for testing with Postman.

## Base URL
- **Local Development**: `http://localhost:8080`

## Authentication
The API uses HTTP Basic Authentication for admin endpoints.
- **Username**: `admin`
- **Password**: `password`

User endpoints (`/api/cars/**`) are open without authentication.
Admin endpoints require authentication.

For Postman, use Basic Auth with the credentials above, or add header:
```
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

---

## Testing Sequence Flow
To test the APIs properly, follow this order:

1. **Create a Branch** (Required first - cars need a branch)
2. **Create a Car** (Uses the branchId from step 1)
3. **Add Services to Car** (Optional - enhances car details)
4. **Update Car Status** (Changes car workflow state)
5. **Get All Cars** (Admin view of all cars)
6. **Get Car Details** (User view by car number)

---

## Admin Endpoints

### 1. Create a Branch (Required First)
- **Method**: POST
- **Endpoint**: `/api/admin/branches`
- **Description**: Creates a new branch for cars to be assigned to.

#### Request Body Example:
```json
{
  "name": "Main Branch",
  "location": "Downtown"
}
```

#### Postman Setup:
- **Method**: POST
- **URL**: `http://localhost:8080/api/admin/branches`
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK with created branch details (note the `id` for use in car creation)

---

### 2. List Branches
- **Method**: GET
- **Endpoint**: `/api/admin/branches`
- **Description**: Retrieves the list of existing branches.

#### Postman Setup:
- **Method**: GET
- **URL**: `http://localhost:8080/api/admin/branches`
- **Headers**:
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Expected Response**: 200 OK with an array of branch objects

---

### 3. Create a New Car (After Branch Creation)
- **Method**: POST
- **Endpoint**: `/api/admin/cars`
- **Description**: Creates a new car entry in the system.

#### Request Body Example:
```json
{
  "carNumber": "ABC123",
  "branchId": 1,
  "expectedDeliveryTime": "2026-04-20T10:00:00",
  "serviceIds": [1, 2]
}
```

> Note: `branchId` must be from an existing branch (created in step 1). The current API expects `branchId`, `carNumber`, `expectedDeliveryTime`, and optional `serviceIds`. Fields such as `ownerName`, `ownerPhone`, and `carModel` are not currently accepted by the backend.

#### Postman Setup:
- **Method**: POST
- **URL**: `http://localhost:8080/api/admin/cars`
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK with created car details (note the `id` for further operations)

---

### 4. Add Services to a Car
- **Method**: POST
- **Endpoint**: `/api/admin/cars/{carId}/services`
- **Description**: Adds services to an existing car.

#### Request Body Example:
```json
{
  "serviceIds": [1, 2, 3]
}
```

#### Postman Setup:
- **Method**: POST
- **URL**: `http://localhost:8080/api/admin/cars/1/services` (replace 1 with actual carId from step 3)
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK

---

### 5. Update Car Status
- **Method**: PUT
- **Endpoint**: `/api/admin/cars/{carId}/status`
- **Description**: Updates the status of a car.

#### Request Body Example:
```json
{
  "status": "IN_PROGRESS"
}
```

#### Postman Setup:
- **Method**: PUT
- **URL**: `http://localhost:8080/api/admin/cars/1/status` (replace 1 with actual carId from step 3)
- **Headers**:
  - Content-Type: application/json
  - Authorization: Basic YWRtaW46cGFzc3dvcmQ=
- **Body**: Raw JSON (paste the example above)
- **Expected Response**: 200 OK

---

### 6. Get All Cars
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

## User Endpoints

### 7. Get Car Details by Car Number
- **Method**: GET
- **Endpoint**: `/api/cars/{carNumber}`
- **Description**: Retrieves details of a specific car by its number.

#### Postman Setup:
- **Method**: GET
- **URL**: `http://localhost:8080/api/cars/ABC123` (replace ABC123 with actual car number from step 3)
- **Headers**: None required
- **Expected Response**: 200 OK with car details including status, services, and total price

---

## Notes
- **Delete Operation**: No delete endpoints are currently implemented in the API.
- **Error Handling**: If a car is not found, the API returns a 404 or appropriate error response.
- **Data Types**: Ensure carId is a Long, carNumber is a String.
- **Testing Tips**:
  - Always create a branch first before creating a car.
  - Use the returned IDs from creation responses for subsequent requests.
  - Test admin endpoints first, then user endpoints.
  - Check the admin "Get All Cars" to verify changes.

This covers all CRUD operations available in the current API implementation with the correct testing sequence.