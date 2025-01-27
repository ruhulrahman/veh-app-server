package com.ibas.brta.vehims.common.payload.response;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaResult {
    private String base64Content;
    private String message;

    // Constructors
//    public MediaResult(String base64Content, String message) {
//        this.base64Content = base64Content;
//        this.message = message;
//    }

    public MediaResult(String base64Content) {
        this(base64Content, null);
    }

//    public MediaResult() {
//        this(null, null);
//    }

    // Getters and Setters
    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
