package main.softunigamestore.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private User user;
    private Set<Game> games;

    public Order() {
    }

    @ManyToOne

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> products) {
        this.games = products;
    }
}
