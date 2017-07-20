package kjh.reservation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kjh.reservation.dao.FileDao;
import kjh.reservation.domain.FileDomain;
import kjh.reservation.domain.ProductImage;

@Service
public class FileService {

	@Autowired
	private FileDao fileDao;

	// =>../resources/myimage/
	private String baseDir = ".." + File.separator + "resources" + File.separator + "myimage" + File.separator;

	public Integer createFile(String title, MultipartFile[] files) {
		//TODO DB에 baseDir넣지 말기
		FileDomain fileDomain = new FileDomain();

		if (files != null && files.length > 0) {

			// \년도\월\일
			Date now = new Date();
			String formattedDate = baseDir + new SimpleDateFormat("yyyyMMdd").format(now);
			// String formattedDate = baseDir + new SimpleDateFormat("yyyy" +
			// File.separator + "MM" + File.separator + "dd").format(now);
			File f = new File(formattedDate);
			if (!f.exists()) { // 파일이 존재하지 않는다면k
				f.mkdirs(); // 해당 디렉토리를 만든다. 하위폴더까지 한꺼번에 만든다.
			}

			for (MultipartFile file : files) {
				if (file.isEmpty())
					continue;
				String contentType = file.getContentType();
				// String name = file.getName(); // form의 name 프로퍼티
				String originalFilename = file.getOriginalFilename();
				long size = file.getSize();
				String uuid = UUID.randomUUID().toString();
				String saveFileName = formattedDate + File.separator + uuid;

				fileDomain.setContentType(contentType);
				fileDomain.setFileName(originalFilename);
				fileDomain.setFileLength(size);
				fileDomain.setSaveFileName(saveFileName);

				fileDomain.setCreateDate(now);
				fileDomain.setModifyDate(now);

				fileDomain.setDeleteFlag(0);
				fileDomain.setUserId(1);

				// 실제 파일을 저장함.
				// try-with-resource 구문. close()를 할 필요가 없다. java 7 이상에서 가능
				try (InputStream in = file.getInputStream();
						FileOutputStream fos = new FileOutputStream(saveFileName)) {
					int readCount = 0;
					byte[] buffer = new byte[512];
					while ((readCount = in.read(buffer)) != -1) {
						fos.write(buffer, 0, readCount);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				fileDao.insert(fileDomain);
			}
		}
		return null;
	}

	public void getThumbImg(Integer id, HttpServletResponse response) {
		ProductImage thumb = fileDao.getFileId(id);
		FileDomain file = fileDao.getFile(thumb.getFile_id());

		String saveFileName = file.getSaveFileName();
		String contentType = file.getContentType();

		// response.setHeader("Content-Disposition", "attachment; filename=\"" +
		// originalFilename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		// response.setHeader("Content-Length", ""+ fileSize);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		java.io.File readFile = new java.io.File(saveFileName);
		if (!readFile.exists()) { // 파일이 존재하지 않다면
			throw new RuntimeException("file not found");
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(readFile);
			FileCopyUtils.copy(fis, response.getOutputStream()); // 파일을 저장할때도
																	// 사용할 수 있다.
			response.getOutputStream().flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				fis.close();
			} catch (Exception ex) {
				// 아무것도 하지 않음 (필요한 로그를 남기는 정도의 작업만 함.)
			}
		}

	}

	public List<FileDomain> getImageList(Integer id) {
		List<FileDomain> fdList = new ArrayList<FileDomain>();
		List<ProductImage> idList = fileDao.getFileIdList(id);
		for(ProductImage img : idList) {
			FileDomain fd = fileDao.getFile(img.getFile_id());
			fdList.add(fd);
		}
		return fdList;
	}
}
