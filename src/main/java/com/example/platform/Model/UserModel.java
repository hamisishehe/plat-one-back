package com.example.platform.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isLocked; // Indicates if the account is locked

    private boolean isEnabled; // Indicates if the account is enabled

    private Date lastLoginDate; // Track the last login date



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private BalanceModel balance;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PasswordResetTokenModel passwordResetTokenModel;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WalletModel walletModel;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DepositModel> depositModel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TaskModel> taskModels;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminDeposit> adminDeposits;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithdrawModel> withdrawModels;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefWithdrawModel> refWithdrawModels;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestmentModel> investmentModels;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileModel profileModel;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefBalanceModel refBalanceModels;

    // Referral functionality
    @ManyToOne
    @JoinColumn(name = "referred_by_id")
    @JsonIgnore
    private UserModel referredBy; // The user who referred this user

    @OneToMany(mappedBy = "referredBy")
    @JsonIgnore
    private List<UserModel> referrals; // Users referred by this user

    private String referralCode;

    public enum Role {
        USER,
        ADMIN
    }

    @Override
    @JsonIgnore // Don't serialize this field
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role.name());
    }


    @Override
    @JsonIgnore // Don't serialize this field
    public boolean isAccountNonExpired() {
        return true; // You can implement this based on your requirements
    }

    @Override
    @JsonIgnore // Don't serialize this field
    public boolean isAccountNonLocked() {
        return !isLocked; // If isLocked is true, account is considered locked
    }

    @Override
    @JsonIgnore // Don't serialize this field
    public boolean isCredentialsNonExpired() {
        return true; // You can implement this based on your requirements
    }

    @Override
    @JsonIgnore // Don't serialize this field
    public boolean isEnabled() {
        return isEnabled; // Account is enabled or not
    }
}
