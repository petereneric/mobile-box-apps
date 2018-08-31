package com.example.ericschumacher.bouncer.Objects.Collection;

public class Rewardmodel {
    private int Id;
    private double PaymentReuse;
    private double PaymentRecycling;

    public Rewardmodel(int id, double paymentReuse, double paymentRecycling) {
        Id = id;
        PaymentReuse = paymentReuse;
        PaymentRecycling = paymentRecycling;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getPaymentReuse() {
        return PaymentReuse;
    }

    public void setPaymentReuse(double paymentReuse) {
        PaymentReuse = paymentReuse;
    }

    public double getPaymentRecycling() {
        return PaymentRecycling;
    }

    public void setPaymentRecycling(double paymentRecycling) {
        PaymentRecycling = paymentRecycling;
    }
}
