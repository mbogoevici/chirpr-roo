// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.jboss.chirpr.roo.domain;

import java.lang.String;

privileged aspect Profile_Roo_ToString {
    
    public String Profile.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Followers: ").append(getFollowers() == null ? "null" : getFollowers().size()).append(", ");
        sb.append("Friends: ").append(getFriends() == null ? "null" : getFriends().size()).append(", ");
        sb.append("IdentityUrl: ").append(getIdentityUrl()).append(", ");
        sb.append("Username: ").append(getUsername());
        return sb.toString();
    }
    
}
