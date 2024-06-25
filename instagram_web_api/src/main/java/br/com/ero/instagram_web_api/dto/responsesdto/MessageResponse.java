package br.com.ero.instagram_web_api.dto.responsesdto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        super();
        this.message = message;
    }
}
