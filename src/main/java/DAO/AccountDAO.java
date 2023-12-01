package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    // 1) Insert new users into account table

    public Account addUserAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            //SQL Logic
            String sql = "INSERT INTO account(username, password) VALUES(?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //preparedStatement's setString methods:
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // 2) Verify existing users in the account table

    public Account validUserAccount(Account account){

        Connection connection = ConnectionUtil.getConnection();

        try{
            //SQL Logic
            String sql = "SELECT * FROM account where username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //preparedStatement's setString methods:
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
               Account validAccount = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                return validAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
