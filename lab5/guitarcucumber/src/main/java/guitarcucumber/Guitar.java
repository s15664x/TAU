package guitarcucumber;

public class Guitar {

	private Long id;

	private String manufacturer;
	private String model;
	private Integer numberOfStrings;
    private boolean soldStatus = false;
    private boolean canClick = false;
    private boolean clicked = false;
    private boolean loggedIn = false;


	public Guitar() {
	}

	public Guitar(String manufacturer, String model, Integer numberOfStrings) {
		this.id = null;
		this.manufacturer = manufacturer;
		this.model = model;
		this.numberOfStrings = numberOfStrings;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String mf) {
		this.manufacturer = mf;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String m) {
		this.model = m;
	}

	public Integer getNumberOfStrings() {
		return numberOfStrings;
	}

	public void setNumberOfStrings(int ns) {
		this.numberOfStrings = ns;
	}


    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public boolean getSoldStatus() {
        return this.soldStatus;
    }

    public boolean canClick() {
        return this.canClick;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setCanClick(boolean cc) {
        this.canClick = cc;
    }

    public void setSoldStatus(boolean status) {
        this.soldStatus = status;
    }

    public void setClickStatus(boolean clicked) {
        this.clicked = clicked;
    }

	@Override
	public boolean equals(Object o) {
		Guitar other = (Guitar) o;
		boolean ret = other.getManufacturer().equals(this.getManufacturer()) &&
				other.getModel().equals(this.getModel()) &&
				((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) &&
				((other.getNumberOfStrings() == this.getNumberOfStrings()) || (other.getNumberOfStrings().intValue() == this.getNumberOfStrings().intValue()));
		return ret;
	}

	@Override
	public String toString() {
		return "[" + id+ ", "
			 + manufacturer + ", " + model + ", "+ numberOfStrings + "]";
	}
}
