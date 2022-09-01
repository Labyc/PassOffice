package app.bootstrap;

import app.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DataStorage dataStorage;
    @Autowired
    BootStrap(DataStorage dataStorage){
        this.dataStorage = Objects.requireNonNull(dataStorage);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.init();
    }

    private void init(){
        /*for (int i=0; i<10; i++){
            dataStorage.addPassport(PassportRF.createRandomPassport());
        }*/
    }
}
