
package com.tsl.repository;

import com.tsl.model.EmailVerificationResponse;
import com.tsl.model.UploadedFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationResponseRepository extends JpaRepository<EmailVerificationResponse, Long> {

     EmailVerificationResponse findByUploadedFile(UploadedFile uploadedFile);


}
