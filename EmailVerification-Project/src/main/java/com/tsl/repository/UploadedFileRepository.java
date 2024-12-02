
package com.tsl.repository;

import com.tsl.model.UploadedFile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    UploadedFile findByFileName(String fileName);

	
	  List<UploadedFile> findByFileNameContainingIgnoreCase(String fileName);
}

