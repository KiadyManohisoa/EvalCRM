package site.easy.to.build.crm.config.entity;

import java.util.ArrayList;
import java.util.List;


public class ListClean {

    private static final List<String> entities = new ArrayList<String>() {{
        // add("trigger_lead");
        // add("trigger_ticket");
        // add("trigger_contract");
        // add("lead_settings");
        // add("ticket_settings");
        // add("contract_settings");
        // add("file");
        // add("lead_action");
        // add("google_drive_file");
        // add("customer_budget");
        // add("customer_expenses");

        //all 
        // add("budget_config");       
        add("contract_settings");   
        add("customer");            
        add("customer_budget");     
        add("customer_expenses");   
        add("customer_login_info"); 
        add("email_template");      
        add("employee");            
        add("file");                
        add("google_drive_file");   
        add("lead_action");         
        add("lead_settings");       
        add("oauth_users");         
        add("ticket_settings");     
        add("trigger_contract");    
        add("trigger_lead");        
        add("trigger_ticket");      
        add("user_profile");        
        add("user_roles");          
        add("users");               
    }};    

    public static List<String> getEntities() {
        return entities;
    }
}
