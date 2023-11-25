package com.example.gestioncontactsfinal.Model;

import java.io.Serializable;

public class Contact implements Serializable {
    private String nom;
    private String prenom;
    private String service;
    private String email;
    private String tel;
    private boolean favorite;

    public Contact(String nom, String prenom, String service, String email, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.service = service;
        this.email = email;
        this.tel = tel;
    }

    public Contact(String nom, String prenom, String service, String email, String tel, boolean favorite) {
        this.nom = nom;
        this.prenom = prenom;
        this.service = service;
        this.email = email;
        this.tel = tel;
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Contact{" +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", service='" + service + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
