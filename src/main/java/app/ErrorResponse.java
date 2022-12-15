package app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ErrorResponse {
    private final String id = UUID.randomUUID().toString();
    private final List<ResponseError> errors = new ArrayList<>();

    public ErrorResponse(ResponseError error) {
        this.getErrors().add(error);
    }

    public ErrorResponse(String code, String message) {
        this.errors.add(new ResponseError(code, message));
    }

    public record ResponseError(String code, String message) {}

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "id='" + id + '\'' +
                ", errors=" + errors +
                '}';
    }
}
