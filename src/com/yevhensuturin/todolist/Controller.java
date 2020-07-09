package com.yevhensuturin.todolist;

import com.yevhensuturin.todolist.datamodel.TodoData;
import com.yevhensuturin.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItems;

    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){
//        TodoItem item1 = new TodoItem("Mail birthday card", "Buy a 30th birthday card for John", LocalDate.of(2020, Month.AUGUST, 07));
//        TodoItem item2 = new TodoItem("Doctors Appointment", "See doctor Smith", LocalDate.of(2020, Month.SEPTEMBER, 12));
//        TodoItem item3 = new TodoItem("Finish design proposal", "I promise Mark", LocalDate.of(2020, Month.OCTOBER, 11));
//        TodoItem item4 = new TodoItem("Pick up Doug transaction", "Doug arriving on November 23", LocalDate.of(2020, Month.AUGUST, 07));
//        TodoItem item5 = new TodoItem("Pick up dry cleaning", "Closes should be ready by Wednesday", LocalDate.of(2021, Month.AUGUST, 20));
//
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);
//
//        TodoData.getInstance().setTodoItems(todoItems);

        todoListView.getSelectionModel().selectedItemProperty().addListener((observableValue, todoItem, newValue) -> {
                if(newValue !=null){
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    deadLineLabel.setText(item.getDeadLine().toString());
                }
            }
        );

        todoListView.setItems(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        todoListView.getSelectionModel().selectFirst();
    }

    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Todo Item");
        dialog.setHeaderText("Use this dialog to create a new item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
            todoListView.getSelectionModel().select(newItem);
        }
    }

//    public void handleClickListView(){
//        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println(item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadLine().toString());

//        itemDetailsTextArea.setText(item.getDetails());
//        deadLineLabel.setText(item.getDeadLine().toString());
//    }
}
