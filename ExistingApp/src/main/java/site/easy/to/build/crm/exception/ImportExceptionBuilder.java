package site.easy.to.build.crm.exception;

import java.util.ArrayList;
import java.util.List;

public class ImportExceptionBuilder {
    
    private final List<String> exceptions = new ArrayList<>();

    public void addException(String message, int lineNumber, String fileName) {
        String formattedMessage = String.format("%s on line %d from %s", 
                                              message, lineNumber, fileName);
        exceptions.add(formattedMessage);
    }

    public boolean hasErrors() {
        return !exceptions.isEmpty();
    }

    public String build() {
        if (exceptions.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder("<ul>");
        for (String exception : exceptions) {
            sb.append("<li>").append(exception).append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

    public List<String> getExceptions() {
        return exceptions;
    }
}