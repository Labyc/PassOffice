package app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ErrorResponse{
    private final String id = UUID.randomUUID().toString();
    private final List<ResponseError> errors = new ArrayList<>();

    public ErrorResponse(ResponseError error){
        this.getErrors().add(error);
    }

public ErrorResponse(String code, String message){
        this.errors.add(new ResponseError(code, message));
}

    @ToString
    @Getter
    public static class ResponseError {
        private final String code;
        private final String message;
        @Id
        private final String id;
        public ResponseError(String code, String message){
            this.code = code;
            this.message = message;
            this.id = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "id='" + id + '\'' +
                ", errors=" + errors +
                '}';
    }
}
