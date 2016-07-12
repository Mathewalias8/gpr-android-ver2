package gpr.com.gprapplication.service.datamodel;

public enum FilterType {
	SPECIALITY("Speciality"), SUPER_SPECIALITY("Super Speciality"), COUNTRY("Country");
	String name;
	FilterType(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
