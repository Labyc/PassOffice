package app;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ErrorResponse{
    private final String id = UUID.randomUUID().toString();

    public ErrorResponse(ResponseError error){
        this.getErrors().add(error);
    }

    public record ResponseError(String code, String message) {
        @Override
        public String toString() {
            return "ResponseError{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
    private final List<ResponseError> errors = new ArrayList<>();

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "id='" + id + '\'' +
                ", errors=" + errors +
                '}';
    }
}
