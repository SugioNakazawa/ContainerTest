/**
 * 
 */
package com.hoge;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testcontainers.containers.MySQLContainer;

/**
 * 
 * referrence  https://kazuhira-r.hatenablog.com/entry/20170311/1489239579
 * @author nakazawasugio
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloTestTest {
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@ClassRule
	public static MySQLContainer MYSQL = new MySQLContainer("mysql:5.7.17");

	@Test
	public void insert() throws SQLException {
		try (Connection connection = DriverManager.getConnection(MYSQL.getJdbcUrl(), MYSQL.getUsername(),
				MYSQL.getPassword());
				PreparedStatement ddl = connection
						.prepareStatement("CREATE TABLE test(id VARCHAR(10), PRIMARY KEY(id))");
				PreparedStatement ps = connection.prepareStatement("INSERT INTO test(id) VALUES(?)")) {
			ddl.executeUpdate();

			ps.setString(1, "hello!!");
			assertThat(ps.executeUpdate()).isEqualTo(1);
		}
	}

	@Test
	public void select() throws SQLException {
		try (Connection connection = DriverManager.getConnection(MYSQL.getJdbcUrl(), MYSQL.getUsername(),
				MYSQL.getPassword());
				PreparedStatement ps = connection.prepareStatement("SELECT id FROM test WHERE id = ?")) {
			ps.setString(1, "hello!!");

			try (ResultSet rs = ps.executeQuery()) {
				rs.next();

				assertThat(rs.getString(1)).isEqualTo("hello!!");
			}
		}
	}
}
