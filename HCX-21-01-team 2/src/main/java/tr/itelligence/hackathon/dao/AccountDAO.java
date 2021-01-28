package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Account;

public interface AccountDAO {


    public Account findAccount(String userName );

    public void save(Account account) throws Exception;
}
