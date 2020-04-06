package db.models;

public class ImagePath {

	private int id;
	private String imageSource;
	private int postId;
	
	public ImagePath(int id, String imageSource, int postId) {
		this.id = id;
		this.imageSource = imageSource;
		this.postId = postId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
