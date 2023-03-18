package tandon.blogger.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String postBody;
    private String title;
    @NotNull(message = "enter a userId ")
    private  Long userId;

}
