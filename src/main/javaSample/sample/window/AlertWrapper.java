package javaSample.sample.window;

public class AlertWrapper {

    private Alert alert;

    public AlertWrapper(Alert alert){
        this.alert=alert;
    }

    public Alert getAlert() {
        return alert;
    }

    public void display(String popUpWindowText, String labelText, String buttonText) {
        alert.display(popUpWindowText, labelText, buttonText);
    }
}
