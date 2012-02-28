package org.dafoe.framework.databasemodeling.samples;

import java.sql.SQLException;
import java.util.List;

import org.dafoe.framework.databasemodeling.factory.DatabaseManagerImpl;
import org.dafoe.framework.databasemodeling.model.ISchema;
import org.dafoe.framework.databasemodeling.model.impl.SchemaImpl;
import org.hibernate.Session;
/**
 * 
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Test {

	

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the args
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SQLException {

		Session schemaSession = org.dafoe.framework.databasemodeling.factory.SessionFactoryImpl
				.openSession();
		List<ISchema> schemas = schemaSession.createCriteria(SchemaImpl.class)
				.list();
		ISchema schema = schemas.get(0);

		DatabaseManagerImpl.createPhysicalSchema(schema);

	}
}
