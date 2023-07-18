package com.oracle;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class oracleMain {

    private static final Logger myLogger = LoggerFactory.getLogger(oracleMain.class);
    
    public static void main(String [] args) throws Exception{
        
        myLogger.info("This is an informational message");
        myLogger.debug("This is a debug message");
        myLogger.error("This is an error message");
        
        
        
        DataSource dataSource = myDataSource(); //creates instance of DataSource object by calling the myDataSource method
        
        SimpleRegistry register = new SimpleRegistry(); //implementation of Registry. Uses a HashMap to store objects that can be looked up by string keys.
        register.bind("myDataSource", dataSource); //dataSource registered in registry with the key: "mydataSource"
        
        CamelContext context = new DefaultCamelContext(register); //creating instance of DefaultCamelContext with the created registry.
        //passing register allows for me to provide it with a set of named beans (objects) so that it can be used with my apache camel application
        
        context.addRoutes(new oracleRoute()); //this route is added so that we define a processing path for a message
        context.start(); //start the CamelContext and all routes attached to it

        ProducerTemplate template = context.createProducerTemplate();
        template.sendBody("direct:employees", "");
        //a producer is created to allow sending messages to endpoints.
        //a message with an empty body is sent to direct:employees endpoint which triggers the route in oracleRoute

       
        Thread.sleep(2000); //pauses program for 2 seconds to allow processing to finish


        context.stop(); //stops the CamelContext
        
    }
    

    private static DataSource myDataSource(){ //creates and configures a BasicDataSource object with my db details.        
        BasicDataSource data = new BasicDataSource();

        data.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        data.setUrl("jdbc:oracle:thin:@127.0.0.1:1521/xepdb1");
        data.setUsername("system");
        data.setPassword("adnans12");
        return data;

    }
}
