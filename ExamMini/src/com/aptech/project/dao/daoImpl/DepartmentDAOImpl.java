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
public class DepartmentDAOImpl implements CRUDDao<Department>{
    DB_Type data = DB_Type.SQL;
    
    @Override
    public ObservableList<Department> getAll() {
         ObservableList<Department> departments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Department ";

        try (
                Connection connect = DB_Connect.getConnection(data);
                Statement stsm = connect.createStatement();
                ResultSet result = stsm.executeQuery(sql);) {
            while (result.next()) {
                Department a = new Department();
                a.setId(result.getInt("Id"));
                a.setName(result.getString("Name"));
                departments.add(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return departments;
    }

    @Override
    public Department add(Department t) {
         String sql = "INSERT INTO Department (Name) "
                + "VALUES (?)";
        ResultSet key = null;
//        boolean check = checkExists(newEm);
//        if (check == false){
        try (
                Connection connect = DB_Connect.getConnection(data);
                PreparedStatement stsm = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stsm.setString(1, t.getName());
            int rowInserted = stsm.executeUpdate();
            if (rowInserted == 1) {
                key = stsm.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                t.setId(newKey);
                return t;
            } else {
                System.out.println("No Department inserted");
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
 //   }
    }

    @Override
    public boolean delete(Department t) {
         String sql = "DELETE  FROM Department "
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
                System.err.println("No Department deleted");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Department t) {
         String sql = "UPDATE Department SET "
                + "Name = ?"
                + "WHERE Id = ?";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);) {

            stmt.setString(1, t.getName());
            stmt.setInt(2, t.getId());
            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No Department updated");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public ObservableList<Department> searchByName(String txt) {
        return null;
    }

    @Override
    public boolean checkExists(Department t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Department> getById(int id) {
        String sql = "SELECT * FROM Department WHERE Id = ?";
        try (
                Connection connect = DB_Connect.getConnection();
                PreparedStatement stmt = connect.prepareStatement(sql);
                ) {
                  stmt.setInt(1, id);
                ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Department a = new Department();
                a.setId(result.getInt("Id"));
                a.setName(result.getString("Name"));
                return Optional.of(a);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
