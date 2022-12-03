/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.project.dao.daoImpl;

import com.aptech.connect.DB_Connect;
import com.aptech.connect.DB_Type;
import com.aptech.project.dao.CRUDDao;
import com.aptech.project.model.Department;
import com.aptech.project.model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Administrator
 */
public class EmployeeDAOImpl implements CRUDDao<Employee> {

    DB_Type data = DB_Type.SQL;
    CRUDDao<Department> deCRUDDao = new DepartmentDAOImpl();

    @Override
    public ObservableList<Employee> getAll() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Employee ";

        try (
                Connection connect = DB_Connect.getConnection(data);
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery(sql);) {
            while (result.next()) {
                Employee a = new Employee();
                a.setId(result.getInt("Id"));
                a.setName(result.getString("Name"));
                a.setAddress(result.getString("Address"));
                a.setSalary(result.getInt("Salary"));

                Department d = new Department();
                d.setName(deCRUDDao.getById(result.getInt("DepartmentId")).get().getName());
                d.setId(result.getInt("DepartmentId"));
                a.setDepartment(d);
                employees.add(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee add(Employee t) {
        String sql = "INSERT INTO Employee (Name, Address, Salary, DepartmentId) "
                + "VALUES (?, ?, ?, ?)";
        ResultSet key = null;
        boolean check = checkExists(t);
        if (check == false) {
            try (
                    Connection connect = DB_Connect.getConnection(data);
                    PreparedStatement stsm = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

                stsm.setString(1, t.getName());
                stsm.setString(2, t.getAddress());
                stsm.setInt(3, t.getSalary());
                stsm.setInt(4, t.getDepartment().getId());

                int rowInserted = stsm.executeUpdate();
                if (rowInserted == 1) {
                    key = stsm.getGeneratedKeys();
                    key.next();
                    int newKey = key.getInt(1);
                    t.setId(newKey);
                    return t;
                } else {
                    System.out.println("No Employee inserted");
                    return null;
                }
            } catch (Exception e) {
                System.err.println(e);
                return null;
            } finally {
                if (key != null) {
                    try {
                        key.close();
                    } catch (SQLException ex) {

                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(Employee t) {
        String sql = "DELETE  FROM Employee "
                + "WHERE Id = ? "
                + "LIMIT 1";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {

            stmt.setInt(1, t.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No Employee deleted");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Employee t) {
        String sql = "UPDATE Employee SET "
                + "Name = ?, "
                + "Address = ?, "
                + "Salary = ?, "
                + "DepartmentId = ? "
                + "WHERE Id = ?";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {

            stmt.setString(1, t.getName());
            stmt.setString(2, t.getAddress());
            stmt.setInt(3, t.getSalary());
            stmt.setInt(4, t.getDepartment().getId());

            stmt.setInt(5, t.getId());
            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No Employee updated");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public ObservableList<Employee> searchByName(String txt) {
        ObservableList employee = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Employee WHERE `Name` LIKE ?";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {
            stmt.setString(1, '%' + txt + '%');
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Employee a = new Employee();
                a.setId(result.getInt("Id"));
                a.setName(result.getString("Name"));
                a.setAddress(result.getString("Address"));
                a.setSalary(result.getInt("Salary"));
                Department d = new Department();
                d.setId(result.getInt("DepartmentId"));
                a.setDepartment(d);
                employee.add(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public boolean checkExists(Employee t) {
        String sql = "SELECT Name FROM Employee "
                + "WHERE Name = ?";
        boolean check = false;
        try (
                Connection connect = DB_Connect.getConnection(data);
                PreparedStatement stmt = connect.prepareStatement(sql);) {
            stmt.setString(1, t.getName());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                check = true;
            }
        } catch (Exception e) {
            check = true;
        }
        return check;
    }

    @Override
    public Optional<Employee> getById(int id) {
        String sql = "SELECT * FROM Employee WHERE Id = ?";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Employee a = new Employee();
                a.setId(result.getInt("Id"));
                a.setName(result.getString("Name"));
                a.setSalary(result.getInt("Salary"));
                a.setAddress(result.getString("Address"));
                Department d = new Department();
                d.setId(result.getInt("DepartmentId"));
                a.setDepartment(d);
                return Optional.of(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
