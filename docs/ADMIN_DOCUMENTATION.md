# Admin Module: Documentation Notes

Notes for the **Administrator** part of the SDAMS report.
Owner: William (Admin). Last updated: 2026-10-17.

---

## 0. Report checklist (Admin parts)

Tick these off as the report gets written.

- [ ] Admin's assumptions written in "Introduction and assumptions" (section 2)
- [ ] OOP concept (**Inheritance**) described and justified with a code screenshot (section 3)
- [ ] Admin use cases added to the group Use Case Diagram (section 4)
- [ ] Admin classes added to the group Class Diagram (section 5)
- [ ] Screenshots of every Admin screen, with a short explanation each (section 6)
- [ ] Screenshots of validation error popups (section 6)
- [ ] Admin limitations + future work (section 8)
- [ ] References for anything used (section 9)

---

## 1. What the brief asks Admin to do

| Function | Operations | Built in |
|---|---|---|
| Manage Staff (Instructors + Facilities Officers) | Add, view, search, update, delete | `admin/ManageStaff` |
| Manage Students | Add, view, search, update, delete | `admin/ManageStudents` |
| Payment Records | View and search/filter past payments | `admin/PaymentRecords` |

Rule from the brief: **each role sees only its own menu after login**. Admin logs in, then only `AdminDashboard` opens.

---

## 2. Assumptions (copy into the report)

1. Admin manages **only** Instructors and Facilities Officers as "staff". Administrators are managed by the Manager. Attendance Officers are not listed under Admin in the brief.
2. Each user has one role. A staff member's **role cannot be changed** after creation (the Role dropdown locks on update). To change a role, delete the user and add them again.
3. IDs are generated automatically and never typed by the user. Prefixes: `MG` Manager, `AD` Admin, `IN` Instructor, `FO` Facilities Officer, `ST` Student, `AO` Attendance Officer. Example: `IN002`.
4. Usernames are unique across **all** users (not case-sensitive).
5. Admin can only **view** payment records. Students create payments, and admin cannot edit or delete them.
6. Phone numbers are Malaysian style: 10 or 11 digits, no dashes.
7. Passwords are stored as plain text in `users.txt`. Acceptable for this assignment; see Limitations.
8. The brief says "five user roles" but lists six. We built all six.

---

## 3. OOP concept: Inheritance (Admin's concept)

### What it is
A child class gets all the fields and methods of its parent class, and can add or change its own.

### Where it is in our code
```
User  (abstract)            id, username, password, name, phone + getters/setters
 ├── Staff (abstract)
 │    ├── Administrator
 │    ├── Instructor
 │    └── FacilitiesOfficer
 ├── Manager
 ├── Student
 └── AttendanceOfficer
```
Files: `src/model/User.java`, `Staff.java`, `Instructor.java`, `FacilitiesOfficer.java`, `Student.java`

### Code to screenshot for the report
1. **`Staff extends User`** and its constructor calling `super(...)` (`model/Staff.java`)
2. **`Instructor extends Staff`** overriding `getRole()` (`model/Instructor.java`)
3. **The Add button in `ManageStaff`.** A parent-type variable holds a child object:
   ```java
   Staff staff;
   if (role.equals("Instructor")) {
       staff = new Instructor(UserFile.nextId("IN"), username, password, name, phone);
   } else {
       staff = new FacilitiesOfficer(UserFile.nextId("FO"), username, password, name, phone);
   }
   UserFile.add(staff);
   ```
4. **The Update button.** It uses `setName()`, `setPhone()` etc. that are written **once** in `User` and inherited by every role.
5. **`loadTable()`** uses `instanceof Instructor || instanceof FacilitiesOfficer` to pick staff out of one list of `User` objects.

### Justification (why we used it), in your own words for the report
- **Less repeated code:** id, username, password, name and phone are shared by all 6 roles, so they are written once in `User` instead of 6 times.
- **One list for all users:** `ArrayList<User>` can hold every role, so one file (`users.txt`) and one helper (`UserFile`) handle everyone.
- **Easy to extend:** adding a new role means making one small child class.
- **`Staff` groups the staff roles:** if staff need extra fields later (salary, join date), they go in `Staff` only.

> Other concepts that also appear in Admin code. Mention them briefly, but **Inheritance is Admin's main one**.
> - Encapsulation: `private` fields in `User` + getters/setters
> - Abstraction: `User` and `Staff` are `abstract`, and `getRole()` is abstract
> - Polymorphism: `user.getRole()` returns a different value per child class

---

## 4. Use Case Diagram (Admin part)

Actor: **Administrator**

- Login / Logout *(shared with all actors)*
- Manage Staff → `<<include>>` Add Staff, View Staff, Search Staff, Update Staff, Delete Staff
- Manage Students → `<<include>>` Add Student, View Student, Search Student, Update Student, Delete Student
- View Payment Records → `<<extend>>` Filter by Month/Year, Search by Student

Links to other actors (for the group diagram):
- Instructors and Facilities Officers that Admin creates can then log in and use their own use cases.
- Payment records come from **Student → Make Payment**.

---

## 5. Class Diagram (Admin part)

| Class | Package | Key members | Relationship |
|---|---|---|---|
| `User` (abstract) | model | `-id, -username, -password, -name, -phone`, getters/setters, `+getRole()` *(abstract)*, `+toFileString()` | Parent of all roles |
| `Staff` (abstract) | model | constructor only | `extends User` |
| `Administrator` / `Instructor` / `FacilitiesOfficer` | model | `+getRole()` | `extends Staff` |
| `Student` | model | `+getRole()` | `extends User` |
| `UserFile` | model | `+readAll()`, `+writeAll()`, `+add()`, `+nextId()`, `+usernameExists()`, `+createUser()` | **uses** (dependency) `User` and its children |
| `LoggedIn` | login | static `userId, username, name, role` | used by `AdminDashboard` |
| `AdminDashboard` | admin | buttons → open the 3 screens | `extends JFrame` |
| `ManageStaff` | admin | `loadTable()`, `validateInputs()`, `clearFields()` | uses `UserFile`, `Staff`, `Instructor`, `FacilitiesOfficer` |
| `ManageStudents` | admin | `loadTable()`, `validateInputs()`, `clearFields()` | uses `UserFile`, `Student` |
| `PaymentRecords` | admin | `loadTable()`, `getStudentName()` | reads `payments.txt`, uses `UserFile` |

Draw `User` → `Staff` → children with **hollow-triangle arrows** (generalization).

---

## 6. Screenshots to take (with what to write under each)

| # | Screen | What to show | Explanation to write |
|---|---|---|---|
| 1 | Login | admin / 123 typed | Admin logs in, and only the Admin menu opens |
| 2 | AdminDashboard | Welcome name + 3 buttons | Role-specific menu |
| 3 | ManageStaff (empty form) | Table listing staff | View staff |
| 4 | ManageStaff → Add | Popup "Staff added. ID: IN002" | ID is generated automatically |
| 5 | ManageStaff → row clicked | Boxes filled, Role locked | Select to update/delete |
| 6 | ManageStaff → Update | Popup + updated row | Update staff |
| 7 | ManageStaff → Delete | Confirm dialog | Delete with confirmation |
| 8 | ManageStaff → Search | e.g. "instructor" typed | Search/filter |
| 9 | ManageStudents | Same 4 actions as staff | Manage students |
| 10 | PaymentRecords | All records + total label | View payments |
| 11 | PaymentRecords → filter | Month = October, Year = 2026 | Filter by month/year |
| 12 | `users.txt` before/after | Opened in NetBeans | Proves data is saved to a text file |

**Validation screenshots (each is a popup):**
- Empty field → "Please fill in all fields"
- Comma in input → "Commas (,) are not allowed"
- Bad phone → "Phone must be 10 or 11 digits"
- Name with numbers → "Name can only contain letters and spaces"
- Duplicate username → "Username already taken"
- Update/Delete with nothing selected → "Please select a ... from the table first"
- Bad year in Payment Records → "Year must be 4 digits"

---

## 7. Data files Admin uses

**`src/data/users.txt`** (Admin reads + writes)
```
id,username,password,name,phone,role
IN001,instructor,123,Default Instructor,0120000003,Instructor
```

**`src/data/payments.txt`** (Admin reads only; the Student module writes it)
```
paymentId,studentId,bookingId,amount,date(yyyy-mm-dd),method
P001,ST001,B001,80.00,2026-10-05,Card
```
> ⚠️ This payment format needs to be confirmed with the Student member.

Test accounts: `admin / 123` (plus one per role, all password `123`).

---

## 8. Limitations and future work (Admin)

**Limitations**
- Passwords are stored in plain text.
- Deleting an Instructor does not check if they still have classes or bookings.
- Deleting a Student does not remove their bookings or payments.
- Role cannot be changed after a user is created.
- Search is a simple "contains" text match.
- If two people save at the same time, one change can overwrite the other (text files have no locking).

**Future work**
- Hash passwords (e.g. SHA-256).
- Block deleting staff/students who have active classes, bookings or payments.
- Export payment records to CSV/PDF.
- Add email + date-joined fields for staff and students.
- Admin can reset a user's password.

---

## 9. References (to fill in)
- Oracle Java Swing tutorial: JTable, JComboBox, JOptionPane
- Lecture notes: OOP concepts, file handling (FileReader / BufferedReader / FileWriter)
- Any martial arts management systems you looked at (the brief asks for research)

---

## 10. Change log
| Date | What changed |
|---|---|
| 2026-10-17 | Project structure set up; Login + role redirection (taught `LoggedIn` style) |
| 2026-10-17 | Admin: Dashboard, ManageStaff, ManageStudents, PaymentRecords, `UserFile` helper |
| 2026-10-17 | Demo data in all 8 `src/data/*.txt` files, linked together; formats in `docs/DATA_FORMATS.md` |
