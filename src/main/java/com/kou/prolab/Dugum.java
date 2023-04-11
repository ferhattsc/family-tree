package com.kou.prolab;

import java.util.ArrayList;
import java.util.List;

public class Dugum {
    private Kisi own;
    private List<Kisi> children;

    public Dugum() {
    }

    public Dugum(Kisi own) {
        this.own = own;
        this.children = new ArrayList<>();;
    }

    private Dugum(Builder builder) {
        setOwn(builder.own);
        setChildren(builder.children);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Kisi getOwn() {
        return own;
    }

    public void setOwn(Kisi own) {
        this.own = own;
    }

    public List<Kisi> getChildren() {
        return children;
    }

    public void setChildren(List<Kisi> children) {
        this.children = children;
    }


    public static final class Builder {
        private Kisi own;
        private List<Kisi> children;

        private Builder() {
        }

        public Builder own(Kisi val) {
            own = val;
            return this;
        }

        public Builder children() {
            children = new ArrayList<>();;
            return this;
        }

        public Dugum build() {
            return new Dugum(this);
        }
    }
}
