package com.kou.prolab;

import java.util.List;

public class AileAgaci {
    private String babaninAdi;
    private String anneninAdi;
    private List<String> cocuklarinAdi;

    private AileAgaci(Builder builder) {
        setBabaninAdi(builder.babaninAdi);
        setAnneninAdi(builder.anneninAdi);
        setCocuklarinAdi(builder.cocuklarinAdi);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getBabaninAdi() {
        return babaninAdi;
    }

    public void setBabaninAdi(String babaninAdi) {
        this.babaninAdi = babaninAdi;
    }

    public String getAnneninAdi() {
        return anneninAdi;
    }

    public void setAnneninAdi(String anneninAdi) {
        this.anneninAdi = anneninAdi;
    }

    public List<String> getCocuklarinAdi() {
        return cocuklarinAdi;
    }

    public void setCocuklarinAdi(List<String> cocuklarinAdi) {
        this.cocuklarinAdi = cocuklarinAdi;
    }


    public static final class Builder {
        private String babaninAdi;
        private String anneninAdi;
        private List<String> cocuklarinAdi;

        private Builder() {
        }

        public Builder babaninAdi(String val) {
            babaninAdi = val;
            return this;
        }

        public Builder anneninAdi(String val) {
            anneninAdi = val;
            return this;
        }

        public Builder cocuklarinAdi(List<String> val) {
            cocuklarinAdi = val;
            return this;
        }

        public AileAgaci build() {
            return new AileAgaci(this);
        }
    }
}
