package kg.home.gav.dto;

import kg.home.gav.enums.CallbackLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackDto {
    private CallbackLevel callbackType;
    private String callbackButton;
}
