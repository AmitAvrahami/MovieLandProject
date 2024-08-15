## Movieland
Movieland is a web-based system that allows users to upload and access information about movies, add movies to their personal watchlist, and receive summaries. The system also offers a feature to randomly select movies based on category. Users can log in either as a regular user or as an admin, with the admin having additional permissions such as uploading movies and deleting users.

Features
User and Admin Roles:
Regular users can browse movies, add them to their watchlist, and view summaries.
Admin users have the ability to upload new movies and manage user accounts.
Random Movie Selection: Choose a random movie based on a selected category.
Personal Watchlist: Users can create and manage their personal movie watchlist.
Architecture
Movieland is based on a client-server architecture using the TCP protocol for secure and reliable communication.

# Client-Side
UI Layer: This layer includes the graphical user interface (GUI) of the application, such as registration screens, movie search, watchlist management, and movie summaries.
Client Application Logic: Handles the actions performed by the user, manages events, sends requests to the server, and updates the client-side state.
Server-Side
Server Application Logic: Processes requests from clients, interacts with the database, and performs operations like searching and retrieving data.
Database: Stores data related to movies, users, and other relevant information.


# Algorithm Implementation
In the Movieland project, advanced algorithms were integrated to enhance the system's efficiency and flexibility. The design was structured to allow easy adaptation and the addition of new algorithms without altering the existing API.

- Strategy Pattern
  The system employs the Strategy Pattern to manage various algorithm implementations. This pattern ensures that different algorithms can be selected or swapped out as needed, providing flexibility in how certain tasks, like searching or caching, are performed.

- String Matching Algorithm
  A key implementation within the system is the StringMatchingAlgorithm, which allows for efficient string searching. This algorithm is vital for functionalities such as searching for movie titles or descriptions within the application.

- Extensibility
  The use of the Strategy Pattern means that new algorithms can be added seamlessly to the system. This design approach provides the flexibility to adapt and evolve the system without requiring changes to the core API, ensuring long-term maintainability and scalability.

# Technology
Java Sockets: Utilized for two-way communication between the client and server applications over a TCP/IP network. Sockets are used to send and receive data efficiently between the client and server.
Object Streams: The implementation includes the use of ObjectInputStream and ObjectOutputStream for handling custom class objects during communication.
Screenshots


Here's a more organized and aesthetically pleasing arrangement of the screenshots in the README format:

Movieland
Movieland is a web-based system that allows users to upload and access information about movies, add movies to their personal watchlist, and receive summaries. The system also offers a feature to randomly select movies based on category. Users can log in either as a regular user or as an admin, with the admin having additional permissions such as uploading movies and deleting users.

Features
User and Admin Roles:
Regular users can browse movies, add them to their watchlist, and view summaries.
Admin users have the ability to upload new movies and manage user accounts.
Random Movie Selection: Choose a random movie based on a selected category.
Personal Watchlist: Users can create and manage their personal movie watchlist.
Architecture
Movieland is based on a client-server architecture using the TCP protocol for secure and reliable communication.

Client-Side
UI Layer: This layer includes the graphical user interface (GUI) of the application, such as registration screens, movie search, watchlist management, and movie summaries.
Client Application Logic: Handles the actions performed by the user, manages events, sends requests to the server, and updates the client-side state.
Server-Side
Server Application Logic: Processes requests from clients, interacts with the database, and performs operations like searching and retrieving data.
Database: Stores data related to movies, users, and other relevant information.
Algorithm Implementation
In the Movieland project, advanced algorithms were integrated to enhance the system's efficiency and flexibility. The design was structured to allow easy adaptation and the addition of new algorithms without altering the existing API.

Strategy Pattern: The system employs the Strategy Pattern to manage various algorithm implementations. This pattern ensures that different algorithms can be selected or swapped out as needed, providing flexibility in how certain tasks, like searching or caching, are performed.

String Matching Algorithm: A key implementation within the system is the StringMatchingAlgorithm, which allows for efficient string searching. This algorithm is vital for functionalities such as searching for movie titles or descriptions within the application.

Extensibility: The use of the Strategy Pattern means that new algorithms can be added seamlessly to the system. This design approach provides the flexibility to adapt and evolve the system without requiring changes to the core API, ensuring long-term maintainability and scalability.

Technology
Java Sockets: Utilized for two-way communication between the client and server applications over a TCP/IP network. Sockets are used to send and receive data efficiently between the client and server.
Object Streams: The implementation includes the use of ObjectInputStream and ObjectOutputStream for handling custom class objects during communication.


# Screenshots
Main Screens:
<p align="center">
  <img src="https://github.com/user-attachments/assets/513a0c39-6a69-4d05-9790-ba0aed2bb1a7" width="250" alt="Main Screen 1">
  <img src="https://github.com/user-attachments/assets/64117e9f-b7c0-4226-a42d-0f935d04e8b2" width="250" alt="Main Screen 2">
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/b06c8f09-7701-4274-ab30-c79082f5f233" width="500" alt="Main Screen Full">
</p>
Search for Random Movie:
<p align="center">
  <img src="https://github.com/user-attachments/assets/22a6d555-508d-4cc4-b391-e20e0e7ed91d" width="250" alt="Random Movie Search 1">
  <img src="https://github.com/user-attachments/assets/a9250be5-7064-4ae4-8525-8a9e6f48ca51" width="300" alt="Random Movie Search 2">
</p>
Search Movies:
<p align="center">
  <img src="https://github.com/user-attachments/assets/3066ef67-1c79-4741-ab8e-3f22c222e86f" width="450" alt="Search Movies 1">
  <img src="https://github.com/user-attachments/assets/9502e1e1-965e-462c-aaf2-93d2518fe1b1" width="450" alt="Search Movies 2">
</p>
User Watch List:
<p align="center">
  <img src="https://github.com/user-attachments/assets/9dabe416-1194-42fe-aeed-f5c4c76f0059" width="530" alt="User Watch List">
</p>





