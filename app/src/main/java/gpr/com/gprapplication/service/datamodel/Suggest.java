package gpr.com.gprapplication.service.datamodel;

public class Suggest extends BaseModel {
	private static final long serialVersionUID = 985469641729938861L;
	private String id;
	private String name;
	private String imageUrl;
	private String location;
	private String speciality;

	public Suggest(String id, String name, String imageUrl, String location, String speciality) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.location = location;
		this.speciality = speciality;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public synchronized String getImageUrl() {
		return imageUrl;
	}

	public synchronized void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
}
