package com.chareesa.exam3.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Size(min = 2, max = 50)
    private String name;
    private String alias;
    private String email;
    private String password;
    private String description;

    //invites
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_invites",
            joinColumns=@JoinColumn(name="myself_Id"),
            inverseJoinColumns=@JoinColumn(name="their_Id")
    )
    private List<User> invitations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_invites",
            joinColumns=@JoinColumn(name="myself_Id"),
            inverseJoinColumns=@JoinColumn(name="their_Id")
    )
    private List<User> invitees;

    //networks
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_network",
            joinColumns=@JoinColumn(name="my_Id"),
            inverseJoinColumns=@JoinColumn(name="user_Id")
    )
    private List<User> peopleInNetwork;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_network",
            joinColumns=@JoinColumn(name="user_Id"),
            inverseJoinColumns=@JoinColumn(name="my_Id")
    )
    private List<User> inNetworkOf;

    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    //Getters and Setters

    public List<User> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<User> invitees) {
        this.invitees = invitees;
    }

    public List<User> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<User> invitations) {
        this.invitations = invitations;
    }

    public List<User> getPeopleInNetwork() {
        return peopleInNetwork;
    }

    public void setPeopleInNetwork(List<User> peopleInNetwork) {
        this.peopleInNetwork = peopleInNetwork;
    }

    public List<User> getInNetworkOf() {
        return inNetworkOf;
    }

    public void setInNetworkOf(List<User> inNetworkOf) {
        this.inNetworkOf = inNetworkOf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}