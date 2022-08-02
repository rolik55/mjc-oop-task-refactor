package main.java.mjc.exceptions;

public class CsvLineException extends Exception{
    public CsvLineException() {

    }

    public CsvLineException(String message) {
        System.err.println(message);
    }
}
