package site.easy.to.build.crm.config.entity;

import java.util.ArrayList;
import java.util.List;


public class ListClean {

    private static final List<String> entities = new ArrayList<String>() {{
        add("trigger_lead");
        add("trigger_ticket");
        add("trigger_contract");
        add("lead_settings");
        add("ticket_settings");
        add("contract_settings");
        add("file");
        add("lead_action");
        add("google_drive_file");
    }};    

    public static List<String> getEntities() {
        return entities;
    }
}
