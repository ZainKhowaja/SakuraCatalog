package com.app.sakura.bootstrapper;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.TypeDetail;
import com.app.sakura.repository.FilterRepository;
import com.app.sakura.repository.ManufacturerRepository;
import com.app.sakura.repository.TypeDetailRepository;
import com.app.sakura.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BootstrapperConfig {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeDetailRepository typeDetailRepository;

    @PostConstruct
    public void fillData(){
        if(!FileUtil.doesDbExists()) {
            loadDefaultFilterType();
            loadDefaultManufacturerType();
            loadTypeDetails();
        }
    }

    private void loadTypeDetails() {
        String typeDetailsName[] = {""};

        List<TypeDetail> typeDetails = Arrays.asList(typeDetailsName).stream()
                .map(x -> new TypeDetail(x))
                .collect(Collectors.toList());

        typeDetailRepository.saveAll(typeDetails);
    }

    private void loadDefaultFilterType() {
        String filterNames[] = {"AIR FILTERS" , "ELEMENT PAPER TYPE", "SPIN ON"};

        List<Filter> filters = Arrays.asList(filterNames).stream()
                .map(x -> new Filter(x))
                .collect(Collectors.toList());

        filterRepository.saveAll(filters);
    }


    private void loadDefaultManufacturerType() {
        String manufactureName[] = { "FUJI","QAIM","GUARD","LONG LIFE","PAKWIN","REX","ZIXS","SAKURA","FLEET GUARD","CLEAN","DONALDSON","MICRO","VIC","LINCLON"
                ,"HONDA","SUZUKI","TOYOTA","HINO","ISUZU","FAW","MASTER","SINO","HYNDAI","KIA","MAZDA","PERKINS","CASE","FIAT","FORD"
        };
        List<Manufacturer> manufacturers = Arrays.asList(manufactureName).stream()
                .map(x -> new Manufacturer(x))
                .collect(Collectors.toList());

        manufacturerRepository.saveAll(manufacturers);
    }
}
