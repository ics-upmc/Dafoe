/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/

package org.dafoe.projectmanagement;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLScriptRunner {

	private static final String DEFAULT_DELIMITER = ";"; //$NON-NLS-1$

	private Connection connection;

	private boolean stopOnError;

	private boolean autoCommit;

	private PrintWriter logWriter = new PrintWriter(System.out);

	private PrintWriter errorLogWriter = new PrintWriter(System.err);

	private String delimiter = DEFAULT_DELIMITER;

	private boolean fullLineDelimiter = false;

	public SQLScriptRunner(Connection connection, boolean autoCommit,
			boolean stopOnError) {
		this.connection = connection;
		this.autoCommit = autoCommit;
		this.stopOnError = stopOnError;
	}

	public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
		this.delimiter = delimiter;
		this.fullLineDelimiter = fullLineDelimiter;
	}

	public void setLogWriter(PrintWriter logWriter) {
		this.logWriter = logWriter;
	}

	public void setErrorLogWriter(PrintWriter errorLogWriter) {
		this.errorLogWriter = errorLogWriter;
	}

	public void runScript(Reader reader) throws IOException, SQLException {
		try {
			boolean originalAutoCommit = connection.getAutoCommit();
			try {
				if (originalAutoCommit != this.autoCommit) {
					connection.setAutoCommit(this.autoCommit);
				}
				runScript(connection, reader);
			} finally {
				connection.setAutoCommit(originalAutoCommit);
			}
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(Messages.SQLScriptRunner_1 + e, e);
		}
	}

	private void runScript(Connection conn, Reader reader) throws IOException, SQLException {
		StringBuffer command = null;
		try {
			LineNumberReader lineReader = new LineNumberReader(reader);
			String line = null;
			while ((line = lineReader.readLine()) != null) {
				if (command == null) {
					command = new StringBuffer();
				}

				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("--")) { //$NON-NLS-1$
					println(trimmedLine);
				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("//")) { //$NON-NLS-1$
					// Do nothing

				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("--")) { //$NON-NLS-1$

					// Do nothing

				} else if (!fullLineDelimiter
						&& trimmedLine.endsWith(getDelimiter())
						|| fullLineDelimiter
						&& trimmedLine.equals(getDelimiter())) {
					command.append(line.substring(0, line
							.lastIndexOf(getDelimiter())));
					command.append(" "); //$NON-NLS-1$
					Statement statement = conn.createStatement();
					println(command);
					boolean hasResults = false;

					if (stopOnError) {
						hasResults = statement.execute(command.toString());
					} else {
						try {
							statement.execute(command.toString());
						} catch (SQLException e) {
							e.fillInStackTrace();
							printlnError(Messages.SQLScriptRunner_6 + command);
							printlnError(e);
						}
					}

					if (autoCommit && !conn.getAutoCommit()) {
						conn.commit();
					}

					ResultSet rs = statement.getResultSet();

					if (hasResults && rs != null) {
						ResultSetMetaData md = rs.getMetaData();
						int cols = md.getColumnCount();
						for (int i = 0; i < cols; i++) {
							String name = md.getColumnLabel(i);
							print(name + "\t"); //$NON-NLS-1$
						}

						println(""); //$NON-NLS-1$

						while (rs.next()) {
							for (int i = 0; i < cols; i++) {
								String value = rs.getString(i);
								print(value + "\t"); //$NON-NLS-1$
							}
							println(""); //$NON-NLS-1$
						}
					}
					command = null;
					try {
						statement.close();
					} catch (Exception e) {
						// Ignore to workaround a bug in Jakarta DBCP
					}
					Thread.yield();
				} else {
					command.append(line);
					command.append(" "); //$NON-NLS-1$
				}
			}
			if (!autoCommit) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.fillInStackTrace();
			printlnError(Messages.SQLScriptRunner_12 + command);
			printlnError(e);
			throw e;
		} catch (IOException e) {
			e.fillInStackTrace();
			printlnError(Messages.SQLScriptRunner_13 + command);
			printlnError(e);
			throw e;
		} finally {
			conn.rollback();
			flush();
		}
	}

	private String getDelimiter() {
		return delimiter;
	}

	private void print(Object o) {
		if (logWriter != null) {
			System.out.print(o);
		}
	}

	private void println(Object o) {
		if (logWriter != null) {
			logWriter.println(o);
		}
	}

	private void printlnError(Object o) {
		if (errorLogWriter != null) {
			errorLogWriter.println(o);
		}
	}

	private void flush() {
		if (logWriter != null) {
			logWriter.flush();
		}
		if (errorLogWriter != null) {
			errorLogWriter.flush();
		}
	}
}
