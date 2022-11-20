/*
 Log4J:
 =======
Full form:Logging for java
Version:1.x(stable),2.x
Vendor:Apache
Open source(May or may not be free in all cases but source code is available).

What is the diff. b/w free s/w and open source s/w?

Free s/w is that we get the s/w for free and can see the code and can modify the code and can sell it.
That means we have freedom to change.


Open source s/w is that we can see the code but s/w may allow to modify or may not allow to modify(means
not free).

Therefore free s/w is like superset and open source is sub-set of it.


Log4j advantages:
====================

1)It allows to categorize log messages and can add priorities to log messages.

ALL < DEBUG < INFO < WARN < ERROR < FATAL

ALL and DEBUG are same level.

DEBUG: Use this for normal confirmation code-flow statements.

Eg: main(-) start,main(-) end,m1() start,m1() end.

INFO: Use this for important confirmation code-flow statements.

Eg: Connection established to db s/w
	Login is successful
	OTP generated
	Token accessed
	etc..
	
WARN: Use this to write log messages for code that shouldnt be used/executed but somehow used and executed.

Eg: When programmer uses deprecated API's/poor API for temporary basis due to short of time.Then put a 
WARN message so that can change in future.


ERROR: Use this to write in known exceptions catch blocks.

Eg:catch(SQLException se),catch(IOException ie),catch(IllegalArgumentException iae) etc.


FATAL: Use this to write in unknown exceptions catch blocks.

Eg: catch(Exception e), catch(Throwable t) etc.



What is the difference between Bug and Issue in testing environment?

Bug: Code is present but expected functionality is not coming.

Eg: When you clicked on "terms and conditions" page you are getting "about us" page.


Issue:

Functionality/Feature itself is not there.

Eg:"terms and conditions" page itself is not there.



What is the difference between Bug and error in general programming?

Bug: Code is present but expected functionality is not coming(Due to wrong logics) but exception won't be raised.

Eg: When you clicked on "terms and conditions" page you are getting "about us" page.

Error:
Code is present and wrong logics and exception's are raised.

Eg:StackOverflow error,StackUnderflow error etc.


2)Log4j allows us to write log msg's to different destinations like console,file,db,mailserver etc.

3)Log4j allows us to format the log msg's using diff. layouts(SimpleLayout,HtmlLayout,XmlLayout,
PatternLayout etc.)

4)Log4j allows us to retrieve log messages by applying filters.
If the log level is "ALL" then we get log msg's from priority ALL and higher(DEBUG,INFO,WARN,ERROR and FATAL).
If the log level is "DEBUG" then we get log msg's from priority DEBUG and higher(INFO,WARN,ERROR and FATAL).
If the log level is "INFO" then we get log msg's from priority INFO and higher(WARN,ERROR and FATAL).
If the log level is "WARN" then we get log msg's from priority WARN and higher(ERROR and FATAL).
If the log level is "ERROR" then we get log msg's from priority ERROR and higher(FATAL).
If the log level is "FATAL" then we get log msg's from priority FATAL and higher().
If the log level is "OFF" then logging is disabled on the application.

NOTE:
In real time for every application 2 log files will be maintained.

1)Common log file --> It records all the msgs from end to end.

2)Exception log file --> It records only ERROR and FATAL level msg's.

5)Can change the inputs to the application related log4j either using properties file or xml file.

Industry prefer's properties file.

===========================================================================================
There are 3 important objects in log4j programming.

1)Logger object
2)Appender object
3)Layout object

1)Logger:
============

1)Logger is a class present in org.apache.log4j as "Logger.java" file

2)Syntax to enable logging on the current class:
Logger.getLogger("classname.class");

NOTE:Need to pass .class file
Here Logger is a single ton class and getLogger(-) is a static factory method so thats
why we are calling with classname "Logger".

As Logger is a singleton class i.e only 1 obj. is created for logging for all classes in the application.


Below is the internal code:

package org.apache.log4j;
public class Logger extends Category {

   public static Logger getLogger(String name) {
      return LogManager.getLogger(name);
   }

}


package org.apache.log4j;
public class LogManager {

  public static Logger getLogger(String name) {
      return getLoggerRepository().getLogger(name);
   }
}//class


3)Specify the logger level like below to retrieve the log msg's.

logger.setLevel(Level.ALL);
logger.setLevel(Level.DEBUG); -->default
logger.setLevel(Level.INFO);
logger.setLevel(Level.WARN);
logger.setLevel(Level.ERROR);
logger.setLevel(Level.FATAL);

If logger level is not mentioned then by default "DEBUG" logging level is captured.


Internal code:

package org.apache.log4j;
public class Logger extends Category { -->imp
   private static final String FQCN;
}//class


package org.apache.log4j;
public class Category implements AppenderAttachable {
   protected String name;
   protected volatile Level level; -->imp

public void setLevel(Level level) { -->imp
      this.level = level;
   }

}//class

package org.apache.log4j;
public class Level extends Priority implements Serializable {
   public static final int TRACE_INT = 5000;
   public static final Level OFF = new Level(Integer.MAX_VALUE, "OFF", 0);
   public static final Level FATAL = new Level(50000, "FATAL", 0);
   public static final Level ERROR = new Level(40000, "ERROR", 3);
   public static final Level WARN = new Level(30000, "WARN", 4);
   public static final Level INFO = new Level(20000, "INFO", 6);
   public static final Level DEBUG = new Level(10000, "DEBUG", 7);
   public static final Level TRACE = new Level(5000, "TRACE", 7);
   public static final Level ALL = new Level(Integer.MIN_VALUE, "ALL", 7);
   static final long serialVersionUID = 3491141966387921974L;

}//class


4)To generate log msg in the application use like below.

logger.all("<log message>"); -->This method is not there so dont use this because it is same like
logger.debug(-) method.

logger.debug("<log message>");
logger.info("<log message>");
logger.warn("<log message>");
logger.error("<log message>");
logger.fatal("<log message>");

Internal code:
package org.apache.log4j;
public class Logger extends Category {
   private static final String FQCN;

}//class

package org.apache.log4j;

public class Category implements AppenderAttachable {

public void debug(Object message) {
      if (!this.repository.isDisabled(10000)) {
         if (Level.DEBUG.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.DEBUG, message, (Throwable)null);
         }

      }
   }//debug(-)

public void info(Object message) {
      if (!this.repository.isDisabled(20000)) {
         if (Level.INFO.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.INFO, message, (Throwable)null);
         }

      }
   }//info(-)
   
    public void warn(Object message) {
      if (!this.repository.isDisabled(30000)) {
         if (Level.WARN.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.WARN, message, (Throwable)null);
         }

      }
   }//warn(-)
   
     public void error(Object message) {
      if (!this.repository.isDisabled(40000)) {
         if (Level.ERROR.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.ERROR, message, (Throwable)null);
         }

      }
   }//error(-)

	
	 public void fatal(Object message) {
      if (!this.repository.isDisabled(50000)) {
         if (Level.FATAL.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.FATAL, message, (Throwable)null);
         }

      }
   }//fatal(-)

}//class

5)Appender object and Layout object is added to logger obj. directly or indirectly.

//Layout object
		SimpleLayout layout=new SimpleLayout();
		
		//ConsoleAppender object
		ConsoleAppender appender=new ConsoleAppender(layout);
		
		//Add appender obj. to logger obj.
		logger.addAppender(appender);


6)Inputs to logger obj. can be hardcoded or can be given using properties file or xml file.
But industry prefers to give in properties file.




2)Appender:
=============
1)Appender is an interface present in org.apache.log4j as Appender.java file.It specifies the destination
i.e where to write the log msg's.

Examples that takes file as destination:
FileAppender,RollingFileAppender,DailyRollingFileAppender

Examples that takes db s/w as the destination:
JDBCAppender

Examples that takes mail server as the destination:
IMAPAppender

Examples that takes console as the destination:
ConsoleAppender

All the above appender classes implements directly/indirectly the Appender(I).


2)Below is the internal code:

package org.apache.log4j;
public interface Appender {
   void addFilter(Filter var1);

   Filter getFilter();

   void clearFilters();

   void close();
   
}//interface

For "FileAppender" class:
==========================
package org.apache.log4j;
public class FileAppender extends WriterAppender { -->imp

}//class

package org.apache.log4j;
public class WriterAppender extends AppenderSkeleton { -->imp

}//class
   
package org.apache.log4j;
public abstract class AppenderSkeleton implements Appender, OptionHandler { -->imp
   protected Layout layout;
   protected String name;
   
}//class

For RollingFileAppender class:
================================
package org.apache.log4j;
public class RollingFileAppender extends FileAppender {

}//class

Again follows the above code.

For DailyRollingFileAppender class:
=====================================
package org.apache.log4j;
public class DailyRollingFileAppender extends FileAppender {

}//class
   
Again follows the above code.

For JDBCAppender class:
==========================
package org.apache.log4j.jdbc;
public class JDBCAppender extends AppenderSkeleton implements Appender {
   
   
}//class

For IMAPAppender class:
============================
This class is not present in log4j 1.2.17 version in org.apache.log4j

For ConsoleAppender class:
============================
package org.apache.log4j;
public class ConsoleAppender extends WriterAppender {

}//class

Again follows the above code(Code present in FileAppender).


3)Layout:
===========
1)This is an abstract class present in org.apache.log4j as "Layout.java" file.

Internal code:
package org.apache.log4j;
public abstract class Layout implements OptionHandler {
   public static final String LINE_SEP = System.getProperty("line.separator");
   public static final int LINE_SEP_LEN;
   
}//class


2)This is used to format the log messages before giving to appender for writing to destination.

Examples:
SimpleLayout,HTMLLayout,XMLLayout,PatternLayout etc.

All the above layout classes extends the "Layout(AC)" directly or indirectly.

For SimpleLayout class:
=========================


public class SimpleLayout extends Layout { -->imp
   StringBuffer sbuf = new StringBuffer(128);

   public void activateOptions() {
   }
}//class

For HTMLLayout class:
========================
package org.apache.log4j;
public class HTMLLayout extends Layout { -->imp

}//class

For XMLLayout class:
=======================
package org.apache.log4j.xml;
public class XMLLayout extends Layout { -->imp

}//class


For PatternLayout class:
===========================
package org.apache.log4j;
public class PatternLayout extends Layout { -->imp

}//class



 */

//Example using SimpleLayout and ConsoleAppender

/*Internal code:
package org.apache.log4j;
 public class ConsoleAppender extends WriterAppender {
 public ConsoleAppender(Layout layout) {  -->imp
      this(layout, "System.out");
   }
}//class

package org.apache.log4j;
public class Logger extends Category {   -->imp
}//class

public class Category implements AppenderAttachable {
  public synchronized void addAppender(Appender newAppender) {   -->imp
      if (this.aai == null) {
         this.aai = new AppenderAttachableImpl();
      }

      this.aai.addAppender(newAppender);
      this.repository.fireAddAppenderEvent(this, newAppender);
   }
}//class

package com.ram.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class JdbcTest {
	//Enabling logging on the current class
	private static Logger logger=Logger.getLogger("JdbcTest.class");
	static {
	
		//Layout object
		SimpleLayout layout=new SimpleLayout();
		
		//ConsoleAppender object
		ConsoleAppender appender=new ConsoleAppender(layout);
		
		//Add appender obj. to logger obj.
		logger.addAppender(appender);
		
		//Setting logging level
		logger.setLevel(Level.ERROR);
		
	}//static
	public static void main(String[] args)  {
		logger.debug("main method");
		Connection con=null;
		Statement st = null;
		ResultSet rs = null;
		try {
				//Loading jdbc driver class(optional)
				Class.forName("oracle.jdbc.driver.OracleDriver");
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc driver class loaded");
				
				//Establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl1","scott1","tiger");
				logger.info("com.ram.jdbc.JdbcTest::Connection to db established successfully");
				
				//create jdbc stmt obj.
				st = con.createStatement();
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc stmt obj. is created");
				//Get the result set obj.
				 rs = st.executeQuery("SELECT * FROM STUDENT");
				 logger.debug("com.ram.jdbc.JdbcTest::SQL Query sent to db and result processed and "
				 		+ "Result set obj. is created");
				 
				 //Processing the result set
				 if(rs!=null) {
			
				 while(rs.next()) {
					 logger.debug("com.ram.jdbc.JdbcTest::RS obj. processing started");
					 System.out.println(rs.getString(1)+" "+rs.getString(2));
					 logger.warn("com.ram.jdbc.JdbcTest::The records of resultset are retrieved using"
					 		+ "getString(-)..so change them accordingly");
				 }//while
				 logger.debug("com.ram.jdbc.JdbcTest::Resultset obj. is processed");
				 }//if
		//
				
		}
		//Handling known exceptions
		catch(ClassNotFoundException e) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in loading jdbc driver class");
				e.printStackTrace();
				
		} catch (SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::some db problem"+se.getMessage()+"--"+se.getErrorCode());
				se.printStackTrace();
		}//catch
		
		//Handling unknown exceptions
		catch(Exception e) {
			logger.fatal("com.ram.jdbc.JdbcTest::Unknown problem");
			e.printStackTrace();
		}//catch
	
		finally {
			
			//close jdbc obj's
			logger.debug("Closing jdbc obj's");
			
			try {
				if(rs!=null) {
					rs.close();
					logger.debug("RS obj. is closed");
				}//if
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::problem in closing RS obj"+se.getMessage());
				se.printStackTrace();
			}//catch
			
			try {
				if(st!=null) {
					st.close();
					logger.debug("stmt obj. is closed");
				}//if
				
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing st obj."+se.getMessage());
				se.printStackTrace();
				
			}//catch
			
			
			
			try {
				if(con!=null) {
					con.close();
					logger.debug("connection obj. closed");
				}//if
	
			}
			catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing con obj."+se.getMessage());
			}//catch
			
		}//finally
		logger.debug("com.ram.jdbc.JdbcTest::Main method is completed");
	}//main
}

====================================================================================
Instead of hardcoding the log4j setup details(Logging level,Appender,Layout etc.) in java file 
directly we can pass them using properties file or xml file.
Industry prefers using properties file.

properties file:
==================
1)Any filename.properties can be taken in any location.
2)In this keys are fixed and values can be provided based on the app's requirement.
3)keys can be gathered from log4j document.
4)To load properties file and to activate log4j setup we need to use PropertyConfigurator.configure
(-) method in our java class.

Example:
   static{
   		try{
   			PropertyConfigurator.configure("<propertiesfilelocation>/<propertiesfilename>.properties");
   		}//try
   		
   		catch(Exception e){
   		
   		}//catch
   }//static

PropertyConfigurator is a class present in org.apache.log4j.configure(-) is a static method present
in PropertyConfigurator class.

Internal code:

package org.apache.log4j;
public class PropertyConfigurator implements Configurator { -->imp

	public static void configure(String configFilename) {
      (new PropertyConfigurator()).doConfigure(configFilename, LogManager.getLoggerRepository());
   }//configure(-)
   
  public void doConfigure(String configFileName, LoggerRepository hierarchy) { -->imp
      Properties props = new Properties();
      FileInputStream istream = null;
      ...............
      this.doConfigure(props, hierarchy);
 	}//doConfigure(-,-)
 	
 	public void doConfigure(Properties properties, LoggerRepository hierarchy) {  -->imp
      this.repository = hierarchy;
      String value = properties.getProperty("log4j.debug");
      if (value == null) {
         value = properties.getProperty("log4j.configDebug");
         if (value != null) {
            LogLog.warn("[log4j.configDebug] is deprecated. Use [log4j.debug] instead.");
         }
      }//if
   
}//class

Example using FileAppender and HTMLLayout using properties file:
===================================================================
package com.ram.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class JdbcTest {
	//Enabling logging on the current class
	private static Logger logger=Logger.getLogger("JdbcTest.class");
	static{
   		try{
   			PropertyConfigurator.configure("src/com/ram/commons/log4j.properties");
   			logger.info("com.ram.jdbc.JdbcTest::log4j setup is ready");
   		}//try
   		
   		catch(Exception e){
   			logger.fatal("com.ram.jdbc.JdbcTest::Unknown problem occured"+e.getMessage());
   			e.printStackTrace();
   		}//catch
   }//static
	public static void main(String[] args)  {
		logger.debug("main method");
		Connection con=null;
		Statement st = null;
		ResultSet rs = null;
		try {
				//Loading jdbc driver class(optional)
				Class.forName("oracle.jdbc.driver.OracleDriver");
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc driver class loaded");
				
				//Establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl1","scott","tiger");
				logger.info("com.ram.jdbc.JdbcTest::Connection to db established successfully");
				
				//create jdbc stmt obj.
				st = con.createStatement();
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc stmt obj. is created");
				//Get the result set obj.
				 rs = st.executeQuery("SELECT * FROM STUDENT");
				 logger.debug("com.ram.jdbc.JdbcTest::SQL Query sent to db and result processed and "
				 		+ "Result set obj. is created");
				 
				 //Processing the result set
				 if(rs!=null) {
			
				 while(rs.next()) {
					 logger.debug("com.ram.jdbc.JdbcTest::RS obj. processing started");
					 System.out.println(rs.getString(1)+" "+rs.getString(2));
					 logger.warn("com.ram.jdbc.JdbcTest::The records of resultset are retrieved using"
					 		+ "getString(-)..so change them accordingly");
				 }//while
				 logger.debug("com.ram.jdbc.JdbcTest::Resultset obj. is processed");
				 }//if
		//
				
		}
		//Handling known exceptions
		catch(ClassNotFoundException e) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in loading jdbc driver class");
				e.printStackTrace();
				
		} catch (SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::some db problem"+se.getMessage()+"--"+se.getErrorCode());
				se.printStackTrace();
		}//catch
		
		//Handling unknown exceptions
		catch(Exception e) {
			logger.fatal("com.ram.jdbc.JdbcTest::Unknown problem");
			e.printStackTrace();
		}//catch
	
		finally {
			
			//close jdbc obj's
			logger.debug("Closing jdbc obj's");
			
			try {
				if(rs!=null) {
					rs.close();
					logger.debug("RS obj. is closed");
				}//if
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::problem in closing RS obj"+se.getMessage());
				se.printStackTrace();
			}//catch
			
			try {
				if(st!=null) {
					st.close();
					logger.debug("stmt obj. is closed");
				}//if
				
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing st obj."+se.getMessage());
				se.printStackTrace();
				
			}//catch
			
			
			
			try {
				if(con!=null) {
					con.close();
					logger.debug("connection obj. closed");
				}//if
	
			}
			catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing con obj."+se.getMessage());
			}//catch
			
		}//finally
		logger.debug("com.ram.jdbc.JdbcTest::Main method is completed");
	}//main
}


*/
package com.ram.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class JdbcTest {
	//Enabling logging on the current class
	private static Logger logger=Logger.getLogger("JdbcTest.class");
	static{
   		try{
   			PropertyConfigurator.configure("src/com/ram/commons/log4j.properties");
   			logger.info("com.ram.jdbc.JdbcTest::log4j setup is ready");
   		}//try
   		
   		catch(Exception e){
   			logger.fatal("com.ram.jdbc.JdbcTest::Unknown problem occured"+e.getMessage());
   			e.printStackTrace();
   		}//catch
   }//static
	public static void main(String[] args)  {
		logger.debug("main method");
		Connection con=null;
		Statement st = null;
		ResultSet rs = null;
		try {
				//Loading jdbc driver class(optional)
				Class.forName("oracle.jdbc.driver.OracleDriver");
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc driver class loaded");
				
				//Establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcl1","scott","tiger");
				logger.info("com.ram.jdbc.JdbcTest::Connection to db established successfully");
				
				//create jdbc stmt obj.
				st = con.createStatement();
				logger.debug("com.ram.jdbc.JdbcTest::Jdbc stmt obj. is created");
				//Get the result set obj.
				 rs = st.executeQuery("SELECT * FROM STUDENT");
				 logger.debug("com.ram.jdbc.JdbcTest::SQL Query sent to db and result processed and "
				 		+ "Result set obj. is created");
				 
				 //Processing the result set
				 if(rs!=null) {
			
				 while(rs.next()) {
					 logger.debug("com.ram.jdbc.JdbcTest::RS obj. processing started");
					 System.out.println(rs.getString(1)+" "+rs.getString(2));
					 logger.warn("com.ram.jdbc.JdbcTest::The records of resultset are retrieved using"
					 		+ "getString(-)..so change them accordingly");
				 }//while
				 logger.debug("com.ram.jdbc.JdbcTest::Resultset obj. is processed");
				 }//if
		//
				
		}
		//Handling known exceptions
		catch(ClassNotFoundException e) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in loading jdbc driver class");
				e.printStackTrace();
				
		} catch (SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::some db problem"+se.getMessage()+"--"+se.getErrorCode());
				se.printStackTrace();
		}//catch
		
		//Handling unknown exceptions
		catch(Exception e) {
			logger.fatal("com.ram.jdbc.JdbcTest::Unknown problem");
			e.printStackTrace();
		}//catch
	
		finally {
			
			//close jdbc obj's
			logger.debug("Closing jdbc obj's");
			
			try {
				if(rs!=null) {
					rs.close();
					logger.debug("RS obj. is closed");
				}//if
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::problem in closing RS obj"+se.getMessage());
				se.printStackTrace();
			}//catch
			
			try {
				if(st!=null) {
					st.close();
					logger.debug("stmt obj. is closed");
				}//if
				
			}catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing st obj."+se.getMessage());
				se.printStackTrace();
				
			}//catch
			
			
			
			try {
				if(con!=null) {
					con.close();
					logger.debug("connection obj. closed");
				}//if
	
			}
			catch(SQLException se) {
				logger.error("com.ram.jdbc.JdbcTest::Problem in closing con obj."+se.getMessage());
			}//catch
			
		}//finally
		logger.debug("com.ram.jdbc.JdbcTest::Main method is completed");
	}//main
}
