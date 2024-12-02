package com.tsl.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_verification_responses")
public class EmailVerificationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int noOfValidEmails;  
    private int noOfInvalidEmails;  
    private String fileName;  

    @OneToOne
    @JoinColumn(name = "uploaded_file_id", nullable = false)
    private UploadedFile uploadedFile;  
}


