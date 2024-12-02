
package com.tsl.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "uploaded")
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;  
    private LocalDateTime uploadTime;  
    private boolean verified;  
}

	
	


	

	
	


	

