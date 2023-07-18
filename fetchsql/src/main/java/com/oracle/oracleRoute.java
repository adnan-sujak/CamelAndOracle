package com.oracle;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;



public class oracleRoute extends RouteBuilder {
    
    private static final Logger myLogger = Logger.getLogger(oracleRoute.class.getName());

    
    @Override
    public void configure(){
    from("direct:employees")
        .setBody(constant("select * from employees"))
        .to("jdbc:myDataSource")
        .process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                List<Map<String, Object>> data = exchange.getIn().getBody(List.class);
                for (Map<String, Object> row : data) {
                    for (Map.Entry<String, Object> field : row.entrySet()) {
                        myLogger.info("Field: " + field.getKey() + ", Value: " + field.getValue());
                    }
                }
            }
        });
}


}