package hcmuaf.edu.vn.bookingtravel.api.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class BaseModel {
    @Id
    @BsonId
    @TextIndexed
    protected String id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    protected Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    protected Date updatedAt;
    protected String createdBy;
    protected String updatedBy;

}
