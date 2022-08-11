package sn.gainde2000.pi.core.generate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class DemandeIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
			throws HibernateException {
		Connection connection = sharedSessionContractImplementor.connection();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String suffix = "" + year;
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(numero_demande_id) FROM numero_demande");
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
			}
			String prefix = generateString(id);
			return "DAF"+suffix+prefix;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generateString(int value) {
		String result = null;
		if (Integer.toString(value).length() == 1) {
			value++;
			result = "000" + value;
		} else if (Integer.toString(value).length() == 2) {
			value++;
			result = "00" + value;
		} else if (Integer.toString(value).length() == 3) {
			value++;
			result = "0" + value;
		} else if (Integer.toString(value).length() == 4) {
			value++;
			result = Integer.toString(value);
		}
		return result;
	}

	@Override
	public boolean supportsJdbcBatchInserts() {
		return false;
	}
}
