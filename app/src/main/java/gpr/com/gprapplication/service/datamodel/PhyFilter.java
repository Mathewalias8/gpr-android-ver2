package gpr.com.gprapplication.service.datamodel;


public class PhyFilter extends BaseModel {
	private static final long serialVersionUID = 5147729838692822485L;
	private FilterType filterType;
	private int id;
	private String value;

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}
	
	public void setFilterType(String filterTypeStr) {
		filterType = FilterType.valueOf(filterTypeStr);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
