//usersService.java
package com.example.ccsd.Users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;


 

    // Method to get a user by ID //display team user
    public users getUserByIdView(String id) {
        Optional<users> userOptional = usersRepository.findById(id);
        return userOptional.orElse(null); // Return null if user not found
    }

     // Method to get a user by email //sign in
     public users getUserByEmail(String email) {
        Optional<users> userOptional = usersRepository.findById(email);
        return userOptional.orElse(null); // Return null if user not found
    }


    // Getting all users
    public List<users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Getting single user by id
    public Optional<users> getUserById(String userId) {
        return usersRepository.findById(userId);
    }

    // //getting single user by email
    // public Optional<users> getUserByEmail(String email) {
    //     return usersRepository.findById(email);
    // }


    // Creating new data in repository
    public users addUser(users users) {
        return usersRepository.save(users);
    }

    // Updating the user
    public users updateUser(String id, users usersDetails) {
        Optional<users> userOpt = usersRepository.findById(id);
        if (userOpt.isPresent()) {

            // Get from database

            users user = userOpt.get();
            user.setFirstName(usersDetails.getFirstName());
            user.setLastName(usersDetails.getLastName());
            user.setPhoneNumber(usersDetails.getPhoneNumber());
            user.setUsername(usersDetails.getUsername());
            user.setDob(usersDetails.getDob());
            user.setRole(usersDetails.getRole());
            user.setAddress(usersDetails.getAddress());
            user.setEmail(usersDetails.getEmail());
            user.setPassword(usersDetails.getPassword());
            user.setProfPic(usersDetails.getProfPic());
            return usersRepository.save(user);
        }
        return null;
    }



    // Deleting user 
    public void deleteUser(String id) {
        usersRepository.deleteById(id);
    }

    
    // Save image in a local directory
    public String saveProfPicToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
    
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);
    
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
        return uniqueFileName;  // Return the filename, which you can later use for linking
    }
    
    // To view an image
    public byte[] getProfPic(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            return null; // Handle missing images
        }
    }

    // Delete an image
    public String deleteProfPic(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Success";
        } else {
            return "Failed"; // Handle missing images
        }
    }

    
 
    
}
