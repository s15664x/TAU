package guitarcucumber;

public class BuyGuitar {

    private boolean soldStatus = false;
    private boolean canClick = false;
    private boolean clicked = false;
    private boolean loggedIn = false;


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

}
