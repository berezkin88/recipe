package person.birch.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aleksandr Beryozkin
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("file obtained");
    }
}
