package kr.co.team.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String uuid;
    private String uploadPath;

    public String getImageURL() {
        try {
            return URLEncoder.encode(uploadPath + "/" + uuid + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(uploadPath  + "/s_" + uuid + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "";
    }

}
