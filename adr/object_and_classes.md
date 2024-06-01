```plantuml
@startuml

class User {
  Integer userId
  String username
  String password
  String email
  String role
}

class Player {
  Long playerId
  String name
  Integer age
  String team
  String position
  Map<String, Object> statistics
}

class Report {
  Long reportId
  Long playerId
  String content
  Date createdAt
}

class Rating {
  Long ratingId
  Long reportId
  Long userId
  Integer score
}

class Review {
  Long reviewId
  Long reportId
  Long userId
  String comment
  Date createdAt
}

User "1" -- "0..*" Rating : has
User "1" -- "0..*" Review : has
Player "1" -- "0..*" Report : has
Report "0..*" -- "1" Player : belongs to
Report "0..*" -- "1" Rating : has
Report "0..*" -- "1" Review : has
Rating "1" -- "1" Report : belongs to
Rating "1" -- "1" User : belongs to
Review "1" -- "1" Report : belongs to
Review "1" -- "1" User : belongs to

@enduml
```