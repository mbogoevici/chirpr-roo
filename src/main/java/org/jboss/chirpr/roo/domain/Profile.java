package org.jboss.chirpr.roo.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProfilesByUsername", "findProfilesByIdentityUrl" })
public class Profile {

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String identityUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<org.jboss.chirpr.roo.domain.Profile> friends = new HashSet<org.jboss.chirpr.roo.domain.Profile>();

    @ManyToMany(mappedBy = "friends", fetch = FetchType.EAGER)
    private Set<org.jboss.chirpr.roo.domain.Profile> followers = new HashSet<org.jboss.chirpr.roo.domain.Profile>();

    public void follow(Profile profile) {
        this.friends.add(profile);
        profile.followers.add(this);
    }
    
    public boolean isFollowing(Profile profile) {
    	for (Profile friend:this.friends) {
    		if (friend.username.equals(profile.username)) {
    			return true;
    		}
    	}
    	return false;
    }
    
  
    public boolean isFollowedBy(Profile profile) {
    	for (Profile friend:this.followers) {
    		if (friend.username.equals(profile.username)) {
    			return true;
    		}
    	}
    	return false;
    }
        
    public void unfollow(Profile profile) {
    	Iterator<Profile> it = this.friends.iterator();
    	while (it.hasNext()) {
    		Profile friend = it.next();
    		if (friend.username.equals(profile.username)) {
    			it.remove();
    		}   			
    	}
        it = profile.followers.iterator();
        while (it.hasNext()) {
        	Profile follower = it.next();
        	if (follower.username.equals(this.username)) {
        		it.remove();
        	}
        }
    }
}
