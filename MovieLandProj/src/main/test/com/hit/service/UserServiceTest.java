package com.hit.service;

import com.hit.dm.MovieDataSample;
import com.hit.dm.UserDataSample;
import com.hit.dm.movie.Movie;
import com.hit.dm.movie.MovieCategory;
import com.hit.dm.user.User;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserServiceTest extends TestCase {
    private static final String m_filePath = "MovieLandProj/src/main/resources/userdatasource.txt";
    private static UserService userService;

    static {
        try {
            userService = new UserService(m_filePath);
        } catch (IOException e) {
            System.out.println("IO exception");
        }
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
        testRegister();
        try {
            List<User> allUsers = userService.getAllUsersFromDb();
            assertEquals(sampleUsers.size(), allUsers.size());
            assertTrue(areUsersListsEqual(sampleUsers, allUsers));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Exception occurred during test: " + e.getMessage(), e);
        } finally {
            removeSampleData();
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
        }finally {
            removeSampleData();
        }
    }

//    public void testLogout() {
//    }


    public void testUpdateUser() {
        testRegister();
        User userToUpdate = sampleUsers.get(4);
        userToUpdate.setUserName("admin");
        try {
            userService.updateUser(userToUpdate);
            assertEquals(userToUpdate,userService.getAllUsers().get(4));
        } catch (Exception e) {
            assertFalse(userService.getAllUsers().contains(userToUpdate));
        }finally {
            removeSampleData();
        }

    }

    public void testAddToWatchlist() {
        testRegister();
        Movie newMovie = new Movie(1, "The Avengers", null, "Action-packed superhero movie", MovieCategory.ACTION, "2h 23m", null);
        try {
            User user = sampleUsers.get(2);
            userService.addToWatchlist(user, newMovie);
            HashMap<Movie,Boolean> updatedWatchlist = user.getUserMovieWatchList();
            assertNotNull(updatedWatchlist);
            assertTrue(updatedWatchlist.containsKey(newMovie));
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    public void testRemoveFromWatchlist() {
    }

    public void testUpdateWatchlistStatus() {
    }

    public void testDeleteUser() {
    }

    public void testChangePassword() {
    }

    public void testLogin() {
    }

    public void testIsAdmin() {
    }
}