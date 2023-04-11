package com.kou.prolab;

import java.util.ArrayList;
import java.util.List;

public class AyniMeslegiYapanlar {
    private String meslek;
    private List<Kisi> kisiList;

    public AyniMeslegiYapanlar(String meslek) {
        this.meslek = meslek;
        this.kisiList = new ArrayList<>();
    }

    private AyniMeslegiYapanlar(Builder builder) {
        setMeslek(builder.meslek);
        setKisiList(builder.kisiList);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getMeslek() {
        return meslek;
    }

    public void setMeslek(String meslek) {
        this.meslek = meslek;
    }

    public List<Kisi> getKisiList() {
        return kisiList;
    }

    public void setKisiList(List<Kisi> kisiList) {
        this.kisiList = kisiList;
    }


    public static final class Builder {
        private String meslek;
        private List<Kisi> kisiList;

        private Builder() {
        }

        public Builder meslek(String val) {
            meslek = val;
            return this;
        }

        public Builder kisiList(List<Kisi> val) {
            kisiList = val;
            return this;
        }

        public AyniMeslegiYapanlar build() {
            return new AyniMeslegiYapanlar(this);
        }
    }
}
