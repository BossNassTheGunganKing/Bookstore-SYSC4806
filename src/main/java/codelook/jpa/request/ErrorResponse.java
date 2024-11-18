package codelook.jpa.request;


import java.time.LocalDateTime;
import java.util.Map;


// allow for handling errors on frontend
public class ErrorResponse {
    private final LocalDateTime errorTimestamp;
    private final String errorMessage;
    private final Map<String, String> errorDetails;

    public ErrorResponse(String errorMessage, Map<String, String> errorDetails) {
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.errorTimestamp = LocalDateTime.now();
    }

    public LocalDateTime errorTimestamp() {
        return errorTimestamp;
    }

    public Map<String, String> errorDetails() {
        return errorDetails;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
