package eai_coding;

public class NewContactCheck implements Validable {
    private String name;
    private String phoneNumber;
    private String address;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isValid() {
        return name != null && !name.isEmpty() && phoneNumber
        		!= null && !phoneNumber.isEmpty() && address != null && !address.isEmpty();
    }
}
