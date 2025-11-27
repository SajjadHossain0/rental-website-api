# Frontend Integration Manual: How the System Works

This manual explains the **logic** and **process** of connecting your frontend application (React/Vue) to the Rental Website API. It focuses on "how to manage it" rather than just code snippets.

## 1. The Core Concept: Client & Server
Think of your system as two separate entities talking to each other:
*   **The Server (Spring Boot)**: The "Brain". It holds the database, logic, and data. It waits for requests.
*   **The Client (Frontend)**: The "Face". It shows data to the user and sends their actions to the server.

**The Golden Rule:** The Frontend **never** changes the database directly. It asks the Server to do it via **API Requests**.

---

## 2. Scenario 1: Displaying Data (The "Read" Process)
**Goal:** Show a list of all properties on the home page.

### How it works (Step-by-Step):
1.  **Page Load:** When the user opens the "Properties" page, the component mounts (starts up).
2.  **The Trigger:** The component immediately sends a signal: "I need data."
3.  **The Request:** It sends a `GET` request to `http://localhost:8080/api/properties`.
4.  **The Wait:** The internet is slower than code. The frontend shows a **Loading Spinner** while waiting.
5.  **The Response:** The Server replies with a JSON list: `[{ "title": "Villa", "price": 5000 }, ...]`.
6.  **State Update:** The frontend takes this JSON and saves it into its internal memory (State).
7.  **The Render:** As soon as the State changes, the screen automatically updates to show the list of cards instead of the spinner.

### How to Manage It:
*   **Empty State:** If the list is empty `[]`, show a message: "No properties found."
*   **Error State:** If the server fails (500 Error), show a message: "Something went wrong, please try again."

---

## 3. Scenario 2: Creating Data (The "Write" Process)
**Goal:** An admin adds a new property listing.

### How it works (Step-by-Step):
1.  **The Form:** The admin fills out a form (Title, Price, Description).
2.  **Local State:** As they type, the frontend saves the values in temporary variables (e.g., `formData`).
3.  **The Submit:** The admin clicks "Save".
4.  **Validation:** The frontend checks: Is the price a number? Is the title empty? If bad, stop and show red error text.
5.  **The Request:** If good, it sends a `POST` request to `http://localhost:8080/api/properties` with the JSON data.
6.  **The Success:** The Server replies: "200 OK, Created".
7.  **The Feedback:** The frontend shows a green "Success!" toast notification and redirects the admin back to the list.

---

## 4. Scenario 3: Dynamic Pages (Details View)
**Goal:** The user clicks on a specific property to see more details.

### How it works:
1.  **The Click:** User clicks a card with ID `5`.
2.  **The URL:** The browser URL changes to `/properties/5`.
3.  **The Detection:** The "Details Page" component wakes up and asks: "What is the ID in the URL?" -> Answer: `5`.
4.  **The Request:** It sends a specific `GET` request: `http://localhost:8080/api/properties/5`.
5.  **The Display:** It shows the details *only* for that specific house.

---

## 5. Scenario 4: Handling Images (The 2-Step Process)
**Challenge:** You cannot send a file and JSON text easily in one go with this API structure.
**Solution:**
1.  **Step 1:** Create the Property first (as in Scenario 2). The server returns the new ID (e.g., `101`).
2.  **Step 2:** Immediately after getting ID `101`, send the Image URL via a second `POST` request to `/api/property-images`.
    *   Payload: `{ "propertyId": 101, "imageUrl": "..." }`

---

## 6. How to Manage the Code (Best Practices)
To keep your project from becoming a mess, follow these rules:

### A. Don't scatter API calls
Don't write `axios.get('...')` inside every button click.
**Do this instead:** Create a folder `src/api/`.
*   File `properties.js`: Contains `getAll()`, `create()`, `delete()`.
*   **Why?** If the URL changes, you only fix it in one place.

### B. Centralize Configuration
Create one file `axiosClient.js` that knows the Base URL (`http://localhost:8080/api`).
*   **Why?** When you deploy to the real internet, you change this *one string* and the whole app updates.

### C. Separation of Concerns
*   **Pages:** Handle logic (fetching data, handling errors).
*   **Components:** Handle looks (displaying the card, styling the button).
*   **Why?** You can reuse the "Property Card" component on the Home page, the Search page, and the Admin page.
