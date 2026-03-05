package in.AY.Blog.Backend.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.AY.Blog.Backend.services.FileService;

@Service
public class FileImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
	        throw new RuntimeException("File is empty");
	    }

	    String name = file.getOriginalFilename();

	    if (name == null || !name.contains(".")) {
	        throw new RuntimeException("Invalid file name");
	    }
		
		//random name given to file name
	    String randomId = UUID.randomUUID().toString();
	    String extension = name.substring(name.lastIndexOf("."));
	    String fileName = randomId + extension;

	    String filePath = path + File.separator + fileName;
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws IOException {
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
