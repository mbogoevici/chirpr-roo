package org.jboss.chirpr.roo.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.jboss.chirpr.roo.domain.Profile;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findChirpsByAuthor" })
public class Chirp {

    @ManyToOne
    @NotNull
    private Profile author;

    @NotNull
    @Size(min = 1, max = 140)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    @NotNull
    private Date postedOn;
    
    
    public static TypedQuery<Chirp> findChirpsByAuthorInList(Collection<Profile> authors) {
        if (authors == null) throw new IllegalArgumentException("The authors argument is required");
        EntityManager em = Chirp.entityManager();
        TypedQuery<Chirp> q = em.createQuery("SELECT o FROM Chirp AS o WHERE o.author in (:authors)", Chirp.class);
        q.setParameter("authors", authors);
        return q;
    }
}
