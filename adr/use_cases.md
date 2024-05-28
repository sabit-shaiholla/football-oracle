# Football Oracle: Use Cases

### 1. User Registration and Login
* Actors: Guest
* Description: A guest user registers and logs into the system.
* Steps:
  1. User accesses the registration page.
  2. User fills in registration details and submits.
  3. System creates a new user account.
  4. User logs in using the registered credentials.
  
### 2. Search for Players
* Actors: Registered User, Guest
* Description: Users search for football players.
* Steps:
  1. User enters a player’s name in the search bar.
  2. System displays a list of players matching the search criteria.

### 3. View Scouting Report
* Actors: Registered User, Guest
* Description: Users view AI-generated reports for a selected player.
* Steps:
  1. User selects a player from the search results.
  2. System displays the scouting report for the selected player.

### 4. Rate and Review Scouting Report
* Actors: Registered User
* Description: Registered users rate and review scouting reports.
* Steps:
  1. User views a scouting report.
  2. User submits a rating and/or review.
  3. System updates the report with the user’s feedback.
  
### 5. Manage User Profile
* Actors: Registered User
* Description: Users update their profile information and review history.
* Steps:
  1. User accesses the profile management page.
  2. User updates personal information or reviews.
  3. System saves the updated information.
  
### 6. Admin Manage Users
* Actors: Admin
* Description: Admin manages user accounts and content.
* Steps:
  1. Admin accesses the user management page.
  2. Admin views and updates user information.
  3. Admin can disable or delete user accounts.

```plantuml
@startuml
left to right direction

actor Guest as g
actor RegisteredUser as ru
actor Admin as a

package "Football Oracle System" {
    usecase "Access registration page" as UC1
    usecase "Fill in registration details and submit" as UC2
    usecase "Create new user account" as UC3
    usecase "Log in using registered credentials" as UC4
    usecase "Enter player’s name in search bar" as UC5
    usecase "Display list of players matching search criteria" as UC6
    usecase "Select player from search results" as UC7
    usecase "Display scouting report for selected player" as UC8
    usecase "View scouting report" as UC9
    usecase "Submit rating and/or review" as UC10
    usecase "Update report with user’s feedback" as UC11
    usecase "Access profile management page" as UC12
    usecase "Update personal information or reviews" as UC13
    usecase "Save updated information" as UC14
    usecase "Access user management page" as UC15
    usecase "View and update user information" as UC16
    usecase "Disable or delete user accounts" as UC17
}

g --> UC1
g --> UC2
UC2 --> UC3
g --> UC4

ru --> UC5
g --> UC5
UC5 --> UC6

ru --> UC7
g --> UC7
UC7 --> UC8

ru --> UC9
ru --> UC10
UC10 --> UC11

ru --> UC12
ru --> UC13
UC13 --> UC14

a --> UC15
a --> UC16
a --> UC17
@enduml
```