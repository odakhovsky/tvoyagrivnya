package com.tvoyagryvnia.bean.user;

import org.hibernate.validator.constraints.NotEmpty;

public class EditUserPass {
    private int userId;

    private String email;

    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPass;

    public EditUserPass(){}

    public EditUserPass(int userId){
        this.userId = userId;
    }

    public EditUserPass(UserBean user){
        this.userId = user.getId();
        this.email = user.getEmail();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
