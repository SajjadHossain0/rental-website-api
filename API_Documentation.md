# Rental Website API Documentation

## Overview
This documentation provides details for the Rental Website API, including available endpoints, request/response structures, and instructions for testing with Postman.

**Base URL:** `http://localhost:8080/api`

## Endpoints

### 1. Properties
Manage property listings.

#### Create Property
- **URL:** `/properties`
- **Method:** `POST`
- **Description:** Create a new property listing.
- **Request Body:** `application/json`
  ```json
  {
    "title": "Luxury Apartment",
    "description": "A beautiful 3BHK apartment in the city center.",
    "price": 25000.0,
    "securityDeposit": 50000.0,
    "propertyTypeId": 1,
    "bedroom": 3,
    "bathroom": 2,
    "balcony": 2,
    "area": 1500.0,
    "amenities": "Gym, Pool, Parking",
    "address": "123 Main St",
    "location": "City Center"
  }
  ```
- **Response:** `200 OK` (Returns created Property object)

#### Get All Properties
- **URL:** `/properties`
- **Method:** `GET`
- **Description:** Retrieve a list of all properties.
- **Response:** `200 OK` (Returns list of Property objects)

#### Update Property
- **URL:** `/properties/{id}`
- **Method:** `PUT`
- **Description:** Update an existing property.
- **Path Parameters:**
    - `id`: ID of the property to update.
- **Request Body:** `application/json` (Same as Create Property)
- **Response:** `200 OK` (Returns updated Property object)

#### Delete Property
- **URL:** `/properties/{id}`
- **Method:** `DELETE`
- **Description:** Delete a property.
- **Path Parameters:**
    - `id`: ID of the property to delete.
- **Response:** `200 OK` (Returns "Deleted")

---

### 2. Property Types
Manage types of properties (e.g., Apartment, House, Commercial).

#### Create Property Type
- **URL:** `/property-types`
- **Method:** `POST`
- **Description:** Create a new property type.
- **Request Body:** `application/json`
  ```json
  {
    "name": "Apartment"
  }
  ```
- **Response:** `200 OK` (Returns created PropertyType object)

#### Get All Property Types
- **URL:** `/property-types`
- **Method:** `GET`
- **Description:** Retrieve all property types.
- **Response:** `200 OK` (Returns list of PropertyType objects)

#### Update Property Type
- **URL:** `/property-types/{id}`
- **Method:** `PUT`
- **Description:** Update a property type.
- **Path Parameters:**
    - `id`: ID of the property type.
- **Request Body:** `application/json`
  ```json
  {
    "name": "Updated Name"
  }
  ```
- **Response:** `200 OK` (Returns updated PropertyType object)

#### Delete Property Type
- **URL:** `/property-types/{id}`
- **Method:** `DELETE`
- **Description:** Delete a property type.
- **Path Parameters:**
    - `id`: ID of the property type.
- **Response:** `200 OK` (Returns "Deleted successfully")

---

### 3. Contact
Handle contact submissions.

#### Submit Contact
- **URL:** `/contact`
- **Method:** `POST`
- **Description:** Submit a contact form or property inquiry.
- **Request Body:** `application/json`
  ```json
  {
    "fullName": "John Doe",
    "email": "john@example.com",
    "contactNumber": "+1234567890",
    "description": "Interested in this property.",
    "propertyTypeId": 1,
    "bedroom": 2,
    "bathroom": 1,
    "balcony": 1,
    "area": 1000.0,
    "price": 20000.0,
    "address": "456 Side St",
    "location": "Suburbs",
    "imageTempUrls": "http://example.com/image1.jpg,http://example.com/image2.jpg"
  }
  ```
- **Response:** `200 OK` (Returns created Contact object)

---

### 4. Property Images
Manage property images.

#### Upload Image
- **URL:** `/property-images`
- **Method:** `POST`
- **Description:** Link an image URL to a property.
- **Request Body:** `application/json`
  ```json
  {
    "propertyId": 1,
    "imageUrl": "http://example.com/image.jpg"
  }
  ```
- **Response:** `200 OK` (Returns created PropertyImage object)

---

## Postman Testing Instructions

1.  **Open Postman**: Launch the Postman application.
2.  **Create a Collection**: Click "New" -> "Collection" and name it "Rental DB API".
3.  **Add Requests**:
    *   Click "Add a request" inside the collection.
    *   Name the request (e.g., "Get All Properties").
    *   Select the HTTP method (GET, POST, PUT, DELETE) from the dropdown.
    *   Enter the URL (e.g., `http://localhost:8080/api/properties`).
4.  **Set Request Body (for POST/PUT)**:
    *   Go to the **Body** tab.
    *   Select **raw**.
    *   Choose **JSON** from the dropdown (instead of Text).
    *   Paste the JSON example provided in the endpoint documentation above.
5.  **Send Request**: Click the **Send** button.
6.  **Verify Response**: Check the **Body** section in the lower pane to see the response data and ensure the Status is `200 OK`.

### Example Workflow
1.  **Create Property Type**:
    *   POST `http://localhost:8080/api/property-types`
    *   Body: `{"name": "Apartment"}`
    *   Note the `id` from the response (e.g., `1`).
2.  **Create Property**:
    *   POST `http://localhost:8080/api/properties`
    *   Body: Use the example JSON, ensuring `propertyTypeId` matches the ID from step 1.
    *   Note the `id` of the created property.
3.  **Get All Properties**:
    *   GET `http://localhost:8080/api/properties`
    *   Verify the property created in step 2 is listed.
