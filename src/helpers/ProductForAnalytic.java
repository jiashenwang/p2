package helpers;

public class ProductForAnalytic {
	
	private String id;
	
	
	private String name;
	
	private String total;

	/**
	 * @param id
	 * @param cid
	 * @param name
	 * @param sKU
	 * @param price
	 */
	public ProductForAnalytic(String id, String name, String total) {
		this.id = id;
		this.name = name;
		this.total = total;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public String getTotal() {
		return total;
	}
}