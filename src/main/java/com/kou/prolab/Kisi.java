package com.kou.prolab;

import java.util.Date;

public class Kisi {
    private Long tcNo;
    private String ad;
    private String soyad;
    private String esi;
    private Date dogumTarihi;
    private String anneAdi;
    private String babaAdi;
    private String kanGrubu;
    private String meslekOzellikleri;
    private String kizlikSoyadi;
    private String cinsiyet;
    private String medeniHali;

    private Kisi(Builder builder) {
        setTcNo(builder.tcNo);
        setAd(builder.ad);
        setSoyad(builder.soyad);
        setEsi(builder.esi);
        setDogumTarihi(builder.dogumTarihi);
        setAnneAdi(builder.anneAdi);
        setBabaAdi(builder.babaAdi);
        setKanGrubu(builder.kanGrubu);
        setMeslekOzellikleri(builder.meslekOzellikleri);
        setKizlikSoyadi(builder.kizlikSoyadi);
        setCinsiyet(builder.cinsiyet);
        setMedeniHali(builder.medeniHali);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getTcNo() {
        return tcNo;
    }

    public void setTcNo(Long tcNo) {
        this.tcNo = tcNo;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEsi() {
        return esi;
    }

    public void setEsi(String esi) {
        this.esi = esi;
    }

    public Date getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(Date dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getAnneAdi() {
        return anneAdi;
    }

    public void setAnneAdi(String anneAdi) {
        this.anneAdi = anneAdi;
    }

    public String getBabaAdi() {
        return babaAdi;
    }

    public void setBabaAdi(String babaAdi) {
        this.babaAdi = babaAdi;
    }

    public String getKanGrubu() {
        return kanGrubu;
    }

    public void setKanGrubu(String kanGrubu) {
        this.kanGrubu = kanGrubu;
    }

    public String getMeslekOzellikleri() {
        return meslekOzellikleri;
    }

    public void setMeslekOzellikleri(String meslekOzellikleri) {
        this.meslekOzellikleri = meslekOzellikleri;
    }

    public String getKizlikSoyadi() {
        return kizlikSoyadi;
    }

    public void setKizlikSoyadi(String kizlikSoyadi) {
        this.kizlikSoyadi = kizlikSoyadi;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getMedeniHali() {
        return medeniHali;
    }

    public void setMedeniHali(String medeniHali) {
        this.medeniHali = medeniHali;
    }


    public static final class Builder {
        private Long tcNo;
        private String ad;
        private String soyad;
        private String esi;
        private Date dogumTarihi;
        private String anneAdi;
        private String babaAdi;
        private String kanGrubu;
        private String meslekOzellikleri;
        private String kizlikSoyadi;
        private String cinsiyet;
        private String medeniHali;

        private Builder() {
        }

        public Builder tcNo(Long val) {
            tcNo = val;
            return this;
        }

        public Builder ad(String val) {
            ad = val;
            return this;
        }

        public Builder soyad(String val) {
            soyad = val;
            return this;
        }

        public Builder esi(String val) {
            esi = val;
            return this;
        }

        public Builder dogumTarihi(Date val) {
            dogumTarihi = val;
            return this;
        }

        public Builder anneAdi(String val) {
            anneAdi = val;
            return this;
        }

        public Builder babaAdi(String val) {
            babaAdi = val;
            return this;
        }

        public Builder kanGrubu(String val) {
            kanGrubu = val;
            return this;
        }

        public Builder meslekOzellikleri(String val) {
            meslekOzellikleri = val;
            return this;
        }

        public Builder kizlikSoyadi(String val) {
            kizlikSoyadi = val;
            return this;
        }

        public Builder cinsiyet(String val) {
            cinsiyet = val;
            return this;
        }

        public Builder medeniHali(String val) {
            medeniHali = val;
            return this;
        }

        public Kisi build() {
            return new Kisi(this);
        }
    }
}
