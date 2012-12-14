package com.example.descom.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.TableQuery;
//import com.vaadin.data.util.sqlcontainer.query.generator.filter.QueryBuilder;
//import com.vaadin.data.util.sqlcontainer.query.generator.filter.StringDecorator;

/**
 *  Manual script to create tabel at MySQL
 *  
CREATE TABLE tbl_employees (
	id_employee bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	first_name varchar(60) NOT NULL DEFAULT '',
	last_name varchar(60) NOT NULL DEFAULT '',
	birth_date date NOT NULL DEFAULT '0000-00-00',
	hire_date date NOT NULL DEFAULT '0000-00-00',
	gender ENUM ('Male', 'Female'),
	tax_identification_number int NOT NULL,
	personal_identification_number varchar(15) NOT NULL DEFAULT '',
	telephone_work varchar(16) NOT NULL,
	telephone_home varchar(16) NOT NULL,
	adress varchar(50) NOT NULL DEFAULT '',
	city varchar(30) NOT NULL DEFAULT '',
	zip_code int NOT NULL,
	email varchar(100) NOT NULL DEFAULT '',
	PRIMARY KEY (id_employee)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE tbl_titles (
	id_title bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	title varchar (30) NOT NULL DEFAULT '',
	PRIMARY KEY (id_title)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE tbl_positions (
	id_position bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	id_employee bigint(20) unsigned  NOT NULL,
	id_title bigint(20) unsigned NOT NULL,
	from_date date NOT NULL DEFAULT '0000-00-00',
	to_date date NOT NULL DEFAULT '0000-00-00',
	PRIMARY KEY (id_position),
	FOREIGN KEY (id_employee) REFERENCES tbl_employees(id_employee),
	FOREIGN KEY (id_title) REFERENCES tbl_titles(id_title)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE tbl_departments (
	id_department bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	department_name varchar (30) NOT NULL DEFAULT '',
	PRIMARY KEY (id_department)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE tbl_department_employee (
	id_department_employee bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	id_employee bigint(20) unsigned  NOT NULL,
	id_department bigint(20) unsigned NOT NULL,
	from_date date NOT NULL DEFAULT '0000-00-00',
	to_date date NOT NULL DEFAULT '0000-00-00',
	PRIMARY KEY (id_department_employee),
	FOREIGN KEY (id_employee) REFERENCES tbl_employees(id_employee),
	FOREIGN KEY (id_department) REFERENCES tbl_departments(id_department)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE tbl_type_of_time_off (
	id_type_of_time_off bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	type varchar (30) NOT NULL DEFAULT '',
	PRIMARY KEY (id_type_of_time_off)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


CREATE TABLE tbl_time_off (
	id_time_off bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	id_employee bigint(20) unsigned  NOT NULL,
	id_type_of_time_off bigint(20) unsigned NOT NULL,
	from_date date NOT NULL DEFAULT '0000-00-00',
	to_date date NOT NULL DEFAULT '0000-00-00',
	PRIMARY KEY (id_time_off),
	FOREIGN KEY (id_employee) REFERENCES tbl_employees(id_employee),
	FOREIGN KEY (id_type_of_time_off) REFERENCES tbl_type_of_time_off(id_type_of_time_off)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


CREATE TABLE tbl_salaries(
	id_salary bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	id_employee bigint(20) unsigned  NOT NULL,
	salary DECIMAL(8,2) NOT NULL,
	from_date date NOT NULL DEFAULT '0000-00-00',
	to_date date NOT NULL DEFAULT '0000-00-00',
	PRIMARY KEY (id_salary),
	FOREIGN KEY (id_employee) REFERENCES tbl_employees(id_employee)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ; */
  
/**
 * This class connect with HSQLDB database.
 * Initialize Container for data
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class DatabaseConnection implements Serializable{

	/**
	 * String using to easy change data used for connections to the database
	 */
	private String url, userName, password;
	
	/**
     * JDBC Connection pool to connect with database
     */
	private JDBCConnectionPool connectionPool = null;
	
	/**
	 * SQLContainer connecting to the employee in database table 
	 */
	private SQLContainer employeeContainer = null;
	
    /**
     * Enable debug mode to output SQL queries to System.out.
     */
    private boolean debugMode = false;
	
	/**
	 * Map to changing polish letters to normal
	 */
	static Map<Character, Character> polishLettersMap;
	
	/**
	 * Map of polish letters
	 */
	static {
		polishLettersMap = new HashMap<Character, Character>();
		polishLettersMap.put('ą', 'a');
		polishLettersMap.put('ę', 'a');
		polishLettersMap.put('ó', 'o');
		polishLettersMap.put('ś', 's');
		polishLettersMap.put('ł', 'l');
		polishLettersMap.put('ż', 'z');
		polishLettersMap.put('ź', 'z');
		polishLettersMap.put('ć', 'c');
		polishLettersMap.put('ń', 'n');
		
	}
		
	/**
     * Natural property order for SQLContainer linked with the PersonAddress
     * database table. Used in tables and forms.
     */
    public static final Object[] NATURAL_COL_ORDER = new Object[] {
            "FIRST_NAME", "LAST_NAME", "EMAIL", "GENDER", "BIRTH_DATE", "HIRE_DATE",
            "POSITION", "SALARY", "DEPARTMENT",
            "TAX_IDENTIFICATION_NUMBER", "PERSONAL_IDENTIFICATION_NUMBER",
            "TELEPHONE_WORK", "TELEPHONE_HOME", "ADRESS", "CITY", "ZIP_CODE" };
     
    /**
     * Readable captions for properties in same order as in
     * NATURAL_COL_ORDER.
     */
    public static final String[] COL_HEADERS_ENGLISH = new String[] {
            "First name", "Last name", "Email", "Gender", "Birth date", "Hire date",
            "Positon", "Salary", "Department",
            "Tax identification", "Personal identification",
            "Telephone work", "Telephone home", "Adress", "City", "Zip code" };  
    
    
    
    /**
     * Constructor which initialize all of data from database into SQL container
     */
	public DatabaseConnection() {
		
		// When I use before Mysql
		//QueryBuilder.setStringDecorator(new StringDecorator("`","`"));
		
		// Available deploy in three mode:
		// :mem  - create database in memory.
		// :file - create database in file. That files is located in home .
		// :url  - create database deploy on server.
		url = "jdbc:hsqldb:file:MyDB/";
		userName = "root";
		password = "";

		// Initialize a new database instance with filling.
        initConnectionPool();
        initDatabase();
        initContainers();
        fillContainers();
	}
	
	
	
	/**
	 * Database using a SimpleJDBCConnectionPool with between two and five open connections.
	 * 
	 * @return String
	 * 				If connection is successful, return "Successful", another way return error
	 */
	private String initConnectionPool() {
		try {
		    connectionPool = new SimpleJDBCConnectionPool("org.hsqldb.jdbc.JDBCDriver", url, userName, password, 2, 5); //for MySQL connection com.mysql.jdbc.Driver
		} catch (SQLException e) {
      	  e.printStackTrace();
      	  
      	  // Return Error if something go wrong
      	  return e.toString();
      }
		// Return Successful string if connection completed successfully  
		return "Successful";
	}
	
	
	
	/**
	 *  Script, which will initialize database structure
	 */
	private void initDatabase() {
		
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            
            try {
            	//statement.execute("DROP TABLE TBL_EMPLOYEES");
                statement.executeQuery("SELECT * FROM TBL_EMPLOYEES");
            } catch (SQLException e) {
            	
            	//Failed, which means that the database is not yet initialized
            	statement.execute("CREATE TABLE tbl_employees(id integer generated always as identity,"
            			+ "first_name varchar(60) DEFAULT '' NOT NULL,"
            			+ "last_name varchar(60) DEFAULT '' NOT NULL,"
            			+ "birth_date date  NOT NULL,"
            			+ "hire_date date  NOT NULL,"
            			+ "gender varchar(13) NOT NULL,"
            			+ "tax_identification_number int NOT NULL,"
            			+ "personal_identification_number varchar(15) DEFAULT '' NOT NULL,"
            			+ "telephone_work varchar(16) NOT NULL,"
            			+ "telephone_home varchar(16) NOT NULL,"
            			+ "adress varchar(50) DEFAULT '' NOT NULL,"
            			+ "city varchar(30) DEFAULT '' NOT NULL,"
            			+ "zip_code int NOT NULL,"
            			+ "email varchar(100) DEFAULT '' NOT NULL,"
            			+ "salary int NOT NULL,"
            			+ "department varchar(30) DEFAULT '' NOT NULL,"
            			+ "position varchar(40) DEFAULT '' NOT NULL,"
            			+ "version INTEGER DEFAULT 0 NOT NULL)");
            	
            	statement.execute("alter table tbl_employees add primary key (id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	
	/**
	 * Initialize containers for tables 
	 */
	private void initContainers() {
		
        try {
        	
            // TableQuery and SQLContainer for tbl_employees
            TableQuery queryEmployees = new TableQuery("tbl_employees", connectionPool);
            queryEmployees.setVersionColumn("VERSION");
            queryEmployees.setDebug(debugMode);
            
            employeeContainer = new SQLContainer(queryEmployees);
            employeeContainer.setDebugMode(debugMode);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	
	/**
     * Method to generate dummy data to the database. Everything is added to the
     * database through the SQLContainers.
     */
    private void fillContainers() {
        if (employeeContainer.size() == 0) {
            
        	// Sample cities
            final String cities[] = { "Kraków", "Amsterdam", "Berlin",
                    "Helsinki", "Hong Kong", "London", "Luxemburg", "New York",
                    "Oslo", "Paris", "Rome", "Stockholm", "Tokyo", "Turku" };
            
            // Sample first names
            final String[] fnames = { "Peter", "Alice", "Joshua", "Mike",
                    "Olivia", "Nina", "Alex", "Rita", "Dan", "Umberto",
                    "Henrik", "Rene", "Lisa", "Marge", "Michał" };
            
            // Sample last names
            final String[] lnames = { "Smith", "Gordon", "Simpson", "Brown",
                    "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates",
                    "Rowling", "Barks", "Ross", "Schneider", "Tate", "Szczygieł" };

            // Sample Streets
            final String streets[] = { "Blandit Av.", "Sem Ave",
                    "Tellus Road", "Libero. Av.",
                    "Pede. Ave", "Aliquet St.",
                    "Mauris St.", "Augue Ave",
                    "Sagittis. Rd.", "Mi Avenue",
                    "Non Av.", "Elementum Street",
                    "Quis St.", "Tincidunt St.",
                    "Adipiscing Rd.", "Nam Ave",
                    "Auctor St.", "Nonummy Av.",
                    "Mi, Avenue", "Dignissim. Rd.",
                    "Magna Avenue", "Pharetra Avenue",
                    "Posuere Rd.", "Adipiscing Avenue",
                    "Gravida St.", "Suscipit Rd.",
                    "Purus Avenue", "Bibendum. Av.",
                    "Vestibulum St.", "Aenean Avenue",
                    "Augue Ave", "Ultricies Street",
                    "Nunc Street", "Porttitor Avenue",
                    "Risus Street",
                    "Lacus. Avenue", "Metus Street",
                    "Fringilla Street",
                    "Scelerisque Road", "Iaculis Avenue" };
            
            // Sample positions
            final String positions[] = {"Sales Director", "Account Director",
            		"Key Account Manager", "Alliance Director", 
            		"Country Director, Poland", "Country Director, Sweden",
            		"Sales Manager, Sweden", "Director, Services",
            		"Director, Solutions", "Director, Data Center Solution",
            		"Executive Assistant", "HR Manager",
            		"Marketing Manager", "CFO", "CEO", "CIO", "CSO", "CMO" };
            
            // Sample departers
            final String departers[] = {"Jyväskylä", "Espoo", "Helsinki", "Tampere", "Stockholm", "Wroclaw" };
                                              
            
            Random r = new Random(0);
            
            try {
            	
                for (int i = 0; i < 100; i++) {
                	
                    Object id = employeeContainer.addItem();
                    
                    String firstName = fnames[r.nextInt(fnames.length)];
                    
                    String lastName = lnames[r.nextInt(lnames.length)];
                    
                    // Need to birth date and PIN
                    String year = "" + (1930 + r.nextInt(50));
                    String month = Integer.toString(r.nextInt(12)+1);
                    String day = Integer.toString(r.nextInt(31)+1);
                    
                    // Use sql date to fill row i hsqldb. If is using normal string, database return exception
                    java.sql.Date jsqlDateB = java.sql.Date.valueOf(year + "-" + month + "-" + day);
                    java.sql.Date jsqlDateH = java.sql.Date.valueOf("" + (2000 + r.nextInt(12)) + "-" + r.nextInt(12)+1 + "-" + r.nextInt(31)+1);
                    
                    // For Zip Code
                    int zip = r.nextInt(100000);
                    if (zip < 10000) {
                        zip += 10000;
                    }
                                     
                    employeeContainer.getContainerProperty(id, "FIRST_NAME").setValue(firstName);
                    employeeContainer.getContainerProperty(id, "LAST_NAME").setValue(lastName);
                    employeeContainer.getContainerProperty(id, "EMAIL").setValue(substitutePolishLettersWithNormal(firstName.toLowerCase()) + "." + substitutePolishLettersWithNormal(lastName.toLowerCase()) + "@descom.fi");
                    employeeContainer.getContainerProperty(id, "BIRTH_DATE").setValue(jsqlDateB);
                    employeeContainer.getContainerProperty(id, "HIRE_DATE").setValue(jsqlDateH);
                    employeeContainer.getContainerProperty(id, "POSITION").setValue(positions[r.nextInt(positions.length)]);
                    employeeContainer.getContainerProperty(id, "SALARY").setValue(1000 * (r.nextInt(5)+1));
                    employeeContainer.getContainerProperty(id, "DEPARTMENT").setValue(departers[r.nextInt(departers.length)]);
                    employeeContainer.getContainerProperty(id, "GENDER").setValue(gender(firstName));
                    employeeContainer.getContainerProperty(id, "TAX_IDENTIFICATION_NUMBER").setValue(80000 - r.nextInt(99)+1);
                    employeeContainer.getContainerProperty(id, "PERSONAL_IDENTIFICATION_NUMBER").setValue(year + month + day + "-" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + "E");
                    employeeContainer.getContainerProperty(id, "TELEPHONE_WORK").setValue("+358 02 555 " + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10));
                    employeeContainer.getContainerProperty(id, "TELEPHONE_HOME").setValue("+358 02 468 " + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10));
                    employeeContainer.getContainerProperty(id, "ADRESS").setValue(streets[r.nextInt(streets.length)] + " " + r.nextInt(100)+1);
                    employeeContainer.getContainerProperty(id, "CITY").setValue(cities[r.nextInt(cities.length)]);
                    employeeContainer.getContainerProperty(id, "ZIP_CODE").setValue(zip);
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                employeeContainer.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    /**
     * Checking the last letter from first name to check gander
     * 
     * @param firstName
     * @return (String) gander 
     */
    private String gender(String firstName) {

    	if(firstName.charAt(firstName.length()-1) == 'a' || firstName.equalsIgnoreCase("alice"))
    		return "female";
    	else
    	    return "male";
    }
    
    
	
    /**
     * Function checking if the letter is existing in ASCII 
     * 
     * @param chVal
     * @return (boolean) true when is normal, false when contain special letter, or sign
     */
	public static boolean isNormalLetter(int chVal) {
		return (chVal >= 65 && chVal <= 90) || (chVal >= 97 && chVal <= 122);
	}
	
	
	
	/**
	 * Function changing polish letter to normal
	 * 
	 * @param word String 
	 * @return (String) without polish letters
	 */
	public static String substitutePolishLettersWithNormal(String word) {
		
		StringBuilder sb = new StringBuilder();
		
		for (char ch: word.toCharArray()) {
			if (Character.isLetter(ch)) {
				sb.append(isNormalLetter(ch)? ch : polishLettersMap.get(ch));
			} else {
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}

	
	
	/**
	 * Return filled employee container
	 * 
	 * @return (SQLContainer) the employeeContainer
	 */
	public SQLContainer getEmployeeContainer() {
	        return employeeContainer;
	    }
}
