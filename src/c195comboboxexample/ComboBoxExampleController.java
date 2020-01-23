/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195comboboxexample;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author amy.antonucci
 */
public class ComboBoxExampleController implements Initializable {

    @FXML
    private ComboBox<String> cboCountry;

    @FXML
    private ComboBox<String> cboCity;
    @FXML
    private Button btnSelectUK;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://52.206.157.109/U03QIu";

        //  Database credentials; change to your database!
        final String DBUSER = "U03QIu";
        final String DBPASS = "53688051379";

        try {

            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ComboBoxExampleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initializeCountry();
    }

    @FXML
    private void initializeCity() {

        String country = cboCountry.getValue();

        String sql = "SELECT city.city "
                + "FROM city, country "
                + "WHERE city.countryId = country.countryId "
                + "AND country.country = \"" + country + "\"";

        ResultSet rs = accessDB(sql);
        cboCity.getItems().clear();

        try {
            while (rs.next()) {

                cboCity.getItems().add(rs.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ComboBoxExampleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initializeCountry() {

        ResultSet rs = accessDB("SELECT country FROM country");
        try {

            while (rs.next()) {

                cboCountry.getItems().add(rs.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ComboBoxExampleController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet accessDB(String sql) {

        ResultSet rs = null;

        try {

            Statement stmt;

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.beforeFirst(); //prepare for rs to be used by the caller
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    @FXML
    void selectUK(ActionEvent event) {

           cboCountry.getSelectionModel().select("Britain");
    }
}
