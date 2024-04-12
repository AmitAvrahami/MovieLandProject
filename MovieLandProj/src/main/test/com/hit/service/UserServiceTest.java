package com.hit.service;

import com.hit.dm.data.sample.UserDataSample;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.user.PermissionLevel;
import com.hit.dm.user.User;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserServiceTest extends TestCase {
    private static UserService userService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        userService = new UserService();
        removeSampleData();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        removeSampleData();
    }

    private List<User> sampleUsers = UserDataSample.getSampleUsers();
    private List<User> allUserTest;

    private void removeSampleData() {
        File file = new File("MovieLandProj/src/main/resources/userdatasource.txt");
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new ArrayList<>());
            System.out.println("Sample data file has been cleared.");
            Thread.sleep(1000);
        } catch (IOException e) {
            System.err.println("Failed to clear sample data file: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean areUsersListsEqual(List<User> list1, List<User> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            User user1 = list1.get(i);
            User user2 = list2.get(i);
            if (!user1.equals(user2)) {
                return false;
            }
        }
        return true;
    }

    public void testRegister() {
        try {
            for (int i = 0; i < sampleUsers.size(); i++) {
                User user = sampleUsers.get(i);
                userService.register(user);
                User userFromDb = userService.getUserById(user.getUserId());
                assertNotNull("User should exist in the database", userFromDb);
                assertEquals(user, userFromDb);
            }
        } catch (Exception e) {
            fail("not success register");
        }
    }

    public void testGetAllUsersFromDb() {
        removeSampleData();
        testRegister();
        try {
            List<User> allUsers = userService.getAllUsersFromDb();
            assertEquals(sampleUsers.size(), allUsers.size());
            assertTrue(areUsersListsEqual(sampleUsers, allUsers));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Exception occurred during test: " + e.getMessage(), e);
        }
    }

    public void testGetUserById() {
        testRegister();
        try {
            allUserTest = userService.getAllUsersFromDb();
            for (int i = 0; i < allUserTest.size(); i++) {
                User userFromDb = userService.getUserById(i + 1);
                assertNotNull("User should not be null for valid ID", userFromDb);
                assertEquals("Retrieved user should match the sample user", sampleUsers.get(i), userFromDb);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Exception occurred during test: " + e.getMessage(), e);
        }
    }


    public void testUpdateUser() {
        removeSampleData();
        testRegister();
        User userToUpdate = sampleUsers.get(4);
        userToUpdate.setUserName("admin");
        try {
            Thread.sleep(1000);
            userService.updateUser(userToUpdate);
            assertEquals(userToUpdate, userService.getAllUsers().get(4));
        } catch (Exception e) {
            assertFalse(userService.getAllUsers().contains(userToUpdate));
        }

    }

    public void testAddToWatchlist() {
        testRegister();
        Movie newMovie = new Movie(1, "The Avengers",  "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
        try {
            User user = sampleUsers.get(2);
            userService.addToWatchlist(user, newMovie);
            List<Movie> updatedWatchlist = user.getUserMovieWatchList();
            assertNotNull(updatedWatchlist);
            assertTrue(updatedWatchlist.contains(newMovie));
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    public void testRemoveFromWatchlist() {
        testRegister();
        Movie newMovie = new Movie(1, "The Avengers",  "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
        try {
            User user = sampleUsers.get(0);
            userService.removeFromWatchlist(user, newMovie);
            List<Movie> updatedWatchlist = user.getUserMovieWatchList();
            assertNotNull(updatedWatchlist);
            assertFalse(updatedWatchlist.contains(newMovie));
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    public void testUpdateWatchlistStatus() {
        testRegister();
        Movie newMovie = new Movie(1, "The Avengers",  "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
        try {
            User user = sampleUsers.get(2);
            userService.addToWatchlist(user, newMovie);
            List<Movie> updatedWatchlist = user.getUserMovieWatchList();
            assertNotNull(updatedWatchlist);
            assertTrue(updatedWatchlist.contains(newMovie));
        } catch (Exception e) {
            fail();
        }
    }

    public void testDeleteUser() {
        removeSampleData();
        testRegister();
        User userToDelete = sampleUsers.get(0);
        try {
            assertEquals(5,userService.getAllUsers().size());
            userService.deleteUser(userToDelete);
            assertEquals(4,userService.getAllUsers().size());
            assertFalse(userService.getAllUsers().contains(userToDelete));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public void testLogin() {
        User sampleUser = new User(1, "sampleUser", "password", PermissionLevel.USER, new ArrayList<>());
        try {
            userService.register(sampleUser);
        } catch (Exception e) {
            fail("Failed to register sample user: " + e.getMessage());
        }finally {
            removeSampleData();
        }
        assertTrue("Login should succeed with correct credentials", Objects.nonNull(userService.login("sampleUser", "password")));
        assertFalse("Login should fail with incorrect username", Objects.nonNull(userService.login("incorrectUser", "password")));
        assertFalse("Login should fail with incorrect password", Objects.nonNull(userService.login("sampleUser", "incorrectPassword")));
    }

    public void testIsAdmin() {
        User adminUser = new User(1, "adminUser", "password", PermissionLevel.ADMIN, new ArrayList<>());
        try {
            userService.register(adminUser);
        } catch (Exception e) {
            fail("Failed to register admin user: " + e.getMessage());
        }
        assertTrue("User should be identified as an admin", userService.isAdmin(adminUser));
        User userUser = new User(2, "userUser", "password", PermissionLevel.USER,new ArrayList<>());
        try {
            userService.register(userUser);
        } catch (Exception e) {
            fail("Failed to register user user: " + e.getMessage());
        }
        assertFalse("User should not be identified as an admin", userService.isAdmin(userUser));
    }
}