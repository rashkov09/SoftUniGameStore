package main.softunigamestore.services.serviceImpl;

import main.softunigamestore.entities.Game;
import main.softunigamestore.repositories.OrderRepository;
import main.softunigamestore.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final List<Game> shoppingCart;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCart = new ArrayList<>();
    }


    @Override
    public void addToShoppingCart(Game game) {
        shoppingCart.add(game);
    }
}
