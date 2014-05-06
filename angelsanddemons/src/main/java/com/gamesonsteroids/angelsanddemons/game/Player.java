package com.gamesonsteroids.angelsanddemons.game;

public class Player {

    private CharSequence name;
    private Role role;

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
