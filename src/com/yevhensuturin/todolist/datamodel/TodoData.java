package com.yevhensuturin.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {
    private static final TodoData instance = new TodoData();
    public static final String filename = "TodoListItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance(){
        return instance;
    }

    private TodoData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem item){
        todoItems.add(item);
    }

//    public void setTodoItems(ObservableList<TodoItem> todoItems) {
//        this.todoItems = todoItems;
//    }

    public void loadTodoItems() throws IOException{
        todoItems = FXCollections.observableArrayList();
        Path path = Path.of(filename);

        String input;

        try(BufferedReader reader = Files.newBufferedReader(path)){
            while ((input = reader.readLine()) != null){
                String[] itemPeaces = input.split("\t");
                String shortDescription = itemPeaces[0];
                String details = itemPeaces[1];

                String dateString = itemPeaces[2];
                LocalDate date = LocalDate.parse(dateString, formatter);

                TodoItem todoItem = new TodoItem(shortDescription, details, date);
                todoItems.add(todoItem);
            }
        }
    }

    public void storeTodoItems() throws IOException{
        Path path = Path.of(filename);

        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            if(writer != null){
                Iterator<TodoItem> iterator = todoItems.iterator();
                while(iterator.hasNext()){
                    TodoItem item = iterator.next();
                    writer.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getDetails(), item.getDeadLine().format(formatter)));
                    writer.newLine();
                }
            }
        }
    }
}
