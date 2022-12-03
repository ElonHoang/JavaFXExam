/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.controller;

import com.aptech.navigator.Navigator;
import com.aptech.project.dao.CRUDDao;
import com.aptech.project.dao.daoImpl.DepartmentDAOImpl;
import com.aptech.project.dao.daoImpl.EmployeeDAOImpl;
import com.aptech.project.model.Department;
import com.aptech.project.model.Employee;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

/**
 *
 * @author Administrator
 */
public class EmployeeController {

    private Employee employee = null;
    private CRUDDao<Department> departmentDao = new DepartmentDAOImpl();
    private CRUDDao<Employee> employeeDao = new EmployeeDAOImpl();

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXComboBox<Department> cbDepartment;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Employee> tvEmployee;

    @FXML
    private TableColumn<Employee, Integer> tcId;

    @FXML
    private TableColumn<Employee, String> tcName;

    @FXML
    private TableColumn<Employee, Integer> tcPrice;

    @FXML
    private TableColumn<Employee, String> tcAddress;

    @FXML
    private TableColumn<Employee, String> tcDepartment;

    @FXML
    void btnAddClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (validation()) {
            try {
                if (employee == null) {
                    Employee insertEm = extractEmployeeFromFields();
                    insertEm = employeeDao.add(insertEm);
                    System.out.println(insertEm);
                    Navigator.getInstance().goToIndex();
                    alert.setTitle("Insert Employee");
                    alert.setHeaderText("A new Employee with ID = " + insertEm.getId());
                    alert.showAndWait();

                }
            } catch (Exception e) {
                alert.setTitle("Error");
                alert.setHeaderText("\"Employee already exists !\"");
                alert.showAndWait();
                return;
            }
        }
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        Employee dl = tvEmployee.getSelectionModel().getSelectedItem();

        if (dl == null) {
            selectEmployeeWarning();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure to do you want delete : " + "\n"
                    + "Name :" + dl.getName() + "\n"
                    + "Salary :" + dl.getSalary() + "\n"
                    + "Department :" + dl.getDepartment().getName());
            alert.setTitle("Deleting");
            Optional<ButtonType> confirmationResponse
                    = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.OK) {
                Employee deleteEm = tvEmployee.getSelectionModel().getSelectedItem();
                boolean result = employeeDao.delete(deleteEm);

                if (result) {
                    tvEmployee.getItems().remove(deleteEm);
                    System.out.println("A Employee is Deleted ");
                } else {
                    System.err.println("No Employee is Deleted");
                }
            }
        }
    }

    @FXML
    void btnEditClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Employee ud = tvEmployee.getSelectionModel().getSelectedItem();
        if (ud == null) {
            selectEmployeeWarning();
        } else {
                System.out.println(ud);
            employee = extractEmployeeFromFields();
            employee.setId(ud.getId());
            
            System.out.println("DeptID: "+ ud.getDepartment().getId());
//            employee.setDepartment(new Department(ud.getDepartment().getName()));
            alert.setTitle("Updating");
            boolean result = employeeDao.update(employee);
            if (result) {
                alert.setTitle("Update Employee");
                alert.setHeaderText("Update Successfull");
                alert.showAndWait();
                Navigator.getInstance().goToIndex();
            } else {
                alert.setTitle("Update Employee");
                alert.setHeaderText("Update Unsuccessful");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void btnSearchClick(ActionEvent event) {
        ObservableList<Employee> list = employeeDao.searchByName(txtSearch.getText());
        if (txtSearch.getText().isEmpty()) {
            tvEmployee.setItems(employeeDao.getAll());
        } else {
            tvEmployee.setItems(list);
        }
    }

    private boolean validation() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (txtName.getText().isEmpty()) {
            alert.setTitle("Name");
            alert.setHeaderText("Name is not empty");
            alert.showAndWait();
            return false;
        }

        if (txtAddress.getText().isEmpty()) {
            alert.setTitle("Address");
            alert.setHeaderText("Address is not empty");
            alert.showAndWait();
            return false;
        }

        if (txtPrice.getText().isEmpty()) {
            alert.setTitle("Salary");
            alert.setHeaderText("Salary is not empty");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private Employee extractEmployeeFromFields() {
        Employee em = new Employee();
        em.setName(txtName.getText());
        em.setAddress(txtAddress.getText());
        em.setSalary(Integer.valueOf(txtPrice.getText()));
        em.setDepartment(cbDepartment.getValue());
        return em;
    }

    private void selectEmployeeWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please select a Employee");
        alert.setHeaderText("A Employee must be selected for the operation ");
        alert.showAndWait();
    }

    // ObservableList<String> list = FXCollections.observableArrayList("Marketing Dept", "Financial Dept", "Technicial Dept", "Design Dept", "Personnel");
    public void initialize() throws SQLException {
        System.out.println("Employee Controller");
        ObservableList<Department> listDe = departmentDao.getAll();
        cbDepartment.setConverter(new StringConverter<Department>() {
            @Override
            public String toString(Department object) {
                return object.getName();
            }

            @Override
            public Department fromString(String string) {
                return cbDepartment.getItems().stream().filter(t->t.getName().equalsIgnoreCase(string)).findFirst().orElse(null);
            }
        });
        cbDepartment.setItems(listDe);
        cbDepartment.getSelectionModel().selectFirst();
        System.out.println(listDe);
        tvEmployee.setItems(employeeDao.getAll());
        tcName.setCellValueFactory((employee) -> {
            return employee.getValue().getNameProperty();
        });
        tcAddress.setCellValueFactory((employee) -> {
            return employee.getValue().getAddressProperty();
        });
        tcPrice.setCellValueFactory((employee) -> {
            return employee.getValue().getSalaryProperty();
        });
        tcId.setCellValueFactory((employee) -> {
            return employee.getValue().getIdProperty();
        });
        tcDepartment.setCellValueFactory((employee) -> {
            return employee.getValue().getDepartment().getNameProperty();
        });
        tvEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtName.setText(newSelection.getName());
                txtAddress.setText(newSelection.getAddress());
                txtPrice.setText(newSelection.getSalary().toString());
                cbDepartment.setValue(newSelection.getDepartment());
                
                System.out.println("NewID: "+ newSelection.getDepartment().getId());
            }
        });

    }

}
