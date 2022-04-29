package app.bootstrap;

import app.DataStorage;
import app.models.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;



@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    DataStorage dataStorage;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.init();
    }

    private void init(){
        for (int i=0; i<10; i++){
            dataStorage.addPassport(Passport.createRandomPassport());
        }
    }
}
