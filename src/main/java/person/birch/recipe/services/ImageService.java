package person.birch.recipe.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aleksandr Beryozkin
 */

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
}
