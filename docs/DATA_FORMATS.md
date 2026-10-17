# SDAMS Data File Formats

All files are in `SDAMS_Java/src/data/`. Read them with `new FileReader("src/data/<file>.txt")`.

**Rules for everyone**
- One record per line. Fields are separated by **commas** (`,`). **Never allow commas in user input.**
- **ID is always the first field.**
- Dates are `yyyy-mm-dd` (e.g. `2026-10-05`). Times are `HH:mm` (24h, e.g. `18:00`).
- Money has 2 decimals, with no "RM" in the file (e.g. `80.00`).
- Empty optional fields are written as `-`. `split(",")` drops empty fields at the end of a line, so the `-` keeps the column count fixed.
- These are **proposed formats + demo data**. The owner of each file can change the columns, but must tell the others who read it.

---

## users.txt (owner: Admin / Manager)
```
id,username,password,name,phone,role
IN002,kenji,123,Kenji Tanaka,0123456701,Instructor
```
Role values: `Manager`, `Administrator`, `Instructor`, `Student`, `FacilitiesOfficer`, `AttendanceOfficer`
ID prefixes: `MG`, `AD`, `IN`, `ST`, `FO`, `AO`

## classes.txt (owner: Instructor)
```
classId,instructorId,className,discipline,date,startTime,endTime,location,capacity,fee,status
C001,IN002,Karate Beginner,Karate,2026-08-10,18:00,19:30,Dojo A,20,80.00,Completed
```
Status: `Open`, `Full`, `Cancelled`, `Completed`
Read by: Student (booking), Attendance Officer, Manager (revenue)

## bookings.txt (owner: Student)
```
bookingId,studentId,classId,bookingDate,status
B001,ST001,C001,2026-08-04,Booked
```
Status: `Booked`, `Cancelled`
Read by: Attendance Officer (who to mark), Manager (number of registrations)

## payments.txt (owner: Student)
```
paymentId,studentId,bookingId,amount,date,method
P001,ST001,B001,80.00,2026-08-04,Card
```
Method: `Card`, `Online Banking`, `Cash`, `E-Wallet`
Read by: **Admin (Payment Records)**, Manager (revenue report)

## attendance.txt (owner: Attendance Officer)
```
attendanceId,bookingId,studentId,classId,status,recordedBy,recordedDate
AT001,B001,ST001,C001,Present,AO001,2026-08-10
```
Status: `Present`, `Absent`, `Late`
Read by: Student (can only rate after attendance is taken)

## ratings.txt (owner: Student)
```
ratingId,studentId,instructorId,classId,score,comment,date
R001,ST001,IN002,C001,5,Very clear teaching,2026-08-11
```
Score: whole number `1`–`5`
Read by: Instructor (View Instructor Rating)

## equipment.txt (owner: Facilities Officer)
```
equipmentId,name,category,quantity,minQuantity,condition,location,lastUpdated
EQ006,MMA Gloves,Gloves,8,10,Good,Equipment Room,2026-09-10
```
Condition: `Good`, `Fair`, `Damaged`
Low stock = `quantity < minQuantity` (useful for the stock report)

## facility_issues.txt (owner: Instructor creates, Facilities Officer updates)
```
issueId,reportedBy,category,description,location,reportedDate,status,updatedBy,lastUpdated,remarks
FI003,IN004,Equipment,Speed bag chain broken,Main Hall,2026-10-02,Open,-,-,-
```
Category: `Mats`, `Equipment`, `Lighting`, `Air-Conditioning`, `Room`
Status: `Open`, `In Progress`, `Resolved`

---

## How the demo data connects (both workflows from the brief)

**Class workflow:** `classes` (C001 by IN002) → `bookings` (B001, ST001) → `payments` (P001, RM80) → `attendance` (AT001 Present) → `ratings` (R001, 5 stars)

**Facility workflow:** `facility_issues` FI002 reported by IN003 → updated to `In Progress` by FO002

Edge cases already in the data, for testing:
- B011: booked but **not paid yet**
- B012: **cancelled** booking (no payment)
- C011: **cancelled** class. C007 / C008 / C010: **upcoming** open classes
- AT004: **absent** student (ST002 should not be able to rate C003)
- EQ006, EQ008, EQ012: **low stock**. EQ004: **damaged**
- P010 / C009: from **2025**, to test the yearly filter
