import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader{

	public static BufferedImage loadImage(String path){

		try{
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (Exception ex){
			ex.printStackTrace();
			System.out.println("image loading error");
			System.exit(0);
		}

		return null;
		
	}

}
