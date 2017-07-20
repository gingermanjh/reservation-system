package kjh.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kjh.reservation.domain.FileDomain;
import kjh.reservation.service.FileService;

@Controller
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@GetMapping
	public String uploadForm() {
		return "fileUpload";
	}
	
	@PostMapping
    public String createAndInsert(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files){
		fileService.createFile(title, files);
		return "redirect:/files";
	}
	
	@GetMapping("/thumb/{id}")
	public void getThumbImg(@PathVariable Integer id, HttpServletResponse response) {
		fileService.getThumbImg(id, response);
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public List<FileDomain> getImageList(@PathVariable Integer id) {
		return fileService.getImageList(id);
	}
}
