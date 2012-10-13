package com.brosinski.eclipse.regex.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.brosinski.eclipse.regex.RegExPlugin;


public class UserDAO {
    
    private static File stateDir = RegExPlugin.getDefault().getStateLocation().toFile();
    private static String fileName = "license.txt";
    
    public static User load() {
        BufferedReader reader = null;
        User user = new User();
        try {
            reader = new BufferedReader(new FileReader(stateDir + File.separator + fileName));
            user.setName(reader.readLine());
            user.setEmail(reader.readLine());
            user.setCode(reader.readLine());
        } catch (IOException ignore) {
            // was never registered, licence file missing?
        	// return empty default user
        	return user;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignore) {}
            }
        }
        return user;
    }

    public static void save(User user) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(stateDir + File.separator + fileName));
            writer.write(user.getName() + "\n" + user.getEmail() + "\n" + user.getCode());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {}
            }
        }
    }
    
}
