package track.messenger.store;

import track.messenger.User;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by denis on 11.04.17.
 */
public class RamUserStore implements UserStore {

    ArrayList<User> users;

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public User updateUser(User user) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if (users.get(i).id == user.id) {
                users.set(i, user);
                return user;
            }
        }
        return null;
    }


    public User getUser(String login, String pass) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if ((users.get(i).login.equals(login)) &&
                (users.get(i).password.equals(pass))) {
                return users.get(i);
            }
        }
        return null;
    }


     public User getUserById(Long id) {
         int i;
         for (i = 0; i < users.size(); i++) {
             if (users.get(i).id == id) {
                 return users.get(i);
             }
         }
         return null;
     }

     public RamUserStore() {
        users = new ArrayList<>();

        //for testing
        User nobody = new User(0, "nobody", "nocity", "nobody");
        User user1 = new User(1, "user1", "Moscow", "pass1");
        User user2 = new User(2, "user2", "Moscow", "pass2");

        addUser(nobody);
        addUser(user1);
        addUser(user2);
     }
}
