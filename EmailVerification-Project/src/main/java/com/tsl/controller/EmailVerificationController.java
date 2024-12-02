
package com.tsl.controller;

import com.tsl.model.UploadedFile;
import com.tsl.repository.UploadedFileRepository;
import com.tsl.service.EmailVerificationService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/email-verification")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @GetMapping
    public String showUploadForm(Model model) {
        model.addAttribute("uploadedFiles", emailVerificationService.getAllUploadedFiles());
        return "upload-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            UploadedFile uploadedFile = emailVerificationService.saveUploadedFile1(file.getInputStream(), file.getOriginalFilename());
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully! File ID: " + uploadedFile.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error uploading file: " + e.getMessage());
        }
        return "redirect:/email-verification";
    }

    // Trigger email verification for a specific file
    @GetMapping("/verify/{fileId}")
    public String verifyFile(@PathVariable Long fileId, RedirectAttributes redirectAttributes) {
        try {
            emailVerificationService.verifyEmails(fileId);
            redirectAttributes.addFlashAttribute("message", "Verification completed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during verification: " + e.getMessage());
        }
        return "redirect:/email-verification";
    }

    // Download verification results
    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            byte[] fileContent = emailVerificationService.getVerificationResults1(fileId);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=verification-results.xlsx");
            response.getOutputStream().write(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search uploaded files by name
    @GetMapping("/search")
    public String searchUploadedFiles(@RequestParam("query") String query, Model model) {
        try {
            List<UploadedFile> files = uploadedFileRepository.findByFileNameContainingIgnoreCase(query);
            model.addAttribute("uploadedFiles", files);
        } catch (Exception e) {
            model.addAttribute("message", "Error during search: " + e.getMessage());
        }
        return "upload-form";
    }

    // Delete a specific file
    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, RedirectAttributes redirectAttributes) {
        try {
            emailVerificationService.deleteFile(fileId); // Ensure this service deletes the file
            redirectAttributes.addFlashAttribute("message", "File removed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to remove file: " + e.getMessage());
        }
        return "redirect:/email-verification";
    }
}

