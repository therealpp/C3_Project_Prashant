import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @BeforeEach
    public void setup() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant openRestaurant = Mockito.mock(Restaurant.class);
        openRestaurant.openingTime = LocalTime.parse("10:30:00");
        openRestaurant.closingTime = LocalTime.parse("22:00:00");

        LocalTime openTime = LocalTime.parse("11:30:00");
        Mockito.when(openRestaurant.getCurrentTime()).thenReturn(openTime);
        Mockito.when(openRestaurant.isRestaurantOpen()).thenCallRealMethod();

        boolean openStatus;
        openStatus = openRestaurant.isRestaurantOpen();

        assertEquals(openStatus, true);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant closedRestaurant = Mockito.mock(Restaurant.class);
        closedRestaurant.openingTime = LocalTime.parse("10:30:00");
        closedRestaurant.closingTime = LocalTime.parse("22:00:00");

        LocalTime closedTime = LocalTime.parse("23:30:00");
        Mockito.when(closedRestaurant.getCurrentTime()).thenReturn(closedTime);
        Mockito.when(closedRestaurant.isRestaurantOpen()).thenCallRealMethod();

        boolean closedStatus;
        closedStatus = closedRestaurant.isRestaurantOpen();

        assertEquals(closedStatus, false);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}