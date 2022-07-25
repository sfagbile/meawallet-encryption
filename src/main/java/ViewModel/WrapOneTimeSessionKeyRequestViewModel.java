package ViewModel;

public class WrapOneTimeSessionKeyRequestViewModel {
    public String Modulus;
    public String Exponent;

    public String getExponent() {
        return Exponent;
    }

    public void setExponent(String exponent) {
        Exponent = exponent;
    }

    public String getModulus() {
        return Modulus;
    }

    public void setModulus(String modulus) {
        Modulus = modulus;
    }
}
