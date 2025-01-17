package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.exception.BadApiRequestException;
import com.zia.electronix.express.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String uploadPath) throws IOException {
        String originalFileName = file.getOriginalFilename();
        logger.info("Filename : {}",originalFileName);
        logger.info("File path : {}",uploadPath);
        String filename = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = uploadPath +fileNameWithExtension;
        logger.info("full image path:{}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            //save file

            File folder = new File(uploadPath);
            if(!folder.exists()){

                //create the folder
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else {
            throw new BadApiRequestException("File with this extension is not supported");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
