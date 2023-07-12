package com.oracle;
import org.apache.camel.builder.RouteBuilder;



public class oracleRoute extends RouteBuilder {
    
    @Override
    public void configure(){
        from("direct:employees" )
            .setBody(constant("select * from employees"))
            .to("jdbc:myDataSource");
    }
    
}
