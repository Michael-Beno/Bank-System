package ie.michael.bank.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<Account>{

	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

		Account account = new Account();
		account.setAcccountId(rs.getInt("accountId"));
		account.setAccountNumber(rs.getInt("accountNumber"));
		account.setAccountBalance(rs.getLong("accountBalance"));
		account.setAccountLimit(rs.getInt("accountLimit"));
		account.setAcountActive(rs.getBoolean("accountActive"));
		return account;
	}

}
