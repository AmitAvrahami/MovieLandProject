package org.example.movieclient.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.movieclient.client.Request;
import org.example.movieclient.model.movie.Movie;
import org.example.movieclient.model.user.User;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;

public class Client {

    private static final Gson gson = new Gson();
    private static final String LOCAL_HOST = "localhost";
    private static final int PORT = 34567;
    private static Socket myServer;

    public Client() throws IOException {
    }

    public static void saveToServer(Request request) throws IOException {
        PrintWriter writer = null;
        try {
            myServer = new Socket(LOCAL_HOST, PORT);
            writer = new PrintWriter(myServer.getOutputStream(), true);
            String x = gson.toJson(request);
            writer.println(x);
            writer.flush();
        } catch (IOException e) {
            throw new ConnectException();
        }
    }

    public static Object receiveFromServer() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                return gson.fromJson(line, Object.class);
            }
        }
        throw new IOException("Failed to receive data from server.");
    }

    public static <E> E receiveUserOrMovieFromSever(TypeToken<E> typeToken) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                return gson.fromJson(line, typeToken.getType());
            }
        }
        throw new IOException("Failed to receive data from server.");
    }

    public static User receiveUserFromServer() throws IOException {
        if (myServer == null) {
            throw new IOException("Server connection is not established.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                return gson.fromJson(line, User.class);
            }
        }
        throw new IOException("Failed to receive user data from server.");
    }

    public static List<Movie> receiveListMovieFromServer() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                return gson.fromJson(line, new TypeToken<List<Movie>>() {
                }.getType());
            }
        }
        throw new IOException("Failed to receive movie list from server.");
    }

    public static <T> List<T> receiveListFromServer(TypeToken<List<T>> typeToken) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                return gson.fromJson(line, typeToken.getType());
            }
        }
        throw new IOException("Failed to receive list from server.");
    }
}
