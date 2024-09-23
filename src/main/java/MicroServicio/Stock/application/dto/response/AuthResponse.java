package MicroServicio.Stock.application.dto.response;

import MicroServicio.Stock.application.dto.request.AuthRequest;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private boolean authorized;
    private String email;
    private String role;

    public AuthResponse(AuthRequest authRequest) {
    }
}