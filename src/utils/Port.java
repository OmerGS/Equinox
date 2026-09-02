package utils;

import exceptions.NotValidPortNumber;

public class Port {
    public static boolean CheckPort(int port) throws NotValidPortNumber {
        if(port > 65535) {
            throw new NotValidPortNumber("A port number cannot exceed 65535.");
        }

        if(port < 0) {
            throw new NotValidPortNumber("A port cannot be a negative number.");
        }

        return true;
    }
}
