package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    public AccountDAO accountDAO;
    
    //No Args constructor to create AccountDAO

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //Constructor for when AccountDAO is provided:

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // 1) Insert new users into account table

    public Account addUserAccount(Account account){
        if(account.getUsername() != "" && account.getPassword().length() >= 4 && accountDAO.validUserAccount(account) == null){
            account = accountDAO.addUserAccount(account);
            return accountDAO.validUserAccount(account);
        }

        return null;
    }

    // 2) Verify existing users in the account table

    public Account validUserAccount(Account account){
        return accountDAO.validUserAccount(account);
    }
}
