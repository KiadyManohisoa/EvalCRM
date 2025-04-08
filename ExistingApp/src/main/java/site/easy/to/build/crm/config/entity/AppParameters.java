package site.easy.to.build.crm.config.entity;

public class AppParameters {
    
    private static final String [] validTypes = {"lead", "ticket"};

    private static final String [] validStatuses = {"open", "assigned", "on-hold", "in-progress", "resolved", "closed", "reopened", 
    "pending-customer-response", "escalated", "archived", "meeting-to-schedule", 
    "scheduled", "success", "assign-to-sales"};

    public static String[] getValidtypes() {
        return validTypes;
    }
    
    public static String[] getValidstatuses() {
        return validStatuses;
    }

    
}
