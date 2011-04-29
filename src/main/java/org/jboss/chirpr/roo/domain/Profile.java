package org.jboss.chirpr.roo.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity
public class Profile {

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String identityUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<org.jboss.chirpr.roo.domain.Profile> friends = new HashSet<org.jboss.chirpr.roo.domain.Profile>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "friends")
    private Set<org.jboss.chirpr.roo.domain.Profile> followers = new HashSet<org.jboss.chirpr.roo.domain.Profile>();
    
    
    public void follow(Profile profile) {
    	this.friends.add(profile);
    	profile.followers.add(this);
    }
    
    public void unfollow(Profile profile) {
    	this.friends.remove(profile);
    	profile.followers.remove(this);
    }
}
